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
 *  ServiceUtils.kt
 *
 *  Developed 2023 by LMASS Desarrolladores, S.C. www.lmass.com.mx
 */
package com.ailegorreta.data.jpa.jpa.service

import org.springframework.data.domain.Sort

/**
 * Singleton class for utilities that call the services classes.
 *
 * @author rlh
 * @project : ailegorreta-kit-data-jpa
 * @date August 2023
 */
object ServiceUtils {

    /**
     * Converts a service REST property:ASC to an instance Sort
     * defined in Spring Data
     *
     * note : 'prefix' optional parameter is because in some cases that
     *        we use the @Query notation with pagination.. in these cases
     *        Neo4j Spring Data adds ORDER BY without the entity name, so
     * 		  the Cypher statements breaks.
     */
    fun sortOrder(sortStr: String? = null, prefix: String = "") : Sort {
        if (sortStr.isNullOrEmpty())
            return Sort.unsorted()

        var sorts = sortStr.split(',')

        if (sorts.isEmpty())
            return Sort.unsorted()

        sorts.forEach{sort ->
            var props = sort.split(':')

            if (props.size == 1)
                if (props[0].trim().isEmpty())
                    return Sort.unsorted()
                else
                    return Sort.by(Sort.Direction.ASC, prefix + props[0].trim())
            else if (props.size == 2)
                if (props[1].trim().equals(Sort.Direction.DESC.toString()))
                    return Sort.by(Sort.Direction.DESC, prefix + props[0].trim())
                else
                    return Sort.by(Sort.Direction.ASC, prefix + props[0].trim())
            else
                return Sort.unsorted()
        }

        return Sort.unsorted()
    }
}
