package logic.viewcontroller;
import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logic.facade.calendar.CalendarBehaviour;
import logic.facade.calendar.EntryCalendar;

public class PopupDeleteController {

	    @FXML
	    private Button closeBtn;

	    @FXML
	    private JFXButton currBtn;

	    @FXML
	    private JFXButton allBtn;

	    @FXML

		private Entry<?> entry;

		private EntryCalendar entries;


	    @FXML
	    public void allDelete(ActionEvent event) {
			entry.getCalendar().clear();
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
			System.out.println(entry.getRecurrenceRule());
			if (entry.getRecurrenceRule() == null) {
				entry.removeFromCalendar();
	        } else {
				entries.deleteCalendarEntry(entry);
	        }
	        Stage window = (Stage) currBtn.getScene().getWindow();
	        window.close();
	    }

		public void setEntryToDelete(Entry<?> evnt, EntryCalendar entries) {
	        this.entry = evnt;
			this.entries = entries;

	    }

		@FXML
		void initialize() {
			entries = CalendarBehaviour.getSingletoneInstance().getEntryCalendar();
		}



}
