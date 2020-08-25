package logic.facade.calendar;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
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
		calendarBehaviour.setSources(calendarViewFactory, monthPage, userProperty, entryCalendar, calendarSource,
				calendarsEvent);
		monthPage.getCalendarSources().addAll(calendarSource);

		monthPage.setShowToday(true);
		monthPage.setMaxSize(680, 502);
		monthPage.setMinSize(680, 502);
		monthPage.getMonthView().setShowWeekNumbers(false);
		monthPage.setEntryDetailsPopOverContentCallback(param -> calendarBehaviour.doubleClickEntry(param));
		monthPage.setEntryContextMenuCallback(param -> calendarBehaviour.rightClickEntry(param));
		calendarBehaviour.multiplesEntries(monthPage);
		if (!userProperty) {
			eventHandler = calendarBehaviour.setEventHandler();
		} else {
			eventHandler = calendarBehaviour.getEventHandler();
		}
		return monthPage;

	}



	public EventHandler<CalendarEvent> getEventHandler() {
		return eventHandler;
	}

	


}
