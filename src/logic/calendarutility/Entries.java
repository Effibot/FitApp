package logic.calendarutility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

public class Entries {
	private static Entries instance = null;
	private static final String PATTERN = "yyyy-MM-dd HH:mm";
	static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern(PATTERN);
	private Calendars cal;


	protected Entries() {
		cal = Calendars.getSingletonInstance();
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
		entry.setCalendar(cal);

		// Interval
		Duration fromHours = Duration.ofHours((long) 1.30);
		entry.setMinimumDuration(fromHours);
		return entry;
	}

	public Entry setEntryCalendar(int year, int month, int day, int hour, int min, Calendar calendar) {
		//// Adding an Entry ////
		LocalDateTime ldtStart = LocalDateTime.of(year, month, day, hour, min);
		LocalDateTime ldtEnd = LocalDateTime.of(year, month, day, hour + 1, min + 30);
		Entry<?> entry = new Entry<>();


		entry.setInterval(ldtStart, ldtEnd);
		entry.setCalendar(calendar);
		entry.setTitle(calendar.getName());
		entry.setId(calendar.getName().toLowerCase());
		entry.setLocation("Palestra Evolution");
		// Interval
		Duration fromHours = Duration.ofHours((long) 1.30);
		entry.setMinimumDuration(fromHours);
		return entry;
	}

	public void searchEntry(int year, int month, int dayMonth, String calendar) {
		///////// Search in Calendar /////////
		List<Entry<?>> entries = cal.findEntries(calendar);
		for (Entry<?> s : entries) {
			Logger logger = Logger.getLogger("bof");
			logger.log(Level.INFO, s.getId());
		}

	}

	public void deleteEntry(int year, int month, int dayOfMonth, String calendar) {
		List<Entry<?>> entries = cal.findEntries(calendar);
		for (Entry<?> entry : entries) {
			//// Delete from calendar by id /////
			if (entry.matches("kick")) {
				entry.removeFromCalendar();
				return;
			}
		}

	}


	public void deleteCalendarEntry(Entry<?> contextEntry) {
		this.dateIterator(contextEntry);
	}

	private void dateIterator(Entry<?> contextEntry) {

		Entries correctEntry = Entries.getSingletonInstance();

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

			Entry<?> iesEntry = (Entry<?>) iterator.next();
			LocalDate nextInstance = iesEntry.getStartDate();

			if (nextInstance.getDayOfMonth() == dayToRemove && nextInstance.getMonthValue() == monthToRemove) {
				calendar.removeEntries(iesEntry);
			} else if (nextInstance.getDayOfMonth() != dayToRemove && nextInstance.getMonthValue() == monthToRemove) {

				correctEntry.setEntryCalendar(nextInstance.getYear(), nextInstance.getMonthValue()
						,
						nextInstance.getDayOfMonth(), tempHour, tempMin, cal.getCalendarById(tempCalendar));

			} else if (nextInstance.getDayOfMonth() != dayToRemove
					&& nextInstance.getMonthValue() != monthToRemove) {
				correctEntry.setEntryCalendar(nextInstance.getYear(), nextInstance
						.getMonthValue(),
						nextInstance.getDayOfMonth(), tempHour, tempMin, cal.getCalendarById(tempCalendar));

			}
		}

		ist.clear();
	}


	public static synchronized Entries getSingletonInstance() {
		if (Entries.instance == null)
			Entries.instance = new Entries();
		return instance;
	}
}
