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
 *  SecurityConfig.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.commons.security.config;

import com.ailegorreta.commons.security.authserver.AuthServerAuthoritiesMapper;
import com.ailegorreta.commons.security.authserver.AuthServerCurrentSession;
import com.ailegorreta.commons.security.repository.SessionRepository;
import com.ailegorreta.commons.security.repository.SessionRepositoryListener;
import com.ailegorreta.commons.security.service.CurrentSession;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * Security configuration for valid URLs in the Application. And also reads what are the valid
 * User and Roles.
 *
 * This is an abstract class if the user want to modify something in the security, but it is
 * needed to call super in order to work correctly
 *
 * @project ailegorreta-kit-commons-security
 * @author rlh
 * @Date: June 2023
 */
public class SecurityConfig {

    @Bean
    public GrantedAuthoritiesMapper grantedAuthoritiesMappes() { return new AuthServerAuthoritiesMapper();}

    @Bean
    public CurrentSession currentSession() { return new AuthServerCurrentSession();}

    @Bean
    public SessionRepository sessionRepo() { return new SessionRepository(); }

    @Bean
    public ServletListenerRegistrationBean<SessionRepositoryListener> sessionRepositoryListener(SessionRepository sessionRepository) {
        var bean = new ServletListenerRegistrationBean<SessionRepositoryListener>();

        bean.setListener(new SessionRepositoryListener(sessionRepository));

        return bean;
    }

    @Bean
    ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new WebSessionServerOAuth2AuthorizedClientRepository();
    }

    protected SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                               ReactiveClientRegistrationRepository clientRegistrationRepository,
                                                               String publicPath) {
        // Configure your static resources with public access before calling
        // super.configure(HttpSecurity) as it adds final anyRequest matcher
        return http

                .authorizeExchange(exchange -> exchange
                       .pathMatchers(  "/*.css", "/*.js", "/favicon.ico", "/images/**","/assets/**","/webjars/**",
                                      "/oauth2/**", "/logged-out/**", "/session-expired/**",
                                      "/login/*+","/authorize/**","/authorized/**",
                                      "/" + publicPath + "/**",  // this prefix is to avoid login 302 redirection
                                      "/actuator/**").permitAll()
                        /* ^ Allows unauthenticated access to the SPA static resources */
                        .pathMatchers(HttpMethod.POST, "/logout/**","/logged-out/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/test/**","/gateway/**").permitAll()
                        .anyExchange().authenticated()
                )
                //.exceptionHandling(exceptionHandling -> exceptionHandling
                //         .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
                // ^ when an exception is thrown because a user is not authenticated, it replies with an HTTP 401 response

                // Enable OAuth2 login
                .oauth2Login(Customizer.withDefaults())
                .oauth2Login(oauth2Login -> oauth2Login
                               .clientRegistrationRepository(clientRegistrationRepository)
                )
                // Configure logout
                .logout(logout ->
                        logout
                                // Enable OIDC logout (requires that we use the 'openid' scope when authenticating)
                                .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository))
                )
                // .csrf(csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
                // ^ Uses a cookie-based strategy for exchanging CSRF tokens with the frontend. This means that the
                // gateway will only receive call from UI microservices and not from back-end microservice, i.e., they
                // will call between them directly.
                // note recommended, to disable csrf protection, but it is a TODO with Vaadin
                // To disable the csrf we can use:
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .build();
    }

    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        var oidcLogoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/logged-out");

        return oidcLogoutSuccessHandler;
    }

    /**
     * At the moment, CookieServerCsrfTokenRepository does not ensure a subscription to CsrfToken, so we must
     * explicitly provide a workaround in a Web-Filter bean. This problem should be solved in future versions of
     * Spring Security (see issue 5766 on GitHub: https://mng.bz/XW89)
     */
    @Bean
    WebFilter csrfWebFilter() {
        // Required because of https://github.com/spring-projects/spring-security/issues/5766
        return (exchange, chain) -> {
            exchange.getResponse().beforeCommit(() -> Mono.defer(() -> {
                Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
                return csrfToken != null ? csrfToken.then() : Mono.empty();
            }));
            return chain.filter(exchange);
        };
    }

    @Bean
    public PolicyFactory htmlSanitizer() {
        // This is the policy we will be using to sanitize HTML input
        return Sanitizers.FORMATTING.and(Sanitizers.BLOCKS)
                .and(Sanitizers.STYLES)
                .and(Sanitizers.LINKS);
    }
}
