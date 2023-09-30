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
 *  AuthServerBackChannelLogoutController.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */

package com.ailegorreta.client.security.authserver;

import com.ailegorreta.client.security.config.SecurityServiceConfig;
import com.ailegorreta.client.security.repository.SessionRepository;
import com.ailegorreta.client.security.utils.HasLogger;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * A REST controller that receives back channel logout requests from Authorization Server (OIDC). Back Channel logout
 * is coming into Spring Security in the future (https://github.com/spring-projects/spring-security/issues/7845) so
 * this is a quick and dirty implementation.
 *
 * TODO also need to wait until the Security Authorization Server support back channel logout.
 * The classes that implements the back channel logout are:
 * - SessionRepository
 * - SessionRepositoryListener
 *
 * @project ailegorreta-kit-client-security
 * @author rlh
 * @date September 2023
 */
@RestController
class AuthServerBackChannelLogoutController implements HasLogger {

    private final JwtDecoder        jwtDecoder;
    private final SessionRepository sessionRepository;

    AuthServerBackChannelLogoutController(ClientRegistrationRepository clientRegistrationRepository,
                                          SessionRepository sessionRepository,
                                          SecurityServiceConfig securityServiceConfig ) {
        this.sessionRepository = sessionRepository;
        var clientId = securityServiceConfig.getSecurityClientId() + "-oidc";
        var registrationId = clientRegistrationRepository.findByRegistrationId(clientId);

        if (registrationId != null) {
            var jwkSetUri = registrationId.getProviderDetails().getJwkSetUri();

            jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
        } else {
            getLogger().error("Fatal error: no se encontró al cliente-id '{}' en el repositorio", clientId);
            jwtDecoder = null;
        }
    }

    @PostMapping("/back-channel-logout")
    public void logout(@RequestParam("logout_token") String rawLogoutToken) {
        System.out.println(">>>>>>>>>>>>>>>> Received a back-channel-logged out from OIDC server");
        var logoutToken = jwtDecoder.decode(rawLogoutToken);
        var sessionId = logoutToken.getClaimAsString("sid");

        invalidateSession(sessionId);
    }

    private void invalidateSession(String keycloakSessionId) {
        sessionRepository.invalidate(session ->
                extractSecurityContext(session)
                        .map(SecurityContext::getAuthentication)
                        .filter(auth -> correspondsToSession(auth, keycloakSessionId))
                        .isPresent()
        );
    }

    private Optional<SecurityContext> extractSecurityContext(HttpSession session) {
        return Optional.ofNullable((SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY));
    }

    private boolean correspondsToSession(Authentication authentication, String keycloakSessionId) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            return keycloakSessionId.equals(((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("sid"));
        }
        return false;
    }

    @Override
    public Logger getLogger() { return HasLogger.DefaultImpls.getLogger(this); }

}
