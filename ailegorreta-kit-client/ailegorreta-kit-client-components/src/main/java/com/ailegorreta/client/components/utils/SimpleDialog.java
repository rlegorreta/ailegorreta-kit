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
 *  SimpleDialog.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.utils;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Simple dialog box.
 *
 *  @project ailegorreta-kit-client-components
 * @author Vaadin
 * @date July 2023
 */
public class SimpleDialog extends Dialog {

    public interface Caller {

        void dialogResponseOk(SimpleDialog dialog);

        void dialogResponseCancel(SimpleDialog dialog);
    }

    Button ok = new Button("Continuar");
    Button cancel = new Button("Cancelar");

    private SimpleDialog(String caption,
                         String okTheme, String cancelTheme,
                         Boolean modal) {
        if (okTheme != null)
            ok.getElement().setAttribute("theme", okTheme);
        if (cancelTheme != null)
            cancel.getElement().setAttribute("theme", cancelTheme);

        HorizontalLayout form = new HorizontalLayout(cancel, ok);
        VerticalLayout dialogLayout = new VerticalLayout(form);

        dialogLayout.setWidth("100%");
        dialogLayout.setMargin(false);
        dialogLayout.setPadding(false);

        if (modal) {
            this.setCloseOnEsc(false);
            this.setCloseOnOutsideClick(false);
        }
        this.add(new H3(caption), dialogLayout);
        this.setWidth("600px");				// default value
    }

    public SimpleDialog(String caption,
                        String okTheme, String cancelTheme,
                        Boolean modal, Caller caller) {
        this(caption, okTheme, cancelTheme, modal);
        ok.addClickListener(event -> {
            caller.dialogResponseOk(this);
            this.close();
        });
        cancel.addClickListener(event -> {
            caller.dialogResponseCancel(this);
            this.close();
        });
    }

    public SimpleDialog(String caption, Caller caller) {
        this(caption, "primary", null, true, caller);
    }

    public SimpleDialog(String caption, Boolean forDelete, Caller caller) {
        this(caption, forDelete ? "error" : "primary", null, true, caller);
    }

    public SimpleDialog(String caption,
                        String okTheme, String cancelTheme,
                        Boolean modal,
                        ComponentEventListener<ClickEvent<Button>> okButtonClickListener,
                        ComponentEventListener<ClickEvent<Button>> cancelButtonClickListener ) {
        this(caption, okTheme, cancelTheme, modal);
        ok.addClickListener(event -> {
            okButtonClickListener.onComponentEvent(event);
            this.close();
        });
        cancel.addClickListener(event -> {
            cancelButtonClickListener.onComponentEvent(event);
            this.close();
        });
    }

    public SimpleDialog(String caption,
                        ComponentEventListener<ClickEvent<Button>> okButtonClickListener,
                        ComponentEventListener<ClickEvent<Button>> cancelButtonClickListener) {
        this(caption, "primary", null, true, okButtonClickListener, cancelButtonClickListener);
    }

    public SimpleDialog(String caption, Boolean forDelete,
                        ComponentEventListener<ClickEvent<Button>> okButtonClickListener,
                        ComponentEventListener<ClickEvent<Button>> cancelButtonClickListener) {
        this(caption, forDelete ? "error" : "primary", null, true,
                okButtonClickListener, cancelButtonClickListener);
    }

    public SimpleDialog start() {
        this.open();
        return this;
    }
}

