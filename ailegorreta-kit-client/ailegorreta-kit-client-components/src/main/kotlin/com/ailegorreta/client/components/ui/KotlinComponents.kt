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
 *  KotlinComponents.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.ui

import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.radiobutton.RadioButtonGroup

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.init

/**
 * This file declares Custom components declared in Java or Kotlin and wrapped them
 * in order to be used with Kalibu-DSL Vaddin on Kotlin
 *
 * @author rlh
 * @project lmasskit-client-components
 * @date February 2022
 */
@VaadinDsl
fun (@VaadinDsl HasComponents).searchBar( block: (@VaadinDsl SearchBar).() -> Unit = {}) = init(SearchBar(), block)

@VaadinDsl
fun (@VaadinDsl HasComponents).searchBarCombo( block: (@VaadinDsl SearchBarCombo).() -> Unit = {}) = init(SearchBarCombo(), block)

@VaadinDsl
fun (@VaadinDsl HasComponents).flexBoxLayout( block: (@VaadinDsl FlexBoxLayout).() -> Unit = {}) = init(FlexBoxLayout(), block)

@VaadinDsl
fun (@VaadinDsl HasComponents).initials(initials: String, block: (@VaadinDsl Initials).() -> Unit = {}) = init(Initials(initials), block)

@VaadinDsl
fun (@VaadinDsl HasComponents).radioButtonGroup(block: (@VaadinDsl RadioButtonGroup<String>).() -> Unit = {}) = init(RadioButtonGroup<String>(), block)

@VaadinDsl
fun (@VaadinDsl HasComponents).twinColSelect(unSelItems:Set<String>, selItems:Set<String>, block: (@VaadinDsl TwinColSelect<String>).() -> Unit = {}) = init(TwinColSelect(unSelItems, selItems), block)

@VaadinDsl
fun (@VaadinDsl HasComponents).validTextField(text:String, block: (@VaadinDsl ValidTextField).() -> Unit = {}) = init(ValidTextField(text), block)

@VaadinDsl
fun (@VaadinDsl HasComponents).validTextField(block: (@VaadinDsl ValidTextField).() -> Unit = {}) = init(ValidTextField(), block)

