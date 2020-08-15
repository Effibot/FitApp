package logic.viewcontroller;
import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logic.calendarutility.Calendars;
import logic.calendarutility.Entries;
import logic.factory.alertfactory.AlertFactory;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recurrenceset.RecurrenceRuleAdapter;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSet;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSetIterator;

import java.time.format.DateTimeFormatter;

public class PopupDeleteController {
	 private static PopupDeleteController instance = null;
	    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    @FXML
	    private Button closeBtn;

	    @FXML
	    private JFXButton currBtn;

	    @FXML
	    private JFXButton allBtn;

	    @FXML

	    private Entry entry;

	    @FXML
	    public void allDelete(ActionEvent event) {
	        Entry onlyCurr = this.getEntryToDelete();
	        onlyCurr.removeFromCalendar();
	        Stage window = (Stage) allBtn.getScene().getWindow();
	        window.close();
	    }

	    @FXML
	    public void closingPopup(ActionEvent event) {
	        Stage stage = (Stage) closeBtn.getScene().getWindow();
	        stage.close();

	    }

	    @FXML
	    public void deleteOnlyCurrent(ActionEvent event) {
	        Entry onlyCurr = this.getEntryToDelete();
	        if (onlyCurr.getRecurrenceRule() == null) {
	            onlyCurr.removeFromCalendar();
	        } else {
	            try {
	                int currentDayOfMonth = 0;
	                int currentMonth = 0;

	                String oldRule;
	                if (onlyCurr.getRecurrenceSourceEntry() != null || onlyCurr.getStartDate().getDayOfMonth() != onlyCurr.getEndDate().getDayOfMonth()) {
	                    currentDayOfMonth = onlyCurr.getRecurrenceSourceEntry().getStartDate().getDayOfMonth();
	                    oldRule = onlyCurr.getRecurrenceSourceEntry().getRecurrenceRule();
	                    if (onlyCurr.getRecurrenceSourceEntry().getStartDate().getMonth() != onlyCurr.getStartDate().getMonth()) {
	                        currentMonth = onlyCurr.getRecurrenceSourceEntry().getStartDate().getMonthValue();
	                    } else {
	                        currentMonth = onlyCurr.getStartDate().getMonthValue();
	                    }

	                } else {
	                    currentDayOfMonth = onlyCurr.getStartDate().getDayOfMonth();
	                    oldRule = onlyCurr.getRecurrenceRule();


	                }
	                System.out.println("THIS IS OLD RULE" + oldRule);
	                RecurrenceRule oldRRule = new RecurrenceRule(oldRule.replace("RRULE:", ""));
	                Entries correctEntry = Entries.getSingletonInstance();
	                Calendars cal = Calendars.getSingletonInstance();
	                RecurrenceRule exRRule = new RecurrenceRule("FREQ=YEARLY;INTERVAL=1;BYMONTHDAY=" + onlyCurr.getStartDate().getDayOfMonth() + ";BYMONTH=" + onlyCurr.getStartDate().getMonthValue() + ";COUNT=1");
	                RecurrenceSet recurrenceSet = new RecurrenceSet();
	                recurrenceSet.addInstances(new RecurrenceRuleAdapter(oldRRule));
	                recurrenceSet.addExceptions(new RecurrenceRuleAdapter(exRRule));
	                int currentYear = onlyCurr.getStartDate().getYear();
	                DateTime start = new DateTime(currentYear, currentMonth - 1, currentDayOfMonth);
	                RecurrenceSetIterator recurrenceSetIterator = recurrenceSet.iterator(start.getTimeZone(), start.getTimestamp());
	                int maxInstances = 30;
	                int dayToRemove = onlyCurr.getStartDate().getDayOfMonth();
	                int monthToRemove = onlyCurr.getStartDate().getMonthValue();
	                System.out.println("DAYTOREMOVE=" + dayToRemove);

	                String tempCalendar = onlyCurr.getCalendar().getName();
	                System.out.println("ONLY CURR CALENDAR:" + tempCalendar);
	                int tempHour = onlyCurr.getStartTime().getHour();
	                int tempMin = onlyCurr.getStartTime().getMinute();
	                onlyCurr.removeFromCalendar();

	                while (recurrenceSetIterator.hasNext() && (!oldRRule.isInfinite() || maxInstances-- > 0)) {
	                    // get the next instance of the recurrence set
	                    DateTime nextInstance = new DateTime(start.getTimeZone(), recurrenceSetIterator.next());
	                    // do something with nextInstance
	                    System.out.println("NEXTINSTANCE DAY" + nextInstance.getDayOfMonth() + "\t\tDAYTOREMOVE" + dayToRemove + "\t\t MONTH TO REMOVE:" + (monthToRemove - 1) + "\t\tNEXTINSTANCE MONTH" + nextInstance.getMonth());
	                    if (nextInstance.getDayOfMonth() != dayToRemove && nextInstance.getMonth() == monthToRemove - 1) {

	                        System.out.println("SETTO NUVOA ENTRY");
	                        correctEntry.setEntryCalendar(nextInstance.getYear(), nextInstance.getMonth() + 1, nextInstance.getDayOfMonth(), tempHour, tempMin, cal.getCalendarById(tempCalendar));

	                    } else if (nextInstance.getDayOfMonth() != dayToRemove && nextInstance.getMonth() != monthToRemove - 1) {
	                        correctEntry.setEntryCalendar(nextInstance.getYear(), nextInstance.getMonth() + 1, nextInstance.getDayOfMonth(), tempHour, tempMin, cal.getCalendarById(tempCalendar));

	                    }
	                }
	            } catch (InvalidRecurrenceRuleException e) {
	            	AlertFactory.getInstance().createAlert(e);
	            }
	        }
	        Stage window = (Stage) currBtn.getScene().getWindow();
	        window.close();
	    }

	    public Entry getEntryToDelete() {
	        return this.entry;
	    }

	    public void setEntryToDelete(Entry evnt) {
	        this.entry = evnt;

	    }

	    public static synchronized PopupDeleteController getSingletonInstance() {
	        if (PopupDeleteController.instance == null)
	            PopupDeleteController.instance = new PopupDeleteController();
	        return instance;
	    }

}
