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
 *  ListItem.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.ailegorreta.client.components.ui.layout.size.*;
import com.ailegorreta.client.components.utils.*;
import com.ailegorreta.client.components.utils.css.*;

/**
 * This if the ListItem component for the structure defined in the
 * Business App Starter defined in Vaadin.
 *
 * This component list in a nicely manner some initial short name.
 *
 * @see //vaadin.com/docs/business-app/overview.html
 *
 * @author Vaadin
 * @project ailegorreta-kit-client-components
 * @date July 2023
 */
@CssImport("./styles/components/list-item.css")
public class ListItem extends FlexBoxLayout {
    private static final long serialVersionUID = -5191730559888211969L;

    private static final String CLASS_NAME = "list-item";

    private Div prefix;
    private Div suffix;

    private FlexBoxLayout content;

    private Span primary;
    private Span secondary;

    public ListItem(String primary, String secondary) {
        addClassName(CLASS_NAME);

        setAlignItems(FlexComponent.Alignment.CENTER);
        setPadding(Wide.RESPONSIVE_L);
        setSpacing(Right.L);

        this.primary = new Span(primary);
        this.secondary = UIUtils.createSpan(FontSize.S, TextColor.SECONDARY, secondary);

        content = new FlexBoxLayout(this.primary, this.secondary);
        content.setClassName(CLASS_NAME + "__content");
        content.setFlexDirection(FlexDirection.COLUMN);
        add(content);
    }

    public ListItem(String primary) {
        this(primary, "");
    }

    /* === PREFIX === */

    public ListItem(Component prefix, String primary, String secondary) {
        this(primary, secondary);
        setPrefix(prefix);
    }

    public ListItem(Component prefix, String primary) {
        this(prefix, primary, "");
    }

    /* === SUFFIX === */

    public ListItem(String primary, String secondary, Component suffix) {
        this(primary, secondary);
        setSuffix(suffix);
    }

    public ListItem(String primary, Component suffix) {
        this(primary, null, suffix);
    }

    /* === PREFIX & SUFFIX === */

    public ListItem(Component prefix, String primary, String secondary, Component suffix) {
        this(primary, secondary);
        setPrefix(prefix);
        setSuffix(suffix);
    }

    public ListItem(Component prefix, String primary, Component suffix) {
        this(prefix, primary, "", suffix);
    }

    /* === MISC === */

    public FlexBoxLayout getContent() {
        return content;
    }

    public void setWhiteSpace(WhiteSpace whiteSpace) {
        UIUtils.setWhiteSpace(whiteSpace, this);
    }

    public void setReverse(boolean reverse) {
        if (reverse)
            content.setFlexDirection(FlexDirection.COLUMN_REVERSE);
        else
            content.setFlexDirection(FlexDirection.COLUMN);
    }

    public void setHorizontalPadding(boolean horizontalPadding) {
        if (horizontalPadding) {
            getStyle().remove("padding-left");
            getStyle().remove("padding-right");
        } else {
            getStyle().set("padding-left", "0");
            getStyle().set("padding-right", "0");
        }
    }

    public void setPrimaryText(String text) {
        primary.setText(text);
    }

    public Span getPrimary() {
        return primary;
    }

    public void setSecondaryText(String text) {
        secondary.setText(text);
    }

    public void setPrefix(Component... components) {
        if (prefix == null) {
            prefix = new Div();
            prefix.setClassName(CLASS_NAME + "__prefix");
            getElement().insertChild(0, prefix.getElement());
            getElement().setAttribute("with-prefix", true);
        }
        prefix.removeAll();
        prefix.add(components);
    }

    public void setSuffix(Component... components) {
        if (suffix == null) {
            suffix = new Div();
            suffix.setClassName(CLASS_NAME + "__suffix");
            getElement().insertChild(getElement().getChildCount(), suffix.getElement());
            getElement().setAttribute("with-suffix", true);
        }
        suffix.removeAll();
        suffix.add(components);
    }

    public void setDividerVisible(boolean visible) {
        if (visible)
            getElement().setAttribute("with-divider", true);
        else
            getElement().removeAttribute("with-divider");
    }

}
