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
 *  AuthServerAuthoritiesMapper.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.security.authserver;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Our authorization server instance has been configured to expose the client roles inside the ID token under the claim
 * <code>resource_access.${client_id}.roles</code>. This mapper will fetch the roles from that claim and convert
 * them into <code>ROLE_</code> {@link GrantedAuthority authorities} that can be used directly by Spring Security.
 *
 * Normally this class is subclassed by the developer in order to create a @Component and initialize a single at
 * startup time.
 *
 * @project ailegorreta-kit-client-security
 * @author rlh
 * @date June 2023
 */
public class AuthServerAuthoritiesMapper implements GrantedAuthoritiesMapper {

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {

        return authorities.stream()
                        .filter(OAuth2UserAuthority.class::isInstance)
                        .map(OAuth2UserAuthority.class::cast)
                        .findFirst()
                        .map(this::extractClientRoles)
                        .orElse(Collections.emptyList());
    }

    @SuppressWarnings("unchecked")
    public Collection<? extends GrantedAuthority> extractClientRoles(OAuth2UserAuthority oauthAuthority) {
        //var username = (String)oauthAuthority.getAttributes().getOrDefault("sub", "");
        var attributes = (Map<String, Object>) oauthAuthority.getAttributes();
        var authorities = (ArrayList<String>)attributes.getOrDefault("authorities", new ArrayList());
        // ^ the authorities fields are inserted by the Auth micro.service (Spring Authorization Server)

        return authorities.stream()
                .map(r -> (r).toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
