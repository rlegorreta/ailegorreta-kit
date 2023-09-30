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
 *  SessionRepository.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.security.repository;

import jakarta.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * A quick and dirty repository of sessions that is used to demonstrate the principle of back channel logout. Don't
 * do this in production code because all session data is kept in memory.
 *
 * Wait for future Spring Security version.
 *
 * Back Channel logout is coming into Spring Security in the future
 * (https://github.com/spring-projects/spring-security/issues/7845) so this is a quick and dirty implementation.
 *
 * @project ailegorreta-kit-client-security
 * @author rlh
 * @date September 2023
 */
public class SessionRepository {

    private final Set<HttpSession> sessions = new HashSet<>();

    public synchronized void add(HttpSession session) {
        sessions.add(session);
    }

    public synchronized void remove(HttpSession session) {
        sessions.remove(session);
    }

    public synchronized void invalidate(Predicate<HttpSession> predicate) {
        Set.copyOf(sessions).stream().filter(predicate).forEach(HttpSession::invalidate);
    }

}
