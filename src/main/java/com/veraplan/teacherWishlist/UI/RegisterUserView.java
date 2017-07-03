package com.veraplan.teacherWishlist.UI;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.veraplan.teacherWishlist.Model.CurrentUser;
import com.veraplan.teacherWishlist.Model.RegistrationField;
import com.veraplan.teacherWishlist.PersistenceManagement.EvaluationPersistenceManager;

/**
 * RegisterUserView is respresentig the 'Registrieren'-page of the application.
 * 
 * @author Lukas Stuckstette
 *
 */
@SuppressWarnings("serial")
@CDIView("register")
public class RegisterUserView extends CustomComponent implements View, ClickListener {

	// inject persistence manager
	@Inject
	EvaluationPersistenceManager epm;
	// inject CurrentUser bean
	@Inject
	CurrentUser user;

	private Navigator navigator;

	// UI-elements:
	private FormLayout formLayout;
	private VerticalLayout masterLayout;
	private Panel registerPanel;
	private Label pageHeader;
	private TextField firstNameTextField;
	private TextField lastNameTextField;
	private DateField birthdayDateField;
	private TextField addressTextField;
	private TextField postalCodeTextField;
	private TextField cityTextField;
	private RadioButtonGroup<String> genderSelect;
	private TextField emailTextField;
	private TextField notificationEmailTextField;
	private PasswordField passwordField;
	private PasswordField passwordConfirmField;
	private RadioButtonGroup<String> positionSelect;
	private DateField positionHireDateField;
	private Button confirm;

	/*
	 * builds UI of register-view
	 */
	@Override
	public void enter(ViewChangeEvent event) {

		navigator = getUI().getNavigator();

		masterLayout = new VerticalLayout();
		masterLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		setCompositionRoot(masterLayout);
		masterLayout.addComponent(new CustomMenuBar(navigator, user));

		pageHeader = new Label("<h1>Registrierung</h1>", ContentMode.HTML);
		masterLayout.addComponent(pageHeader);
		// spacing:
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		registerPanel = new Panel("Bitte geben Sie folgende Informationen zu ihrer Person an:");

		masterLayout.addComponent(registerPanel);
		masterLayout.setComponentAlignment(registerPanel, Alignment.TOP_CENTER);
		masterLayout.setSizeFull();
		registerPanel.setWidth(null);

		formLayout = new FormLayout();
		formLayout.setMargin(true);
		setupFormElements();

		registerPanel.setContent(formLayout);

	}

	/**
	 * builds UI-elements concerning registration data entry
	 */
	private void setupFormElements() {
		firstNameTextField = new TextField("Vorname");
		firstNameTextField.setIcon(VaadinIcons.USER);
		firstNameTextField.setRequiredIndicatorVisible(true);
		formLayout.addComponent(firstNameTextField);

		lastNameTextField = new TextField("Nachname");
		lastNameTextField.setIcon(VaadinIcons.USER);
		lastNameTextField.setRequiredIndicatorVisible(true);
		formLayout.addComponent(lastNameTextField);

		birthdayDateField = new DateField("Geburtsdatum");
		birthdayDateField.setIcon(VaadinIcons.DATE_INPUT);
		birthdayDateField.setDateFormat("dd.MM.yyyy");
		formLayout.addComponent(birthdayDateField);

		addressTextField = new TextField("Adresse");
		addressTextField.setIcon(VaadinIcons.HOME);
		formLayout.addComponent(addressTextField);

		postalCodeTextField = new TextField("Postleitzahl");
		postalCodeTextField.setIcon(VaadinIcons.HASH);
		formLayout.addComponent(postalCodeTextField);

		cityTextField = new TextField("Stadt");
		cityTextField.setIcon(VaadinIcons.HOME);
		formLayout.addComponent(cityTextField);

		genderSelect = new RadioButtonGroup<>("Geschlecht");
		genderSelect.setItems("männlich", "weiblich", "keine Angabe");
		genderSelect.setIcon(VaadinIcons.FAMILY);
		formLayout.addComponent(genderSelect);

		emailTextField = new TextField("Email");
		emailTextField.setRequiredIndicatorVisible(true);
		emailTextField.setIcon(VaadinIcons.AT);
		formLayout.addComponent(emailTextField);

		notificationEmailTextField = new TextField("Benachrichtigungs Email");
		notificationEmailTextField.setRequiredIndicatorVisible(true);
		notificationEmailTextField.setIcon(VaadinIcons.AT);
		formLayout.addComponent(notificationEmailTextField);

		passwordField = new PasswordField("Kennwort");
		passwordField.setRequiredIndicatorVisible(true);
		passwordField.setIcon(VaadinIcons.PASSWORD);
		formLayout.addComponent(passwordField);

		passwordConfirmField = new PasswordField("Kennwort wiederholen");
		passwordConfirmField.setRequiredIndicatorVisible(true);
		passwordConfirmField.setIcon(VaadinIcons.PASSWORD);
		passwordConfirmField.addValueChangeListener(typeEvent -> {

			if (!passwordField.getValue().equals(passwordConfirmField.getValue())) {
				passwordConfirmField.setComponentError(new UserError("Die Passwörter stimmen nicht überein!"));
			} else {
				passwordConfirmField.setComponentError(null);
			}
		});
		formLayout.addComponent(passwordConfirmField);

		positionSelect = new RadioButtonGroup<>("Rolle am Institut");
		positionSelect.setRequiredIndicatorVisible(true);
		positionSelect.setItems("Lehrer", "Schüler", "Mitarbeiter");
		positionSelect.setItemEnabledProvider(item -> !"Schüler".equals(item));
		positionSelect.setItemEnabledProvider(item -> !"Mitarbeiter".equals(item));
		positionSelect.setIcon(VaadinIcons.WORKPLACE);
		formLayout.addComponent(positionSelect);

		// ...
		positionHireDateField = new DateField("Beginn des Arbeitsverhältnisses");
		positionHireDateField.setIcon(VaadinIcons.DATE_INPUT);
		positionHireDateField.setDateFormat("dd.MM.yyyy");
		formLayout.addComponent(positionHireDateField);

		confirm = new Button("Absenden");
		confirm.addClickListener(this);
		formLayout.addComponent(confirm);
	}

	/**
	 * Fetches entered data from input fields
	 * 
	 * @return returns a Map containing entered data mapped to a corresponding
	 *         RegistrationField value
	 */
	private Map<RegistrationField, String> getEnteredData() {
		// Null-Pointer checks:
		String firstname = "<no-data>";
		if (firstNameTextField.getValue() != null) {
			firstname = firstNameTextField.getValue();
		}

		String lastname = "<no-data>";
		if (lastNameTextField.getValue() != null) {
			lastname = lastNameTextField.getValue();
		}

		String birthday = "01.01.1900";
		if (birthdayDateField.getValue() != null) {
			birthday = birthdayDateField.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		}

		String address = "<no-data>";
		if (addressTextField.getValue() != null) {
			address = addressTextField.getValue();
		}

		String postalcode = "0";
		if (postalCodeTextField.getValue() != null) {
			postalcode = postalCodeTextField.getValue();
		}

		String city = "<no-data>";
		if (cityTextField.getValue() != null) {
			city = cityTextField.getValue();
		}

		String gender = "O";
		if (genderSelect.getSelectedItem() != null) {
			gender = genderSelect.getSelectedItem().get();
		}

		String email = "<no-data>";
		if (emailTextField.getValue() != null) {
			email = emailTextField.getValue();
		}

		String notiemail = "<no-data>";
		if (notificationEmailTextField.getValue() != null) {
			notiemail = notificationEmailTextField.getValue();
		}

		String password = "<no-data>";
		if (passwordField.getValue() != null) {
			password = passwordField.getValue();
		}

		String position = "<no-data>";
		if (positionSelect.getSelectedItem() != null) {
			position = positionSelect.getSelectedItem().get();
		}

		String hiredate = "01.01.1900";
		if (positionHireDateField.getValue() != null) {
			hiredate = positionHireDateField.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		}

		// build map
		Map<RegistrationField, String> enteredData = new HashMap<RegistrationField, String>();
		enteredData.put(RegistrationField.FIRSTNAME, firstname);
		enteredData.put(RegistrationField.LASTNAME, lastname);
		enteredData.put(RegistrationField.BIRTHDATE, birthday);
		enteredData.put(RegistrationField.ADDRESS, address);
		enteredData.put(RegistrationField.POSTALCODE, postalcode);
		enteredData.put(RegistrationField.CITY, city);
		enteredData.put(RegistrationField.GENDER, gender);
		enteredData.put(RegistrationField.EMAIL, email);
		enteredData.put(RegistrationField.NOTIFICATION_EMAIL, notiemail);
		enteredData.put(RegistrationField.PASSWORD, password);
		enteredData.put(RegistrationField.POSITION, position);
		enteredData.put(RegistrationField.HIREDATE, hiredate);
		return enteredData;
	}

	/*
	 * ClickListener - callback method: persists entered data
	 * 
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		Map<RegistrationField, String> enteredData = getEnteredData();
		// persist map
		if (!epm.registerUser(enteredData)) {
			Notification.show("Benutzername existiert bereits.", Notification.Type.ERROR_MESSAGE);
		} else {
			Notification.show("Benutzer erfolgreich erstellt.", Notification.Type.TRAY_NOTIFICATION);
		}

	}

}
