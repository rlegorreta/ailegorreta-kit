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
 *  PageableHierarchicalDataProvider.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.dataproviders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.util.Pair;

import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

/**
 * This is the same as PageableHierarchicalDataProvider in the Vaadin pageable plug-in
 * but with Hierarchical data source.
 *
 * @see //github.com/Artur-/spring-data-provider/blob/master/src/main/java/org/vaadin/artur/spring/dataprovider/FilterablePageableDataProvider.java
 *
 * @project ailegorreta-kit-client-dataprovider
 * @author rlh
 * @datre July 2023
 */
public abstract class PageableHierarchicalDataProvider<T, F> extends AbstractBackEndHierarchicalDataProvider<T, F> {

    protected abstract Page<T> fetchFromBackEnd(HierarchicalQuery<T, F> query, Pageable pageable);

    protected Pageable getPageable(HierarchicalQuery<T, F> q) {
        Pair<Integer, Integer> pageSizeAndNumber = limitAndOffsetToPageSizeAndNumber(
                q.getOffset(), q.getLimit());

        return PageRequest.of(pageSizeAndNumber.getSecond(), pageSizeAndNumber.getFirst(), createSpringSort(q));
    }

    private <T, F> Sort createSpringSort(HierarchicalQuery<T, F> q) {
        List<QuerySortOrder> sortOrders;

        if (q.getSortOrders().isEmpty())
            sortOrders = getDefaultSortOrders();
        else
            sortOrders = q.getSortOrders();

        List<Order> orders = sortOrders.stream()
                                       .map(PageableHierarchicalDataProvider::queryOrderToSpringOrder)
                                       .collect(Collectors.toList());
        if (orders.isEmpty())
            return Sort.unsorted();
        else
            return Sort.by(orders);
    }

    protected abstract List<QuerySortOrder> getDefaultSortOrders();

    private static Order queryOrderToSpringOrder(QuerySortOrder queryOrder) {
        return new Order(queryOrder.getDirection() == SortDirection.ASCENDING
                ? Direction.ASC
                : Direction.DESC, queryOrder.getSorted());
    }

    public static Pair<Integer, Integer> limitAndOffsetToPageSizeAndNumber( int offset, int limit) {
        int minPageSize = limit;
        int lastIndex = offset + limit - 1;
        int maxPageSize = lastIndex + 1;

        for (double pageSize = minPageSize; pageSize <= maxPageSize; pageSize++) {
            int startPage = (int) (offset / pageSize);
            int endPage = (int) (lastIndex / pageSize);
            if (startPage == endPage) {
                // It fits on one page, let's go with that
                return Pair.of((int) pageSize, startPage);
            }
        }

        // Should not really get here
        return Pair.of(maxPageSize, 0);
    }

    protected <T> Stream<T> fromPageable(Page<T> result, Pageable pageable,
                                         HierarchicalQuery<T, ?> query) {
        List<T> items = result.getContent();

        int firstRequested = query.getOffset();
        int nrRequested = query.getLimit();
        int firstReturned = (int) pageable.getOffset();
        int firstReal = firstRequested - firstReturned;
        int afterLastReal = firstReal + nrRequested;
        if (afterLastReal > items.size()) {
            afterLastReal = items.size();
        }
        return items.subList(firstReal, afterLastReal).stream();
    }
}
