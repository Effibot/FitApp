package logic.facade.calendar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.calendarfx.model.Calendar;

public class CalendarsEvent extends Calendar {
	private List<Calendar> availableCalendar;
	// All Calendar list with its own label, style and then add to avaiableCalendar
	// list

	public CalendarsEvent() {
		availableCalendar = new ArrayList<>();
		// Calendar adding a new label
		Calendar cal1 = new Calendar("Kick Boxing");
		cal1.setStyle(Calendar.Style.STYLE1);
		availableCalendar.add(cal1);
		Calendar cal2 = new Calendar("Pugilato");
		cal2.setStyle(Calendar.Style.STYLE2);
		availableCalendar.add(cal2);
		// Calendar adding a new label
		Calendar cal3 = new Calendar("Zumba");
		cal3.setStyle(Calendar.Style.STYLE3);
		availableCalendar.add(cal3);
		Calendar cal4 = new Calendar("Salsa");
		cal4.setStyle(Calendar.Style.STYLE4);
		availableCalendar.add(cal4);
		// Calendar adding a new label
		Calendar cal5 = new Calendar("Funzionale");
		cal5.setStyle(Calendar.Style.STYLE5);
		availableCalendar.add(cal5);
		Calendar cal6 = new Calendar("Walking");
		cal6.setStyle(Calendar.Style.STYLE6);
		availableCalendar.add(cal6);
		// Calendar adding a new label
		Calendar cal7 = new Calendar("Pump");
		cal7.setStyle(Calendar.Style.STYLE7);
		availableCalendar.add(cal7);
	}

	public Calendar getCalendar(int i) {
		/*
		 * It return the calendar by id There is "connection" between id of the course
		 * and id of this list. Each element of available.
		 */
		return availableCalendar.get(i);
	}

	/*
	 * Method to return a calendar by name in which there is a iterator to iterate
	 * among all calendar available.
	 */
	public Calendar getCalendarBynName(String name) {
		Iterator<Calendar> calendarIterator = availableCalendar.iterator();
		int i = 0;
		while (calendarIterator.hasNext()) {
			if (calendarIterator.next().getName().equals(name)) {
				return availableCalendar.get(i);
			}
			i++;
		}
		return availableCalendar.get(i);

	}

	/* Get all calendars */
	public List<Calendar> getAvaiableCalendar() {

		return availableCalendar;
	}
}
