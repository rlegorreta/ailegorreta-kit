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
 *  PredicateDate.java
 *
 *  Developed 2023 by LMASS Desarrolladores, S.C. www.lmass.com.mx
 */
package com.ailegorreta.data.jpa.querydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Singleton class to get a LocalDateTime query from a QueryDSL repository.
 *
 * An example of usage of this class is the EventDate filter used in sys-ui microservice that calls the
 * auth microservice. The aut server repo generates a MongoDB query for this date ranges
 *
 * @progect ailegorreta-kit-data-jpa
 * @author rlh
 * @date August 2023
 */
public class PredicateDate {

    public static Predicate getPredicateDate(DateTimePath<LocalDateTime> dateTimeDateTimePath,
                                             String dateValue) {
        if (dateValue.isEmpty()) return null;
        // custom words
        if (dateValue.equals("hoy")) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0);
            LocalDateTime endDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59);

            return dateTimeDateTimePath.between(startDate, endDate);
        }
        if (dateValue.equals("ayer")) {
            LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
            LocalDateTime startDate = LocalDateTime.of(yesterday.getYear(), yesterday.getMonth(), yesterday.getDayOfMonth(), 0, 0);
            LocalDateTime endDate = LocalDateTime.of(yesterday.getYear(), yesterday.getMonth(), yesterday.getDayOfMonth(), 23, 59);

            return dateTimeDateTimePath.between(startDate, endDate);
        }
        if (dateValue.equals("mañana")) {
            LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
            LocalDateTime startDate = LocalDateTime.of(tomorrow.getYear(), tomorrow.getMonth(), tomorrow.getDayOfMonth(), 0, 0);
            LocalDateTime endDate = LocalDateTime.of(tomorrow.getYear(), tomorrow.getMonth(), tomorrow.getDayOfMonth(), 23, 59);

            return dateTimeDateTimePath.between(startDate, endDate);
        }
        if (dateValue.equals("semana")) {
            LocalDateTime oneWeek = LocalDateTime.now().minusDays(7);
            LocalDateTime startDate = LocalDateTime.of(oneWeek.getYear(), oneWeek.getMonth(), oneWeek.getDayOfMonth(), 0, 0);

            return dateTimeDateTimePath.between(startDate, LocalDateTime.now());
        }
        // dates
        var yearsMonthsDays = dateValue.split("-");

        switch (yearsMonthsDays.length) {
            case 1: /* just year */ {
                if (!isNumeric(yearsMonthsDays[0])) return null;
                try {
                    Integer year = Integer.parseInt(yearsMonthsDays[0]);
                    LocalDateTime startDate = LocalDateTime.of(year, 1, 1, 0, 0);
                    LocalDateTime endDate = LocalDateTime.of(year, 12, 31, 23, 59);

                    return dateTimeDateTimePath.between(startDate, endDate);
                } catch (Exception e) {
                    return null;  // invalid year
                }
            }
            case 2: /* year & month */ {
                if (!isNumeric(yearsMonthsDays[0]) || !isNumeric(yearsMonthsDays[1])) return null;
                try {
                    Integer year = Integer.parseInt(yearsMonthsDays[0]);
                    Integer month = Integer.parseInt(yearsMonthsDays[1]);

                    if (month <= 0 || month > 12) return null;
                    LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
                    LocalDateTime endDate = startDate.plusMonths(1);

                    return dateTimeDateTimePath.between(startDate, endDate);
                } catch (Exception e) {
                    return null;  // invalid year & month
                }
            }
            case 3: /* year & month & day */ {
                if (!isNumeric(yearsMonthsDays[0]) || !isNumeric(yearsMonthsDays[1]) || !isNumeric(yearsMonthsDays[2]))
                    return null;
                try {
                    Integer year = Integer.parseInt(yearsMonthsDays[0]);
                    Integer month = Integer.parseInt(yearsMonthsDays[1]);
                    Integer day = Integer.parseInt(yearsMonthsDays[2]);

                    if (month <= 0 || month > 12) return null;
                    if (day <= 0 || day > 31) return null;

                    LocalDateTime startDate = LocalDateTime.of(year, month, day, 0, 0);
                    LocalDateTime endDate = LocalDateTime.of(year, month, day, 23, 59);

                    return dateTimeDateTimePath.between(startDate, endDate);
                } catch (Exception e) {
                    return null;  // invalid year monto or day
                }
            }
            case 4: /* year & month & day, toDay */ {
                if (!isNumeric(yearsMonthsDays[0]) || !isNumeric(yearsMonthsDays[1]) || !isNumeric(yearsMonthsDays[2]) ||
                        !isNumeric(yearsMonthsDays[3])) return null;
                try {
                    Integer fromYear = Integer.parseInt(yearsMonthsDays[0]);
                    Integer fromMonth = Integer.parseInt(yearsMonthsDays[1]);
                    Integer fromDay = Integer.parseInt(yearsMonthsDays[2]);
                    Integer toDay = Integer.parseInt(yearsMonthsDays[3]);

                    if (fromMonth > 12) return null;
                    if (fromDay > 31 || toDay > 31) return null;

                    LocalDateTime startDate = LocalDateTime.of(fromYear, fromMonth, fromDay, 0, 0);
                    LocalDateTime endDate = LocalDateTime.of(fromYear, fromMonth, toDay, 23, 59);

                    return dateTimeDateTimePath.between(startDate, endDate);
                } catch (Exception e) {
                    return null;  // invalid year, month, day or toDay
                }
            }
            case 5: /* year & month & day, toMonth, toDay */ {
                if (!isNumeric(yearsMonthsDays[0]) || !isNumeric(yearsMonthsDays[1]) || !isNumeric(yearsMonthsDays[2]) ||
                        !isNumeric(yearsMonthsDays[3]) || !isNumeric(yearsMonthsDays[4])) return null;
                try {
                    Integer fromYear = Integer.parseInt(yearsMonthsDays[0]);
                    Integer fromMonth = Integer.parseInt(yearsMonthsDays[1]);
                    Integer fromDay = Integer.parseInt(yearsMonthsDays[2]);
                    Integer toMonth = Integer.parseInt(yearsMonthsDays[3]);
                    Integer toDay = Integer.parseInt(yearsMonthsDays[4]);

                    if (fromMonth > 12 || toMonth > 12) return null;
                    if (fromDay > 31 || toDay > 31) return null;

                    LocalDateTime startDate = LocalDateTime.of(fromYear, fromMonth, fromDay, 0, 0);
                    LocalDateTime endDate = LocalDateTime.of(fromYear, toMonth, toDay, 23, 59);

                    return dateTimeDateTimePath.between(startDate, endDate);
                } catch (Exception e) {
                    return null;  // invalid year, month, day, toMonth or toDay
                }
            }
            case 6: /* year & month & day, two dates*/ {
                if (!isNumeric(yearsMonthsDays[0]) || !isNumeric(yearsMonthsDays[1]) || !isNumeric(yearsMonthsDays[2]) ||
                        !isNumeric(yearsMonthsDays[3]) || !isNumeric(yearsMonthsDays[4]) || !isNumeric(yearsMonthsDays[5]))
                    return null;
                try {
                    Integer fromYear = Integer.parseInt(yearsMonthsDays[0]);
                    Integer fromMonth = Integer.parseInt(yearsMonthsDays[1]);
                    Integer fromDay = Integer.parseInt(yearsMonthsDays[2]);
                    Integer toYear = Integer.parseInt(yearsMonthsDays[3]);
                    Integer toMonth = Integer.parseInt(yearsMonthsDays[4]);
                    Integer toDay = Integer.parseInt(yearsMonthsDays[5]);

                    if (fromMonth > 12 || toMonth > 12) return null;
                    if (fromDay > 31 || toDay > 31) return null;

                    LocalDateTime startDate = LocalDateTime.of(fromYear, fromMonth, fromDay, 0, 0);
                    LocalDateTime endDate = LocalDateTime.of(toYear, toMonth, toDay, 23, 59);

                    return dateTimeDateTimePath.between(startDate, endDate);
                } catch (Exception e) {
                    return null;  // // invalid year, month, day, toYear, toMonth or toDay
                }
            }
            default:
                return null;
        }
    }
}