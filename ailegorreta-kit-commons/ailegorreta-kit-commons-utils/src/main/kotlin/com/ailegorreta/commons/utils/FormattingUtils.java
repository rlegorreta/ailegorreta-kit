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
 *  FormattingUtils.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.commons.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * Utility to do value formatting.
 *
 * @author Vaadin
 * @project ailegorreta-kit-commont-utils
 * @date May 2023
 */
public class FormattingUtils {

    public static final Locale APP_LOCALE = Locale.US;
    public static final String DECIMAL_ZERO = "0.00";

    /**
     * 3 letter month name + day number E.g: Nov 20
     */
    public static final DateTimeFormatter MONTH_AND_DAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d",
            APP_LOCALE);

    /**
     * Full day name. E.g: Monday.
     */
    public static final DateTimeFormatter WEEKDAY_FULLNAME_FORMATTER = DateTimeFormatter.ofPattern("EEEE",
            APP_LOCALE);

    /**
     * For getting the week of the year from the local date.
     */
    public static final TemporalField WEEK_OF_YEAR_FIELD = WeekFields.of(APP_LOCALE).weekOfWeekBasedYear();

    /**
     * 3 letter day of the week + day number. E.g: Mon 20
     */
    public static final DateTimeFormatter SHORT_DAY_FORMATTER = DateTimeFormatter.ofPattern("E d",
            APP_LOCALE);

    /**
     * Format an Instant that needs a time zone
     */
    public static final DateTimeFormatter LOCALDATETIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
            .withLocale( Locale.UK )
            .withZone( ZoneId.systemDefault());

    /**
     * Full date format. E.g: 03/03/2001
     */
    public static final DateTimeFormatter FULL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy", APP_LOCALE);

    /**
     * Full date format. E.g: 2021/03/03
     */
    public static final DateTimeFormatter FINANCIAL_FULL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd", APP_LOCALE);

    /**
     * Formats hours with am/pm. E.g: 2:00 PM
     */
    public static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter
            .ofPattern("h:mm a", APP_LOCALE);

    /**
     * Returns the month name of the date, according to the application locale.
     * @param date {@link LocalDate}
     * @return The full month name. E.g: November
     */
    public static String getFullMonthName(LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.FULL, APP_LOCALE);
    }

    public static String formatAsCurrency(BigDecimal value) {
        return NumberFormat.getCurrencyInstance(APP_LOCALE).format(value);
    }

    public static DecimalFormat getUiPriceFormatter() {
        DecimalFormat formatter = new DecimalFormat("#" + DECIMAL_ZERO,
                DecimalFormatSymbols.getInstance(APP_LOCALE));
        formatter.setGroupingUsed(false);
        return formatter;
    }
}
