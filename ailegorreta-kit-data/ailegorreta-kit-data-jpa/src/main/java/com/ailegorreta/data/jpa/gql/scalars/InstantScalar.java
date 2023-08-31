/* Copyright (c) 2023, LMASS Desarrolladores S.C.
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
 *  InstantScalar.java
 *
 *  Developed 2023 by LMASS Desarrolladores, S.C. www.lmass.com.mx
 */
package com.ailegorreta.data.jpa.gql.scalars;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.Value;
import graphql.schema.*;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Locale;

/**
 * This GraphQL scalar type is to handle correctly the Instant java Time class.
 *
 * @author: rlh
 * @project: ailegorreta-kit-data-jpa
 * @date: August 2023
 */
public class InstantScalar implements Coercing<Instant, String> {

    public static GraphQLScalarType graphQLScalarType() {
        return GraphQLScalarType.newScalar()
                .name("Instant")
                .description("Instant type")
                .coercing(new InstantScalar())
                .build();
    }

    @Override
    public String serialize(Object dataFetcherResult,
                            @NotNull GraphQLContext graphQLContext,
                            @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof Instant) {
            return dataFetcherResult.toString();
        } else {
            throw new CoercingSerializeException("Not a valid Instant");
        }
    }

    @Override
    public Instant parseValue(Object input,
                              @NotNull GraphQLContext graphQLContext,
                              @NotNull Locale locale) throws CoercingParseValueException {
        return parseLiteral((Value)input,null, graphQLContext, locale);
    }

    @Override
    public Instant parseLiteral(@NotNull Value<?> input,
                                CoercedVariables variables,
                                @NotNull GraphQLContext graphQLContext,
                                @NotNull Locale locale) throws CoercingParseLiteralException {
        try {
            return Instant.parse(input.toString());
        } catch (Exception e) {
            throw new CoercingParseLiteralException("Value is not a valid Instant:" + e.getMessage());
        }
    }
}