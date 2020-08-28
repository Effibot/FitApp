package logic.facade.calendar;


import java.util.Iterator;
import java.util.List;

import org.controlsfx.control.PopOver;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.controller.CalendarController;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;
import logic.viewcontroller.GymPopUpCalendarViewController;
import logic.viewcontroller.ReviewViewController;
import logic.viewcontroller.UserPopupCalendarViewController;

public class CalendarBehaviour {
	private static CalendarBehaviour instance = null;

	private ViewFactory viewFactory = ViewFactory.getInstance();
	private MonthPage monthPage;
	private boolean userProperty;
	private EntryCalendar entryCalendar;
	private CalendarSource calendarSource;
	private EventHandler<CalendarEvent> evtHandler;
	private CalendarsEvent calendarsEvent;
	private DayPage dayPage;
	private CalendarController calendarController;
	private List<EntryCustom<?>> allEvEntryCustoms;
	private List<EntryCustom<?>> allBookedSession;

	public void setSources( MonthPage monthPage, CalendarController calendarController,
			EntryCalendar entryCalendar, CalendarSource calendarSource, CalendarsEvent calendarsEvent,
			DayPage dayPage,boolean userProperty) {
		this.entryCalendar = entryCalendar;
		this.monthPage = monthPage;
		this.calendarSource = calendarSource;
		this.calendarController = calendarController;
		this.calendarsEvent = calendarsEvent;
		this.dayPage = dayPage;
		this.allEvEntryCustoms = calendarController.getAllEntry();
		this.allBookedSession = calendarController.getAllBookedSession();
		this.userProperty = userProperty;
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
		// Loading of main popup of calendar
		EntryCustom<?> currEntryCustom = null;
		System.out.println(allEvEntryCustoms);
		System.out.println("SONO gym");
		Iterator<EntryCustom<?>> enIterator;
		if(userProperty) {
			 enIterator = allEvEntryCustoms.iterator();
		}else {
			enIterator = allBookedSession.iterator();

		}
		while (enIterator.hasNext()) {
			EntryCustom<?> tEntryCustom = enIterator.next();
			if (param.getEntry().equals(tEntryCustom.getEntry())) {
				System.out.println("Entry Gia esistente");
				tEntryCustom.setEntry(param.getEntry());
				currEntryCustom = tEntryCustom;
				System.out.println(currEntryCustom.getEntry());

			}

		}if(currEntryCustom == null) {
			currEntryCustom = new EntryCustom<>(param.getEntry(), null);
		}
		System.out.println("THIS IS USER PROPERY"+userProperty);
		if (userProperty) {


			System.out.println("AARIVO QUI?");
			viewFactory.create(ViewType.MAINPOPUP);
			GymPopUpCalendarViewController popupViewController = (GymPopUpCalendarViewController) viewFactory.getCurrentController();
			// setting parameters to popupView
			popupViewController.setParam(currEntryCustom,calendarsEvent);


		}else {
			System.out.println("ENtro qui");
			viewFactory.create(ViewType.USERPOPUP);
			UserPopupCalendarViewController popupViewController = (UserPopupCalendarViewController) viewFactory.getCurrentController();
			// setting parameters to popupView
			popupViewController.setParam(currEntryCustom);
		}
		// adding popup to a box and return it
		HBox box = new HBox();
		box.getChildren().add(viewFactory.getRoot());

		return box;

	}
	/*
	 * Handling contexMenuItems item 1: view Reviews, item 2: delete only current
	 * event, item 3: delete all current event from calendar, item 4:send email to
	 * gym
	 * 
	 */
	EntryCustom<?> contextEntry = null;
	public ContextMenu rightClickEntry(EntryContextMenuParameter param) {
		// define element of right click contextMenu
		MenuItem item1 = new MenuItem("Information");
		MenuItem item2 = new MenuItem("Delete this");
		MenuItem item3 = new MenuItem("Delete All");
		MenuItem item4 = new MenuItem("Send e-mail");
		// getting current entry
		
		Iterator<EntryCustom<?>> enIterator;
		if(userProperty) {
			 enIterator = allEvEntryCustoms.iterator();
		}else {
			enIterator = allBookedSession.iterator();

		}
		while (enIterator.hasNext()) {
			EntryCustom<?> tEntryCustom = enIterator.next();
			if (param.getEntry().equals(tEntryCustom.getEntry())) {
				System.out.println("Entry Gia esistente");
				tEntryCustom.setEntry(param.getEntry());
				contextEntry = tEntryCustom;
				System.out.println(contextEntry.getEntry());

			}

		}if(contextEntry == null) {
			contextEntry = new EntryCustom<>(param.getEntry(), null);
		}
		
		// initialize stage
		Stage reviewStage = new Stage();
		reviewStage.initStyle(StageStyle.TRANSPARENT);
		reviewStage.initModality(Modality.APPLICATION_MODAL);
		reviewStage.setMinWidth(335);
		reviewStage.setMinHeight(150);

		item1.setOnAction(event -> {
			viewFactory.create(ViewType.REWIES);
			ReviewViewController reviewViewController = (ReviewViewController) viewFactory.getCurrentController();
			reviewViewController.setTypeView(this.userProperty);
			Scene scene = new Scene(viewFactory.getRoot());
			reviewStage.setScene(scene);
			reviewStage.showAndWait();
		});
		item2.setOnAction(event -> {
			
				entryCalendar.deleteCalendarEntry(this.getContextEntry());
			

		});
		item3.setOnAction(event -> this.getContextEntry().getEntry().getCalendar().clear());

		// Email sender
		item4.setOnAction(event -> {
			viewFactory.create(ViewType.EMAIL);

			Scene scene = new Scene(viewFactory.getRoot());
			reviewStage.setScene(scene);
			reviewStage.showAndWait();
		});

		ContextMenu rBox = new ContextMenu();

		if (userProperty) {
			rBox.getItems().addAll(item1, item2, item3);
		} else {

			rBox.getItems().addAll(item1, item2, item3, item4);

		}
		return rBox;
	}

	/*
	 * MultiplesEntris When on MonthPage there more than 2 event, it will show a
	 * popUp to manage events
	 * 
	 */

	private EntryCustom<?> getContextEntry() {
		return contextEntry;
	}

	public void multiplesEntries() {

		monthPage.addEventFilter(RequestEvent.REQUEST_DATE, event -> {

			// set current date
			dayPage.setDate(event.getDate());
			PopOver popOver = this.fullDayOver();
			// Auto hide property
			popOver.setAutoHide(true);
			popOver.show((Node) event.getTarget());

		});

	}

	public EventHandler<CalendarEvent> setEventHandler() {
	
			// Intercepting Calendar Event on clicking
			this.evtHandler = new EventHandler<CalendarEvent>() {

				@Override
				public void handle(CalendarEvent event) {

					EventType<CalendarEvent> calendarEvent1 = CalendarEvent.ENTRY_CALENDAR_CHANGED;
					// Calendar Event new entry added on Month Page
					if (event.getEventType().equals(calendarEvent1) && !userProperty) {
						event.getEntry().removeFromCalendar();

					}
				}
			};
		
		return this.evtHandler;
	}

	/* Return only the eventHandler of the calendar */
	public EventHandler<CalendarEvent> getEventHandler() {

		return evtHandler;
	}
	/* Setting the behavior of a full day view */

	public PopOver fullDayOver() {
		// dayPage layOut
		dayPage.setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
		dayPage.getCalendarSources().add(calendarSource);
		dayPage.setMinWidth(340);
		dayPage.setMaxHeight(300);
		// handle double click on new Entry
		dayPage.setEntryDetailsPopOverContentCallback(param -> instance.doubleClickEntry(param));
		// handle right click on new Entry
		dayPage.setEntryContextMenuCallback(param -> instance.rightClickEntry(param));
		/*
		 * Handling the behavior of calendar according to user or manager
		 */
		if (!userProperty) {
			for (int i = 0; i < 8; i++) {
				// all calendars are readOnly
				dayPage.getCalendars().get(i).setReadOnly(true);
			}
			// all double click are disable
			dayPage.getCalendars().get(0).addEventHandler(getEventHandler());

		} else {
			for (int i = 0; i < 7; i++) {
				// all calendar are manageable
				dayPage.getCalendars().get(i).setReadOnly(false);
			}
			// all double click are enabled
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
