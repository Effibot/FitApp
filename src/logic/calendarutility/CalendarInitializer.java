package logic.calendarutility;

import java.io.IOException;

import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl.EntryContextMenuParameter;
import com.calendarfx.view.DateControl.EntryDetailsPopOverContentParameter;
import com.calendarfx.view.RequestEvent;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.controller.CalendarController;
import logic.controller.MainController;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.calendarviewfactory.CalendarViewFactory;
import logic.factory.calendarviewfactory.CalendarViewType;
import logic.view.calendarview.CalendarView;
import logic.viewcontroller.FullDayViewController;
import logic.viewcontroller.PopupViewController;

public class CalendarInitializer {
	private static CalendarInitializer instance = null;

	private MonthPage monthPage;
	private DayPage dayPage;
	private Entries entries;
	private static final String gym = "gym";
	private CalendarSource calendarSource;
	private CalendarController cal = CalendarController.getSingletoneInstance();
	private CalendarViewFactory calendarViewFactory = CalendarViewFactory.getInstance();

	Event update = new Event(CalendarEvent.CALENDAR_CHANGED);
	MainController ctrl = MainController.getInstance();

	protected CalendarInitializer() {

		this.entries = Entries.getSingletonInstance();
		this.monthPage = new MonthPage();
		monthPage.setShowToday(true);
		monthPage.setMaxSize(680, 502);
		monthPage.setMinSize(680, 502);
		this.multiplesEntries();

		this.monthPage.fireEvent(update);

		monthPage.setEntryDetailsPopOverContentCallback(param -> doubleClickEntry(param));
		monthPage.setEntryContextMenuCallback(param -> rightClickEntry(param));

	}

	public ContextMenu rightClickEntry(EntryContextMenuParameter param) {
		MenuItem item1 = new MenuItem("Information");
		MenuItem item2 = new MenuItem("Delete this");
		MenuItem item3 = new MenuItem("Delete All");
		MenuItem item4 = new MenuItem("Send e-mail");
		Entry<?> contextEntry = param.getEntry();
		Stage reviewStage = new Stage();
		reviewStage.initStyle(StageStyle.TRANSPARENT);
		reviewStage.initModality(Modality.APPLICATION_MODAL);
		reviewStage.setMinWidth(335);
		reviewStage.setMinHeight(150);
		item1.setOnAction(event -> {
			try {

				CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.REWIES);

				Scene scene = new Scene(calendarView.getRoot());
				reviewStage.setScene(scene);
				reviewStage.showAndWait();
			} catch (IOException e) {
				AlertFactory.getInstance().createAlert(e);
			}
		});
		item2.setOnAction(event -> {
			if (!contextEntry.getTitle().contains("New Entry")) {

				if (contextEntry.getRecurrenceRule() == null)
					contextEntry.removeFromCalendar();
				else {
					try {
						entries.deleteCalendarEntry(contextEntry);
					} catch (InvalidRecurrenceRuleException e) {
						AlertFactory.getInstance().createAlert(e);
					}
				}

			} else {
				contextEntry.removeFromCalendar();
			}

		});
		item3.setOnAction(event -> contextEntry.getCalendar().clear());

		// Email sender
		item4.setOnAction(event -> {
			try {

				CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.EMAIL);

				Scene scene = new Scene(calendarView.getRoot());
				reviewStage.setScene(scene);
				reviewStage.showAndWait();

			} catch (IOException e) {
				AlertFactory.getInstance().createAlert(e);
			}
		});

		ContextMenu rBox = new ContextMenu();
		rBox.getItems().addAll(item1, item2, item3, item4);

		return rBox;
	}

	public HBox doubleClickEntry(EntryDetailsPopOverContentParameter param) {
		try {
			CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.MAINPOPUP);

			PopupViewController popupViewController = (PopupViewController) calendarView.getCurrentController();
			popupViewController.setParam(param);
			popupViewController.setSelectedEvent();
			popupViewController.setDetailsPopup();
			popupViewController.setMonthPage(monthPage);

			HBox box = new HBox();
			box.getChildren().add(calendarView.getRoot());
			calendarView.getRoot().setOnMouseExited(event -> monthPage.fireEvent(update));

			return box;
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;

	}

	public void multiplesEntries() {
		monthPage.addEventFilter(RequestEvent.REQUEST_DATE, event -> {
			try {
				Stage stage = new Stage();
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.initModality(Modality.APPLICATION_MODAL);
				/*
				 * FXMLLoader rootFXML = new
				 * FXMLLoader(getClass().getResource("/logic/fxml/fullDay.fxml")); Parent root =
				 * rootFXML.load(); FullDayViewController fullDayViewController =
				 * rootFXML.getController();
				 * fullDayViewController.setCalendarSource(calendarSource);
				 */
				CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.FULLDAY);

				FullDayViewController fullDayViewController = (FullDayViewController) calendarView
						.getCurrentController();
				fullDayViewController.setDaySources(calendarSource, event);

				Scene scene = new Scene(calendarView.getRoot());
				stage.setScene(scene);
				stage.showAndWait();
			} catch (IOException e) {
				AlertFactory.getInstance().createAlert(e);
			}

		});
	}

	public MonthPage getMonthPage() {
		return monthPage;
	}

	public void setMonthPage(MonthPage monthPage) {
		this.monthPage = monthPage;
	}

	public static synchronized CalendarInitializer getSingletonInstance() {
		if (CalendarInitializer.instance == null)
			CalendarInitializer.instance = new CalendarInitializer();
		return instance;
	}

	public void refresh(int id) {
		if (this.calendarSource != null) {
			for (int i = 0; i < 7; i++) {
				Calendar tmpCalendar = this.calendarSource.getCalendars().get(i);
				tmpCalendar.clear();

			}
			this.monthPage.fireEvent(update);
		}
		calendarSource = cal.getCalendarSource(id);
		monthPage.getCalendarSources().addAll(calendarSource);
	}
}
