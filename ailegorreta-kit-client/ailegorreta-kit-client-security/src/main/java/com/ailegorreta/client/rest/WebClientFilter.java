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
 *  WebClientFilter.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.rest;

import com.ailegorreta.client.rest.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

/**
 * Static methods to add log filters to the REST web client class (spring reactive web flux)
 *
 * Also makes error handling for web client calls
 *
 * note: For microservice clients we do NOT need to forward the correlation-id since the first call
 *       passes through the gateway, which adds the correlation-id. It is not the case for the filter
 *       developer in ailegorreta-kit-resource-server
 *
 * note: Load balanced is just used for Docker environment and not for Kubernetes (that it is native)
 *
 * @project: ailegorreta-kit-client-security
 * @author rlh
 * @date January 2023
 */
public class WebClientFilter {
    private static final Logger logger = LoggerFactory.getLogger(WebClientFilter.class);

    public static ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is5xxServerError())
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new ServiceException(errorBody,
                                clientResponse.statusCode().value())));
            else if (clientResponse.statusCode().is4xxClientError())
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new ServiceException(errorBody,
                                clientResponse.statusCode().value())));
            else
                return Mono.just(clientResponse);
        });
    }

    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            logMethodAndUrl(request);
            logHeaders(request);

            return Mono.just(request);
        });
    }

    public static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            logStatus(response);
            logHeaders(response);

            return logBody(response);
        });
    }

    private static void logStatus(ClientResponse response) {
        HttpStatusCode status = response.statusCode();
        logger.debug("Returned status code {}", status.value());
    }

    /**
     * The logBody method just logs when an error exist. For security reasons we don´´ log successful
     * response.
     */
    private static Mono<ClientResponse> logBody(ClientResponse response) {
        if (response.statusCode() != null && (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError())) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> {
                        logger.debug("Body is {}", body);
                        return Mono.error(new ServiceException(body, response.statusCode().value()));
                    });
        } else
            return Mono.just(response);
    }

    private static void logHeaders(ClientResponse response) {
        response.headers().asHttpHeaders().forEach((name, values) -> {
            values.forEach(value -> {
                logNameAndValuePair(name, value);
            });
        });
    }

    private static void logHeaders(ClientRequest request) {
        request.headers().forEach((name, values) -> {
            values.forEach(value -> {
                logNameAndValuePair(name, value);
            });
        });
    }

    private static void logNameAndValuePair(String name, String value) {
        logger.debug("{}={}", name, value);
    }

    private static void logMethodAndUrl(ClientRequest request) {
        StringBuilder sb = new StringBuilder();

        sb.append(request.method().name());
        sb.append(" to ");
        sb.append(request.url());
        logger.debug(sb.toString());
    }

}