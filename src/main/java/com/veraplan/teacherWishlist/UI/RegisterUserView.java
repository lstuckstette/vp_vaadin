package com.veraplan.teacherWishlist.UI;

import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
@CDIView("register")
public class RegisterUserView extends CustomComponent implements View, ClickListener {

	private Navigator navigator;

	private Label pageHeader;
	
	//private Label firstNameLabel;
	private TextField firstNameTextField;
	
	//private Label lastNameLabel;
	private TextField lastNameTextField;
	
	//private Label birthdayLabel;
	private DateField birthdayDateField;
	
	//private Label addressLabel;
	private TextField addressTextField;
	
	private TextField postalCode;
	
	//private Label cityLabel;
	private TextField cityTextField;
	
	//private Label genderLabel;
	private ListSelect<String> genderListSelect;
	
	//private Label emailLabel;
	private TextField emailTextField;
	
	//private Label notificationEmailLabel;
	private TextField notificationEmailTextField;
	
	//private Label passwordLabel;
	private PasswordField passwordField;
	
	//private Label positionLabel;
	private ListSelect<String> positionListSelect;
	
	//private Label positionHireDateLabel;
	private DateField positionHireDateField;
	
	private Button confirm;

	@Override
	public void enter(ViewChangeEvent event) {

		navigator = getUI().getNavigator();

		VerticalLayout layout = new VerticalLayout();
		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		layout.addComponent(new CustomMenuBar(navigator));
		setCompositionRoot(layout);

		pageHeader = new Label("<h1>Registrierung</h1>", ContentMode.HTML);
		// spacing:
		layout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		layout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		
		FormLayout form = new FormLayout();
		
		firstNameTextField = new TextField("Vorname");
		firstNameTextField.setIcon(VaadinIcons.USER);
		firstNameTextField.setRequiredIndicatorVisible(true);
		form.addComponent(firstNameTextField);
		
		//...
		
		layout.addComponent(form);		

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

}
