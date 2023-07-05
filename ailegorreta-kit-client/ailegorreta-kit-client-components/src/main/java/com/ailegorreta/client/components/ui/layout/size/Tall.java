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
 *  Tall.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.ui.layout.size;

/**
 * css Lumo theme for margains, padding, spacing attributes
 *
 * s@project ailegorreta-kit--client-components
 * @author Vaadin
 * @date July 2022
 */
public enum Tall implements Size {

	XS("var(--lumo-space-xs)", "spacing-tall-xs"), S("var(--lumo-space-s)",
			"spacing-tall-s"), M("var(--lumo-space-m)", "spacing-tall-m"), L(
			"var(--lumo-space-l)", "spacing-tall-l"), XL(
			"var(--lumo-space-xl)", "spacing-tall-xl");

	private String variable;
	private String spacingClassName;

	Tall(String variable, String spacingClassName) {
		this.variable = variable;
		this.spacingClassName = spacingClassName;
	}

	@Override
	public String[] getMarginAttributes() {
		return new String[]{"margin"};
	}

	@Override
	public String[] getPaddingAttributes() {
		return new String[]{"padding"};
	}

	@Override
	public String getSpacingClassName() {
		return this.spacingClassName;
	}

	@Override
	public String getVariable() {
		return this.variable;
	}
}
