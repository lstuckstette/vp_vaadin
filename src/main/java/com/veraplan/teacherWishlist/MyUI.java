package com.veraplan.teacherWishlist;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.veraplan.teacherWishlist.Entities.Person;




/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@SuppressWarnings("serial")
@Theme("mytheme")
public class MyUI extends UI {

	private ArrayList<TimeSlotRowContainer> timeTableData;

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		
		testJPA();
		
		timeTableData = new ArrayList<>();

		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setWidth("100%");
		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		// show dummy Navigation-Header

		MenuBar menu = new MenuBar();
		menu.addItem("Startseite", null, null);
		menu.addItem("Login", null, null);
		menu.addItem("Wunschzettel", null, null);
		menu.addItem("Hilfe", null, null);
		menu.setWidth("100%");
		layout.addComponent(menu);

		// username
		TextField name = new TextField();
		name.setCaption("Type your name here:");
		layout.addComponent(name);
		// display TimeTable Grid

		setupGridData();

		Grid<TimeSlotRowContainer> grid = new Grid<>("Periodische Abwesenheit bitte markieren:");
		grid.setItems(timeTableData);
		grid.addColumn(TimeSlotRowContainer::getTimeString).setId("time").setCaption("Uhrzeit")
				.setStyleGenerator(item -> "v-align-center");
		grid.addColumn(TimeSlotRowContainer::getMonday).setId("monday").setCaption("Montag")
				.setStyleGenerator(item -> "v-align-center");
		grid.addColumn(TimeSlotRowContainer::getTuesday).setId("tuesday").setCaption("Dienstag")
				.setStyleGenerator(item -> "v-align-center");
		grid.addColumn(TimeSlotRowContainer::getWednesday).setId("wednesday").setCaption("Mittwoch")
				.setStyleGenerator(item -> "v-align-center");
		grid.addColumn(TimeSlotRowContainer::getThursday).setId("thursday").setCaption("Donnerstag")
				.setStyleGenerator(item -> "v-align-center");
		grid.addColumn(TimeSlotRowContainer::getFriday).setId("friday").setCaption("Freitag")
				.setStyleGenerator(item -> "v-align-center");
		grid.setWidth("50%");
		grid.addItemClickListener(new ItemClickListener<TimeSlotRowContainer>() {

			@Override
			public void itemClick(ItemClick<TimeSlotRowContainer> event) {
				Column<TimeSlotRowContainer, ?> col = event.getColumn();
				// TimeSlot element = (TimeSlot) col.getValueProvider();
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

		layout.addComponent(grid);

		// confirm Button with listener
		Button button = new Button("Send");
		button.addClickListener(e -> {
			layout.addComponent(new Label("Thanks " + name.getValue() + ", it works!"));
		});
		// button.setWidth("100%");
		layout.addComponents(button);

		setContent(layout);
	}

	private void testJPA() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqlconn");
		EntityManager em = emf.createEntityManager();
		int id=1;
		Person p1 = (Person) em.find(Person.class,id);
		Notification.show("JPA: found person "+ p1.getFirstName() + " "+p1.getLastName()+ "with ID "+id);
		
		
	}

	private void setupGridData() {

		// populate:
		for (int i = 1; i <= StaticSchoolData.TIMESLOT_COUNT; i++) {
			timeTableData.add(new TimeSlotRowContainer(i));
		}

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
