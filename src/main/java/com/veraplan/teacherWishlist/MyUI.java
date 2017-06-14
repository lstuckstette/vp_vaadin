package com.veraplan.teacherWishlist;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.PropertySet;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.v7.data.util.BeanItemContainer;

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

		timeTableData = new ArrayList<>();

		VerticalLayout layout = new VerticalLayout();

		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);

		// username
		TextField name = new TextField();
		name.setCaption("Type your name here:");
		layout.addComponent(name);
		// display TimeTable Grid

		setupGridData();

		Grid<TimeSlotRowContainer> grid = new Grid<>();
		grid.setWidth("75%");
		grid.addColumn(TimeSlotRowContainer::getTimeString).setId("time").setCaption("Uhrzeit");
		grid.addColumn(TimeSlotRowContainer::getMonday).setId("monday").setCaption("Montag");
		grid.addColumn(TimeSlotRowContainer::getTuesday).setId("tuesday").setCaption("Dienstag");
		grid.addColumn(TimeSlotRowContainer::getWednesday).setId("wednesday").setCaption("Mittwoch");
		grid.addColumn(TimeSlotRowContainer::getThursday).setId("thursday").setCaption("Donnerstag");
		grid.addColumn(TimeSlotRowContainer::getFriday).setId("friday").setCaption("Freitag");
		
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
				//grid.markAsDirtyRecursive();
				//Notification.show("Selected: " + ts.printInfo());
			}
		});

		grid.setItems(timeTableData);

		layout.addComponent(grid);

		// confirm Button with listener
		Button button = new Button("Send");
		button.addClickListener(e -> {
			layout.addComponent(new Label("Thanks " + name.getValue() + ", it works!"));
		});

		layout.addComponents(button);

		setContent(layout);
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
