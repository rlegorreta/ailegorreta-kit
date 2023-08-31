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
 *  QuerydslRepository.java
 *
 *  Developed 2023 by LMASS Desarrolladores, S.C. www.lmass.com.mx
 */
package com.ailegorreta.data.jpa.querydsl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Optional;

/**
 * Interface that any JPA repository willing to support QueryDSL predicates should extend.
 *
 * This interface add two binding customizations:
 *
 * - When searching for a string ignore a case and do it like a 'like' operation.
 * - Binds to b able to filter by a date range 'between' likewise.
 *
 * An examples are in the param-service Test classes
 *
 * note: This interface must be in Java and the repositories to avoid Kotlin issue @JvmDefault
 *       and receive the error "No property customize found in 'XXXX'. See:
 *       https://github.com/spring-projects/spring-data-jpa/issues/2576
 *
 * @param T The entity type class.
 * @param ID The entity ID type class.
 * @param Q The QueryDSL entity root class generated from the entity type class
 *
 *          <T, ID, Q : EntityPath<T>>
 *
 *  @progect ailegorreta-kit-data-jpa
 *  @author rlh
 *  @date August 2023.
 */
@NoRepositoryBean
public interface QuerydslRepository<T, ID, Q extends EntityPath<?>> extends JpaRepository<T, ID>,
                                                                            QuerydslPredicateExecutor<T>,
                                                                            QuerydslBinderCustomizer<Q> {
    /**
     * Adds common binding customizations. To provide special binding customizations use customizeBindings.
     */
    @Override
    default void customize(QuerydslBindings bindings , Q root) {
        bindings.bind(String.class).first((obj, str) -> ((StringPath)obj).containsIgnoreCase(str));
        customizeBindings(bindings, root);
    }

    /**
     * Customize the bindings for the given root.
     * @param bindings the bindings to customize.
     * @param root the entity root.
     */
    default void customizeBindings(QuerydslBindings bindings, Q root) {
        // Default implementation is empty
    }

    /**
     * Binds the provided datePath to be able to filter by a date range "between" likewise.
     * @param bindings the bindings.
     * @param datePath the date path to bind to between.
     */
    default <C extends Comparable<ChronoLocalDate>> void bindDateBetween(QuerydslBindings bindings, DatePath<C> datePath) {
        bindings.bind(datePath).all((path, values) -> {
            if (values.size() == 2)
                return Optional.of(path.between(values.stream().findFirst().get(),
                        values.stream().skip(1).findFirst().get()));
            else
                return Optional.of(path.eq(values.stream().findFirst().get()));
        });
    }

    /**
     * Binds the provided dateTimePath to be able to filter by a date range "between" likewise.
     * @param bindings the bindings.
     * @param dateTimePath the date path to bind to between.
     */
    default <C extends Comparable<ChronoLocalDateTime<?>>> void bindDateTimeBetween(QuerydslBindings bindings,
                                                                                    DateTimePath<C> dateTimePath) {
        bindings.bind(dateTimePath).all((path, values) -> {
            if (values.size() == 2)
                return Optional.of(path.between(values.stream().findFirst().get(),
                        values.stream().skip(1).findFirst().get()));
            else
                return Optional.of(path.eq(values.stream().findFirst().get()));
        });
    }

}