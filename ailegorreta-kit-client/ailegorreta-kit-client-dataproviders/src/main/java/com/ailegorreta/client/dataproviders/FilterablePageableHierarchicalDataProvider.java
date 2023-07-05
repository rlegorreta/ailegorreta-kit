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
 *  FilterablePageableHierarchicalDataProvider.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.dataproviders;

import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;

/**
 * This is the same as FilterablePageableDataProvider in the Vaadin pageable plug-in
 * but with Hierarchical data source.
 *
 * @see //github.com/Artur-/spring-data-provider/blob/master/src/main/java/org/vaadin/artur/spring/dataprovider/FilterablePageableDataProvider.java
 *
 * @project ailegorreta-kit-client-dataprovider
 * @author rlh
 * @datre July 2023
 */
public abstract class FilterablePageableHierarchicalDataProvider<T, F> extends PageableHierarchicalDataProvider<T, F> {

    private F filter = null;

    public void setFilter(F filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null");
        }
        this.filter = filter;
        refreshAll();
    }

    protected HierarchicalQuery<T, F> getFilterQuery(HierarchicalQuery<T, F> t) {
        return new HierarchicalQuery(t.getOffset(), t.getLimit(), t.getSortOrders(),
                                     t.getInMemorySorting(), filter, t.getParent());
    }

    protected Optional<F> getOptionalFilter() {
        if (filter == null)
            return Optional.empty();
        else
            return Optional.of(filter);
    }
}
