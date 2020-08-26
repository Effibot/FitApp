package logic.facade.calendar;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;


public class EntryCalendar {

	private CalendarsEvent calendarsEvent;

	public EntryCalendar(CalendarsEvent calendarsEvent) {
		this.calendarsEvent = calendarsEvent;
	}

	public Entry<?> setEntry(int year, int month, int day, int hour, int min) {
		//// Adding an Entry ////
		LocalDateTime ldtStart = LocalDateTime.of(year, month, day, hour, min);
		LocalDateTime ldtEnd = LocalDateTime.of(year, month, day, hour + 1, min + 30);
		Entry<?> entry = new Entry<>();

		entry.setTitle("KickBoxing");
		entry.setId("kick");
		entry.setLocation("Palestra Evolution");
		entry.setInterval(ldtStart, ldtEnd);
		entry.setCalendar(calendarsEvent);

		// Interval
		Duration fromHours = Duration.ofHours((long) 1.30);
		entry.setMinimumDuration(fromHours);
		return entry;
	}

	public Entry<?> setEntryCalendar(int year, int month, int day, int hour, int min, Calendar calendar) {
		//// Adding an Entry ////
		LocalDateTime ldtStart = LocalDateTime.of(year, month, day, hour, min);
		LocalDateTime ldtEnd = LocalDateTime.of(year, month, day, hour + 1, min + 30);
		Entry<?> entry = new Entry<>();

		entry.setInterval(ldtStart, ldtEnd);
		entry.setCalendar(calendar);
		entry.setTitle(calendar.getName());
		entry.setId(calendar.getName().toLowerCase());
		// Interval
		Duration fromHours = Duration.ofHours((long) 1.30);
		entry.setMinimumDuration(fromHours);
		return entry;
	}





	public void deleteCalendarEntry(Entry<?> contextEntry) {
		this.dateIterator(contextEntry);
	}

	private void dateIterator(Entry<?> contextEntry) {

		int dayToRemove = contextEntry.getStartDate().getDayOfMonth();
		int monthToRemove = contextEntry.getStartDate().getMonthValue();
		String tempCalendar = contextEntry.getCalendar().getName();
		int tempHour = contextEntry.getStartTime().getHour();
		int tempMin = contextEntry.getStartTime().getMinute();
		Calendar calendar = contextEntry.getCalendar();

		List<Entry<?>> ist = contextEntry.getCalendar().findEntries(contextEntry.getCalendar().getName());
		calendar.removeEntries(ist);

		Iterator<Entry<?>> iterator = ist.iterator();
		while (iterator.hasNext()) {

			Entry<?> iesEntry = iterator.next();
			LocalDate nextInstance = iesEntry.getStartDate();
			if (nextInstance != null) {


				if (nextInstance.getDayOfMonth() == dayToRemove && nextInstance.getMonthValue() == monthToRemove) {
					calendar.removeEntries(iesEntry);
				} else if (nextInstance.getDayOfMonth() != dayToRemove
						&& nextInstance.getMonthValue() == monthToRemove) {
					this.setEntryCalendar(nextInstance.getYear(), nextInstance
							.getMonthValue(),
							nextInstance.getDayOfMonth(), tempHour, tempMin,
							calendarsEvent.getCalendarById(tempCalendar));

				} else if (nextInstance.getDayOfMonth() != dayToRemove
						&& nextInstance.getMonthValue() != monthToRemove) {
					this.setEntryCalendar(nextInstance.getYear(), nextInstance
							.getMonthValue(),
							nextInstance.getDayOfMonth(), tempHour, tempMin,
							calendarsEvent.getCalendarById(tempCalendar));

				}
			}
		}

		ist.clear();
	}
}
