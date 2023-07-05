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
 *  DetailsDrawerFooter.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.navigation.detailsdrawer;

import com.ailegorreta.client.components.ui.FlexBoxLayout;
import com.ailegorreta.client.components.ui.layout.size.*;
import com.ailegorreta.client.components.utils.LumoStyles;
import com.ailegorreta.client.components.utils.UIUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.shared.Registration;

/**
 * This if the DetailDrawer Footer component for the structure defined in the
 * Business App Starter defined in Vaadin.
 *
 * @see //vaadin.com/docs/business-app/overview.html
 *
 * @author Vaadin
 * @project ailegorreta-kit-client-components
 * @date July 2023
 */
public class DetailsDrawerFooter extends FlexBoxLayout {
	private static final long serialVersionUID = 39410190000464868L;
	
	private Button save;
	private Button cancel;
	private Button delete = null;

	public DetailsDrawerFooter() {
		setBackgroundColor(LumoStyles.Color.Contrast._5);
		setPadding(Horizontal.RESPONSIVE_L, Vertical.S);
		setSpacing(Right.S);
		setWidthFull();

		save = UIUtils.createPrimaryButton("Guardar");
		cancel = UIUtils.createTertiaryButton("Cancelar");
		add(save, cancel);
	}

	public DetailsDrawerFooter(Boolean withDelete) {
		setBackgroundColor(LumoStyles.Color.Contrast._5);
		setPadding(Horizontal.RESPONSIVE_L, Vertical.S);
		setSpacing(Right.S);
		setWidthFull();

		save = UIUtils.createPrimaryButton("Guardar");
		cancel = UIUtils.createTertiaryButton("Cancelar");
		if (withDelete) {
			delete = UIUtils.createPrimaryButton("Eliminar");
			add(save, delete, cancel);
		} else
			add(save, cancel);
	}

	public Registration addSaveListener(ComponentEventListener<ClickEvent<Button>> listener) {
		return save.addClickListener(listener);
	}

	public Registration addCancelListener( ComponentEventListener<ClickEvent<Button>> listener) {
		return cancel.addClickListener(listener);
	}

	public Registration addDeleteListener( ComponentEventListener<ClickEvent<Button>> listener) {
		return delete.addClickListener(listener);
	}
	
	public Button getSaveButton() {
		return save;
	}

	public Button getDeleteButton() {
		return delete;
	}
}
