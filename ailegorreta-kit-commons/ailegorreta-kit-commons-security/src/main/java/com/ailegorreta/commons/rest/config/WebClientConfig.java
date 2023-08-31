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
 *  WebClientConfig.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.commons.rest.config;

import com.ailegorreta.commons.rest.WebClientFilter;
import com.ailegorreta.commons.security.config.SecurityServiceConfig;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Creates a new instance of WebClient.
 *
 * One bean for client_credentials grant type and
 * a second bean for authentication_code
 *
 * @project: ailegorreta-kit-commons-security-rest
 * @author rlh
 * @date June 2023
 */
public class WebClientConfig {

    /**
     * Client-credential version. Very rare use this method since we are un the UI interface, so normally
     * we use the authorization-code grant type.
     *
     */
    public WebClient webClientClientCredentials(WebClient.Builder webClientBuilder,
                                                SecurityServiceConfig securityServiceConfig,
                                                ReactiveOAuth2AuthorizedClientManager clientManager) {
        var oauth2 = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientManager);

        oauth2.setDefaultClientRegistrationId(securityServiceConfig.getSecurityClientId() + "-client-credentials");

        return  webClientBuilder.filter(WebClientFilter.errorHandler())
                                .filter(WebClientFilter.logRequest())
                                .filter(WebClientFilter.logResponse())
                                .filter(oauth2)
                                .build();
    }

    /**
     * Client-credential version. This is no filters added for logging operations and error handlers
     */
    public WebClient webClientClientCredentialsNoFilters(WebClient.Builder webClientBuilder,
                                                         SecurityServiceConfig securityServiceConfig,
                                                         OAuth2AuthorizedClientManager clientManager) {
        var oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientManager);

        oauth2.setDefaultClientRegistrationId(securityServiceConfig.getSecurityClientId() + "-client-credentials");

        return  WebClient.builder()
                        .apply(oauth2.oauth2Configuration())
                        .build();
    }

    /**
     *  Authorization_code super class to generate the webClient @Bean for UI microservices
     *
     */
    public WebClient webClientAuthenticationCode(WebClient.Builder webClientBuilder,
                                                 SecurityServiceConfig securityServiceConfig,
                                                 OAuth2AuthorizedClientManager authorizedClientManager) {
        var oauth2Client = new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);

        oauth2Client.setDefaultOAuth2AuthorizedClient(true);
        oauth2Client.setDefaultClientRegistrationId(securityServiceConfig.getSecurityClientId() + "-oidc");

        return webClientBuilder.filter(WebClientFilter.errorHandler())
                               .filter(WebClientFilter.logRequest())
                               .filter(WebClientFilter.logResponse())
                               .apply(oauth2Client.oauth2Configuration())
                               .build();
    }

    /**
     *  Authorization_code super class to generate the webClient @Bean for UI microservices
     * No Filters
     */
    public WebClient webClientAuthenticationCodeNoFilters(WebClient.Builder webClientBuilder,
                                                          SecurityServiceConfig securityServiceConfig,
                                                          OAuth2AuthorizedClientManager authorizedClientManager) {
        var oauth2Client = new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);

        oauth2Client.setDefaultOAuth2AuthorizedClient(true);
        oauth2Client.setDefaultClientRegistrationId(securityServiceConfig.getSecurityClientId() + "-oidc");

        return webClientBuilder.apply(oauth2Client.oauth2Configuration())
                               .build();
    }

    public AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager getClientManager(SecurityServiceConfig securityServiceConfig,
                                                                                         ReactiveClientRegistrationRepository clientRegistrationRepository,
                                                                                         String grantType) {
        var clientRegistration = clientRegistrationRepository.findByRegistrationId(securityServiceConfig.getSecurityClientId() + "-" + grantType).block();
        var clients = new InMemoryReactiveClientRegistrationRepository(clientRegistration);
        var clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clients);

        return new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clients, clientService);
    }

    public OAuth2AuthorizedClientManager getAuthorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
                                                                    OAuth2AuthorizedClientRepository authorizedClientRepository) {

        var authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                                                                            .authorizationCode()
                                                                            .refreshToken()
                                                                            .clientCredentials()
                                                                            .build();
        var authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
}
