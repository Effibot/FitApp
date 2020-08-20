package logic.viewcontroller;
import java.time.format.DateTimeFormatter;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recurrenceset.RecurrenceRuleAdapter;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSet;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSetIterator;

import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logic.calendarutility.Calendars;
import logic.calendarutility.Entries;
import logic.factory.alertfactory.AlertFactory;

public class PopupDeleteController {
	 private static PopupDeleteController instance = null;
	 private Entries entries = Entries.getSingletonInstance();
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
	        Entry<?> onlyCurr = this.getEntryToDelete();
	        if (onlyCurr.getRecurrenceRule() == null) {
	            onlyCurr.removeFromCalendar();
	        } else {
	            try {
	               entries.deleteCalendarEntry(onlyCurr);
	                
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
