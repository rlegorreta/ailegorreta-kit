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
 *  FontWeight.java
 *
 *  Developed 2023 by LegoSoft Soluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.utils;

/**
 * css values for FontWeight
 *
 * @project ailegorreta-kit-components
 * @author Vaadin
 * @date July 2023
 */
public enum FontWeight {

    LIGHTER("lighter"),
    NORMAL("normal"),
    BOLD("bold"),
    BOLDER("bolder"),
    _100("100"),
    _200("200"),
    _300("300"),
    _400("400"),
    _500("500"),
    _600("600"),
    _700("700"),
    _800("800"),
    _900("900");

    private String value;

    FontWeight(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

