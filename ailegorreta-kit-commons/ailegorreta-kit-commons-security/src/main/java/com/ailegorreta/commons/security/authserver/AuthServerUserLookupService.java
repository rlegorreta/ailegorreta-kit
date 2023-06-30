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
 *  AuthServerUser.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.commons.security.authserver;

import com.ailegorreta.commons.rest.exception.ServiceException;
import com.ailegorreta.commons.security.config.SecurityServiceConfig;
import com.ailegorreta.commons.security.service.UserLookupService;
import com.ailegorreta.commons.security.utils.HasLogger;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.*;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

/**
 * Implementation of {@link UserLookupService} that uses the Auth Server REST API to look up users based on a query
 * string.
 *
 * The class is a perfect example how to call a resource server (i.e., iamserverrepo in two way):
 * (a) Machine-machine communication. That is without the end user participation. In this cas we register a
 *     client with the grant type: client_credentials.
 * (b) Escenario when user intervention exists, in other words a login has been done and this class configured
 *     the WebClient instance with a grant type: authorization_code
 *
 * To learn more see tutorial: https://www.baeldung.com/spring-webclient-oauth2
 *
 * The application must define in three properties:
 * - security.iam.provider-uri
 *
 * In other words, must implement SecurityServiceConfig interface in ServiceConfig
 *
 * @project ailegorreta-kit-commons-security
 * @author rlh
 * @date June 2023
 */
public class AuthServerUserLookupService implements UserLookupService, HasLogger {
    protected final SecurityServiceConfig serviceConfig;
    protected final WebClient client_credentials;
    protected final WebClient authorization_code;
    private final String restApiUri;

    public AuthServerUserLookupService(SecurityServiceConfig serviceConfig,
                                       WebClient client_credentials,
                                       WebClient authorization_code ) {
        this.serviceConfig = serviceConfig;
        this.client_credentials = client_credentials;
        this.authorization_code = authorization_code;
        this.restApiUri = serviceConfig.getSecurityIAMProvider();
    }

    @Override
    public Mono<List<OidcUser>> findUsers(@Nullable String query, Boolean batch) {
        if (batch == null) batch = true;
        if (query == null || query.isBlank())
            return Mono.just(Collections.emptyList());

        try {
            if (batch)
                return client_credentials.get()
                        .uri(uri().path("/iam/facultad/usuarios/nombreusuario").queryParam("nombreUsuario", query).build().toUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(AuthServerUser[].class)
                        .switchIfEmpty(Mono.just(new AuthServerUser[0]))
                        .doOnNext(s -> getLogger().debug("Found users: {}", Arrays.toString(s)))
                        .map(List::of);
            else
                return authorization_code.get()
                        .uri(uri().path("/iam/facultad/usuarios/nombreusuario").queryParam("nombreUsuario", query).build().toUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .attributes(clientRegistrationId(serviceConfig.getSecurityClientId() + "-authentication-code"))
                        .retrieve()
                        .bodyToMono(AuthServerUser[].class)
                        .switchIfEmpty(Mono.just(new AuthServerUser[0]))
                        .doOnNext(s -> getLogger().debug("Found users: {}", Arrays.toString(s)))
                        .map(List::of);
        } catch (WebClientResponseException we) {
            throw new ServiceException(we.getMessage(), we.getStatusCode().value());
        }
    }

    @Override
    public Mono<OidcUser> findByPrincipalName(String name, Boolean batch) {
        if (batch == null) batch = true;
        try {
            if (batch)
                return client_credentials.get()
                        .uri(uri().path("/iam/facultad/usuario/by/nombreusuario").queryParam("nombreUsuario",name).build().toUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(AuthServerUser.class)
                        .doOnNext(s -> getLogger().debug("Found user: {}", s))
                        .cast(OidcUser.class);
            else
                return authorization_code.get()
                        .uri(uri().path("/iam/facultad/usuario/by/nombreusuario").queryParam("nombreUsuario", name).build().toUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .attributes(clientRegistrationId(serviceConfig.getSecurityClientId() + "-authentication-code"))
                        .retrieve()
                        .bodyToMono(AuthServerUser.class)
                        .doOnNext(s -> getLogger().debug("Found user: {}", s))
                        .cast(OidcUser.class);
        } catch (WebClientResponseException we) {
            throw new ServiceException(we.getMessage(), we.getStatusCode().value());
        }

    }

    protected UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(restApiUri);
    }

    @Override
    public Logger getLogger() { return HasLogger.DefaultImpls.getLogger(this); }

}

