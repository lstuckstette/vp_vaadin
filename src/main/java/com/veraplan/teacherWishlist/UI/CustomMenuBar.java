package com.veraplan.teacherWishlist.UI;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.MenuBar;
import com.veraplan.teacherWishlist.Model.CurrentUser;

/**
 * @author Lukas Stuckstette
 *
 */
@SuppressWarnings("serial")
public class CustomMenuBar extends MenuBar {

	//menu items:
	private MenuItem login;
	private MenuItem register;
	private MenuItem evaluation;
	private MenuItem administration;
	private MenuItem loginIndicator;
	//object representing current user
	private CurrentUser user;

	/**
	 * constructor of CustomMenuBar - builds the components ui.
	 * handles click events by redirecting the user to the desired view
	 * 
	 * @param navigator reference to the ui's navigator object.
	 * @param user reference to the current user
	 */
	public CustomMenuBar(Navigator navigator, CurrentUser user) {
		super();
		this.user = user;
		//setup login menuitem
		login = this.addItem("Login", null, null);
		login.setCommand(selectedItem -> {
			if (navigator != null) {
				navigator.navigateTo("login");

			}
		});
		//setup register menuitem
		register = this.addItem("Registrieren", null, null);
		register.setCommand(selectedItem -> {
			if (navigator != null) {
				navigator.navigateTo("register");

			}
		});
		//setup evaluation menuitem
		evaluation = this.addItem("Erhebungsbogen", null, null);
		evaluation.setCommand(selectedItem -> {
			if (navigator != null) {
				navigator.navigateTo("evaluation");
			}
		});
		//setup administration menuitem
		administration = this.addItem("Administration", null, null);
		administration.setCommand(selectedItem -> {
			if (navigator != null) {
				navigator.navigateTo("administration");
			}
		});
		//setup login-indicator
		loginIndicator = this.addItem("", null, null);
		loginIndicator.setIcon(VaadinIcons.USER);

		setLoginIndicatorCaption();
		this.setWidth("100%");

	}

	/**
	 * sets the login-indicator caption depending on current user
	 */
	private void setLoginIndicatorCaption() {
		if (user.isLoggedIn()) {
			loginIndicator.setText("eingeloggt als: " + user.getPerson().getFirstName() + " "+ user.getPerson().getLastName());
		} else {
			loginIndicator.setText("nicht eingeloggt.");
		}
	}
}