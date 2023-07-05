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
 *  InMemoryService.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.dataproviders.service

import com.github.mvysny.vokdataloader.*
import com.github.mvysny.vokdataloader.SortClause
import com.ailegorreta.commons.dtomappers.FilteredDTO
import com.ailegorreta.commons.service.FilterChangeEvent
import com.ailegorreta.commons.service.FilterChangeListener
import com.ailegorreta.commons.service.ServiceWithFilter

import java.math.BigDecimal
import java.time.*
import java.util.*
import kotlin.Comparator
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * This service gets all the data from memory, instead of call
 * REST service (for REST controller use the VOK CrudClient class)
 *
 *  note: The DTO has to have all its properties defined that have
 *  filters.
 *
 *  @project : ailegorreta-kit-client-dataproviders
 *  @author rlh
 *  @date July 2023
 */
abstract class InMemoryService<T : FilteredDTO<T>>: ServiceWithFilter {

    companion object {
        fun<T> compareValues(val1: T, val2: T): Int {
            if (val1 is String)
                return val1.compareTo(val2 as String)
            if (val1 is LocalDate)
                return val1.compareTo(val2 as LocalDate)
            if (val1 is LocalDateTime)
                return val1.compareTo(val2 as LocalDateTime)
            if (val1 is Long)
                return val1.compareTo(val2 as Long)
            if (val1 is BigDecimal)
                return val1.compareTo(val2 as BigDecimal)

            return compareValuesBy(val1, val2, {c -> c.toString()})
        }

        fun <T:Any>convert(value: Any, clazz: KClass<T>): Any {
            if (clazz == value::class)
                return value
            if (clazz == BigDecimal::class && value is Double)
                return BigDecimal.valueOf(value)
            if (clazz == Long::class && value is Double)
                return value.toLong()


            throw IllegalArgumentException("Illegal conversion supported: from ${value::class} to $clazz")
        }
    }

    private var dirty = true
    private var cacheFilter: Filter<T>? = null
    private var cacheResult: List<T>?= null
    private val listeners = ArrayList<FilterChangeListener>()

    var dataSource: List<T> = ArrayList<T>()

    fun initDataSource(dataSource: List<T>) {
        this.dataSource = dataSource
        dirty = true
    }

    private fun simulatePaging(result: List<T>, range: LongRange): List<T> {
        println("     first:${range.first}  last:${range.last} start:${range.start} step:${range.step}")
        println("     endInclusive:${range.endInclusive} ")

        if (range.isEmpty())
            return ArrayList()

        val end = minOf(range.last.toInt() + 1, result.size)

        println("      end:${end}")

        val res = result.subList(range.first.toInt(), end)

        return res
    }

    open fun fetch(filter: Filter<T>?, sortBy: List<SortClause>, range: LongRange): List<T> {
        if (filter != null) {
            if (!dirty && filter.toString().equals(cacheFilter.toString()))
                return simulatePaging(doSorting(cacheResult!!, sortBy), range)

            return simulatePaging(doSorting(doFiltering(filter, dataSource), sortBy), range)
        } else
            return simulatePaging(doSorting(dataSource, sortBy), range)
    }

    /**
     *  Returns the number of rows that complies with the query
     */
    open fun getCount(filter: Filter<T>?): Long {
        if (filter != null) {
            if (!dirty && filter.toString().equals(cacheFilter.toString()))
                return cacheResult!!.size.toLong()
            dirty = false
            cacheFilter = filter
            cacheResult = doFiltering(filter, dataSource)
            fireEvent(cacheFilter.toString())

            return cacheResult!!.size.toLong()
        } else {
            dirty = true
            return dataSource.size.toLong()
        }
    }

    /**
     *  This method do the filtering for each of the columns declared
     */
    private fun doFiltering(filter: Filter<T>, res: List<T>): List<T> {

        fun compare(val1: Any?, val2: Any?, op: CompareOperator) =
                if (val1 == null || val2 == null)
                    false
                else {
                    val r = compareValues(val1, convert(val2, val1::class))

                    when (op) {
                        CompareOperator.eq -> r == 0
                        CompareOperator.ne -> r != 0
                        CompareOperator.ge -> r >= 0
                        CompareOperator.gt -> r > 0
                        CompareOperator.le -> r <= 0
                        CompareOperator.lt -> r < 0
                    }
                }

        fun like(value1: Any?, value2: Any?) =
                if (value1 == null || value2 == null)
                    false
                else if (value1 is String && value2 is String)
                    value1.startsWith(value2)
                else
                    false
        fun ilike(value1: Any?, value2: Any?) = if (value1 == null || value2 == null)
            false
        else if (value1 is String && value2 is String)
            value1.contains(value2)
        else
            false

        fun fullText(value1: Any?, value2: Any?) =
                if (value1 == null || value2 == null)
                    false
                else if (value1 is String && value2 is String)
                    value1.endsWith(value2)
                else
                    false

        if (res.isEmpty()) return res  // avoid res.first() call to collapse
        if (filter is BeanFilter) {
            val propName = filter.propertyName
            val prop = res.first()
                          .filterProperties()[propName]
                        ?: throw IllegalArgumentException("Property $propName not declare as filterable")

            return when (filter) {
                is EqFilter -> res.filter { p -> prop.get(p)!!.equals(filter.value) }
                is IsNotNullFilter -> res.filter { p -> prop.get(p) != null }
                is IsNullFilter -> res.filter { p -> prop.get(p) == null }
                is StartsWithFilter -> res.filter { p -> like(prop.get(p), filter.value.dropLast(1)) }
                is OpFilter -> res.filter { p -> compare(prop.get(p), filter.value, filter.operator) }
                is FullTextFilter -> res.filter { p -> fullText(prop.get(p), filter.value) }
                else -> throw IllegalArgumentException("Unsupported filter $filter")
            }
        } else {
            when (filter) {
                is AndFilter -> {
                    var result = res

                    filter.children.forEach {
                        result = doFiltering(it as Filter<T>, result ) }

                    return result
                }
                else -> throw IllegalArgumentException("Unsupported filter $filter")
            }
        }
    }

    /**
     *  After the filtering is done now we can do the sorting. For simplicity
     *  we do the sorting for the first attribute only (in mmeory), to avoid doing
     *  sorting for inner multiple properties.
     */
    private fun doSorting(res: List<T>, sortBy: List<SortClause>): List<T> {
        if (sortBy.isEmpty() || res.isEmpty())
            return res
        val sort = sortBy.first()
        val prop = res.first().filterProperties().get(sort.propertyName)

        return res.sortedWith( ComparatorWithProperty((prop as KProperty1<T, Any>?)!!, sort))
    }

    /**
     * For display purpose in the menu, show the  active filter
     */
    override fun addFilterChangeListener(listener: FilterChangeListener): FilterChangeListener {
        listeners.add(listener)

        return listener
    }

    private fun fireEvent(filter: String) {
        listeners.forEach { it.filterChange(FilterChangeEvent(this, filter)) }
    }

    /**
     * This inner class do the comparision between two POJOS according to
     * a variable property.
     *
     * note: since we do not know waht type os value will be returned the property
     *       we do not know is if is Comparable and the Kotlin compiler complains
     *       so we need to check each type of data the property returns. Not a
     *       good general solution.
     */
    class ComparatorWithProperty<T>(val prop: KProperty1<T, Any>, val sort: SortClause): Comparator<T> {
        override fun compare(o1: T, o2: T): Int {
            val val1 = prop.get(o1)
            val val2 = prop.get(o2)

            return compareValues(val1, val2) * (if (sort.asc) 1 else -1)
        }
    }
}

