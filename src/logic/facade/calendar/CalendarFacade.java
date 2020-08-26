package logic.facade.calendar;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.DateControl.EntryDetailsPopOverContentParameter;
import com.calendarfx.view.page.MonthPage;

import javafx.event.EventHandler;
import logic.controller.CalendarController;
import logic.factory.calendarviewfactory.CalendarViewFactory;

public class CalendarFacade {

	private CalendarController calendarController;
	private CalendarViewFactory calendarViewFactory;
	private EntryCalendar entryCalendar;
	private CalendarsEvent calendarsEvent;
	private EventHandler<CalendarEvent> eventHandler;
	private boolean userProperty;
	private MonthPage monthPage;
	private EntryDetailsPopOverContentParameter entryPopover;
	
	public CalendarFacade(boolean userProperty) {
		this.calendarViewFactory = CalendarViewFactory.getInstance();
		this.calendarsEvent = new CalendarsEvent();
		this.entryCalendar = new EntryCalendar(calendarsEvent);
		this.userProperty = userProperty;
		this.monthPage = new MonthPage();
		this.calendarController = new CalendarController(calendarsEvent, entryCalendar);

	}

	public MonthPage initializeCalendar(int id) {
		CalendarBehaviour calendarBehaviour = CalendarBehaviour.getSingletoneInstance();
		CalendarSource calendarSource = calendarController.getCalendarSource(id);

		monthPage.getCalendarSources().addAll(calendarSource);

		monthPage.setShowToday(true);
		monthPage.setMaxSize(680, 502);
		monthPage.setMinSize(680, 502);
		monthPage.getMonthView().setShowWeekNumbers(false);
		calendarBehaviour.multiplesEntries(monthPage);
		monthPage.setEntryDetailsPopOverContentCallback(
				param ->

				calendarBehaviour.doubleClickEntry(param));
		monthPage.setEntryContextMenuCallback(param -> calendarBehaviour.rightClickEntry(param));
		// entryPopover = monthPage.getEntryDetailsPopOverContentCallback();
		calendarBehaviour.setSources(calendarViewFactory, monthPage, userProperty, entryCalendar, calendarSource,
				calendarsEvent, entryPopover);
		eventHandler = calendarBehaviour.setEventHandler();

		return monthPage;

	}



	public EventHandler<CalendarEvent> getEventHandler() {
		return eventHandler;
	}

	


}
