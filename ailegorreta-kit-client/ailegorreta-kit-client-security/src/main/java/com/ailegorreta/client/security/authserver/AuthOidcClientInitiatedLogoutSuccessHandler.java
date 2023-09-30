/* Copyright (c) 2023, LegoSoft Soluciones, S.C.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 *  AuthOidcClientInitiatedLogoutSuccessHandler.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.security.authserver;

import com.ailegorreta.client.security.utils.HasLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This class is because the Spring Authorization server 1.0.0 has not implemented the end_session_endpoint
 * at this date. see:
 * https://github.com/spring-projects/spring-authorization-server/issues/266
 *
 * Meanwhile, this class implements de OidcClientInitiatedLogoutSuccessHandler class and when it looks for
 * the metadata in the provider details of the Client registration is returns the URI from the issuer URI.
 *
 * @project: ailegorreta-kit-client-security
 * @author: rlh
 * @date: September 2023
 */
public final class AuthOidcClientInitiatedLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
                                                               implements HasLogger {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final String issuerUri;

    private String postLogoutRedirectUri;

    public AuthOidcClientInitiatedLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository,
                                                       String issuerUri) {
        Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
        Assert.notNull(issuerUri, "issuerUri cannot be null");
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.issuerUri = issuerUri;
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String targetUrl = null;

        if (authentication instanceof OAuth2AuthenticationToken && authentication.getPrincipal() instanceof OidcUser) {
            String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
            ClientRegistration clientRegistration = this.clientRegistrationRepository
                                                        .findByRegistrationId(registrationId);
            URI endSessionEndpoint = this.endSessionEndpoint(clientRegistration);

            if (endSessionEndpoint != null) {
                String idToken = idToken(authentication);
                String postLogoutRedirectUri = postLogoutRedirectUri(request, clientRegistration);

                targetUrl = endpointUri(endSessionEndpoint, idToken, postLogoutRedirectUri);
            }
        }
        return (targetUrl != null) ? targetUrl : super.determineTargetUrl(request, response);
    }

    private URI endSessionEndpoint(ClientRegistration clientRegistration) {
        if (clientRegistration != null) {
            ClientRegistration.ProviderDetails providerDetails = clientRegistration.getProviderDetails();
            Object endSessionEndpoint = providerDetails.getConfigurationMetadata().get("end_session_endpoint");


            if (endSessionEndpoint != null) {
                getLogger().info("The end_session_endpoint for the issuer is {}", endSessionEndpoint);
                getLogger().info("It is NOT used because still a bug in Spring Authorization Server");
                getLogger().info("Use hard coded '/logout end point.");
                // TODO there is a bug in the Spring Authorization server 1.0.0 where the end_session_endpoint is not
                // TODO implemented yet. So we need to fix it latter. Must be 'logout' path
                // TODO see LogoutUtil class java class
                // return URI.create(endSessionEndpoint.toString());
                return URI.create(issuerUri + "/logout");
            } else
                return URI.create(issuerUri + "/logout");
        }
        return null;
    }

    private String idToken(Authentication authentication) {
        return ((OidcUser) authentication.getPrincipal()).getIdToken().getTokenValue();
    }

    private String postLogoutRedirectUri(HttpServletRequest request, ClientRegistration clientRegistration) {
        if (this.postLogoutRedirectUri == null) {
            return null;
        }
        // @formatter:off
        UriComponents uriComponents = UriComponentsBuilder
                                        .fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                                        .replacePath(request.getContextPath())
                                        .replaceQuery(null)
                                        .fragment(null)
                                        .build();

        Map<String, String> uriVariables = new HashMap<>();
        String scheme = uriComponents.getScheme();
        uriVariables.put("baseScheme", (scheme != null) ? scheme : "");
        uriVariables.put("baseUrl", uriComponents.toUriString());

        String host = uriComponents.getHost();
        uriVariables.put("baseHost", (host != null) ? host : "");

        String path = uriComponents.getPath();
        uriVariables.put("basePath", (path != null) ? path : "");

        int port = uriComponents.getPort();
        uriVariables.put("basePort", (port == -1) ? "" : ":" + port);

        uriVariables.put("registrationId", clientRegistration.getRegistrationId());

        return UriComponentsBuilder.fromUriString(this.postLogoutRedirectUri)
                .buildAndExpand(uriVariables)
                .toUriString();
        // @formatter:on
    }

    private String endpointUri(URI endSessionEndpoint, String idToken, String postLogoutRedirectUri) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(endSessionEndpoint);
        builder.queryParam("id_token_hint", idToken);
        if (postLogoutRedirectUri != null) {
            builder.queryParam("post_logout_redirect_uri", postLogoutRedirectUri);
        }
        // @formatter:off
        return builder.encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
        // @formatter:on
    }

    /**
     * Set the post logout redirect uri template.
     *
     * <br />
     * The supported uri template variables are: {@code {baseScheme}}, {@code {baseHost}},
     * {@code {basePort}} and {@code {basePath}}.
     *
     * <br />
     * <b>NOTE:</b> {@code "{baseUrl}"} is also supported, which is the same as
     * {@code "{baseScheme}://{baseHost}{basePort}{basePath}"}
     *
     * <pre>
     * 	handler.setPostLogoutRedirectUri("{baseUrl}");
     * </pre>
     *
     * will make so that {@code post_logout_redirect_uri} will be set to the base url for
     * the client application.
     * @param postLogoutRedirectUri - A template for creating the
     * {@code post_logout_redirect_uri} query parameter
     * @since 5.3
     */
    public void setPostLogoutRedirectUri(String postLogoutRedirectUri) {
        Assert.notNull(postLogoutRedirectUri, "postLogoutRedirectUri cannot be null");
        this.postLogoutRedirectUri = postLogoutRedirectUri;
    }

    @Override
    public Logger getLogger() { return HasLogger.DefaultImpls.getLogger(this); }

}
