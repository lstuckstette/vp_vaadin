package com.veraplan.teacherWishlist.UI;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.veraplan.teacherWishlist.Entities.Absence;
import com.veraplan.teacherWishlist.Entities.Periodicabsencetimeslot;
import com.veraplan.teacherWishlist.Entities.Person;
import com.veraplan.teacherWishlist.Entities.Teacherwishlist;
import com.veraplan.teacherWishlist.Model.CurrentUser;
import com.veraplan.teacherWishlist.Model.StaticSchoolData;
import com.veraplan.teacherWishlist.Model.TimeSlotRowContainer;
import com.veraplan.teacherWishlist.PersistenceManagement.EvaluationExportManager;
import com.veraplan.teacherWishlist.PersistenceManagement.EvaluationPersistenceManager;

@SuppressWarnings("serial")
@CDIView("administration")
public class AdministrationView extends CustomComponent implements View, ClickListener {

	@Inject
	CurrentUser user;
	@Inject
	EvaluationPersistenceManager epm;
	@Inject 
	EvaluationExportManager eem;

	private Navigator navigator;
	private Label pageHeader;

	private Panel teacherWishlistSelect;
	private Panel teacherWishlistView;

	private VerticalLayout masterLayout;
	private FormLayout teacherWishlistSelectLayout;
	private VerticalLayout teacherWishlistViewLayout;

	private Button teacherWishlistSelectConfirm;
	private Button generateJSONBatch;
	private ComboBox<Person> selectTeacher;
	private ComboBox<Teacherwishlist> selectWishList;

	@Override
	public void enter(ViewChangeEvent event) {

		navigator = getUI().getNavigator();

		masterLayout = new VerticalLayout();
		masterLayout.setSizeFull();
		masterLayout.setMargin(true);
		masterLayout.setSpacing(true);
		masterLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		masterLayout.addComponent(new CustomMenuBar(navigator, user));
		setCompositionRoot(masterLayout);

		pageHeader = new Label("<h1>Administration</h1>", ContentMode.HTML);
		masterLayout.addComponent(pageHeader);
		// spacing:
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		// build TeacherWishlistSelect Panel:
		teacherWishlistSelect = new Panel("Erhebungsbogen auswählen:");
		masterLayout.addComponent(teacherWishlistSelect);
		masterLayout.setComponentAlignment(teacherWishlistSelect, Alignment.TOP_CENTER);
		teacherWishlistSelect.setWidth(null);

		// content:
		teacherWishlistSelectLayout = new FormLayout();
		teacherWishlistSelectLayout.setMargin(true);

		selectTeacher = new ComboBox<Person>("Lehrer auswählen");
		selectTeacher.setRequiredIndicatorVisible(true);
		selectTeacher.setItems(epm.getPeopleRepresentingTeacher());
		selectTeacher.setItemCaptionGenerator(person -> person.getFirstName() + "" + person.getLastName());
		selectTeacher.addSelectionListener(selectEvent -> {
			selectWishList.setItems(epm.getTeacherwishlist(selectTeacher.getSelectedItem().get().getTeacher()));
		});

		selectWishList = new ComboBox<Teacherwishlist>("Erhebungsbogen auswählen:");
		selectWishList.setRequiredIndicatorVisible(true);
		selectWishList.setItemCaptionGenerator(twl -> convertTime(twl.getDate()));
		selectWishList.setItems();

		teacherWishlistSelectConfirm = new Button("Auswahl betrachten");
		teacherWishlistSelectConfirm.addClickListener(this);

		generateJSONBatch = new Button("Alle Erhebungsdaten exportieren:");
		StreamResource downloadData = eem.getEvaluationBatchJSONData();
		FileDownloader fileDownloader = new FileDownloader(downloadData);
		fileDownloader.extend(generateJSONBatch);

		teacherWishlistSelectLayout.addComponents(selectTeacher, selectWishList, teacherWishlistSelectConfirm,
				generateJSONBatch);
		// <--

		teacherWishlistSelect.setContent(teacherWishlistSelectLayout);

		// build On-Demand TeacherWishlistView Panel

		// spacing:
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		
		// dummy-Panel:
		teacherWishlistView = new Panel("Auswahl:");

		masterLayout.addComponent(teacherWishlistView);
		teacherWishlistView.setVisible(false);
	}

	private String convertTime(Timestamp t) {
		if (t == null) {
			return "<no-time-string>";
		}

		Date date = new Date();
		date.setTime(t.getTime());
		return convertTime(date);
	}

	private String convertTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return dateFormat.format(date);
	}

	private void replaceViewPanel() {

		masterLayout.removeComponent(teacherWishlistView);
		teacherWishlistView = new Panel("Auswahl:");
		masterLayout.addComponent(teacherWishlistView);
		masterLayout.setComponentAlignment(teacherWishlistView, Alignment.TOP_CENTER);
		teacherWishlistView.setWidth(null);
		teacherWishlistViewLayout = new VerticalLayout();
		teacherWishlistViewLayout.setMargin(true);
		teacherWishlistViewLayout.setSpacing(true);
		teacherWishlistViewLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		// Get Entities:
		Teacherwishlist twl = selectWishList.getValue();
		List<Absence> vacationList = twl.getAbsences();
		List<Periodicabsencetimeslot> periodicList = twl.getPeriodicabsencetimeslots();
		// Add Vacation Grid:
		Grid<Absence> vacationGrid = new Grid<>("Urlaubswünsche");
		vacationGrid.setItems(vacationList);
		vacationGrid.addColumn(item -> convertTime(item.getStart())).setCaption("Urlaubsbeginn");
		vacationGrid.addColumn(item -> convertTime(item.getEnd())).setCaption("Urlaubende");
		vacationGrid.addColumn(Absence::getComment).setCaption("Kommentar");
		teacherWishlistViewLayout.addComponent(vacationGrid);

		// spacing:
		teacherWishlistViewLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		teacherWishlistViewLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		// Add Periodic Grid:
		Grid<TimeSlotRowContainer> periodicGrid = new Grid<TimeSlotRowContainer>("Periodische Abwesenheiten");
		periodicGrid.setItems(TimeSlotRowContainer.fromPeriodicabsencetimeslotList(periodicList));
		periodicGrid.setHeightByRows(StaticSchoolData.TIMESLOT_COUNT);
		periodicGrid.addColumn(TimeSlotRowContainer::getTimeString).setCaption("Uhrzeit")
				.setStyleGenerator(item -> "v-align-center");
		periodicGrid.addColumn(TimeSlotRowContainer::getMonday).setCaption("Montag")
				.setStyleGenerator(item -> "v-align-center");
		periodicGrid.addColumn(TimeSlotRowContainer::getTuesday).setCaption("Dienstag")
				.setStyleGenerator(item -> "v-align-center");
		periodicGrid.addColumn(TimeSlotRowContainer::getWednesday).setCaption("Mittwoch")
				.setStyleGenerator(item -> "v-align-center");
		periodicGrid.addColumn(TimeSlotRowContainer::getThursday).setCaption("Donnerstag")
				.setStyleGenerator(item -> "v-align-center");
		periodicGrid.addColumn(TimeSlotRowContainer::getFriday).setCaption("Freitag")
				.setStyleGenerator(item -> "v-align-center");

		periodicGrid.setWidth("800");
		teacherWishlistViewLayout.addComponent(periodicGrid);

		teacherWishlistView.setContent(teacherWishlistViewLayout);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// remove old Panel:
		// masterLayout.removeComponent(teacherWishlistView);
		// create new ViewPanel

		replaceViewPanel();

	}

}
