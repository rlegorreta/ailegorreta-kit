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
package com.ailegorreta.client.security.authserver

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import java.time.Instant
import java.util.*

@JsonIgnoreProperties(value = ["grantedAuthorities", "token", "claims", "authorities", "attributes", "userInfo", "idToken"])
data class AuthServerUser(@JsonProperty val mail: String,
                          @JsonProperty val nombreUsuario: String,
                          @JsonProperty val nombre: String,
                          @JsonProperty val apellido: String,
                          @JsonProperty val nombreCompania: String? = null,
                          @JsonProperty val zonaHoraria: String,
                          @JsonProperty val administrador: Boolean,
                          @JsonProperty val interno: Boolean,
                          val grantedAuthorities: Collection<GrantedAuthority> = Collections.emptyList(),
                          val token: OidcIdToken? = null,
                          val _claims: Map<String, Any> = Collections.emptyMap()): OidcUser {

    companion object {
        fun userNotExist() = AuthServerUser(mail = "",
                                            nombreUsuario = "not exist",
                                            nombre = "", apellido = "", nombreCompania = "", zonaHoraria = "",
                                            administrador = false, interno = true)
    }

    override fun getEmail() = mail
    override fun getName() = nombreUsuario
    override fun getGivenName() = nombre
    override fun getFamilyName() = apellido
    override fun getFullName(): String {
        val firstName: String = nombre
        val lastName: String = apellido
        val sb = StringBuilder()

        if (firstName.isNotBlank()) sb.append(firstName)
        if (lastName.isNotBlank()) {
            if (sb.isNotEmpty()) sb.append(" ")
            sb.append(lastName)
        }
        if (sb.isEmpty()) sb.append(name)

        return sb.toString()
    }
    override fun getPhoneNumber() = "llamar al REST"
    override fun getPreferredUsername() = nombreUsuario
    override fun getZoneInfo() = zonaHoraria
    override fun getUpdatedAt(): Instant = Instant.now()
    // Noop fields
    override fun getAttributes() = emptyMap<String, Any>()
    override fun getAuthorities() = grantedAuthorities
    override fun getClaims() = _claims
    override fun getUserInfo() = null
    override fun getIdToken() = token

    override fun toString() = """
                             nombreUsuario = $nombreUsuario
                             nombre = $nombre
                             apellido = $apellido
                             mail = $mail
                             zoneinfo = $zonaHoraria
                             nombreCompania = $nombreCompania
                             administrator = $administrador
                             interno = $interno
                              """.trimIndent()
}
