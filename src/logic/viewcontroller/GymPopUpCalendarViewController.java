package logic.viewcontroller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.DateControl.EntryDetailsPopOverContentParameter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.facade.calendar.CalendarsEvent;
import logic.facade.calendar.EntryCalendar;
import logic.facade.calendar.EntryCustom;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;

public class GymPopUpCalendarViewController {
	@FXML
	private MenuItem style1;
	@FXML
	private MenuItem style2;
	@FXML
	private MenuItem style3;
	@FXML
	private MenuItem style4;
	@FXML
	private MenuItem style5;
	@FXML
	private MenuItem style6;
	@FXML
	private MenuItem style7;
	@FXML
	private JFXTextField courseNameId;
	@FXML
	private JFXTextField dateId;
	@FXML
	private JFXTimePicker timeId;
	@FXML
	private JFXTimePicker timeId1;
	@FXML
	private MenuItem daily;
	@FXML
	private MenuItem weekly;
	@FXML
	private MenuItem monthly;
//	@FXML
//	private JFXButton saveBtn;
	@FXML
	private JFXButton deleteBtn;
	@FXML
	private MenuButton intervalMenu;
	@FXML
	private JFXButton eMailBtn;
	@FXML
	private JFXButton setButton;
	@FXML
	private MenuButton colorMenu;
	@FXML
	private JFXTextArea textArea;

	private EntryCustom<?> selectedEntry;
	private CalendarsEvent calendars;
	private EntryCalendar entries;
	private DateControl.EntryDetailsPopOverContentParameter param;
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	ViewFactory viewFactory = ViewFactory.getInstance();



	public DateControl.EntryDetailsPopOverContentParameter getParam() {
		return param;
	}

	public void setParam(EntryCustom<?> currEntryCustom, CalendarsEvent calendarsEvent) {

		this.calendars = calendarsEvent;
		this.selectedEntry = currEntryCustom;
		courseNameId.setText(selectedEntry.getEntry().getCalendar().getName());
		courseNameId.setEditable(false);
		dateId.setText(selectedEntry.getStartDate().format(dateFormatter));
		timeId.set24HourView(true);
		timeId.setValue(selectedEntry.getStartTime());
		timeId.setDefaultColor(Color.valueOf("#009688"));
		timeId1.set24HourView(true);
		timeId1.setValue(selectedEntry.getEndTime());
		timeId1.setDefaultColor(Color.valueOf("#009688"));
		dateId.setEditable(false);
		timeId.setEditable(false);
		timeId1.setEditable(false);
	


	}


	@FXML
	public void setRecurrence(ActionEvent event) {
		Object obj = event.getSource();
		String rrule = null;
		LocalDate startDay = selectedEntry.getEntry().getStartDate();
		if (obj.hashCode() == daily.hashCode()) {

			rrule = "RRULE:FREQ=DAILY;INTERVAL=7;COUNT=4;";


		} else if (obj.hashCode() == weekly.hashCode()) {
			LocalDate weeklyDay = startDay.plusDays(7);
			String weeklyFormatted = weeklyDay.format(dateTimeFormatter);
			rrule = "RRULE:FREQ=DAILY;UNTIL=" + weeklyFormatted.replace("-", "");


		}
		if (obj.hashCode() == monthly.hashCode()) {
			LocalDate monthlyDay = startDay.plusMonths(1);
			String monthlyFormatted = monthlyDay.format(dateTimeFormatter);
			rrule = "RRULE:FREQ=DAILY;UNTIL=" + monthlyFormatted.replace("-", "");


		}
		selectedEntry.getEntry().setRecurrenceRule(rrule);

		intervalMenu.setText(daily.getText());


	}


	@FXML
	public void delete(MouseEvent event) {
		Stage window = new Stage();
		window.initStyle(StageStyle.TRANSPARENT);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(335);
		window.setMinHeight(150);

		viewFactory.create(ViewType.DELETEPOPUP);
		PopupDeleteController popupDeleteController = (PopupDeleteController) viewFactory.getCurrentController();
		popupDeleteController.setEntryToDelete(selectedEntry);
		System.out.println(selectedEntry.getEntry().getId());
		Scene scene = new Scene(viewFactory.getRoot());
		window.setScene(scene);
		window.showAndWait();

	}

	@FXML
	void setCourse(ActionEvent event) {
		// Gym sets event
		System.out.println("LOL");
	}

	





	@FXML
	void setStyleEntry(ActionEvent event) {
		Object obj = event.getSource();
		Calendar calMenuItem = calendars.getCalendar(0);

		if (obj.hashCode() == style1.hashCode()) {
			calMenuItem = calendars.getCalendar(0);

		}
		if (obj.hashCode() == style2.hashCode()) {
			calMenuItem = calendars.getCalendar(1);

		}
		if (obj.hashCode() == style3.hashCode()) {
			calMenuItem = calendars.getCalendar(2);

		}
		if (obj.hashCode() == style4.hashCode()) {
			calMenuItem = calendars.getCalendar(3);

		}
		if (obj.hashCode() == style5.hashCode()) {
			calMenuItem = calendars.getCalendar(4);

		}
		if (obj.hashCode() == style6.hashCode()) {
			calMenuItem = calendars.getCalendar(5);

		}
		if (obj.hashCode() == style7.hashCode()) {
			calMenuItem = calendars.getCalendar(6);

		}
		selectedEntry.getEntry().setCalendar(calMenuItem);
		selectedEntry.getEntry().setTitle(calMenuItem.getName());
		courseNameId.setText(calMenuItem.getName());


	}


	@FXML
	public void setTimeEntry(ActionEvent event) {
		Object obj = event.getSource();
		LocalTime time;

		if (obj.hashCode() == timeId.hashCode()) {
			time = timeId.getValue();
			selectedEntry.getEntry().changeStartTime(time);

		} else if (obj.hashCode() == timeId1.hashCode()) {
			time = timeId1.getValue();
			selectedEntry.getEntry().changeEndTime(time);
		}
	}

	@FXML
	void sendEmail(MouseEvent event) {
		Stage emlStage = new Stage();
		emlStage.initStyle(StageStyle.TRANSPARENT);
		emlStage.initModality(Modality.APPLICATION_MODAL);
		emlStage.setMinWidth(335);
		emlStage.setMinHeight(150);



		viewFactory.create(ViewType.EMAIL);
		EmailViewController emailViewController = (EmailViewController) viewFactory.getCurrentController();
		/// Da mettere

		emailViewController.setEvent(selectedEntry.getEntry().getCalendar().getName(),
				selectedEntry.getEntry().getStartTime().toString());
		Scene scene = new Scene(viewFactory.getRoot());
		emlStage.setScene(scene);
		emlStage.showAndWait();
	}



}
