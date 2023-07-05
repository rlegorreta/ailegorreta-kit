/* Copyright (c) 2022, LMASS Desarrolladores, S.C.
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
 *  DateToFinancialDateConverter.kt
 *
 *  Developed 2022 by LMASS Desarrolladores, S.C www.lmass.com.mx
 */
package com.ailegorreta.client.components.utils.converter

import com.ailegorreta.client.components.utils.UIUtils
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.converter.Converter
import java.time.LocalDate

/**
 * Converters to be utilized in Vaadin Grid, an TextFields to handle scaling
 * and formatting for Financial Dates (YYYY MM DD)
 *
 * @author Rlh
 * @project lmasskit-client-components
 * @date February 2022
 */
class DateToFinancialDateConverter (emptyValue: LocalDate?): Converter<String, LocalDate> {

    override fun convertToModel(value: String?,context: ValueContext?): Result<LocalDate> {
        if (value == null)
            return Result.ok(LocalDate.now())

        return Result.ok(LocalDate.parse(value))

    }

    override fun convertToPresentation(date: LocalDate, valueContext: ValueContext?): String {
        return UIUtils.financialFormatDate(date)
    }
}
