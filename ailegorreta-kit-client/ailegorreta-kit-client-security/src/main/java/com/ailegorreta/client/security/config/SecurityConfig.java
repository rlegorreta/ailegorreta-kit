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
package com.ailegorreta.client.security.config;

import com.ailegorreta.commons.security.authserver.AuthOidcClientInitiatedLogoutSuccessHandler;
import com.ailegorreta.commons.security.config.SecurityServiceConfig;
import com.ailegorreta.commons.security.repository.SessionRepository;
import com.ailegorreta.commons.security.repository.SessionRepositoryListener;
import com.ailegorreta.client.security.vaadin.VaadinAwareSecurityContextHolderStrategy;
import com.vaadin.flow.spring.security.VaadinSavedRequestAwareAuthenticationSuccessHandler;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * Security configuration for valid URLs in the Application. And also reads what are the valid
 * User and Roles.
 *
 * This is an abstract class if the user want to modify something in the security, but it is
 * needed to call super in order to work correctly
 *
 * @project ailegorreta-kit-client-security
 * @author rlh
 * @Date: June 2023
 */
public abstract class SecurityConfig extends VaadinWebSecurity {

    protected final ClientRegistrationRepository clientRegistrationRepository;
    final GrantedAuthoritiesMapper authoritiesMapper;
    final private String oidcClient;
    private final String issuerUri;

    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository,
                          GrantedAuthoritiesMapper authoritiesMapper,
                          String oidcClient,
                          SecurityServiceConfig securityServiceConfig) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authoritiesMapper = authoritiesMapper;
        this.oidcClient = oidcClient;
        this.issuerUri = securityServiceConfig.getIssuerUri();
        SecurityContextHolder.setStrategyName(VaadinAwareSecurityContextHolderStrategy.class.getName());
    }

    @Bean
    public SessionRepository sessionRepository() {
        return new SessionRepository();
    }

    @Bean
    public ServletListenerRegistrationBean<SessionRepositoryListener> sessionRepositoryListener(SessionRepository sessionRepository) {
        var bean = new ServletListenerRegistrationBean<SessionRepositoryListener>();

        bean.setListener(new SessionRepositoryListener(sessionRepository));

        return bean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configure your static resources with public access before calling
        // super.configure(HttpSecurity) as it adds final anyRequest matcher
        // In previous versions we use http.authorizeRequests().antMatchers()
        http.authorizeHttpRequests( auth -> {
                        auth.requestMatchers("/images/**").permitAll();
                        auth.requestMatchers("/oauth2/**").permitAll();
                        auth.requestMatchers("/logged-out/**").permitAll();
                        auth.requestMatchers("/session-expired/**").permitAll();
                        auth.requestMatchers("/back-channel-logout/**").permitAll();
                        auth.requestMatchers("/actuator/**").permitAll();
                    });
        // Set default security policy that permits Vaadin internal requests and denies all other
        super.configure(http);
        // Because we use an openid connect we do not use the setLoginView
        http
                // Enable OAuth2 login
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .clientRegistrationRepository(clientRegistrationRepository)
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                // Use a custom authorities' mapper to get the roles from the identity
                                                // provider into the Authentication token
                                                .userAuthoritiesMapper(authoritiesMapper)
                                )
                                .loginPage("/oauth2/authorization/" + oidcClient)
                                // Use a Vaadin aware authentication success handler
                                .successHandler(new VaadinSavedRequestAwareAuthenticationSuccessHandler())
                )
                // Configure logout
                .logout(logout ->
                        logout
                                // Enable OIDC logout (requires that we use the 'openid' scope when authenticating)
                                .logoutSuccessHandler(logoutSuccessHandler())
                                // When CSRF is enabled, the logout URL normally requires a POST request with the CSRF
                                // token attached. This makes it difficult to perform a logout from within a Vaadin
                                // application (since Vaadin uses its own CSRF tokens). By changing the logout endpoint
                                // to accept GET requests, we can redirect to the logout URL from within Vaadin.
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                );
    }

    protected SimpleUrlLogoutSuccessHandler logoutSuccessHandler() {
        var logoutSuccessHandler = new AuthOidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository, issuerUri);

        // This is to set a static page to inform the user that he(she) has been logged-out
        logoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/logged-out");

        return logoutSuccessHandler;
    }

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
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public PolicyFactory htmlSanitizer() {
        // This is the policy we will be using to sanitize HTML input
        return Sanitizers.FORMATTING.and(Sanitizers.BLOCKS)
                .and(Sanitizers.STYLES)
                .and(Sanitizers.LINKS);
    }

}
