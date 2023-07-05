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

import java.util.*;

import com.ailegorreta.client.components.utils.UIUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

/**
 * This if the NaviDrawer component for the structure defined in the
 * Business App Starter defined in Vaadin.
 *
 * It is single navigation item for the the navigation slot that is is reserved
 * for the NaviDrawer a responsive and collapsible navigation sidebar that houses
 * searchable and hierarchical navigation items ot this class.
 * It consists of a header intended for app menu options switching, a list of navigation items,
 * (i.e., this class) and a footer which by default houses the collapse button.
 *
 * @see //vaadin.com/docs/business-app/overview.html
 *
 * @author Vaadin
 * @project ailegorreta-kit-client-navigator
 * @date July 2023
 */
@CssImport("./styles/components/navi-item.css")
public class NaviItem extends Div {

	private String CLASS_NAME = "navi-item";

	private int 		level = 0;

	private Component 	link;
	private Class<? extends Component> navigationTarget;
	private String 		text;

	protected Button 	expandCollapse;

	private List<NaviItem> 	subItems;
	private boolean 		subItemsVisible;

	public NaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
		this(text, navigationTarget);
		link.getElement().insertChild(0, new Icon(icon).getElement());
	}

	public NaviItem(Image image, String text, Class<? extends Component> navigationTarget) {
		this(text, navigationTarget);
		link.getElement().insertChild(0, image.getElement());
	}

	public NaviItem(String text, Class<? extends Component> navigationTarget) {
		setClassName(CLASS_NAME);
		setLevel(0);

		this.text = text;
		this.navigationTarget = navigationTarget;

		if (navigationTarget != null) {
			RouterLink routerLink = new RouterLink((String) null, navigationTarget);
			
			routerLink.add(new Span(text));
			routerLink.setClassName(CLASS_NAME + "__link");
			routerLink.setHighlightCondition(HighlightConditions.sameLocation());
			this.link = routerLink;
		} else {
			Div div = new Div(new Span(text));
			
			div.addClickListener(e -> expandCollapse.click());
			div.setClassName(CLASS_NAME + "__link");
			this.link = div;
		}

		expandCollapse = UIUtils.createButton(VaadinIcon.CARET_UP, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		expandCollapse.addClickListener(event -> setSubItemsVisible(!subItemsVisible));
		expandCollapse.setVisible(false);

		subItems = new ArrayList<>();
		subItemsVisible = true;
		updateAriaLabel();

		add(link, expandCollapse);
	}

	private void updateAriaLabel() {
		String action = (subItemsVisible ? "Comprimir " : "Expander ") + text;
		
		UIUtils.setAriaLabel(action, expandCollapse);
	}

	public boolean isHighlighted(AfterNavigationEvent e) {
		return link instanceof RouterLink && ((RouterLink) link)
				.getHighlightCondition().shouldHighlight((RouterLink) link, e);
	}

	public void setLevel(int level) {
		this.level = level;
		if (level > 0) 
			getElement().setAttribute("level", Integer.toString(level));
	}

	public int getLevel() {
		return level;
	}

	public Class<? extends Component> getNavigationTarget() {
		return navigationTarget;
	}

	public void addSubItem(NaviItem item) {
		if (!expandCollapse.isVisible()) 
			expandCollapse.setVisible(true);
		item.setLevel(getLevel() + 1);
		subItems.add(item);
	}

	private void setSubItemsVisible(boolean visible) {
		if (level == 0) 
			expandCollapse.setIcon(new Icon(visible ? VaadinIcon.CARET_UP : VaadinIcon.CARET_DOWN));
		subItems.forEach(item -> item.setVisible(visible));
		subItemsVisible = visible;
		updateAriaLabel();
	}

	public String getText() {
		return text;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		// If true, we only update the icon. If false, we hide all the sub items
		if (visible) {
			if (level == 0) 
				expandCollapse.setIcon(new Icon(VaadinIcon.CARET_DOWN));
		} else {
			setSubItemsVisible(visible);
		}
	}
}
