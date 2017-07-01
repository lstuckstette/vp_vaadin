package com.veraplan.teacherWishlist.UI;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.MenuBar;
import com.veraplan.teacherWishlist.Model.CurrentUser;

@SuppressWarnings("serial")
public class CustomMenuBar extends MenuBar {

	private MenuItem login;
	private MenuItem register;
	private MenuItem evaluation;
	private MenuItem administration;
	private MenuItem loginIndicator;
	private CurrentUser user;

	public CustomMenuBar(Navigator navigator, CurrentUser user) {
		super();

		this.user = user;
		login = this.addItem("Login", null, null);
		login.setCommand(selectedItem -> {
			if (navigator != null) {
				navigator.navigateTo("login");

			}
		});

		register = this.addItem("Registrieren", null, null);
		register.setCommand(selectedItem -> {
			if (navigator != null) {
				navigator.navigateTo("register");

			}
		});

		evaluation = this.addItem("Erhebungsbogen", null, null);
		evaluation.setCommand(selectedItem -> {
			if (navigator != null) {
				navigator.navigateTo("evaluation");
			}
		});

		administration = this.addItem("Administration", null, null);
		administration.setCommand(selectedItem -> {
			if (navigator != null) {
				navigator.navigateTo("administration");
			}
		});

		loginIndicator = this.addItem("", null, null);
		loginIndicator.setIcon(VaadinIcons.USER);

		setLoginIndicatorCaption();
		this.setWidth("100%");

	}

	private void setLoginIndicatorCaption() {
		if (user.isLoggedIn()) {
			loginIndicator.setText("eingeloggt als: " + user.getPerson().getFirstName() + " "+ user.getPerson().getLastName());
		} else {
			loginIndicator.setText("nicht eingeloggt.");
		}

	}

}

/*
 * 
 * menu = new MenuBar(); menu.addItem("Startseite", null, null);
 * menu.addItem("Login", null, null); menu.addItem("Wunschzettel", null, null);
 * menu.addItem("Administration", null, null);
 * 
 * menu.setWidth("100%"); masterLayout.addComponent(menu);
 * 
 */