package com.veraplan.teacherWishlist.UI;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.veraplan.teacherWishlist.Entities.User;
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

	private VerticalLayout masterLayout;
	private Panel loginPanel;
	private FormLayout loginForm;

	@Override
	public void enter(ViewChangeEvent event) {
		navigator = getUI().getNavigator();

		usernameField = new TextField("Email");
		usernameField.setIcon(VaadinIcons.USER);
		usernameField.setRequiredIndicatorVisible(true);
		passwordField = new PasswordField("Passwort");
		passwordField.setIcon(VaadinIcons.PASSWORD);
		passwordField.setRequiredIndicatorVisible(true);
		loginButton = new Button("Login");
		loginButton.addClickListener(this);
		loginButton.setClickShortcut(KeyCode.ENTER);

		masterLayout = new VerticalLayout();
		masterLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		setCompositionRoot(masterLayout);
		masterLayout.setSizeFull();
		masterLayout.setMargin(true);
		masterLayout.setSpacing(true);
		masterLayout.addComponent(new CustomMenuBar(navigator, user));

		pageHeader = new Label("<h1>Login</h1>", ContentMode.HTML);
		masterLayout.addComponent(pageHeader);

		// spacing:
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		loginPanel = new Panel("Bitte folgende Daten angeben:");
		masterLayout.addComponent(loginPanel);
		masterLayout.setSizeFull();
		masterLayout.setComponentAlignment(loginPanel, Alignment.TOP_CENTER);
		loginPanel.setWidth(null);

		loginForm = new FormLayout();
		loginForm.setMargin(true);
		loginForm.setStyleName("loginForm");
		loginForm.addComponent(usernameField);
		loginForm.addComponent(passwordField);
		loginForm.addComponent(loginButton);

		loginPanel.setContent(loginForm);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// Dummy implementation
		String username = usernameField.getValue();
		String password = passwordField.getValue();

		if (navigator != null && epm.checkUserLogin(username, password)) {
			User currentUser = epm.getSingleUser(username);
			user.setLoggedIn(true);
			user.setUser(currentUser);
			user.setPerson(epm.getPerson(currentUser));

			navigator.navigateTo("evaluation");
		} else {
			Notification.show("Benutzername/Passwort nicht akzeptiert.", Notification.Type.ERROR_MESSAGE);
		}

	}

}
