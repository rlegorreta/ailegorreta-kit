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
 *  VaadinSessionConfiguration.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.security.vaadin;

import com.vaadin.flow.server.*;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Configures Vaadin to work properly with sessions when timeout occurs.
 *
 * @project lmasskit-client-security
 * @author rlh
 * @date January 2023
 */
@Component
class VaadinSessionConfiguration implements VaadinServiceInitListener,
                                            SystemMessagesProvider,
                                            SessionDestroyListener {

    private final String relativeSessionExpiredUrl;

    VaadinSessionConfiguration(ServerProperties serverProperties) {
        relativeSessionExpiredUrl = UriComponentsBuilder.fromPath(serverProperties.getServlet()
                                                        .getContextPath())
                                                        .path("session-expired")
                                                        .build()
                                                        .toUriString();
    }

    @Override
    public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
        var messages = new CustomizedSystemMessages();
        // Redirect to a specific screen when the session expires. In this particular case we don't want to logout
        // just yet. If you would like the user to be completely logged out when the session expires, this URL
        // should the logout URL.
        // nota: If we want the authorization server session expired algo. We need to redirect this URI to the logout
        //       uri of the authorization server i.e., OIDC (Open Id Connect Provider)
        messages.setSessionExpiredURL(relativeSessionExpiredUrl);

        return messages;
    }

    /**
     * Session destroy listener for the Vaadin session
     */
    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        // We also want to destroy the underlying HTTP session since it is the one that contains the authentication
        // token.
        try {
            event.getSession().getSession().invalidate();
        } catch (Exception ignore) {
            // Session was probably already invalidated.
        }
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        // Register me as listener for two function this class has
        event.getSource().setSystemMessagesProvider(this);
        event.getSource().addSessionDestroyListener(this);
    }
}
