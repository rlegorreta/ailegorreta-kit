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
 *  public interface CurrentSession {
.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.commons.security.service;

import java.util.Optional;

/**
 * Interface used by other parts of the system to get information about the current session and user, regardless
 * of how that information is stored and retrieved.
 *
 * @project lmasskit-client-security
 * @author rlh
 * @date June 2023
 */
public interface CurrentSession {

    /**
     * Gets the current user, if any.
     */
    Optional<UserInfo> getAuthenticatedUser();

    /**
     * Gets the current user and throws an exception if there is none.
     */
    default UserInfo currentUser() {
        return getAuthenticatedUser().orElseThrow(() -> new IllegalStateException("No user bound to current thread"));
    }

    /**
     * Checks if the current user has the given role. If there is no current user this method always returns false.
     */
    boolean hasRole(String role);
}
