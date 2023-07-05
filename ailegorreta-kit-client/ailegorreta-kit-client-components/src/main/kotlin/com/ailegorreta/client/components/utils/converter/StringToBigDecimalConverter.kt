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
 *  StringToBigDecimalConverter.kt
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.utils.converter

import com.ailegorreta.client.components.utils.formatters.Scale
import com.vaadin.flow.data.binder.ValueContext
import java.math.BigDecimal
import java.math.RoundingMode
import com.vaadin.flow.data.binder.Result
import java.util.*

/**
 * Converters to be uilized in Vaadin Grid, an TextFields to handle scaling
 * and formatting
 *
 * This converter is for BigDecimals
 *
 * @author Rlh
 * @project ailegorreta-kit-client-components
 * @date July 2023
 */
class StringToBigDecimalConverter (emptyValue: BigDecimal?,
                                   private val scale: Scale
):
    com.vaadin.flow.data.converter.StringToBigDecimalConverter(emptyValue, scale.errorMessage) {

    override fun convertToModel(value: String?,context: ValueContext?): Result<BigDecimal> {
        if (value.isNullOrEmpty())
            return Result.ok(BigDecimal.ZERO.setScale(scale.scale, RoundingMode.CEILING))
        try {
            val result = BigDecimal(value.replace(",",""))

            if (scale.precision != null && result >= (BigDecimal.TEN.pow(scale.precision - scale.scale)))
                return Result.error(scale.errorMessage + " " + scale.description())  // Arithmetic overflow

            result.setScale(scale.scale, RoundingMode.CEILING)

            return Result.ok(result)
        } catch (e : NumberFormatException) {
            return Result.error(scale.errorMessage + " " + scale.description())
        }
    }

    override fun getFormat(locale: Locale?) = scale.format
}
