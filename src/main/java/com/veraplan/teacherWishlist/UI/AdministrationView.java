package com.veraplan.teacherWishlist.UI;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@CDIView("administration")
public class AdministrationView extends CustomComponent implements View {

	private Navigator navigator;
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		navigator = getUI().getNavigator();
		
		VerticalLayout layout = new VerticalLayout();
		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		layout.addComponent(new EvaluationMenuBar(navigator));
		setCompositionRoot(layout);
		
	}

}
