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
 *  ValidTextField.java
 *
 *  Developed 2022 by LMASS Desarrolladores www.lmass.com.mx
 */
package com.ailegorreta.client.components.ui;

import java.util.*;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.Binder.BindingBuilder;
import com.vaadin.flow.function.SerializablePredicate;

/**
 * For more information about this Component see:
 * https://vaadin.com/learn/tutorials/simple-field-validation
 *
 * This is a TextField but with validation and avoid to
 * use a binder from the User.
 *
 * Example how to use it:
 *
 * ValidTextField valid = new ValidTextField();
 *
 * valid.addValidator( new StringLengthValidator("Wrong length", 1, 10));
 * valid.addValidator( s -> s.equals("LMASS"), "Not LMASS!");
 *
 *@author Vaadin tutorial
 *@project ailegorreta-kit-client-components
 *@date July 2023
 *
 */
public class ValidTextField extends TextField {

    class Content {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    private Content content = new Content();
    private Binder<Content> binder = new Binder<>();
    private List<Validator<String>> validators = new ArrayList<>();

    public ValidTextField() {
        binder.setBean(content);
    }

    public ValidTextField(String text) {
        super(text);
        binder.setBean(content);
    }

    public void addValidator(SerializablePredicate<String> predicate, String errorMessage) {
        addValidator(Validator.from(predicate, errorMessage));
    }

    public void addValidator(Validator<String> validator) {
        validators.add(validator);
        build();
    }

    private void build() {
        BindingBuilder<Content, String> builder = binder.forField(this);

        for (Validator<String> v : validators)
            builder.withValidator(v);

        builder.bind(Content::getContent, Content::setContent);
    }
}
