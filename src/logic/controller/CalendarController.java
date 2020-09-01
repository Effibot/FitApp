package logic.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import logic.facade.calendar.CalendarsEvent;
import logic.facade.calendar.EntryCalendar;
import logic.facade.calendar.EntryCustom;
import logic.model.dao.GymDAO;
import logic.model.dao.SessionDAO;
import logic.model.dao.UserDAO;
import logic.model.entity.Gym;
import logic.model.entity.Session;
import logic.model.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarController {

    private EntryCalendar entries;
    private CalendarsEvent calendars;
    private GymDAO gymDAO;
    private SessionDAO sessionDAO;
    private UserDAO userDAO;
    private List<EntryCustom<?>> allEvEntryCustoms;
    private List<EntryCustom<?>> allUserBookedSession;

    private List<Session> managerSession;

    // public CalendarController(CalendarsEvent calendarsEvent, EntryCalendar
    // entryCalendar, CalendarBean bean) {
    public CalendarController(CalendarsEvent calendarsEvent, EntryCalendar entryCalendar) {
        this.calendars = calendarsEvent;
        this.entries = entryCalendar;
        this.gymDAO = GymDAO.getInstance();
        this.sessionDAO = SessionDAO.getInstance();
        this.userDAO = UserDAO.getInstance();
        this.allEvEntryCustoms = new ArrayList<>();
        this.managerSession = new ArrayList<>();
        this.allUserBookedSession = new ArrayList<>();
    }

    public CalendarSource getCalendarSource(int id) {
        // Instance of new calendar Source
        CalendarSource calendarSource = new CalendarSource(String.valueOf(id));
        // Populate calendar by user or manager id
        this.populateCalendar(id);
        // adding all the calendar event to calendar resource of Month and Day Page
        calendarSource.getCalendars().addAll(calendars.getAvaiableCalendar());
        return calendarSource;
    }

    public void populateCalendar(int userId) {
        // Getting User from userDao
        User user = userDAO.getUserEntity(userId);
        Calendar calendar;
        EntryCustom<?> entry;
        // Manage behavior between manager and user
        if (user.isManager()) {
            // Get gymEntyry from database
            Gym gym = gymDAO.getGymEntityById(user.getId());
            // Getting all the manager session from database
            managerSession = sessionDAO.getCourseGym(gym.getGymId());
            /*
             * Take values for all specific session of the current manager which: gymName,
             * courseName,calendarName, start and time of the session and putting in an
             * entry to populate calendar
             */
            for (Session managerCourse : managerSession) {

                // Getting name gym, name course to complete the Session entity
                String gymName = gym.getGymName();
                String courseName = sessionDAO.getCourseById(managerCourse.getCourseId());
                managerCourse.setGym(gymName);
                managerCourse.setCourseName(courseName);
                // Get calendar name by course name
                calendar = calendars.getCalendarBynName(courseName);
                /*
                 * Manage conversion between type Time of Session entity and LocalDateTime of
                 * Calendar
                 */

                LocalTime timeStart = managerCourse.getTimeStart().toLocalTime();
                LocalTime timeEnd = managerCourse.getTimeEnd().toLocalTime();
                LocalDate localDate = managerCourse.getDate().toLocalDate();
                LocalDateTime dateTimeStarTime = localDate.atTime(timeStart);
                LocalDateTime dateTimeEndTime = localDate.atTime(timeEnd);

                // Setting entry to calendar
                entry = entries.setEntryCalendar(dateTimeStarTime, dateTimeEndTime, calendar, managerCourse);
                entry.setSession(managerCourse);
                // Calendar adds the entry
                allEvEntryCustoms.add(entry);
                calendar.addEntries(entry.getEntry());

            }
        } else {
            // Getting in database the specific list of event booked by userId
            List<Integer> userSessions = sessionDAO.getBookedSessionById(user.getId());
            System.out.println("bookedSession by "+ user +":" +userSessions);
            /*
             * Iterate in userSession Each sessionId is an id of one session which must be
             * sought between training_session table. Then create an entry and add to
             * calendar
             */
            for (Integer sessionId : userSessions) {
                Session bookedSession = sessionDAO.getBookedSessionEntity(sessionId);
                Gym gym = gymDAO.getGymEntityById(Integer.parseInt(bookedSession.getGym()));
                bookedSession.setGym(gym.getGymName());
                bookedSession.setCourseName(sessionDAO.getCourseById(bookedSession.getCourseId()));
                calendar = calendars.getCalendarBynName(bookedSession.getCourseName());
                GregorianCalendar newCalendar = new GregorianCalendar();
                newCalendar.setTime(bookedSession.getDate());
                LocalDate localDate = bookedSession.getDate().toLocalDate();
                LocalDateTime dateTimeStarTime = localDate.atTime(bookedSession.getTimeStart().toLocalTime());
                LocalDateTime dateTimeEndTime = localDate.atTime(bookedSession.getTimeEnd().toLocalTime());

                entry = entries.setEntryCalendar(dateTimeStarTime, dateTimeEndTime, calendar, bookedSession);
                /*
                 * Control the booked recurrence, if it is not null then set the recurrence
                 * Pending: it could be necessary to iterate
                 */
                if (bookedSession.getRecurrence() != null) {
                    entry.setRecurrenceRule(bookedSession.getRecurrence());
                }
                calendar.addEntries(entry.getEntry());
                allUserBookedSession.add(entry);
            }
        }
    }

    public List<EntryCustom<?>> getAllEntry() {
        return allEvEntryCustoms;
    }

    public List<EntryCustom<?>> getAllBookedSession() {
        return allUserBookedSession;
    }

}
