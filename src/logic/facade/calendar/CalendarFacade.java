package logic.facade.calendar;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import javafx.event.EventHandler;
import logic.controller.CalendarController;

public class CalendarFacade {
    private CalendarController calendarController;
    private EntryCalendar entryCalendar;
    private CalendarsEvent calendarsEvent;
    private EventHandler<CalendarEvent> eventHandler;
    private boolean userProperty;
    private MonthPage monthPage;
    private DayPage dayPage;

    // Inseirre bean tra userPageView Controller / GymPage viewcontroller
    // public CalendarFacade(boolean userProperty, CalendarBean bean) {
    public CalendarFacade(boolean userProperty) {
        this.calendarsEvent = new CalendarsEvent();
        this.entryCalendar = new EntryCalendar(calendarsEvent);
        this.userProperty = userProperty;
        this.monthPage = new MonthPage();
        // this.calendarController = new CalendarController(calendarsEvent,
        // entryCalendar, bean);
        this.calendarController = new CalendarController(calendarsEvent, entryCalendar);
        this.dayPage = new DayPage();
    }

    public MonthPage initializeCalendar(int id) {
        CalendarBehaviour calendarBehaviour = CalendarBehaviour.getSingletoneInstance();
        // Take avaiable calendares and punt in a calendarSource
        CalendarSource calendarSource = calendarController.getCalendarSource(id);
        // Set al sources to manage calendar
        calendarBehaviour.setSources(monthPage, calendarController, entryCalendar, calendarSource, calendarsEvent,
                dayPage, userProperty);

        // add all avaiable calendar ad insert into monthPage
        monthPage.getCalendarSources().addAll(calendarSource);
        // setting view
        monthPage.setShowToday(true);
        monthPage.setMaxSize(680, 502);
        monthPage.setMinSize(680, 502);
        monthPage.getMonthView().setShowWeekNumbers(false);
        // Handling click on ad entry
        monthPage.setEntryDetailsPopOverContentCallback(param -> calendarBehaviour.doubleClickEntry(param));
        // handling rightClick on calendar entry
        monthPage.setEntryContextMenuCallback(param -> calendarBehaviour.rightClickEntry(param));
        // show Full day View
        calendarBehaviour.multiplesEntries();

        // setting calendar beahaviour
        eventHandler = calendarBehaviour.setEventHandler();

        return monthPage;

    }

    public EventHandler<CalendarEvent> getEventHandler() {
        // return the eventHandler
        return eventHandler;
    }

}
