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
 *  UserInfo.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.commons.security.service;

import java.security.Principal;

/**
 * Interface providing information about a user.
 *
 * @project ailegorreta-kit-commons-client-security
 * @author rlh
 * @date June 2023
 */
public interface UserInfo extends Principal {

    /**
     * Gets the user's first name.
     */
    String getNombre();

    /**
     * Gets the user's last name.
     */
    String getApellido();

    /**
     * Gets the user's full name, which is a combination of the first and last name. If the user has no first nor
     * last name, the user's username is used instead.
     */
    default String getNombreCompleto() {
        var firstName = getNombre();
        var lastName = getApellido();
        var sb = new StringBuilder();

        if (!firstName.isBlank())
            sb.append(firstName);
        if (!lastName.isBlank()) {
            if (sb.length() > 0)
                sb.append(" ");
            sb.append(lastName);
        }
        if (sb.length() == 0)
            sb.append(getName());

        return sb.toString();
    }

    /**
     * Gets the user email
     */
    String getEmail();

    /**
     * Gets the User Zone Info
     */
    String getZoneInfo();

    /**
     * Gets the company where the user works
     */
    String getCompany();

    /**
     * Check if the user is an administrator
     */
    Boolean isAdministrator();

    /**
     * Check if the user is employee or not
     */
    Boolean isEmployee();
}
