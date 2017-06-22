package com.veraplan.teacherWishlist.UI;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.MenuBar;

@SuppressWarnings("serial")
public class CustomMenuBar extends MenuBar {

	private Navigator navigator;
	MenuItem login;
	MenuItem register;
	MenuItem evaluation;
	MenuItem administration;
	

	public CustomMenuBar(Navigator navigator) {
		super();
		this.navigator = navigator;
		login = this.addItem("Login", null, null);
		login.setCommand(selectedItem -> {
			if (navigator != null) {
				navigator.navigateTo("login");
			}
		});
		
		register= this.addItem("Registrieren", null, null);
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
		
		this.setWidth("100%");

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