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
 *  DetailsDrawer.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.navigation.detailsdrawer;

import com.ailegorreta.client.components.ui.FlexBoxLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

/**
 * This if the DetailDrawer component for the structure defined in the
 * Business App Starter defined in Vaadin.
 *
 * @see //vaadin.com/docs/business-app/overview.html
 *
 * @author Vaadin
 * @project ailegorreta-kit-client-components
 * @date July 2023
 */
@CssImport("./styles/components/details-drawer.css")
public class DetailsDrawer extends FlexBoxLayout {
    private static final long serialVersionUID = 8571449270107319629L;

    private static final String CLASS_NAME = "details-drawer";

    private FlexBoxLayout header;
    private FlexBoxLayout content;
    private FlexBoxLayout footer;

    public enum Position {
        BOTTOM, RIGHT
    }

    public DetailsDrawer(Position position, Component... components) {
        setClassName(CLASS_NAME);
        setPosition(position);

        header = new FlexBoxLayout();
        header.setClassName(CLASS_NAME + "__header");

        content = new FlexBoxLayout(components);
        content.setClassName(CLASS_NAME + "__content");
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);

        footer = new FlexBoxLayout();
        footer.setClassName(CLASS_NAME + "__footer");

        add(header, content, footer);
    }

    public void setHeader(Component... components) {
        this.header.removeAll();
        this.header.add(components);
    }

    public FlexBoxLayout getHeader() {
        return this.header;
    }

    public void setContent(Component... components) {
        this.content.removeAll();
        this.content.add(components);
    }

    public void setFooter(Component... components) {
        this.footer.removeAll();
        this.footer.add(components);
    }

    public FlexBoxLayout getFooter() {
        return this.footer;
    }

    public void setPosition(Position position) {
        getElement().setAttribute("position", position.name().toLowerCase());
    }

    public void hide() {
        getElement().setAttribute("open", false);
    }

    public void show() {
        getElement().setAttribute("open", true);
    }
}
