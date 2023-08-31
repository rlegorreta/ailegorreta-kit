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
 *  UUIDScalar.java
 *
 *  Developed 2023 by LMASS Desarrolladores, S.C. www.lmass.com.mx
 */
package com.ailegorreta.data.jpa.gql.scalars;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.UUID;

/**
 * This class is for GraphQL scalar type for UUID identifiers.  The class was taken from the spring-graphql-querydl
 * example. For more information see the link: https://github.com/hantsy/spring-graphql-sample
 *
 * @author: Rlh
 * @project: ailegorretakit-data-jpa
 * @date: August 2023
 */
public class UUIDScalar implements Coercing<UUID, String> {

    public static GraphQLScalarType graphQLScalarType() {
        return GraphQLScalarType.newScalar()
                .name("UUID")
                .description("UUID type")
                .coercing(new UUIDScalar())
                .build();
    }

    @Override
    public String serialize(@NotNull Object dataFetcherResult,
                            @NotNull GraphQLContext graphQLContext,
                            @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof UUID)
            return dataFetcherResult.toString();
        else
            throw new CoercingSerializeException("Not a valid UUID");
    }

    @Override
    public UUID parseValue(@NotNull Object input,
                           @NotNull GraphQLContext graphQLContext,
                           @NotNull Locale locale) throws CoercingParseValueException {
        try {
            return UUID.fromString(input.toString());
        } catch (Exception e) {
            throw new CoercingParseLiteralException("Value is not a valid UUID string:" + e.getMessage());
        }
    }

    @Override
    public UUID parseLiteral(@NotNull Value<?> input,
                             @NotNull CoercedVariables variables,
                             @NotNull GraphQLContext graphQLContext,
                             @NotNull Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue)
            try {
                return UUID.fromString(((StringValue) input).getValue());
            } catch (Exception e) {
                throw new CoercingParseLiteralException("Value is not a valid UUID string:" + e.getMessage());
            }

        throw new CoercingParseLiteralException("Value is not a valid UUID string");
    }
}
