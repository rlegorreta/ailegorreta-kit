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
 *  UIUtils.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.components.utils;

import com.ailegorreta.client.components.ui.FlexBoxLayout;
import com.ailegorreta.client.components.ui.layout.size.Right;
import com.ailegorreta.client.components.utils.css.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.component.html.*;

import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * UI utilities. It is a singleton with all as static methods.
 *
 * @project lsmass-client-components
 * @author rlh
 * @date February 2022
 */
public class UIUtils {
    public static final String VIEWPORT = "width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover";

    /**
     * Thread-unsafe formatters.
     */
    private static final ThreadLocal<DecimalFormat> decimalFormat = ThreadLocal
            .withInitial(() -> new DecimalFormat("###,###.00", DecimalFormatSymbols.getInstance(Locale.US)));
    private static final ThreadLocal<DateTimeFormatter> dateFormat = ThreadLocal
            .withInitial(() -> DateTimeFormatter.ofPattern("dd MMM, YYYY"));
    private static final ThreadLocal<DateTimeFormatter> dateFormatZone = ThreadLocal
            .withInitial(() -> DateTimeFormatter.ofPattern("dd MMM, YYYY")
                    .withZone(ZoneId.systemDefault()));
    private static final ThreadLocal<DateTimeFormatter> dateTimeFormatZone = ThreadLocal
            .withInitial(() -> DateTimeFormatter.ofPattern("dd MMM, YYYY HH:mm")
                    .withZone(ZoneId.systemDefault()));
    public static final ThreadLocal<DateTimeFormatter> financialDateFormat = ThreadLocal
            .withInitial(() -> DateTimeFormatter.ofPattern("YYYY/MM/dd"));
    private static final ThreadLocal<DateTimeFormatter> financialDateFormatZone = ThreadLocal
            .withInitial(() -> DateTimeFormatter.ofPattern("YYYY/MM/dd")
                    .withZone(ZoneId.systemDefault()));
    private static final ThreadLocal<DateTimeFormatter> financialDateTimeFormatZone = ThreadLocal
            .withInitial(() -> DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm")
                    .withZone(ZoneId.systemDefault()));

    /* ==== BUTTONS ==== */

    // Styles

    public static Button createPrimaryButton(String text) {
        return createButton(text, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createPrimaryButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createPrimaryButton(String text, VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createTertiaryButton(String text) {
        return createButton(text, ButtonVariant.LUMO_TERTIARY);
    }

    public static Button createTertiaryButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_TERTIARY);
    }

    public static Button createTertiaryButton(String text, VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_TERTIARY);
    }

    public static Button createTertiaryInlineButton(String text) {
        return createButton(text, ButtonVariant.LUMO_TERTIARY_INLINE);
    }

    public static Button createTertiaryInlineButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_TERTIARY_INLINE);
    }

    public static Button createTertiaryInlineButton(String text, VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_TERTIARY_INLINE);
    }

    public static Button createSuccessButton(String text) {
        return createButton(text, ButtonVariant.LUMO_SUCCESS);
    }

    public static Button createSuccessButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_SUCCESS);
    }

    public static Button createSuccessButton(String text, VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_SUCCESS);
    }

    public static Button createSuccessPrimaryButton(String text) {
        return createButton(text, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createSuccessPrimaryButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createSuccessPrimaryButton(String text, VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createErrorButton(String text) {
        return createButton(text, ButtonVariant.LUMO_ERROR);
    }

    public static Button createErrorButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_ERROR);
    }

    public static Button createErrorButton(String text, VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_ERROR);
    }

    public static Button createErrorPrimaryButton(String text) {
        return createButton(text, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createErrorPrimaryButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createErrorPrimaryButton(String text, VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createContrastButton(String text) {
        return createButton(text, ButtonVariant.LUMO_CONTRAST);
    }

    public static Button createContrastButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_CONTRAST);
    }

    public static Button createContrastButton(String text, VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_CONTRAST);
    }

    public static Button createContrastPrimaryButton(String text) {
        return createButton(text, ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createContrastPrimaryButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createContrastPrimaryButton(String text, VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
    }

    // Size

    public static Button createSmallButton(String text) {
        Button button = createButton(text, ButtonVariant.MATERIAL_CONTAINED);

        button.setWidth("20px");
        button.setHeight("20px");

        return button;
    }

    public static Button createSmallButton(VaadinIcon icon) {
        Button button = createButton(icon, ButtonVariant.MATERIAL_CONTAINED);

        button.setWidth("16px");
        button.setHeight("16px");
        return button;
    }

    public static Button createSmallButton(String text, VaadinIcon icon) {
        Button button = createButton(icon, ButtonVariant.MATERIAL_CONTAINED);

        button.setWidth("25px");
        button.setHeight("25px");
        return button;
    }

    public static Button createLargeButton(String text) {
        return createButton(text, ButtonVariant.MATERIAL_OUTLINED);
    }

    public static Button createLargeButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.MATERIAL_OUTLINED);
    }

    public static Button createLargeButton(String text, VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.MATERIAL_OUTLINED);
    }

    public static Checkbox createCheckbox(String text) {
        Checkbox checkbox = new Checkbox(text);

        checkbox.getElement().setAttribute("aria-label", text);

        return checkbox;
    }

    public static Button createButton(String text, ButtonVariant... variants) {
        Button button = new Button(text);

        button.addThemeVariants(variants);
        button.getElement().setAttribute("aria-label", text);

        return button;
    }

    public static Button createButton(VaadinIcon icon, ButtonVariant... variants) {
        Button button = new Button(new Icon(icon));

        button.addThemeVariants(variants);

        return button;
    }

    public static Button createButton(String text, VaadinIcon icon, ButtonVariant... variants) {
        Icon i = new Icon(icon);

        i.getElement().setAttribute("slot", "prefix");

        Button button = new Button(text, i);

        button.addThemeVariants(variants);

        return button;
    }

    /* ==== TEXTFIELDS ==== */

    public static TextField createSmallTextField() {
        TextField textField = new TextField();

        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);

        return textField;
    }

    /* ==== LABELS ==== */

    public static Span createSpan(FontSize size, TextColor color, String text) {
        Span span = new Span(text);

        span.getElement().getStyle().set("font-size", size.getValue());
        span.getElement().getStyle().set("txt-color", color.getValue());

        return span;
    }

    public static Span createSpan(FontSize size, String text) {
        return createSpan(size, TextColor.BODY, text);
    }

    public static Span createLabel(TextColor color, String text) {
        return createSpan(FontSize.M, color, text);
    }

    public static Html createH1Label(String text) {
        Html html = new Html("<h1>" + text + "</h1>");

        return html;
    }

    public static Html createH2Label(String text) {
        Html html = new Html("<h2>" + text + "</h2>");

        return html;
    }

    public static Html createH3Label(String text) {
        Html html = new Html("<h3>" + text + "</h3>");

        return html;
    }

    public static Html createH4Label(String text) {
        Html html = new Html("<h4>" + text + "</h4>");

        return html;
    }

    public static Html createH5Label(String text) {
        Html html = new Html("<h5>" + text + "</h5>");

        return html;
    }

    public static Html createH6Label(String text) {
        Html html = new Html("<h6>" + text + "</h6>");

        return html;
    }

    /* === MISC === */

    public static String shortDescription(String description, int lenght) {
        if (description.length() > lenght)
            return description.substring(0, lenght - 3) + "...";
        else
            return description;
    }

    public static Button createFloatingActionButton(VaadinIcon icon) {
        Button button = createPrimaryButton(icon);

        button.addThemeName("fab");

        return button;
    }

    public static FlexLayout createPhoneLayout() {
        TextField prefix = new TextField();

        prefix.setValue("+358");
        prefix.setWidth("80px");

        TextField       number = new TextField();
        FlexBoxLayout   layout = new FlexBoxLayout(prefix, number);

        layout.setFlexGrow(1, number);
        layout.setSpacing(Right.S);

        return layout;
    }

    /* === NUMBERS === */

    public static String formatAmount(Double amount) {
        return decimalFormat.get().format(amount);
    }

    public static String formatAmount(int amount) {
        return decimalFormat.get().format(amount);
    }

    public static Html createAmountLabel(double amount) {
        Html html = createH5Label(formatAmount(amount));

        html.addClassName(LumoStyles.FontFamily.MONOSPACE);

        return html;
    }

    public static String formatUnits(int units) {
        return NumberFormat.getIntegerInstance().format(units);
    }

    public static Span createUnitsLabel(int units) {
        Span span = new Span(formatUnits(units));

        span.addClassName(LumoStyles.FontFamily.MONOSPACE);

        return span;
    }

    /* === ICONS === */

    public static Icon createPrimaryIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);

        setTextColor(TextColor.PRIMARY, i);

        return i;
    }

    public static Icon createSecondaryIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);

        setTextColor(TextColor.SECONDARY, i);

        return i;
    }

    public static Icon createTertiaryIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);

        setTextColor(TextColor.TERTIARY, i);

        return i;
    }

    public static Icon createDisabledIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);

        setTextColor(TextColor.DISABLED, i);

        return i;
    }

    public static Icon createSuccessIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);

        setTextColor(TextColor.SUCCESS, i);

        return i;
    }

    public static Icon createErrorIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);

        setTextColor(TextColor.ERROR, i);

        return i;
    }

    public static Icon createSmallIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);

        i.addClassName(IconSize.S.getClassName());

        return i;
    }

    public static Icon createLargeIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);

        i.addClassName(IconSize.L.getClassName());

        return i;
    }

    // Combinations

    public static Icon createIcon(IconSize size, TextColor color, VaadinIcon icon) {
        Icon i = new Icon(icon);

        i.addClassNames(size.getClassName());
        setTextColor(color, i);

        return i;
    }

    /* === DATES === */

    public static String formatDate(LocalDate date) {
        return dateFormat.get().format(date);
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date);
    }

    public static String formatInstant(Instant instant) {
        return dateTimeFormatZone.get().format(instant);
    }

    public static String formatLocalDateTime(LocalDateTime date) {
        return dateTimeFormatZone.get().format(date);
    }

    public static String formatLocalDate(LocalDate date) {
        return dateFormatZone.get().format(date);
    }

    public static String financialFormatDate(LocalDate date) {
        return financialDateFormat.get().format(date);
    }

    public static String financialFormatInstant(Instant instant) {
        return financialDateTimeFormatZone.get().format(instant);
    }

    public static String financialFormatLocalDateTime(LocalDateTime date) {
        return financialDateTimeFormatZone.get().format(date);
    }

    public static String financialFormatLocalDate(LocalDate date) {
        return financialDateFormatZone.get().format(date);
    }

    /* === NOTIFICATIONS === */

    public static void showNotification(String text) {
        Notification.show(text, 3000, Notification.Position.BOTTOM_CENTER);
    }

    /* === CSS UTILITIES === */

    public static void setAlignSelf(AlignSelf alignSelf, Component... components) {

        for (Component component : components)
            component.getElement().getStyle().set("align-self", alignSelf.getValue());
    }

    public static void setBackgroundColor(String backgroundColor, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("background-color", backgroundColor);
    }

    public static void setBorderRadius(BorderRadius borderRadius, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("border-radius", borderRadius.getValue());
    }

    public static void setBoxSizing(BoxSizing boxSizing, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("box-sizing", boxSizing.getValue());
    }

    public static void setColSpan(int span, Component... components) {
        for (Component component : components)
            component.getElement().setAttribute("colspan", Integer.toString(span));
    }

    public static void setFontSize(FontSize fontSize, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("font-size", fontSize.getValue());
    }

    public static void setFontWeight(FontWeight fontWeight, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("font-weight", fontWeight.getValue());
    }

    public static void setLineHeight(LineHeight lineHeight, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("line-height", lineHeight.getValue());
    }

    public static void setLineHeight(String value, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("line-height", value);
    }

    public static void setMaxWidth(String value, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("max-width", value);
    }

    public static void setOverflow(Overflow overflow, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("overflow", overflow.getValue());
    }

    public static void setPointerEvents(PointerEvents pointerEvents, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("pointer-events", pointerEvents.getValue());
    }

    public static void setShadow(Shadow shadow, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("box-shadow", shadow.getValue());
    }

    public static void setTextAlign(TextAlign textAlign, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("text-align", textAlign.getValue());
    }

    public static void setTextColor(TextColor textColor, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("color", textColor.getValue());
    }

    public static void setTextOverflow(TextOverflow textOverflow, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("text-overflow", textOverflow.getValue());
    }

    public static void setTheme(String theme, Component... components) {
        for (Component component : components)
            component.getElement().setAttribute("theme", theme);
    }

    public static void setTooltip(String tooltip, Component... components) {
        for (Component component : components)
            component.getElement().setProperty("title", tooltip);
    }

    public static void setWhiteSpace(WhiteSpace whiteSpace, Component... components) {
        for (Component component : components)
            component.getElement().setProperty("white-space", whiteSpace.getValue());
    }

    public static void setWidth(String value, Component... components) {
        for (Component component : components)
            component.getElement().getStyle().set("width", value);
    }

    /* ---- Links -- */
    public static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);

        return a;
    }

    /* === ACCESSIBILITY === */

    public static void setAriaLabel(String value, Component... components) {
        for (Component component : components)
            component.getElement().setAttribute("aria-label", value);

    }
}
