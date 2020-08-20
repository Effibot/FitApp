package logic.calendarutility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recurrenceset.RecurrenceRuleAdapter;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSet;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSetIterator;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

import logic.factory.alertfactory.AlertFactory;

public class Entries {
	 private static Entries instance = null;
	    private static final  String PATTERN = "yyyy-MM-dd HH:mm";
	    static final  DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern(PATTERN);
	    private Calendars cal;
	    private int currentDayOfMonth ;
		private int currentMonth ;
	    protected Entries(){
	        cal = Calendars.getSingletonInstance();
	    }

	    public Entry<?> setEntry(int year, int month, int day, int hour, int min){
	        //// Adding an Entry ////
	        LocalDateTime ldtStart = LocalDateTime.of(year, month, day, hour, min);
	        LocalDateTime ldtEnd = LocalDateTime.of(year, month,day,hour+1,min+30);
	        Entry<?> entry = new Entry<>();

	        entry.setTitle("KickBoxing");
	        entry.setId("kick");
	        entry.setLocation("Palestra Evolution");
	        entry.setInterval(ldtStart, ldtEnd);
	        entry.setCalendar(cal);

	        //Interval
	        Duration fromHours = Duration.ofHours((long) 1.30);
	        entry.setMinimumDuration(fromHours);
	        return entry;
	    }
	    public Entry setEntryCalendar(int year, int month, int day, int hour, int min, Calendar calendar){
	        //// Adding an Entry ////
	        LocalDateTime ldtStart = LocalDateTime.of(year, month, day, hour, min);
	        LocalDateTime ldtEnd = LocalDateTime.of(year, month,day,hour+1,min+30);
	        Entry<?> entry = new Entry<>();
	        

	        entry.setInterval(ldtStart, ldtEnd);
	        entry.setCalendar(calendar);
	        entry.setTitle(calendar.getName());
	        entry.setId(calendar.getName().toLowerCase());
	        entry.setLocation("Palestra Evolution");
	        //Interval
	        Duration fromHours = Duration.ofHours((long) 1.30);
	        entry.setMinimumDuration(fromHours);
	        return entry;
	    }

	    public void searchEntry(int year, int month, int dayMonth, String calendar){
	        /////////Search in Calendar /////////
	        LocalDate ldt = LocalDate.of(year, month, dayMonth);
	        List<Entry<?>> entries = cal.findEntries(calendar);
	        for (Entry<?> s : entries) {
	            Logger logger = Logger.getLogger("bof");
	            logger.log(Level.INFO, s.getId());
	        }

	    }

	    public void deleteEntry(int year, int month, int dayOfMonth, String calendar) {
	        LocalDate ldt = LocalDate.of(year, month, dayOfMonth);
	        List<Entry<?>> entries = cal.findEntries(calendar);
	        for (Entry<?> entry : entries) {
	            //// Delete from calendar by id /////
	            if (entry.matches("kick")) {
	                entry.removeFromCalendar();
	                return;
	            }
	        }

	    }
	    			 
	    		
	    public void deleteCalendarEntry(Entry<?> contextEntry) throws InvalidRecurrenceRuleException {
	    	currentDayOfMonth = 0;
	    	currentMonth = 0;
	    	String oldRule;
			if (contextEntry.getRecurrenceSourceEntry() != null || contextEntry.getStartDate()
					.getDayOfMonth() != contextEntry.getEndDate().getDayOfMonth()) {
				currentDayOfMonth = contextEntry.getRecurrenceSourceEntry().getStartDate().getDayOfMonth();
				oldRule = contextEntry.getRecurrenceSourceEntry().getRecurrenceRule();
				if (contextEntry.getRecurrenceSourceEntry().getStartDate().getMonth() != contextEntry
						.getStartDate().getMonth()) {
					currentMonth = contextEntry.getRecurrenceSourceEntry().getStartDate().getMonthValue();
				} else {
					currentMonth = contextEntry.getStartDate().getMonthValue();
				}

			} else {
				currentDayOfMonth = contextEntry.getStartDate().getDayOfMonth();
				oldRule = contextEntry.getRecurrenceRule();

			}
			System.out.println("THIS IS OLD RULE" + oldRule);
			this.dateIterator(contextEntry, oldRule);
		
	    }
	    
	    private void dateIterator(Entry<?>contextEntry, String oldRule) throws InvalidRecurrenceRuleException {

	    	RecurrenceRule oldRRule = new RecurrenceRule(oldRule.replace("RRULE:", ""));
			Entries correctEntry = Entries.getSingletonInstance();

			RecurrenceRule exRRule = new RecurrenceRule(
					"FREQ=YEARLY;INTERVAL=1;BYMONTHDAY=" + contextEntry.getStartDate().getDayOfMonth()
							+ ";BYMONTH=" + contextEntry.getStartDate().getMonthValue() + ";COUNT=1");
			RecurrenceSet recurrenceSet = new RecurrenceSet();
			recurrenceSet.addInstances(new RecurrenceRuleAdapter(oldRRule));
			recurrenceSet.addExceptions(new RecurrenceRuleAdapter(exRRule));
			int currentYear = contextEntry.getStartDate().getYear();
			DateTime start = new DateTime(currentYear, currentMonth - 1, currentDayOfMonth);
			RecurrenceSetIterator recurrenceSetIterator = recurrenceSet.iterator(start.getTimeZone(),
					start.getTimestamp());
			int maxInstances = 30;
			int dayToRemove = contextEntry.getStartDate().getDayOfMonth();
			int monthToRemove = contextEntry.getStartDate().getMonthValue();
			System.out.println("DAYTOREMOVE=" + dayToRemove);

			String tempCalendar = contextEntry.getCalendar().getName();
			System.out.println("ONLY CURR CALENDAR:" + tempCalendar);
			int tempHour = contextEntry.getStartTime().getHour();
			int tempMin = contextEntry.getStartTime().getMinute();
			contextEntry.removeFromCalendar();

			while (recurrenceSetIterator.hasNext() && (!oldRRule.isInfinite() || maxInstances-- > 0)) {
				// get the next instance of the recurrence set
				DateTime nextInstance = new DateTime(start.getTimeZone(), recurrenceSetIterator.next());
				// do something with nextInstance
				System.out.println("NEXTINSTANCE DAY" + nextInstance.getDayOfMonth() + "\t\tDAYTOREMOVE"
						+ dayToRemove + "\t\t MONTH TO REMOVE:" + (monthToRemove - 1)
						+ "\t\tNEXTINSTANCE MONTH" + nextInstance.getMonth());
				if (nextInstance.getDayOfMonth() != dayToRemove
						&& nextInstance.getMonth() == monthToRemove - 1) {

					System.out.println("SETTO NUVOA ENTRY");
					correctEntry.setEntryCalendar(nextInstance.getYear(), nextInstance.getMonth() + 1,
							nextInstance.getDayOfMonth(), tempHour, tempMin,
							cal.getCalendarById(tempCalendar));

				} else if (nextInstance.getDayOfMonth() != dayToRemove
						&& nextInstance.getMonth() != monthToRemove - 1) {
					correctEntry.setEntryCalendar(nextInstance.getYear(), nextInstance.getMonth() + 1,
							nextInstance.getDayOfMonth(), tempHour, tempMin,
							cal.getCalendarById(tempCalendar));

				}
			}
	    }
	    
	    


	    public static synchronized Entries getSingletonInstance() {
	        if (Entries.instance == null)
	            Entries.instance = new Entries();
	        return instance;
	    }
}
