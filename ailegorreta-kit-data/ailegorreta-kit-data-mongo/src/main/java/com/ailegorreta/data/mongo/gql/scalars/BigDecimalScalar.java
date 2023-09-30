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
 *  BigDecimalScalar.java
 *
 *  Developed 2023 by LMASS Desarrolladores, S.C. www.lmass.com.mx
 */
package com.ailegorreta.data.mongo.gql.scalars;

import java.math.BigDecimal;
import java.util.Locale;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import org.jetbrains.annotations.NotNull;

/**
 * This GraphQL scalar type is to handle correctly the LocalDate. The example was taken from the project
 * spring-graphql-querydsl. Is need more information use the link: https://github.com/hantsy/spring-graphql-sample
 *
 * @author: rlh
 * @project: ailegorreta-kit-data-mongo
 * @date: August 2023
 */
public class BigDecimalScalar implements Coercing<BigDecimal, String> {

    public static GraphQLScalarType graphQLScalarType() {
        return GraphQLScalarType.newScalar()
                                .name("BigDecimal")
                                .description("BigDecimal type")
                                .coercing(new BigDecimalScalar())
                                .build();
    }

    @Override
    public String serialize(@NotNull Object dataFetcherResult,
                            @NotNull GraphQLContext graphQLContext,
                            @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof BigDecimal) {
            return dataFetcherResult.toString();
        } else {
            throw new CoercingSerializeException("Not a valid BigDecimal");
        }
    }

    @Override
    public BigDecimal parseValue(@NotNull Object input,
                                 @NotNull GraphQLContext graphQLContext,
                                 @NotNull Locale locale) throws CoercingParseValueException {
        try {
            return new BigDecimal(input.toString());
        } catch (Exception e) {
            throw new CoercingSerializeException("Not a valid BigDecimal:" + e.getMessage());
        }
    }

    @Override
    public BigDecimal parseLiteral(@NotNull Value<?> input,
                                   @NotNull CoercedVariables variables,
                                   @NotNull GraphQLContext graphQLContext,
                                   @NotNull Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            try {
                return new BigDecimal(input.toString());
            } catch (Exception e) {
                throw new CoercingParseLiteralException("Value is not a valid Big Decimal:" + e.getMessage());
            }
        }
        throw new CoercingParseLiteralException("Value is not a valid Big Decimal");
    }
}
