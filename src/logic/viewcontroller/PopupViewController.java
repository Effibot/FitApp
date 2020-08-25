package logic.viewcontroller;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.page.MonthPage;
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
import logic.facade.calendar.CalendarBehaviour;
import logic.facade.calendar.CalendarsEvent;
import logic.facade.calendar.EntryCalendar;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.calendarviewfactory.CalendarViewFactory;
import logic.factory.calendarviewfactory.CalendarViewType;
import logic.view.calendarview.CalendarView;

public class PopupViewController {
	private static PopupViewController instance = null;
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
	@FXML
	private JFXButton saveBtn;
	@FXML
	private JFXButton deleteBtn;
	@FXML
	private MenuButton intervalMenu;
	@FXML
	private JFXButton eMailBtn;
	@FXML
	private JFXButton setCourse;
	@FXML
	private MenuButton colorMenu;
	@FXML
	private JFXTextArea textArea;

	private Entry<?> selectedEntry;
	private CalendarsEvent calendars;
	private EntryCalendar entries;
	private DateControl.EntryDetailsPopOverContentParameter param;
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	CalendarViewFactory calendarViewFactory = CalendarViewFactory.getInstance();
	private MonthPage monthPage;
	private boolean userProperty;



	public DateControl.EntryDetailsPopOverContentParameter getParam() {
		return param;
	}

	public void setParam(DateControl.EntryDetailsPopOverContentParameter param, boolean userProperty) {
		this.param = param;
		this.userProperty = userProperty;
		this.setParamView();

	}




	public PopupViewController() {
		CalendarBehaviour calendarBehaviour = CalendarBehaviour.getSingletoneInstance();
		this.entries = calendarBehaviour.getEntryCalendar();
		this.calendars = calendarBehaviour.getCalendarsEvent();
	}

	public void setSelectedEvent() {

		selectedEntry = this.getParam().getEntry();
	}



	@FXML
	public void setRecurrence(ActionEvent event) {
		Object obj = event.getSource();
		String rrule = null;
		LocalDate startDay = selectedEntry.getStartDate();
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
		selectedEntry.setRecurrenceRule(rrule);

		intervalMenu.setText(daily.getText());


	}


	@FXML
	public void delete(MouseEvent event) {

		try {
			Stage window = new Stage();
			window.initStyle(StageStyle.TRANSPARENT);
			window.initModality(Modality.APPLICATION_MODAL);
			window.setMinWidth(335);
			window.setMinHeight(150);

			CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.DELETEPOPUP);
			PopupDeleteController popupDeleteController = (PopupDeleteController) calendarView.getCurrentController();
			popupDeleteController.setEntryToDelete(selectedEntry, entries);
			System.out.println(selectedEntry.getId());
			Scene scene = new Scene(calendarView.getRoot());
			window.setScene(scene);
			window.showAndWait();
		} catch (IOException ex) {
			AlertFactory.getInstance().createAlert(ex);
		}

	}

	@FXML
	void saveCourse(MouseEvent event) {
		// Gym sets event
	}

	@FXML
	private void save(MouseEvent event) {
		// user request booking
	}

	public void setNameEvent() {
		courseNameId.setText(selectedEntry.getCalendar().getName());
		courseNameId.setEditable(false);

	}

	public void setDateEvent() {
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

	public void setDetailsPopup() {
		this.setNameEvent();
		this.setDateEvent();
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
		selectedEntry.setCalendar(calMenuItem);
		selectedEntry.setTitle(calMenuItem.getName());
		courseNameId.setText(calMenuItem.getName());


	}


	@FXML
	public void setTimeEntry(ActionEvent event) {
		Object obj = event.getSource();
		LocalTime time;

		if (obj.hashCode() == timeId.hashCode()) {
			time = timeId.getValue();
			selectedEntry.changeStartTime(time);

		} else if (obj.hashCode() == timeId1.hashCode()) {
			time = timeId1.getValue();
			selectedEntry.changeEndTime(time);
		}
	}

	@FXML
	void sendEmail(MouseEvent event) {
		try {

			Stage emlStage = new Stage();
			emlStage.initStyle(StageStyle.TRANSPARENT);
			emlStage.initModality(Modality.APPLICATION_MODAL);
			emlStage.setMinWidth(335);
			emlStage.setMinHeight(150);



			CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.EMAIL);
			EmailViewController emailViewController = (EmailViewController) calendarView.getCurrentController();
			/// Da mettere

			// emailViewController.setEvent(selectedEntry.getCalendar().getName(),
			// selectedEntry.getStartTime().toString(), null );
			Scene scene = new Scene(calendarView.getRoot());
			emlStage.setScene(scene);
			emlStage.showAndWait();

		} catch (IOException ex) {
			AlertFactory.getInstance().createAlert(ex);
		}
	}

	public static synchronized PopupViewController getSingletonInstance() {
		if (PopupViewController.instance == null)
			PopupViewController.instance = new PopupViewController();
		return instance;
	}

	public void setMonthPage(MonthPage monthPage) {
		this.monthPage = monthPage;
	}

	private void setParamView() {
		if (userProperty) {
			eMailBtn.setVisible(false);
			setCourse.setVisible(true);
			saveBtn.setVisible(false);
			courseNameId.setEditable(false);
			dateId.setEditable(true);
			timeId.setEditable(true);
			timeId1.setEditable(true);
			colorMenu.setVisible(true);
			intervalMenu.setVisible(true);
			textArea.setEditable(true);
			timeId.setDisable(false);
			timeId1.setDisable(false);
			dateId.setDisable(false);

		} else {
			eMailBtn.setVisible(true);
			setCourse.setVisible(false);
			saveBtn.setVisible(true);
			courseNameId.setEditable(false);
			dateId.setEditable(false);
			timeId.setEditable(false);
			timeId1.setEditable(false);
			colorMenu.setVisible(false);
			intervalMenu.setVisible(false);
			textArea.setEditable(false);
			timeId.setDisable(true);
			timeId1.setDisable(true);
			dateId.setDisable(true);

		}
	}
}
