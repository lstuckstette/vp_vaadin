package com.veraplan.teacherWishlist.UI;

import java.time.LocalDate;
import java.util.ArrayList;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.veraplan.teacherWishlist.Model.StaticSchoolData;
import com.veraplan.teacherWishlist.Model.TimeSlot;
import com.veraplan.teacherWishlist.Model.TimeSlotRowContainer;
import com.veraplan.teacherWishlist.Model.VacationItem;
import com.veraplan.teacherWishlist.PersistenceManagement.EvaluationPersistenceManager;

@SuppressWarnings("serial")
@CDIView("evaluation")
public class EvaluationView extends CustomComponent implements View {

	private ArrayList<TimeSlotRowContainer> periodicTimeTableData;
	private ArrayList<VacationItem> vacationList;
	private MenuBar menu;
	private VerticalLayout masterLayout;
	private HorizontalLayout vacationHorizontalLayout;
	private Label headerVacation, headerPeriodic, pageHeader;
	private Grid<TimeSlotRowContainer> periodicGrid;
	private Grid<VacationItem> vacationGrid;
	private Button confirmButton;
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// initialize Containers
				periodicTimeTableData = new ArrayList<>();
				vacationList = new ArrayList<>();
				// setup Master-Layout
				masterLayout = new VerticalLayout();
				masterLayout.setMargin(true);
				masterLayout.setWidth("100%");
				masterLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);

				// show dummy Navigation-Header
				masterLayout.addComponent(new EvaluationMenuBar(getUI().getNavigator()));

				pageHeader = new Label("<h1>Erhebungsbogen zur Unterrichtsverteilung</h1>", ContentMode.HTML);
				masterLayout.addComponent(pageHeader);

				// spacing:
				masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

				// show specific Vacation Chooser:
				buildVacationChooser();

				// <--

				// spacing:
				masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));
				masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

				// periodic absence
				headerPeriodic = new Label("<b>Periodische Abwesenheiten:</b>", ContentMode.HTML);
				masterLayout.addComponent(headerPeriodic);

				// spacing:
				masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

				setupPeriodicGridData();
				buildPeriodicGrid();

				// spacing:
				masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));
				masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

				// confirm Button that links to persist
				confirmButton = new Button("Absenden");
				confirmButton.addClickListener(e -> {
					persistData();
				}); // button.setWidth("100%");
				masterLayout.addComponents(confirmButton);

				setCompositionRoot(masterLayout);
		
	}
	
	private void buildVacationChooser() {
		headerVacation = new Label("<b>Spezifische Urlaubswünsche:</b>", ContentMode.HTML);
		masterLayout.addComponent(headerVacation);

		vacationHorizontalLayout = new HorizontalLayout();
		vacationHorizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		Label vacationStartCaption = new Label("Urlaubsanfang");
		Label vacationEndCaption = new Label("Urlaubsende");
		DateField vacationStartDate = new DateField();
		vacationStartDate.setDateFormat("dd.MM.yyyy");
		DateField vacationEndDate = new DateField();
		vacationEndDate.setDateFormat("dd.MM.yyyy");

		TextArea vacationComment = new TextArea("Kommentar");
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

		VerticalLayout vacationStartLayout = new VerticalLayout();
		vacationStartLayout.addComponents(vacationStartCaption, vacationStartDate);

		VerticalLayout vacationEndLayout = new VerticalLayout();
		vacationEndLayout.addComponents(vacationEndCaption, vacationEndDate);

		vacationHorizontalLayout.addComponents(vacationStartLayout, vacationEndLayout, vacationComment,
				vacationAddButton);

		masterLayout.addComponent(vacationHorizontalLayout);
		// spacing:
		masterLayout.addComponent(new Label("&nbsp;", ContentMode.HTML));

		// show Grid containing chosen Elements
		vacationGrid = new Grid<VacationItem>("Liste der eingetragenen Urlaubswünsche");
		vacationGrid.setItems(vacationList);
		vacationGrid.addColumn(VacationItem::getStartDate).setId("start").setCaption("Urlaubsbeginn");
		vacationGrid.addColumn(VacationItem::getEndDate).setId("end").setCaption("Urlaubsende");
		vacationGrid.addColumn(VacationItem::getComment).setId("comment").setCaption("Kommentar");
		vacationGrid.addColumn(vacationitem -> "Entfernen", new ButtonRenderer<>(clickEvent -> {
			vacationList.remove(clickEvent.getItem());
			vacationGrid.setItems(vacationList);
		})).setCaption("Aktion");
		vacationGrid.setWidth("50%");
		vacationGrid.setHeightByRows(4);

		masterLayout.addComponent(vacationGrid);
	}

	private void buildPeriodicGrid() {
		
		HorizontalLayout periodicHorizontalLayout = new HorizontalLayout();
		periodicHorizontalLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		
		periodicHorizontalLayout.setWidth("50%");
		TextArea periodicComment = new TextArea("Kommentar");
		periodicComment.setWidth("100%");
		
		periodicGrid = new Grid<>("Periodische Abwesenheit -  bitte einzelne Felder markieren:");
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
		periodicGrid.setWidth("100%");
		
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
				// grid.markAsDirtyRecursive();
				// Notification.show("Selected: " + ts.printInfo());
			}
		});
		
		//periodicComment.setHeight("400");
		periodicHorizontalLayout.addComponents(periodicGrid, periodicComment);
		periodicHorizontalLayout.setExpandRatio(periodicGrid, 5);
		periodicHorizontalLayout.setExpandRatio(periodicComment, 1);
		
		masterLayout.addComponent(periodicHorizontalLayout);
	}

	private void buildMenu() {
		menu = new MenuBar();
		menu.addItem("Startseite", null, null);
		menu.addItem("Login", null, null);
		menu.addItem("Wunschzettel", null, null);
		menu.addItem("Administration", null, null);
		
		menu.setWidth("100%");
		masterLayout.addComponent(menu);
	}

	private void setupPeriodicGridData() {

		// populate:
		for (int i = 1; i <= StaticSchoolData.TIMESLOT_COUNT; i++) {
			periodicTimeTableData.add(new TimeSlotRowContainer(i));
		}

	}

	private void persistData() {
		new EvaluationPersistenceManager();
		
	}

}














	

