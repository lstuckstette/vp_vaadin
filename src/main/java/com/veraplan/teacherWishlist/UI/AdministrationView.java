package com.veraplan.teacherWishlist.UI;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@CDIView("administration")
public class AdministrationView extends CustomComponent implements View {

	private Navigator navigator;
	private Label pageHeader;
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		navigator = getUI().getNavigator();
		
		VerticalLayout layout = new VerticalLayout();
		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		layout.addComponent(new CustomMenuBar(navigator));
		setCompositionRoot(layout);
		
		pageHeader = new Label("<h1>Erhebungsbogen zur Unterrichtsverteilung</h1>", ContentMode.HTML);
		// spacing:
		layout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		layout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		
	}

}
