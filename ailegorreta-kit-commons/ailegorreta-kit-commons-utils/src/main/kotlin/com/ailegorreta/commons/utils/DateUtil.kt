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
 *  DateUtil.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.commons.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * Utility class that implements common and standar date operations with <code>LocalDate</code> and
 * <code>LocalDateTime</code>.
 *
 * @author rlh
 * @project ailegorreta-kit-commons
 * @date March 2022
 */
class DateUtil {
    companion object {
        private val formatterDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
        private val formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        /**
         * Returns a <code>LocalDate</code> from a string according to the format
         * <code>yyyy-MM-dd'T'hh:mm:ss.SSS'Z'</code>
         */
        fun toLocalDate(date: String): LocalDate {

            if (date.indexOf('T') > -1) {
                val editedDate = date.substring(0, date.indexOf('T'))
                return LocalDate.parse(editedDate, formatterDate)
            }
            return LocalDate.parse(date, formatterDate)
        }

        /**
         * Returns a <code>LocalDateTime</code> from a string according to the format
         * <code>yyyy-MM-dd'T'hh:mm:ss.SSS'Z'</code>
         */
        fun toLocalDateTime(date: String): LocalDateTime {
            if (date.indexOf('T') == -1) {
                val editedDate = date + "T06:00:00.000Z"
                return LocalDateTime.parse(editedDate, formatterDateTime)
            }
            return LocalDateTime.parse(date, formatterDateTime)
        }

        /**
         * Returns a string from an object <code>LocalDate</code> with the standard company format
         * for dates, <code>yyyy-MM-dd'T'hh:mm:ss.SSS'Z'</code>
         */
        fun toStringFormat(fecha: LocalDate) : String {
            return formatterDate.format(fecha)
        }

        /**
         * Returns a string from and object <code>LocalDateTime</code> using the standard company format
         * for dates, <code>yyyy-MM-dd'T'hh:mm:ss.SSS'Z'</code>
         */
        fun toStringFormat(fecha: LocalDateTime) : String {
            return formatterDateTime.format(fecha)
        }

        fun substractDates(initialDate: Date, endDate: Date): Long {
            val id = convertToLocalDateViaInstant(initialDate)
            val ed = convertToLocalDateViaInstant(endDate)

            return ChronoUnit.DAYS.between(id, ed)
        }

        fun convertToLocalDateViaInstant(dateToConvert: Date): LocalDate {
            return dateToConvert.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
        }
    }
}
