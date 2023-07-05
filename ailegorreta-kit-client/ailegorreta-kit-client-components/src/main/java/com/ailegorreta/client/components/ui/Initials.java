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
 *  Initials.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.ui;

import com.ailegorreta.client.components.utils.css.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.ailegorreta.client.components.utils.*;

/**
 * This if the Initials component for the structure defined in the
 * IAM-UI defined in Vaadin.
 *
 * This component list in a nice manner some initial short name.
 *
 * @see //vaadin.com/docs/business-app/overview.html
 *
 * @author Vaadin
 * @project ailegorreta-kit-client-components
 * @date July 2023
 */
@SuppressWarnings("serial")
public class Initials extends FlexBoxLayout {

    private static final String CLASS_NAME = "initials";

    public Initials(String initials) {
        setAlignItems(FlexComponent.Alignment.CENTER);
        setBackgroundColor(LumoStyles.Color.Contrast._10);
        setBorderRadius(BorderRadius.L);
        setClassName(CLASS_NAME);
        UIUtils.setFontSize(FontSize.S, this);
        UIUtils.setFontWeight(FontWeight._600, this);
        setHeight(LumoStyles.Size.M);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setWidth(LumoStyles.Size.M);

        add(initials);
    }

}
