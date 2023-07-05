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
 *  NaviDrawer.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.navigation.navigation.drawer;

import com.ailegorreta.client.components.utils.UIUtils;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import elemental.json.JsonObject;

/**
 * This if the NaviDrawer component for the structure defined in the
 * Business App Starter defined in Vaadin.
 *
 * The navigation slot is reserved for the NaviDrawer a responsive and collapsible
 * navigation sidebar that houses searchable and hierarchical navigation items.
 * It consists of a header intended for app menu options switching, a list of navigation items,
 * and a footer which by default houses the collapse button.
 *
 * @see //vaadin.com/docs/business-app/overview.html
 *
 * @author Vaadin
 * @project ailegorreta-kit-client-navigator
 * @date July 2023
 */
@CssImport("./styles/components/navi-drawer.css")
@JsModule("./swipe-away.js")
public class NaviDrawer extends Div implements AfterNavigationObserver {

	private final String CLASS_NAME = "navi-drawer";
	private final String RAIL = "rail";
	private final String OPEN = "open";

	private Div 			scrim;

	private Div 			mainContent;
	private TextField 		search;
	private Div 			scrollableArea;

	private Button 			railButton;
	public  HorizontalLayout notificationView; // This button is public in order to overwrite for notification button & company name (if desired)
	private NaviMenu 		menu;


	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		UI ui = attachEvent.getUI();
		ui.getPage().executeJs("window.addSwipeAway($0,$1,$2,$3)",
								mainContent.getElement(), this, "onSwipeAway",
								scrim.getElement());
	}

	@ClientCallable
	public void onSwipeAway(JsonObject data) {
		close();
	}

	public NaviDrawer() {
		this("Administración");
	}

	public NaviDrawer(String title) {
		setClassName(CLASS_NAME);

		initScrim();
		initMainContent();

		initHeader(title);
		initSearch();

		initScrollableArea();
		initMenu();

		initFooter();
	}

	private void initScrim() {
		// Backdrop on small viewports
		scrim = new Div();
		scrim.addClassName(CLASS_NAME + "__scrim");
		scrim.addClickListener(event -> close());
		add(scrim);
	}

	private void initMainContent() {
		mainContent = new Div();
		mainContent.addClassName(CLASS_NAME + "__content");
		add(mainContent);
	}

	private void initHeader(String title) {
		mainContent.add(new BrandExpression(title));
	}

	private void initSearch() {
		search = new TextField();
		search.addValueChangeListener(e -> menu.filter(search.getValue()));
		search.setClearButtonVisible(true);
		search.setPlaceholder("Búsqueda");
		search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
		mainContent.add(search);
	}

	private void initScrollableArea() {
		scrollableArea = new Div();
		scrollableArea.addClassName(CLASS_NAME + "__scroll-area");
		mainContent.add(scrollableArea);
	}

	private void initMenu() {
		menu = new NaviMenu();
		scrollableArea.add(menu);
	}

	private void initFooter() {
		railButton = UIUtils.createSmallButton("Comprimir", VaadinIcon.CHEVRON_LEFT_SMALL);
		railButton.addClickListener(event -> toggleRailMode());
		railButton.getElement().setAttribute("aria-label", "Collapse menu");
		notificationView = new HorizontalLayout();
		notificationView.setVisible(false);	// by default, we hide it

		var footer = new HorizontalLayout(notificationView, railButton);

		footer.addClassName(CLASS_NAME + "__footer");
		footer.getElement().setAttribute("aria-label", "Collapse menu");

		mainContent.add(footer);
	}

	private void toggleRailMode() {
		if (getElement().hasAttribute(RAIL)) {
			getElement().setAttribute(RAIL, false);
			railButton.setIcon(new Icon(VaadinIcon.CHEVRON_LEFT_SMALL));
			railButton.getElement().setAttribute("title", "Comprimir");
			UIUtils.setAriaLabel("Collapse menu", railButton);
		} else {
			getElement().setAttribute(RAIL, true);
			railButton.setIcon(new Icon(VaadinIcon.CHEVRON_RIGHT_SMALL));
			railButton.getElement().setAttribute("title", "Expander");
			UIUtils.setAriaLabel("Expand menu", railButton);
			getUI().get().getPage().executeJs(
					"var originalStyle = getComputedStyle($0).pointerEvents;" //
							+ "$0.style.pointerEvents='none';" //
							+ "setTimeout(function() {$0.style.pointerEvents=originalStyle;}, 170);",
					getElement());
		}
	}

	public void toggle() {
		if (getElement().hasAttribute(OPEN)) 
			close();
		else 
			open();
	}

	private void open() {
		getElement().setAttribute(OPEN, true);
	}

	private void close() {
		getElement().setAttribute(OPEN, false);
		applyIOS122Workaround();
	}

	private void applyIOS122Workaround() {
		// iOS 12.2 sometimes fails to animate the menu away.
		// It should be gone after 240ms
		// This will make sure it disappears even when the browser fails.
		getUI().get().getPage().executeJs(
				"var originalStyle = getComputedStyle($0).transitionProperty;" //
						+ "setTimeout(function() {$0.style.transitionProperty='padding'; requestAnimationFrame(function() {$0.style.transitionProperty=originalStyle})}, 250);",
				mainContent.getElement());
	}

	public NaviMenu getMenu() {
		return menu;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
		close();
	}

}
