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
 *  SecurityServiceConfig.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.commons.security.config;

/**
 * Interface for security properties that must be declared in the *.yml files
 *
 * @Project ailegorreta-kit-commons-security
 * @author rlh
 * @date June 2023
 */
public interface SecurityServiceConfig {

    /**
     * Gets the issuer uir
     */
    String getIssuerUri();

    /**
     * The clientID that has been registered in the authDB without any postfix
     */
    String getSecurityClientId();

    /**
     * The port for the application. Needed for @LoadBalanced in RestCallerContigurationClass
     */
    int getServerPort();

    /**
     * The clientID that has been registered in the authDB with the postfix that has the scopes to read the
     * IAM database
     */
    String getSecurityIAMClientId();

    /**
     * This property indicates the IAM repository URI e.g., http://iamserverrepo:8070
     */
    String getSecurityIAMProvider();
}
