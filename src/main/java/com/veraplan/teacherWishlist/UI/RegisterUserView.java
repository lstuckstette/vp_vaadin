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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.veraplan.teacherWishlist.Model.CurrentUser;
import com.veraplan.teacherWishlist.Model.RegistrationField;
import com.veraplan.teacherWishlist.PersistenceManagement.EvaluationPersistenceManager;

@SuppressWarnings("serial")
@CDIView("register")
public class RegisterUserView extends CustomComponent implements View, ClickListener {

	@Inject
	EvaluationPersistenceManager epm;
	@Inject
	CurrentUser user;
	private Navigator navigator;

	private FormLayout form;

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

	@Override
	public void enter(ViewChangeEvent event) {

		navigator = getUI().getNavigator();

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		setCompositionRoot(layout);
		layout.addComponent(new CustomMenuBar(navigator, user));

		pageHeader = new Label("<h1>Registrierung</h1>", ContentMode.HTML);
		layout.addComponent(pageHeader);
		// spacing:
		layout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		layout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		layout.addComponent(
				new Label("<h2>Bitte geben Sie folgende Informationen zu ihrer Person an:</h2>", ContentMode.HTML));

		form = new FormLayout();
		form.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		form.setSizeFull();

		firstNameTextField = new TextField("Vorname");
		firstNameTextField.setIcon(VaadinIcons.USER);
		firstNameTextField.setRequiredIndicatorVisible(true);
		form.addComponent(firstNameTextField);

		lastNameTextField = new TextField("Nachname");
		lastNameTextField.setIcon(VaadinIcons.USER);
		lastNameTextField.setRequiredIndicatorVisible(true);
		form.addComponent(lastNameTextField);

		birthdayDateField = new DateField("Geburtsdatum");
		birthdayDateField.setIcon(VaadinIcons.DATE_INPUT);
		birthdayDateField.setDateFormat("dd.MM.yyyy");
		form.addComponent(birthdayDateField);

		addressTextField = new TextField("Adresse");
		addressTextField.setIcon(VaadinIcons.HOME);
		form.addComponent(addressTextField);

		postalCodeTextField = new TextField("Postleitzahl");
		postalCodeTextField.setIcon(VaadinIcons.HASH);
		form.addComponent(postalCodeTextField);

		cityTextField = new TextField("Stadt");
		cityTextField.setIcon(VaadinIcons.HOME);
		form.addComponent(cityTextField);

		genderSelect = new RadioButtonGroup<>("Geschlecht");
		genderSelect.setItems("männlich", "weiblich", "keine Angabe");
		genderSelect.setIcon(VaadinIcons.FAMILY);
		form.addComponent(genderSelect);

		emailTextField = new TextField("Email");
		emailTextField.setRequiredIndicatorVisible(true);
		emailTextField.setIcon(VaadinIcons.AT);
		form.addComponent(emailTextField);

		notificationEmailTextField = new TextField("Benachrichtigungs Email");
		notificationEmailTextField.setRequiredIndicatorVisible(true);
		notificationEmailTextField.setIcon(VaadinIcons.AT);
		form.addComponent(notificationEmailTextField);

		passwordField = new PasswordField("Kennwort");
		passwordField.setRequiredIndicatorVisible(true);
		passwordField.setIcon(VaadinIcons.PASSWORD);
		form.addComponent(passwordField);

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
		form.addComponent(passwordConfirmField);

		positionSelect = new RadioButtonGroup<>("Rolle am Institut");
		positionSelect.setRequiredIndicatorVisible(true);
		positionSelect.setItems("Lehrer", "Schüler", "Mitarbeiter");
		positionSelect.setItemEnabledProvider(item -> !"Schüler".equals(item));
		positionSelect.setItemEnabledProvider(item -> !"Mitarbeiter".equals(item));
		positionSelect.setIcon(VaadinIcons.WORKPLACE);
		form.addComponent(positionSelect);

		// ...
		positionHireDateField = new DateField("Beginn des Arbeitsverhältnisses");
		positionHireDateField.setIcon(VaadinIcons.DATE_INPUT);
		positionHireDateField.setDateFormat("dd.MM.yyyy");
		form.addComponent(positionHireDateField);

		confirm = new Button("Absenden");
		confirm.addClickListener(this);
		form.addComponent(confirm);

		VerticalLayout formParent = new VerticalLayout();
		formParent.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		formParent.addComponent(form);
		layout.addComponent(formParent);

	}

	private Map<RegistrationField, String> getEnteredData() {
		Map<RegistrationField, String> enteredData = new HashMap<RegistrationField, String>();
		enteredData.put(RegistrationField.FIRSTNAME, firstNameTextField.getValue());
		enteredData.put(RegistrationField.LASTNAME, lastNameTextField.getValue());
		enteredData.put(RegistrationField.BIRTHDATE,
				birthdayDateField.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		enteredData.put(RegistrationField.ADDRESS, addressTextField.getValue());
		enteredData.put(RegistrationField.POSTALCODE, postalCodeTextField.getValue());
		enteredData.put(RegistrationField.CITY, cityTextField.getValue());
		enteredData.put(RegistrationField.GENDER, genderSelect.getSelectedItem().get());
		enteredData.put(RegistrationField.EMAIL, emailTextField.getValue());
		enteredData.put(RegistrationField.NOTIFICATION_EMAIL, notificationEmailTextField.getValue());
		enteredData.put(RegistrationField.PASSWORD, passwordField.getValue());
		enteredData.put(RegistrationField.POSITION, positionSelect.getSelectedItem().get());
		enteredData.put(RegistrationField.HIREDATE,
				positionHireDateField.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		return enteredData;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Map<RegistrationField, String> enteredData = getEnteredData();

		if(!epm.registerUser(enteredData)){
			Notification.show("Benutzername existiert bereits.", Notification.Type.ERROR_MESSAGE);
		} else {
			Notification.show("Benutzer erfolgreich erstellt.");
		}
		// persist map

	}

}
