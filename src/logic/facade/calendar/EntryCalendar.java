package logic.facade.calendar;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

import logic.model.entity.Session;

public class EntryCalendar {

	private CalendarsEvent calendarsEvent;
	private EntryCustom entryCustom;
	
	public EntryCalendar(CalendarsEvent calendarsEvent) {
		this.calendarsEvent = calendarsEvent;
	}

	/*
	 * Adding a calendar Entry by year, month, day, hour,minute by a reference of
	 * calendar.
	 */
	public EntryCustom<?> setEntryCalendar(LocalDateTime startTime, LocalDateTime endTime, Calendar calendar, Session managerCourse) {
		// Parsing the given int day to a LocalDateTime

		// New instance of an entry
		Entry<?> entry = new Entry<>();
		// Setting entry parameter
		entry.setInterval(startTime, endTime);
		entry.setCalendar(calendar);
		entry.setTitle(calendar.getName());
		entry.setId(calendar.getName().toLowerCase());
		// Interval
		Duration fromHours = Duration.ofHours((long) 1.30);
		entry.setMinimumDuration(fromHours);
		
		return new EntryCustom<>(entry, managerCourse);
	}

	public void deleteCalendarEntry(EntryCustom<?> contextEntry) {
		this.dateIterator(contextEntry);
	}

	/*
	 * Date iterator: iterate through a list of entry to delete a single event given
	 * by an contextEntry
	 * 
	 */
	private void dateIterator(EntryCustom<?> contextEntry) {
		// Get from contextEntry calendar, startTime and endTime of current event
		String tempCalendar = contextEntry.getEntry().getCalendar().getName();
		LocalTime starLocalTime = contextEntry.getEntry().getStartTime();
		LocalTime endoLocalTime = contextEntry.getEntry().getEndTime();
		Session session = contextEntry.getSession();
		LocalDateTime startTime = contextEntry.getEntry().getStartAsLocalDateTime();
		// Getting day and month of the entry to remove
		int dayToRemove = startTime.getDayOfMonth();
		int monthToRemove = startTime.getMonthValue();
		LocalDateTime endTime = contextEntry.getEntry().getEndAsLocalDateTime();
		// getting calendar
		Calendar calendar = contextEntry.getEntry().getCalendar();
		// Getting all entry of the given calendar
		List<Entry<?>> list = calendar.findEntries(contextEntry.getEntry().getCalendar().getName());
		// remove all entry in the given calendar
		calendar.removeEntries(list);
		/*
		 * Iterate through list of entry each element is an entry if the entry to remove
		 * belongs to current month, then simply delete the event if the current day
		 * belongs to currentMonth but the day is different, then add the day in
		 * calendar if the current day doesn't belong currentMonth or the current day to
		 * remove, then add the event
		 */
		Iterator<Entry<?>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Entry<?> iesEntry = iterator.next();
			LocalDate nextInstance = iesEntry.getStartDate();
			if (nextInstance != null) {

				if (nextInstance.getDayOfMonth() == dayToRemove && nextInstance.getMonthValue() == monthToRemove) {
					calendar.removeEntries(iesEntry);
				} else if (nextInstance.getDayOfMonth() != dayToRemove
						&& nextInstance.getMonthValue() == monthToRemove) {
					this.setEntryCalendar(nextInstance.atTime(starLocalTime), nextInstance.atTime(endoLocalTime),
							calendarsEvent.getCalendarBynName(tempCalendar),session);

				} else if (nextInstance.getDayOfMonth() != dayToRemove
						&& nextInstance.getMonthValue() != monthToRemove) {
					this.setEntryCalendar(startTime, endTime, calendarsEvent.getCalendarBynName(tempCalendar),session);

				}
			}
		}
		// Clear the entry list
		list.clear();
	}
}
