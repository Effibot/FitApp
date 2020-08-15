package logic.calendarutility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.calendarfx.model.Calendar;

public class Calendars extends Calendar {
	private static Calendars instance = null;

    public List<Calendar> getAvailableCalendar() {
        return availableCalendar;
    }

    private List<Calendar> availableCalendar;
    protected Calendars(){
        availableCalendar = new ArrayList<>();
        //Calendar adding a new label
        Calendar cal1 = new Calendar("Kick Boxing");
        cal1.setStyle(Calendar.Style.STYLE1);
        availableCalendar.add(cal1);
        Calendar cal2 = new Calendar("Pugilato");
        cal2.setStyle(Calendar.Style.STYLE2);
        availableCalendar.add(cal2);
        //Calendar adding a new label
        Calendar cal3 = new Calendar("Zumba");
        cal3.setStyle(Calendar.Style.STYLE3);
        availableCalendar.add(cal3);
        Calendar cal4 = new Calendar("Salsa");
        cal4.setStyle(Calendar.Style.STYLE4);
        availableCalendar.add(cal4);
        //Calendar adding a new label
        Calendar cal5 = new Calendar("Funzionale");
        cal5.setStyle(Calendar.Style.STYLE5);
        availableCalendar.add(cal5);
        Calendar cal6 = new Calendar("Walking");
        cal6.setStyle(Calendar.Style.STYLE6);
        availableCalendar.add(cal6);
        //Calendar adding a new label
        Calendar cal7 = new Calendar("Pump");
        cal7.setStyle(Calendar.Style.STYLE7);
        availableCalendar.add(cal7);
        System.out.println("STYLE 6"+cal6.getStyle()+"STYLE5"+cal5.getStyle());
    }

    public Calendar getCalendar(int i){
        return availableCalendar.get(i);
    }
    public Calendar getCalendarBynName(String name){
        Iterator<Calendar> calendarIterator = availableCalendar.iterator();
        int i = 0;
        while(calendarIterator.hasNext()){
            if(calendarIterator.next().getName().equals(name)){
                return availableCalendar.get(i);
            }
            i++;
        }
        System.out.println("INDEX OF i"+i);
        return availableCalendar.get(i);

    }

    public Calendar getCalendarById(String name){
        Iterator<Calendar> calendarIterator = availableCalendar.iterator();
        int i = 0;
        while(calendarIterator.hasNext()){
            if(calendarIterator.next().getName().equals(name)){
                return getCalendar(i);
            }
            i++;
        }
        return null;
    }

    public static synchronized Calendars getSingletonInstance() {
        if (Calendars.instance == null)
            Calendars.instance = new Calendars();
        return instance;
    }

}
