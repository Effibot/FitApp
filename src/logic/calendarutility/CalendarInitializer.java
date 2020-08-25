package logic.calendarutility;

import java.io.IOException;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.LoadEvent;
import com.calendarfx.view.DateControl.EntryContextMenuParameter;
import com.calendarfx.view.DateControl.EntryDetailsPopOverContentParameter;
import com.calendarfx.view.RequestEvent;
import com.calendarfx.view.page.MonthPage;

import javafx.event.Event;
import javafx.event.EventHandler;
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
import logic.viewcontroller.ReviewViewController;

public class CalendarInitializer {
	private static CalendarInitializer instance = null;

	private MonthPage monthPage;
	private Entries entries;
	private CalendarSource calendarSource;
	private CalendarController cal = CalendarController.getSingletoneInstance();
	private CalendarViewFactory calendarViewFactory = CalendarViewFactory.getInstance();
	private FullDayViewController fullDayViewController;
	private Event update = new Event(LoadEvent.LOAD);
	private MainController ctrl = MainController.getInstance();
	private EventHandler<CalendarEvent> eventHandler;

	private boolean userProperty;

	protected CalendarInitializer() {

		this.entries = Entries.getSingletonInstance();
		this.monthPage = new MonthPage();
		System.out.println(userProperty);
		this.multiplesEntries();


	}

	public ContextMenu rightClickEntry(EntryContextMenuParameter param, boolean userProperty) {
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
				ReviewViewController reviewViewController = (ReviewViewController) calendarView.getCurrentController();
				reviewViewController.setTypeView(this.userProperty);
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
					entries.deleteCalendarEntry(contextEntry);
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

		if (userProperty) {
			rBox.getItems().addAll(item1, item2, item3);
		} else {


		rBox.getItems().addAll(item1, item2, item3, item4);

	}
		return rBox;
	}

	public HBox doubleClickEntry(EntryDetailsPopOverContentParameter param, boolean userProperty) {
		try {

			CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.MAINPOPUP);
			PopupViewController popupViewController = (PopupViewController) calendarView.getCurrentController();
			popupViewController.setParam(param, userProperty);
			popupViewController.setSelectedEvent();
			popupViewController.setDetailsPopup();
			popupViewController.setMonthPage(monthPage);

			HBox box = new HBox();
			box.getChildren().add(calendarView.getRoot());

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

				CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.FULLDAY);

				fullDayViewController = (FullDayViewController) calendarView
						.getCurrentController();
				fullDayViewController.setDaySources(calendarSource, event, this.userProperty);

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
		}
		calendarSource = cal.getCalendarSource(id);
		monthPage.getCalendarSources().addAll(calendarSource);
	}


	public EventHandler<CalendarEvent> setEventHandler() {
		this.eventHandler = new EventHandler<CalendarEvent>() {
			@Override
			public void handle(CalendarEvent event) {
				
					event.getEntry().removeFromCalendar();

			}
		};
		return this.eventHandler;
	}

	public EventHandler<CalendarEvent> getEventHandler() {
		return this.eventHandler;
	}


	public MonthPage setView(boolean userProperty) {
		this.userProperty = userProperty;
		monthPage.setShowToday(true);
		monthPage.setMaxSize(680, 502);
		monthPage.setMinSize(680, 502);
		monthPage.getMonthView().setShowWeekNumbers(false);

		monthPage.setEntryDetailsPopOverContentCallback(param -> doubleClickEntry(param, userProperty));
		monthPage.setEntryContextMenuCallback(param -> rightClickEntry(param, userProperty));
		return this.monthPage;
	
	}


}
