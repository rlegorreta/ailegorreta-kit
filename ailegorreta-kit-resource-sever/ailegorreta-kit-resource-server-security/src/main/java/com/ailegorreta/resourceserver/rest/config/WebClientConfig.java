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
package com.ailegorreta.resourceserver.rest.config;

import com.ailegorreta.resourceserver.rest.WebClientFilter;
import com.ailegorreta.resourceserver.security.config.SecurityServiceConfig;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Creates a new instance of WebClient.
 *
 * Bean for client_credentials grant type
 *
 * @project: ailegorreta-kit-resource-server-security
 * @author rlh
 * @date August 2023
 */
public class WebClientConfig {

    /**
     * Client-credential version. Always use this method since we are host-host communication
     *
     * load balanced version
     */
    public WebClient webClientClientCredentials(WebClient.Builder webClientBuilder,
                                                SecurityServiceConfig securityServiceConfig,
                                                String hostName,
                                                ReactiveOAuth2AuthorizedClientManager clientManager) {
        var oauth2 = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientManager);

        oauth2.setDefaultClientRegistrationId(securityServiceConfig.getSecurityClientId()
                                                                   .get(hostName) + "-client-credentials");

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
                                                         String hostName,
                                                         ReactiveOAuth2AuthorizedClientManager clientManager) {

        var oauth2 = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientManager);

        oauth2.setDefaultClientRegistrationId(securityServiceConfig.getSecurityClientId()
                                                                   .get(hostName) + "-client-credentials");

        return  webClientBuilder.filter(oauth2)
                                .build();
    }

    public AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager getClientManager(SecurityServiceConfig securityServiceConfig,
                                                                                         String hostName,
                                                                                         ReactiveClientRegistrationRepository clientRegistrationRepository) {
        var clientRegistration = clientRegistrationRepository.findByRegistrationId(
                                                              securityServiceConfig.getSecurityClientId()
                                                                                   .get(hostName) + "-client-credentials").block();
        var clients = new InMemoryReactiveClientRegistrationRepository(clientRegistration);
        var clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clients);

        return new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clients, clientService);
    }

    public ReactiveOAuth2AuthorizedClientManager getAuthorizedClientManager( SecurityServiceConfig securityServiceConfig,
                                                                     String hostName,
                                                                     ReactiveClientRegistrationRepository clientRegistrationRepository,
                                                                     ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {

        var authorizedClientProvider = ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                                                                                    .authorizationCode()
                                                                                    .refreshToken()
                                                                                    .clientCredentials()
                                                                                    .build();
        var clientRegistration = clientRegistrationRepository.findByRegistrationId(
                                                              securityServiceConfig.getSecurityClientId()
                                                                                   .get(hostName) + "-client-credentials").block();
        var clients = new InMemoryReactiveClientRegistrationRepository(clientRegistration);
        var authorizedClientManager = new DefaultReactiveOAuth2AuthorizedClientManager(clients, authorizedClientRepository);

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
}
