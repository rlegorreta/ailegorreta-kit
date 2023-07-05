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
 *  NaviMenu.java
 *
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
 */
package com.ailegorreta.client.navigation.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This if the NaviMenu component for the structure defined in the
 * Business App Starter defined in Vaadin.
 *
 * It is single navigation item for the the navigation slot that is is reserved
 * for the NaviMenu is the main menu that it is a responsive and collapsible navigation
 * sidebar that houses searchable and hierarchical navigation items.
 * It consists of a header intended for app menu options switching, a list of navigation items,
 *  and a footer which by default houses the collapse button.
 *
 * @see //vaadin.com/docs/business-app/overview.html
 *
 * @author Vaadin
 * @project ailegorreta-kit-client-navigator
 * @date July 2023
 */
@CssImport("./styles/components/navi-menu.css")
public class NaviMenu extends Div {

	private String CLASS_NAME = "navi-menu";

	public NaviMenu() {
		setClassName(CLASS_NAME);
	}

	protected void addNaviItem(NaviItem item) {
		add(item);
	}

	protected void addNaviItem(NaviItem parent, NaviItem item) {
		parent.addSubItem(item);
		addNaviItem(item);
	}

	public void filter(String filter) {
		getNaviItems().forEach(naviItem -> {
			boolean matches = ((NaviItem) naviItem).getText().toLowerCase()
					.contains(filter.toLowerCase());
			naviItem.setVisible(matches);
		});
	}

	public NaviItem addNaviItem(String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(text, navigationTarget);
		addNaviItem(item);
		return item;
	}

	public NaviItem addNaviItem(VaadinIcon icon, String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(icon, text, navigationTarget);
		addNaviItem(item);
		return item;
	}

	public NaviItem addNaviItem(Image image, String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(image, text, navigationTarget);
		addNaviItem(item);
		return item;
	}

	public NaviItem addNaviItem(NaviItem parent, String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(text, navigationTarget);
		addNaviItem(parent, item);
		return item;
	}

	public List<NaviItem> getNaviItems() {
		List<NaviItem> items = (List) getChildren().collect(Collectors.toList());
		return items;
	}

}
