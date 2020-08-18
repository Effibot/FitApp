package logic.calendarutility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

public class Entries {
	 private static Entries instance = null;
	    private static final  String PATTERN = "yyyy-MM-dd HH:mm";
	    static final  DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern(PATTERN);
	    Calendars cal;
	    protected Entries(){
	        cal = Calendars.getSingletonInstance();
	    }

	    public Entry setEntry(int year, int month, int day, int hour, int min){
	        //// Adding an Entry ////
	        LocalDateTime ldtStart = LocalDateTime.of(year, month, day, hour, min);
	        LocalDateTime ldtEnd = LocalDateTime.of(year, month,day,hour+1,min+30);
	        Entry entry = new Entry();

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
	        Entry entry = new Entry();


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
	        for (Entry s : entries) {
	            Logger logger = Logger.getLogger("bof");
	            logger.log(Level.INFO, s.getId());
	        }

	    }

	    public void deleteEntry(int year, int month, int dayOfMonth, String calendar) {
	        LocalDate ldt = LocalDate.of(year, month, dayOfMonth);
	        List<Entry<?>> entries = cal.findEntries(calendar);
	        for (Entry entry : entries) {
	            //// Delete from calendar by id /////
	            if (entry.matches("kick")) {
	                entry.removeFromCalendar();
	                return;
	            }
	        }

	    }


	    public static synchronized Entries getSingletonInstance() {
	        if (Entries.instance == null)
	            Entries.instance = new Entries();
	        return instance;
	    }
}
