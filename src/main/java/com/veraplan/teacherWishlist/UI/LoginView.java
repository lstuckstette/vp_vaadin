package com.veraplan.teacherWishlist.UI;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.veraplan.teacherWishlist.Model.CurrentUser;
import com.veraplan.teacherWishlist.PersistenceManagement.EvaluationPersistenceManager;

@SuppressWarnings("serial")
@CDIView("login")
public class LoginView extends CustomComponent implements View, ClickListener {

	@Inject
	CurrentUser user;
	
	@Inject
	EvaluationPersistenceManager epm;
	
	private TextField usernameField;
	private PasswordField passwordField;
	private Button loginButton;

	private Navigator navigator;
	private Label pageHeader;

	@Override
	public void enter(ViewChangeEvent event) {
		navigator = getUI().getNavigator();

		usernameField = new TextField("Email");
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
		layout.addComponent(new CustomMenuBar(navigator,user));

		pageHeader = new Label("<h1>Login</h1>", ContentMode.HTML);
		layout.addComponent(pageHeader);

		// spacing:
		layout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		layout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		layout.addComponent(usernameField);
		layout.addComponent(passwordField);
		layout.addComponent(loginButton);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// Dummy implementation
		String username = usernameField.getValue();
		String password = passwordField.getValue();

		if (navigator != null && epm.checkUserLogin(username, password)) {
			user.setLoggedIn(true);
			user.setUser(epm.getSingleUser(username));
			
			navigator.navigateTo("evaluation");
		} else {
			Notification.show("Benutzername/Passwort nicht akzeptiert.", Notification.Type.ERROR_MESSAGE);
		}

	}



}
