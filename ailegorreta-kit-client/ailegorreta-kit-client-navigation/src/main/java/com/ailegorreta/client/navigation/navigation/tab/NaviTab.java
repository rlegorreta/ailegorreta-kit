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
 *  ClosableNaviTab.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.navigation.navigation.tab;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tab;

import java.util.Objects;

/**
 * This if the NaviTab component for the structure defined in the
 * Business App Starter defined in Vaadin.
 *
 * @see //vaadin.com/docs/business-app/overview.html
 *
 * @author Vaadin
 * @project ailegorreta-kit-client-navigator
 * @date July 2023
 */
public class NaviTab extends Tab {

	private Class<? extends Component> navigationTarget;

	public NaviTab(String label, Class<? extends Component> navigationTarget) {
		super(label);
		setNavigationTarget(navigationTarget);
	}

	public Class<? extends Component> getNavigationTarget() {
		return navigationTarget;
	}

	public void setNavigationTarget(
			Class<? extends Component> navigationTarget) {
		this.navigationTarget = Objects.requireNonNull(navigationTarget);
	}
}
