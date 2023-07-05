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
 *  FlexBoxLayout.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.ui;

import com.ailegorreta.client.components.ui.layout.size.Size;
import com.ailegorreta.client.components.utils.css.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.*;

/**
 * This if the FlexBoxLayout component for the structure defined in the
 * Business App Starter defined in Vaadin.
 *
 * FlexBoxLayout is a server-side implementation of CSS Flexible Box Layout.
 * It extends FlexLayout and contains convenience methods for setting flex,
 * flex-basis, flex-direction, flex-shrink and flex-wrap. It also supports
 * setting background-color, margin, overflow, padding, shadow, spacing and theme.
 *
 * @see //vaadin.com/docs/business-app/overview.html
 *
 * @author Vaadin
 * @project ailegorreta-kit-client-components
 * @date July 2023
 */
public class FlexBoxLayout extends FlexLayout {
    private static final long serialVersionUID = 3589614797106729096L;

    private ArrayList<Size> spacings;

    public FlexBoxLayout(Component... components) {
        super(components);
        spacings = new ArrayList<>();
    }

    public void setBackgroundColor(String value) {
        getStyle().set("background-color", value);
    }

    public void setBackgroundColor(String value, String theme) {
        getStyle().set("background-color", value);
        setTheme(theme);
    }

    public void removeBackgroundColor() {
        getStyle().remove("background-color");
    }

    public void setBorderRadius(BorderRadius radius) {
        getStyle().set("border-radius", radius.getValue());
    }

    public void removeBorderRadius() {
        getStyle().remove("border-radius");
    }

    public void setBoxSizing(BoxSizing sizing) {
        getStyle().set("box-sizing", sizing.getValue());
    }

    public void removeBoxSizing() {
        getStyle().remove("box-sizing");
    }

    public void setDisplay(Display display) {
        getStyle().set("display", display.getValue());
    }

    public void removeDisplay() {
        getStyle().remove("display");
    }

    public void setFlex(String value, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("flex", value);
    }

    public void setFlexBasis(String value, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("flex-basis", value);
    }

    public void setFlexShrink(String value, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("flex-shrink", value);
    }

    public void setFlexWrap(FlexWrap wrap) {
        getStyle().set("flex-wrap", wrap.toString());
    }

    public void removeFlexWrap() {
        getStyle().remove("flex-wrap");
    }

    public void setMargin(Size... sizes) {
        for (Size size : sizes)
            for (String attribute : size.getMarginAttributes())
                getStyle().set(attribute, size.getVariable());
    }

    public void removeMargin() {
        getStyle().remove("margin");
        getStyle().remove("margin-top");
        getStyle().remove("margin-botton");
        getStyle().remove("margin-left");
        getStyle().remove("margin-right");
    }

    public void setMaxWidth(String value) {
        getStyle().set("max-width", value);
    }

    public void removeMaxWidth() {
        getStyle().remove("max-width");
    }

    public void setOverflow(Overflow overflow) {
        getStyle().set("overflow", overflow.getValue());
    }

    public void removeOverflow() {
        getStyle().remove("overflow");
    }

    public void setPadding(Size... sizes) {
        removePadding();
        for (Size size : sizes)
            for (String attribute : size.getPaddingAttributes())
                getStyle().set(attribute, size.getVariable());
    }

    public void removePadding() {
        getStyle().remove("padding");
        getStyle().remove("padding-botton");
        getStyle().remove("padding-top");
        getStyle().remove("padding-left");
        getStyle().remove("padding-right");
    }

    public void setPosition(Position position) {
        getStyle().set("position", position.getValue());
    }

    public void removePosition() {
        getStyle().remove("position");
    }

    public void setShadow(Shadow shadow) {
        getStyle().set("box-shadow", shadow.getValue());
    }

    public void removeShadow() {
        getStyle().remove("box-shadow");
    }

    public void setSpacing(Size... sizes) {
        // Remove old styles (if applicable)
        for (Size spacing : spacings)
            removeClassName(spacing.getSpacingClassName());
        spacings.clear();

        // Add new
        for (Size size : sizes) {
            addClassName(size.getSpacingClassName());
            spacings.add(size);
        }
    }

    public void setTheme(String theme) {
        if (Lumo.DARK.equals(theme))
            getElement().setAttribute("theme", Lumo.DARK);
        else
            getElement().removeAttribute("theme");
    }
}

