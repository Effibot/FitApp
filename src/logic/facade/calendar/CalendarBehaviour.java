package logic.facade.calendar;

import java.io.IOException;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl.EntryContextMenuParameter;
import com.calendarfx.view.DateControl.EntryDetailsPopOverContentParameter;
import com.calendarfx.view.RequestEvent;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.calendarviewfactory.CalendarViewFactory;
import logic.factory.calendarviewfactory.CalendarViewType;
import logic.view.calendarview.CalendarView;
import logic.viewcontroller.FullDayViewController;
import logic.viewcontroller.PopupViewController;
import logic.viewcontroller.ReviewViewController;

public class CalendarBehaviour {
	private static CalendarBehaviour instance = null;

	private CalendarViewFactory calendarViewFactory;
	private MonthPage monthPage;
	private boolean userProperty;
	private EntryCalendar entryCalendar;
	private FullDayViewController fullDayViewController;
	private CalendarSource calendarSource;
	private EventHandler<CalendarEvent> evtHandler;
	private CalendarsEvent calendarsEvent;
	private DayPage dayPage;

	private EventHandler<CalendarEvent> evtHandler1;


	public void setSources(CalendarViewFactory calendarViewFactory, MonthPage monthPage,
			boolean userProperty,
			EntryCalendar entryCalendar, CalendarSource calendarSource, CalendarsEvent calendarsEvent) {
		this.calendarViewFactory = calendarViewFactory;
		this.entryCalendar = entryCalendar;
		this.monthPage = monthPage;
		this.entryCalendar = entryCalendar;
		this.calendarSource = calendarSource;
		this.userProperty = userProperty;
		this.calendarsEvent = calendarsEvent;
	}

	public CalendarsEvent getCalendarsEvent() {
		return calendarsEvent;
	}

	public EntryCalendar getEntryCalendar() {
		return entryCalendar;
	}

	protected CalendarBehaviour() {

	}

	public HBox doubleClickEntry(EntryDetailsPopOverContentParameter param) {
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
					entryCalendar.deleteCalendarEntry(contextEntry);
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

	public void multiplesEntries(MonthPage monthPage) {
		monthPage.addEventFilter(RequestEvent.REQUEST_DATE, event -> {
			try {

				Stage stage = new Stage();
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.initModality(Modality.APPLICATION_MODAL);

				CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.FULLDAY);

				fullDayViewController = (FullDayViewController) calendarView.getCurrentController();
				fullDayViewController.setDaySources(calendarSource, event, this.userProperty, monthPage);
				dayPage = fullDayViewController.getDayPage();
				if (!userProperty) {
					dayPage.getCalendars().get(0).addEventHandler(getEventHandler());
					for (int i = 0; i < 7; i++) {
						dayPage.getCalendars().get(i).addEventHandler(getEventHandler1());
					}
				}
				else {
					dayPage.getCalendars().get(0).removeEventHandler(getEventHandler());
					for (int i = 0; i < 7; i++) {
						dayPage.getCalendars().get(i).removeEventHandler(getEventHandler1());
					}
				}
				Scene scene = new Scene(calendarView.getRoot());
				stage.setScene(scene);
				stage.showAndWait();
			} catch (IOException e) {
				AlertFactory.getInstance().createAlert(e);
			}

		});
	}

	public EventHandler<CalendarEvent> setEventHandler() {
		this.evtHandler = new EventHandler<CalendarEvent>() {

			@Override
			public void handle(CalendarEvent event) {
				EventType<CalendarEvent> calendarEvent1 = CalendarEvent.ENTRY_CALENDAR_CHANGED;

				if (event.getEventType().equals(calendarEvent1))
					event.getEntry().removeFromCalendar();
			}
		};
		return this.evtHandler;
	}

	public EventHandler<CalendarEvent> setEventHandler1() {
		this.evtHandler1 = new EventHandler<CalendarEvent>() {

			@Override
			public void handle(CalendarEvent event) {
				EventType<CalendarEvent> calendarEvent2 = CalendarEvent.ENTRY_INTERVAL_CHANGED;

				if (event.getEventType().equals(calendarEvent2)) {
					event.getEntry().getCalendar().setReadOnly(true);
				}
			}
		};
		return this.evtHandler1;
		}

	public EventHandler<CalendarEvent> getEventHandler() {
		return evtHandler;
	}

	public EventHandler<CalendarEvent> getEventHandler1() {
		return evtHandler1;
	}


	public static synchronized CalendarBehaviour getSingletoneInstance() {
		if (CalendarBehaviour.instance == null)
			CalendarBehaviour.instance = new CalendarBehaviour();
		return CalendarBehaviour.instance;
	}
}
