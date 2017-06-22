package com.veraplan.teacherWishlist.UI;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@CDIView("login")
public class LoginView extends CustomComponent implements View, ClickListener {

	private TextField usernameField;
	private PasswordField passwordField;
	private Button loginButton;

	private Navigator navigator;

	@Override
	public void enter(ViewChangeEvent event) {
		navigator = getUI().getNavigator();

		usernameField = new TextField("Benutzername");
		passwordField = new PasswordField("Passwort");
		loginButton = new Button("Login");
		loginButton.addClickListener(this);
		loginButton.setClickShortcut(KeyCode.ENTER);

		VerticalLayout layout = new VerticalLayout();
		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		setCompositionRoot(layout);
		layout.setSizeFull();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.addComponent(new CustomMenuBar(navigator));

		layout.addComponent(usernameField);
		layout.addComponent(passwordField);
		layout.addComponent(loginButton);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// Dummy implementation
		String username = usernameField.getValue();
		String password = passwordField.getValue();

		if (navigator != null && !checkCredentials(username, password)) {
			navigator.navigateTo("evaluation");
		} else {
			Notification.show("Benutzername nicht akzeptiert.", Notification.Type.ERROR_MESSAGE);
		}

	}

	public boolean checkCredentials(String username, String password) {
		// TODO: implement this functionality, point to
		// EvaluationPersistenceManager for check;
		return true;
	}

}
