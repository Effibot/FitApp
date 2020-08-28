package logic.viewcontroller;
import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logic.facade.calendar.CalendarBehaviour;
import logic.facade.calendar.EntryCalendar;
import logic.facade.calendar.EntryCustom;

public class PopupDeleteController {

	    @FXML
	    private Button closeBtn;

	    @FXML
	    private JFXButton currBtn;

	    @FXML
	    private JFXButton allBtn;

	    @FXML

		private EntryCustom<?> entry;

		private EntryCalendar entries;
		
		/*
		 * Deleting all event on calendar and close stage
		 * 
		 * */

	    @FXML
	    public void allDelete(ActionEvent event) {
	    	
			entry.getEntry().getCalendar().clear();
	        Stage window = (Stage) allBtn.getScene().getWindow();
	        window.close();
	    }
	    /* 
	     * Closing popUp
	     */

	    @FXML
	    public void closingPopup(ActionEvent event) {
	        Stage stage = (Stage) closeBtn.getScene().getWindow();
	        stage.close();

	    }

	    	
	    /*
	     * Delete only current event
	     * */
	    @FXML
		public void deleteOnlyCurrent(ActionEvent event) {
			if (entry.getEntry().getRecurrenceRule() == null) {
				entry.getEntry().removeFromCalendar();
	        } else {
				entries.deleteCalendarEntry(this.entry);
	        }
	        Stage window = (Stage) currBtn.getScene().getWindow();
	        window.close();
	    }

		public void setEntryToDelete(EntryCustom<?> selectedEntry) {
	        this.entry = selectedEntry;
			entries = CalendarBehaviour.getSingletoneInstance().getEntryCalendar();


	    }




}
