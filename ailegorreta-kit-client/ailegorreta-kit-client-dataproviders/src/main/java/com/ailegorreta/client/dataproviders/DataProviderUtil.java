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
 *  DataProviderUtil.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.dataproviders;

import java.util.function.Function;
import java.util.function.Supplier;

import com.vaadin.flow.component.ItemLabelGenerator;

/**
 * Singleton object just to have some useful methods for the DataProviders
 *
 *  @author rlh
 *  @project : ailegorreta-kit-client-dataproviders
 *  @date July 2023
 */
public class DataProviderUtil {

	public static <S, T> T convertIfNotNull(S source, Function<S, T> converter) {
		return convertIfNotNull(source, converter, () -> null);
	}

	public static <S, T> T convertIfNotNull(S source, Function<S, T> converter, Supplier<T> nullValueSupplier) {
		return source != null ? converter.apply(source) : nullValueSupplier.get();
	}

	public static <T> ItemLabelGenerator<T> createItemLabelGenerator(Function<T, String> converter) {
		return item -> convertIfNotNull(item, converter, () -> "");
	}
}
