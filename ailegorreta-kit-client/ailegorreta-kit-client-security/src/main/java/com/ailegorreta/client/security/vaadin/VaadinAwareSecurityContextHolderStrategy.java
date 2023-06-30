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
 *  VaadinAwareSecurityContextHolderStrategy.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.security.vaadin;

import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of {@link SecurityContextHolderStrategy} that is almost identical to
 * {@link org.springframework.security.core.context.ThreadLocalSecurityContextHolderStrategy}, but also looks at the
 * current {@link VaadinSession} for security context if there is none bound to the current thread. This is needed when
 * you invoke backend calls or other secured operations from within {@link com.vaadin.flow.component.UI#access(Command)}.
 *
 * @project lmasskit-client-security
 * @autho rlh
 * @dat February 2022
 */
public final class VaadinAwareSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

    private final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<>();

    @Override
    public void clearContext() {
        contextHolder.remove();
    }

    @Override
    @NonNull
    public SecurityContext getContext() {
        var context = contextHolder.get();

        if (context == null) {
            context = getFromVaadinSession().orElseGet(() -> {
                var newCtx = createEmptyContext();
                // This copies the behaviour of ThreadLocalSecurityContextHolder.
                contextHolder.set(newCtx);
                return newCtx;
            });
        }
        return context;
    }

    @NonNull
    private Optional<SecurityContext> getFromVaadinSession() {
        // Don't store this security context in the ThreadLocal as that may lead to the context leaking
        // into other sessions as threads may be reused.
        var session = VaadinSession.getCurrent();
        if (session == null)
            return Optional.empty();

        var securityContext = session.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext instanceof SecurityContext)
            return Optional.of((SecurityContext) securityContext);
        else
            return Optional.empty();
    }

    @Override
    public void setContext(@NonNull SecurityContext securityContext) {
        contextHolder.set(requireNonNull(securityContext));
    }

    @Override
    @NonNull
    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }
}

