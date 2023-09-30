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
 *  AuthServerCurrentSession.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.security.authserver;

import com.ailegorreta.client.security.service.CurrentSession;
import com.ailegorreta.client.security.service.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.*;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

/**
 * Security service to get the log-in user data with its roles. Check if the User has a specific
 * role and lastly do the logout operation.
 *
 * @project ailegorreta-kit-client-security
 * @author rlh
 * @date September 2023
 */
public class AuthServerCurrentSession implements CurrentSession {
    @Override
    public Optional<UserInfo> getAuthenticatedUser() {
        return getOauth2User().map(user -> new UserInfo() {
            @Override
            public String getNombre() { return requireNonNullElse(user.getGivenName(), ""); }

            @Override
            public String getApellido() { return requireNonNullElse(user.getFamilyName(), "");}

            @Override
            public String getEmail() { return requireNonNullElse(user.getEmail(), ""); }

            @Override
            public String getZoneInfo() { return requireNonNullElse(user.getZoneInfo(), ""); }

            @Override
            public String getCompany() { return requireNonNullElse(user.getNombreCompania(), ""); }

            @Override
            public Boolean isAdministrator() { return requireNonNullElse(user.getAdministrador(), false); }

            @Override
            public Boolean isEmployee() { return requireNonNullElse(user.getInterno(), true); }

            @Override
            public String getName() { return requireNonNull(user.getName()); }
        });
    }

    @Override
    public boolean hasRole(String role) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var realRole = role.toUpperCase(Locale.ROOT);

        if (!realRole.startsWith("ROLE_"))
            realRole = "ROLE_" + realRole;
        // avoid the developer forgets to add the ROLE prefix

        // Oauth2 the authorities came from SecurityContextHolder.getContext().getAuthentication() and not from
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal().getAuthorities()
        if (authentication != null && authentication instanceof OAuth2AuthenticationToken) {
            String finalRealRole = realRole;

            return authentication.getAuthorities().stream()
                                 .anyMatch(permit -> permit.getAuthority().equals(finalRealRole));
        }

        return false;
    }

    protected Optional<AuthServerUser> getOauth2User() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof DefaultOidcUser) {
                var extraData = ((DefaultOidcUser) principal).getAttributes().get("extraData");

                if (extraData != null && extraData instanceof Map)
                    return Optional.of(new AuthServerUser(getMail((Map) extraData),
                                                       getNombreUsuario((DefaultOidcUser) principal),
                                                       getNombre((Map) extraData),
                                                       getApellido((Map) extraData),
                                                       getCompania((Map) extraData),
                                                       getZonaHoraria((Map) extraData),
                                                       isAdministrador((Map) extraData),
                                                       isInterno((Map) extraData),
                                                       authentication.getAuthorities(),
                                                       ((DefaultOidcUser) principal).getIdToken(),
                                                       ((DefaultOidcUser) principal).getIdToken().getClaims()));

            }
        }
        // Anonymous or no authentication.
        return Optional.empty();
    }

    private String getMail(Map extraData) {
        var mail = extraData.get("email");

        if (mail != null)
            return mail.toString();

        return "no definido";
    }

    private String getNombreUsuario(DefaultOidcUser principal) {
        var nombreUsuario = principal.getAttributes().get("sub");

        if (nombreUsuario != null && nombreUsuario instanceof String)
            return (String)nombreUsuario;

        return "no definido";
    }

    private String getNombre(Map extraData) {
        var nombre = extraData.get("givenName");

        if (nombre != null)
            return nombre.toString();

        return "no definido";
    }

    private String getApellido(Map extraData) {
        var apellido = extraData.get("familyName");

        if (apellido != null)
            return apellido.toString();

        return "no definido";
    }

    private String getZonaHoraria(Map extraData) {
        var zonaHoraria = extraData.get("zoneInfo");

        if (zonaHoraria != null)
            return zonaHoraria.toString();

        return "no definida";
    }

    private String getCompania(Map extraData) {
        var compania = extraData.get("company");

        if (compania != null)
            return compania.toString();

        return "ninguna";
    }

    private Boolean isAdministrador(Map extraData) {
        var isAdministrator = extraData.get("administrator");

        if (isAdministrator != null)
            return isAdministrator.toString().equals("true");

        return false;
    }

    private Boolean isInterno(Map extraData) {
        var isInterno = extraData.get("employee");

        if (isInterno != null)
            return isInterno.toString().equals("true");

        return false;
    }

}
