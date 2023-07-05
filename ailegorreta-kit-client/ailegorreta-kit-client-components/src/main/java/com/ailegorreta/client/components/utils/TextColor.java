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
 *  TextColor.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.utils;

/**
 * css values for TextColor
 *
 * @project lskit-vaadin-components
 * @author Vaadin
 * @date Febriuary 2022
 */
public enum TextColor {

    HEADER("var(--lumo-header-text-color)"),
    BODY("var(--lumo-body-text-color)"),
    SECONDARY("var(--lumo-secondary-text-color)"),
    TERTIARY("var(--lumo-tertiary-text-color)"),
    DISABLED("var(--lumo-disabled-text-color)"),
    PRIMARY("var(--lumo-primary-text-color)"),
    PRIMARY_CONTRAST("var(--lumo-primary-contrast-color)"),
    ERROR("var(--lumo-error-text-color)"),
    ERROR_CONTRAST("var(--lumo-error-contrast-color)"),
    SUCCESS("var(--lumo-success-text-color)"),
    SUCCESS_CONTRAST("var(--lumo-success-contrast-color)");

    private String value;

    TextColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
