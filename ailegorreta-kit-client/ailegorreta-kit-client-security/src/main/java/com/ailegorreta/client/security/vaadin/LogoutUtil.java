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
 *  LogoutUtil.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.security.vaadin;

import com.vaadin.flow.component.UI;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility used by Vaadin views to log out the current user.
 *
 * @project ailegorreta-kit-client-security
 * @author rlh
 * @date September 2023
 */
public class LogoutUtil {
    private final String relativeLogoutUrl;

    public LogoutUtil(ServerProperties serverProperties) {
        relativeLogoutUrl = UriComponentsBuilder.fromPath(serverProperties
                                                .getServlet()
                                                .getContextPath())
                                                .path("/logout")
                                                .build()
                                                .toUriString();
        // TODO there is a bug in the Spring Authorization server 1.0.0 where the end_session_endpoint is defined
        // TODO as /connect/logout. So we need to fix it latter. Must be manually 'logout' path
        // TODO see AuthOidcClientInitiatedLogoutSuccessHandler java class
    }

    /**
     * Logs out the current user by redirecting the browser to the logout URL.
     */
    public void logout() {
        UI.getCurrent().getPage().setLocation(relativeLogoutUrl);
    }
}
