package com.veraplan.teacherWishlist.UI;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.veraplan.teacherWishlist.Model.CurrentUser;
import com.veraplan.teacherWishlist.PersistenceManagement.EvaluationPersistenceManager;

@SuppressWarnings("serial")
@CDIView("administration")
public class AdministrationView extends CustomComponent implements View {

	@Inject
	CurrentUser user;
	@Inject
	EvaluationPersistenceManager epm;

	private Navigator navigator;
	private Label pageHeader;

	private Panel TeacherWishlistSelect;
	private Panel TeacherWishlistView;

	private FormLayout TeacherWishlistSelectLayout;
	private VerticalLayout TeacherWishlistViewLayout;

	private Button TeacherWishlistSelectConfirm;

	@Override
	public void enter(ViewChangeEvent event) {

		navigator = getUI().getNavigator();

		VerticalLayout masterLayout = new VerticalLayout();
		masterLayout.setSizeFull();
		masterLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		masterLayout.addComponent(new CustomMenuBar(navigator, user));
		setCompositionRoot(masterLayout);

		pageHeader = new Label("<h1>Administration</h1>", ContentMode.HTML);
		masterLayout.addComponent(pageHeader);
		// spacing:
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		// build TeacherWishlistSelect Panel:
		
		
		
		//build On-Demand TeacherWishlistView Panel

	}

}
