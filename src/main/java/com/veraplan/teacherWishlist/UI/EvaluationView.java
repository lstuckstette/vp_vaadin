package com.veraplan.teacherWishlist.UI;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.veraplan.teacherWishlist.Model.CurrentUser;
import com.veraplan.teacherWishlist.Model.StaticSchoolData;
import com.veraplan.teacherWishlist.Model.TimeSlot;
import com.veraplan.teacherWishlist.Model.TimeSlotRowContainer;
import com.veraplan.teacherWishlist.Model.VacationItem;
import com.veraplan.teacherWishlist.PersistenceManagement.EvaluationPersistenceManager;

/**
 * EvaluationView is respresentig the 'Erhebungsbogen'-page of the application.
 * 
 * @author Lukas Stuckstette
 *
 */
@SuppressWarnings("serial")
@CDIView("evaluation")
public class EvaluationView extends CustomComponent implements View {

	// inject persistence manager
	@Inject
	EvaluationPersistenceManager epm;
	// inject CurrentUser bean
	@Inject
	CurrentUser user;

	// UI-elements:
	private ArrayList<TimeSlotRowContainer> periodicTimeTableData;
	private ArrayList<VacationItem> vacationList;
	private VerticalLayout masterLayout;
	private VerticalLayout vacationLayout;
	private FormLayout periodicLayout;
	private FormLayout vacationFormLayout;
	private Label pageHeader;
	private Grid<TimeSlotRowContainer> periodicGrid;
	private Grid<VacationItem> vacationGrid;
	private Button confirmButton;
	private TextArea periodicComment;
	private Panel vacationPanel;
	private Panel periodicPanel;

	/*
	 * builds UI of evaluation-view
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// initialize Containers
		periodicTimeTableData = new ArrayList<>();
		vacationList = new ArrayList<>();
		// setup Master-Layout
		masterLayout = new VerticalLayout();
		masterLayout.setMargin(true);
		masterLayout.setSizeFull();
		masterLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		setCompositionRoot(masterLayout);
		// show dummy Navigation-Header
		masterLayout.addComponent(new CustomMenuBar(getUI().getNavigator(), user));

		pageHeader = new Label("<h1>Erhebungsbogen zur Unterrichtsverteilung</h1>", ContentMode.HTML);
		masterLayout.addComponent(pageHeader);

		// spacing:
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		// show specific Vacation Chooser:

		vacationPanel = new Panel("Spezielle Urlaubswünsche:");
		masterLayout.addComponent(vacationPanel);
		masterLayout.setComponentAlignment(vacationPanel, Alignment.TOP_CENTER);
		vacationPanel.setWidth(null);

		vacationLayout = new VerticalLayout();
		vacationLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		vacationLayout.setMargin(true);
		vacationLayout.setSpacing(true);
		buildVacationChooser();

		vacationPanel.setContent(vacationLayout);
		// <--

		// spacing:
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		// show periodic Absence Grid:

		periodicPanel = new Panel("Periodische Abwesenheiten:");
		masterLayout.addComponent(periodicPanel);
		masterLayout.setComponentAlignment(periodicPanel, Alignment.TOP_CENTER);
		periodicPanel.setWidth(null);

		periodicLayout = new FormLayout();
		periodicLayout.setMargin(true);
		periodicLayout.setSpacing(true);
		setupPeriodicGridData();
		buildPeriodicGrid();

		periodicPanel.setContent(periodicLayout);

		// spacing:
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		// confirm Button that links to persist
		confirmButton = new Button("Alles Absenden");
		confirmButton.addClickListener(e -> {
			// checkLogin
			if (user.isLoggedIn()) {
				persistData();
			} else {
				// show not logged in error message
				Notification.show("Sie sind nicht eingeloggt!", Notification.Type.ERROR_MESSAGE);
				// redirect user to login page
				getUI().getNavigator().navigateTo("login");
			}

		});
		masterLayout.addComponent(confirmButton);

	}

	/**
	 * builds UI-elements concerning vacation entry
	 */
	private void buildVacationChooser() {
		vacationFormLayout = new FormLayout();
		vacationFormLayout.setMargin(true);
		DateField vacationStartDate = new DateField("Urlaubsanfang");
		vacationStartDate.setIcon(VaadinIcons.DATE_INPUT);
		vacationStartDate.setRequiredIndicatorVisible(true);
		vacationStartDate.setDateFormat("dd.MM.yyyy");

		DateField vacationEndDate = new DateField("Urlaubsende");
		vacationEndDate.setIcon(VaadinIcons.DATE_INPUT);
		vacationEndDate.setRequiredIndicatorVisible(true);
		vacationEndDate.setDateFormat("dd.MM.yyyy");

		TextArea vacationComment = new TextArea("Kommentar");
		vacationComment.setIcon(VaadinIcons.TEXT_INPUT);

		Button vacationAddButton = new Button("Zur Liste hinzufügen");
		vacationAddButton.addClickListener(e -> {
			LocalDate start = vacationStartDate.getValue();
			LocalDate end = vacationEndDate.getValue();
			String comment = vacationComment.getValue() == null ? "" : vacationComment.getValue();
			// check for null
			if (start == null || end == null) {
				Notification.show("Start-/Enddatum darf nicht leer sein!");
			} else {
				vacationList.add(new VacationItem(start, end, comment));
				// clear selection:
				vacationStartDate.clear();
				vacationEndDate.clear();
				vacationComment.clear();
				// update List:
				vacationGrid.getDataProvider().refreshAll();
			}
		});

		vacationFormLayout.addComponents(vacationStartDate, vacationEndDate, vacationComment, vacationAddButton);

		vacationLayout.addComponent(vacationFormLayout);
		vacationLayout.setComponentAlignment(vacationFormLayout, Alignment.TOP_CENTER);

		// spacing:
		vacationLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		// show Grid containing chosen Elements
		vacationGrid = new Grid<VacationItem>("Liste der eingetragenen Urlaubswünsche");
		vacationGrid.setItems(vacationList);
		vacationGrid.addColumn(item -> item.getStartDate()).setId("start").setCaption("Urlaubsbeginn");
		vacationGrid.addColumn(VacationItem::getEndDate).setId("end").setCaption("Urlaubsende");
		vacationGrid.addColumn(VacationItem::getComment).setId("comment").setCaption("Kommentar");
		vacationGrid.addColumn(vacationitem -> "Entfernen", new ButtonRenderer<>(clickEvent -> {
			vacationList.remove(clickEvent.getItem());
			vacationGrid.setItems(vacationList);
		})).setCaption("Aktion");

		vacationGrid.setHeightByRows(4);

		vacationLayout.addComponent(vacationGrid);
		// spacing:
		vacationLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));
	}

	/**
	 * builds UI-elements concerning periodic absence entry
	 */
	private void buildPeriodicGrid() {
		periodicComment = new TextArea("Kommentar");

		periodicGrid = new Grid<>("Bitte einzelne Felder markieren:");
		periodicGrid.setItems(periodicTimeTableData);
		periodicGrid.setHeightByRows(StaticSchoolData.TIMESLOT_COUNT);
		periodicGrid.addColumn(TimeSlotRowContainer::getTimeString).setId("time").setCaption("Uhrzeit")
				.setStyleGenerator(item -> "v-align-center");
		periodicGrid.addColumn(TimeSlotRowContainer::getMonday).setId("monday").setCaption("Montag")
				.setStyleGenerator(item -> "v-align-center");
		periodicGrid.addColumn(TimeSlotRowContainer::getTuesday).setId("tuesday").setCaption("Dienstag")
				.setStyleGenerator(item -> "v-align-center");
		periodicGrid.addColumn(TimeSlotRowContainer::getWednesday).setId("wednesday").setCaption("Mittwoch")
				.setStyleGenerator(item -> "v-align-center");
		periodicGrid.addColumn(TimeSlotRowContainer::getThursday).setId("thursday").setCaption("Donnerstag")
				.setStyleGenerator(item -> "v-align-center");
		periodicGrid.addColumn(TimeSlotRowContainer::getFriday).setId("friday").setCaption("Freitag")
				.setStyleGenerator(item -> "v-align-center");

		periodicGrid.setWidth("800");

		periodicGrid.addItemClickListener(new ItemClickListener<TimeSlotRowContainer>() {
			@Override
			public void itemClick(ItemClick<TimeSlotRowContainer> event) {
				Column<TimeSlotRowContainer, ?> col = event.getColumn();
				TimeSlotRowContainer row = event.getItem();
				TimeSlot ts;

				switch (col.getId()) {
				case "monday":
					ts = row.getMonday();
					ts.toggleSelected();
					break;
				case "tuesday":
					ts = row.getTuesday();
					ts.toggleSelected();
					break;
				case "wednesday":
					ts = row.getWednesday();
					ts.toggleSelected();
					break;
				case "thursday":
					ts = row.getThursday();
					ts.toggleSelected();
					break;
				case "friday":
					ts = row.getFriday();
					ts.toggleSelected();
					break;
				default:
					ts = new TimeSlot(-1, -1);
				}
			}
		});
		periodicLayout.addComponents(periodicGrid, periodicComment);
	}

	/**
	 * generates the predefined contents of the absence entry grid
	 */
	private void setupPeriodicGridData() {
		// populate:
		for (int i = 1; i <= StaticSchoolData.TIMESLOT_COUNT; i++) {
			periodicTimeTableData.add(new TimeSlotRowContainer(i));
		}
	}

	/**
	 * persists entered data
	 */
	private void persistData() {
		epm.persistEvaluation(user.getUser(), periodicTimeTableData, vacationList, periodicComment.getValue());
	}

}
