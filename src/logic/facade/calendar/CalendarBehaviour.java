package logic.facade.calendar;

import java.io.IOException;

import org.controlsfx.control.PopOver;

import com.calendarfx.model.Calendar;
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
import javafx.scene.Node;
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
import logic.viewcontroller.PopupViewController;
import logic.viewcontroller.ReviewViewController;

public class CalendarBehaviour {
	private static CalendarBehaviour instance = null;

	private CalendarViewFactory calendarViewFactory;
	private MonthPage monthPage;
	private boolean userProperty;
	private EntryCalendar entryCalendar;
	private CalendarSource calendarSource;
	private EventHandler<CalendarEvent> evtHandler;
	private CalendarsEvent calendarsEvent;
	private DayPage dayPage;



	public void setSources(CalendarViewFactory calendarViewFactory, MonthPage monthPage,
			boolean userProperty,
			EntryCalendar entryCalendar, CalendarSource calendarSource, CalendarsEvent calendarsEvent,
			DayPage dayPage) {
		this.calendarViewFactory = calendarViewFactory;
		this.entryCalendar = entryCalendar;
		this.monthPage = monthPage;
		this.entryCalendar = entryCalendar;
		this.calendarSource = calendarSource;
		this.userProperty = userProperty;
		this.calendarsEvent = calendarsEvent;
		this.dayPage = dayPage;
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

	public void multiplesEntries() {

		monthPage.addEventFilter(RequestEvent.REQUEST_DATE, event -> {


			dayPage.setDate(event.getDate());
			PopOver popOver = this.fullDayOver();

			popOver.setAutoHide(true);
			popOver.show((Node) event.getTarget());


		});

	}

	int i = 1;
	public EventHandler<CalendarEvent> setEventHandler() {

		monthPage.setEntryDetailsPopOverContentCallback(param -> {

			return instance.doubleClickEntry(param);
		});
		monthPage.setEntryContextMenuCallback(param -> instance.rightClickEntry(param));



		this.evtHandler = new EventHandler<CalendarEvent>() {

			@Override
			public void handle(CalendarEvent event) {
				
				EventType<CalendarEvent> calendarEvent1 = CalendarEvent.ENTRY_CALENDAR_CHANGED;
				if (event.getEventType().equals(calendarEvent1) && !userProperty) {
					System.out.println("NOT CLICK");
					event.getEntry().removeFromCalendar();


				} else if (event.getEventType().equals(calendarEvent1) && userProperty) {
					System.out.println("STO METTENDO UN ENTRY");
					Entry evEntry = event.getEntry();
					System.out.println("GET ID\t" + evEntry.getId());
					Calendar c = evEntry.getCalendar();
					if (evEntry.getTitle().equals("New Entry " + (i + 1))) {

						if (c.getName().equals("Default")) {
							java.util.List<Entry<?>> f = c.findEntries("New Entry " + i);

							System.out.println("ENTRY GEDITD" + f.get(0).getId());

							System.out.println("THis is entry" + f.get(0).getTitle());
							i++;
							Entry currEntry = f.get(0);
							if (Integer.parseInt(currEntry.getId()) == (i - 1)) {
								System.out.println(currEntry.getTitle());
								c.removeEntry(evEntry);
							}

						}

					}
				}
				//
				// else if (event.getEventType().equals(calendarEvent1) && userProperty) {
				//
				//// PopOver popOver = new PopOver(doubleClickEntry2(event.getEntry()));
				//
				//// popOver.show(node);
				//
				//
				// }
			}
		};
		return this.evtHandler;
	}



	public EventHandler<CalendarEvent> getEventHandler() {
		return evtHandler;
	}

	public PopOver fullDayOver() {
		dayPage.setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
		dayPage.getCalendarSources().add(calendarSource);
		dayPage.setMinWidth(340);
		dayPage.setMaxHeight(300);
		dayPage.setEntryDetailsPopOverContentCallback(param -> {

			return instance.doubleClickEntry(param);
		});
		dayPage.setEntryContextMenuCallback(param -> instance.rightClickEntry(param));
		if (!userProperty) {
			for (int i = 0; i < 8; i++) {
				dayPage.getCalendars().get(i).setReadOnly(true);
			}
			dayPage.getCalendars().get(0).addEventHandler(getEventHandler());

		} else {
			for (int i = 0; i < 7; i++) {
				dayPage.getCalendars().get(i).setReadOnly(false);
			}
			dayPage.getCalendars().get(0).removeEventHandler(getEventHandler());

		}
		return new PopOver(this.dayPage);

	}



	public static synchronized CalendarBehaviour getSingletoneInstance() {
		if (CalendarBehaviour.instance == null)
			CalendarBehaviour.instance = new CalendarBehaviour();
		return CalendarBehaviour.instance;
	}
}
