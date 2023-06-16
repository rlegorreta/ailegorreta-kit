/* Copyright (c) 2023, LMASS Desarrolladores, S.C.
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
 *  RestCallerConfiguration.kt
 *
 *  Developed 2023 by LMASS Desarrolladores, S.C. www.lmass.com.mx
 */
package com.lmass.resourceserver.rest;

import com.lmass.resourceserver.security.config.SecurityServiceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *  When @LoadBalanced is enabled just when this microservice runs inside the docker. This is because it utilizes
 *  the eureka server repository and the URLs must be just for with the server name.
 *
 *  When it is used this class is used.
 *
 *  Comment the @LoadBalanced configuration in ApplicationWebClientConfig class is if you want to run it in
 *  localNoDocker mode.
 *
 *  note about Error 503 service unavailable: if we use @LoadBalanced webClient with Spring actuator.
 *
 *  see: https://stackoverflow.com/questions/68153309/spring-webclient-load-balance
 *  and see: https://stackoverflow.com/questions/67953892/load-balancer-does-not-contain-an-instance-for-the-service
 *  for more information.
 *
 *  If we don´t want to use loadBalanced at all webClients we need to do the following:
 * - comment @LoadBalancedClient
 * - comment @LoadBalanced
 * - (optional) comment @Bean for this class and RestCaller class is not used.
 * and:
 *
 * @author: rlh
 * @project: lmass-resource-server-rest
 * @date: January 2023
 */
public class RestCallerConfiguration {

    private final WebClient.Builder webClientBuilder;
    private final SecurityServiceConfig serviceConfig;

    RestCallerConfiguration(@Qualifier("webClientBuilder") WebClient.Builder webClientBuilder,
                            SecurityServiceConfig serviceConfig) {
        this.webClientBuilder = webClientBuilder;
        this.serviceConfig = serviceConfig;
    }

    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext ctx) {
        return ServiceInstanceListSupplier.builder()
                .withDiscoveryClient()
                .withRetryAwareness()
                .withHealthChecks(webClientBuilder.build())
                .withBase(new RestCaller("restCaller",
                        serviceConfig.getSecurityDefaultClientId(),
                        serviceConfig.getServerPort()))
                .build(ctx);
    }
}
