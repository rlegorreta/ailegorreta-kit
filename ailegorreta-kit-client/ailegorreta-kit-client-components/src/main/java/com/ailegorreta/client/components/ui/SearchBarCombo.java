/* Copyright (c) 2022, LMASS Desarrolladores, S.C:
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
 *  SearchBar.java
 *
 *  Developed 2022 by LMASS Desarrolladores www.lmass.com.mx
 */
package com.ailegorreta.client.components.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.DebounceSettings;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.DebouncePhase;
import com.vaadin.flow.component.template.Id;

/**
 * This if the SearchBarCombo component for the structure defined
 * for searching component with in a combo box.
 *
 * This component is the shown the search bar criteria for a Grid.
 *
 * @see //vaadin.com/docs/business-app/overview.html
 *
 *  The new version was changed as a LiteEmlement instead of a
 *  Polymeter element
 *
 * @author rlh
 * @project ailegorreta-kit-client-components
 * @date July 2023
 */
@Tag("search-bar-combo")
@JsModule("./src/components/search-bar-combo.js")
public class SearchBarCombo extends LitTemplate {

    @Id
    private TextField field;

    @Id
    private ComboBox<String> combobox;

    @Id
    private Button clear;

    @Id
    private Button action;

    public SearchBarCombo() {
        field.setValueChangeMode(ValueChangeMode.EAGER);
        ComponentUtil.addListener(field, SearchValueChanged.class,
                e -> fireEvent(new FilterChanged(this, false)));
        combobox.addValueChangeListener( e -> fireEvent(new FilterChanged(this, false)));
        clear.addClickListener(e -> {
            field.clear();
            combobox.setValue(null);
        });

        getElement().addPropertyChangeListener("comboboxValue", e -> fireEvent(new FilterChanged(this, false)));
    }

    public String getFilter() {
        return field.getValue();
    }

    public Object comboboxValue() {
        return combobox.getValue();
    }

    public ComboBox comboBox() { return combobox; }

    public void setPlaceHolder(String placeHolder) {
        field.setPlaceholder(placeHolder);
    }

    public void setActionText(String actionText) {
        action.setText(actionText);
    }

    public void addFilterChangeListener(ComponentEventListener<FilterChanged> listener) {
        this.addListener(FilterChanged.class, listener);
    }

    public void addActionClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        action.addClickListener(listener);
    }

    public Button getActionButton() {
        return action;
    }

    @DomEvent(value = "value-changed", debounce = @DebounceSettings(timeout = 300, phases = DebouncePhase.TRAILING))
    public static class SearchValueChanged extends ComponentEvent<TextField> {
        public SearchValueChanged(TextField source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    public static class FilterChanged extends ComponentEvent<SearchBarCombo> {
        public FilterChanged(SearchBarCombo source, boolean fromClient) {
            super(source, fromClient);
        }
    }
}

