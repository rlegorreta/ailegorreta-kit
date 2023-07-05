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
 *  Wide.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.ui.layout.size;

/**
 * css Lumo theme for margins, padding, spacing attributes
 *
 * @project ailegorreta-kit-client-components
 * @author Vaadi
 * @date July 2023
 */
public enum Wide implements Size {

	XS("var(--lumo-space-wide-xs)", "spacing-wide-xs"), S(
			"var(--lumo-space-wide-s)",
			"spacing-wide-s"), M("var(--lumo-space-wide-m)",
			"spacing-wide-m"), L("var(--lumo-space-wide-l)",
			"spacing-wide-l"), XL("var(--lumo-space-wide-xl)",
			"spacing-wide-xl"),

	RESPONSIVE_M("var(--lumo-space-wide-r-m)",
			null), RESPONSIVE_L("var(--lumo-space-wide-r-l)", null);

	private String variable;
	private String spacingClassName;

	Wide(String variable, String spacingClassName) {
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
