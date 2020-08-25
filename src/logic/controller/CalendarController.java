package logic.controller;

import java.util.GregorianCalendar;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;

import logic.calendarutility.Calendars;
import logic.calendarutility.Entries;
import logic.entity.Gym;
import logic.entity.Session;
import logic.entity.User;
import logic.entity.dao.GymDAO;
import logic.entity.dao.SessionDAO;
import logic.entity.dao.UserDAO;

public class CalendarController {


	private static CalendarController instance = null;
	private CalendarSource calendarSource;
	private Entries entries;
	private Calendars calendars;
	private GymDAO gymDAO;
	private SessionDAO sessionDAO;
	private UserDAO userDAO;
	private List<Calendar> calendarsList;



	protected CalendarController() {
		this.calendars = Calendars.getSingletonInstance();
		this.entries = Entries.getSingletonInstance();
		this.gymDAO = GymDAO.getInstance();
		this.sessionDAO = SessionDAO.getInstance();
		this.userDAO = UserDAO.getInstance();

	}

	public static synchronized CalendarController getSingletoneInstance() {
		if(CalendarController.instance == null)
			CalendarController.instance = new CalendarController();
		return CalendarController.instance;
	}

	public CalendarSource getCalendarSource(int id) {
		calendarSource = new CalendarSource(String.valueOf(id));

		this.populateCalendar(id);
		calendarSource.getCalendars().addAll(calendars.getAvaiableCalendar());
		return calendarSource;		
	}

	public void populateCalendar( int userId) {
		User user = userDAO.getUserEntity(userId);
		Calendar calendar;
		Entry<?> entry;
		if(user.isManager()) {
			Gym gym = gymDAO.getGymEntityById(user.getId());
			List<Session> managerSession = sessionDAO.getCourseGym(gym.getGymId());

			for(Session s:managerSession) {

				String gymName = gymDAO.getGymEntityById(Integer.parseInt(s.getGym())).getGymName();
				String courseName = sessionDAO.getCourseById(s.getCourseId());
				s.setGym(gymName);
				s.setCourseName(courseName);
				calendar = calendars.getCalendarBynName(courseName);
				GregorianCalendar newCalendar = new GregorianCalendar();
				newCalendar.setTime(s.getDate());
				int year = newCalendar.get(java.util.Calendar.YEAR);
				int month = newCalendar.get(java.util.Calendar.MONTH) + 1;
				int day = newCalendar.get(java.util.Calendar.DAY_OF_MONTH);
				int hours = newCalendar.get(java.util.Calendar.HOUR);
				int min = newCalendar.get(java.util.Calendar.MINUTE);
				entry = entries.setEntryCalendar(year, month, day, hours, min, calendar);

				calendar.addEntries(entry);

			}
		}else {
			List<Integer> userSessions = sessionDAO.getBookedSessionById(user.getId());
			for (Integer sessionId : userSessions) {
				Session bookedSession = sessionDAO.getBookedSessionEntity(sessionId);
				Gym gym = gymDAO.getGymEntityById(Integer.parseInt(bookedSession.getGym()));

				bookedSession.setGym(gym.getGymName());
				bookedSession.setCourseName(sessionDAO.getCourseById(bookedSession.getCourseId()));

				calendar = calendars.getCalendarBynName(bookedSession.getCourseName());
				GregorianCalendar newCalendar = new GregorianCalendar();
				newCalendar.setTime(bookedSession.getDate());
				int year = newCalendar.get(java.util.Calendar.YEAR);
				int month = newCalendar.get(java.util.Calendar.MONTH) + 1;
				int day = newCalendar.get(java.util.Calendar.DAY_OF_MONTH);
				int hours = newCalendar.get(java.util.Calendar.HOUR);
				int min = newCalendar.get(java.util.Calendar.MINUTE);
				entry = entries.setEntryCalendar(year, month, day, hours, min, calendar);
				if (bookedSession.getRecurrence() != null) {
					entry.setRecurrenceRule(bookedSession.getRecurrence());
				}
				calendar.addEntries(entry);
			}
		}
	}


}
