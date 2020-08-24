package logic.viewcontroller;
import java.time.format.DateTimeFormatter;

import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logic.calendarutility.Entries;

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

		private Entry<?> entry;

	    @FXML
	    public void allDelete(ActionEvent event) {
			Entry<?> onlyCurr = this.getEntryToDelete();
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
			System.out.println("ONLY CURR:" + onlyCurr.getId());
	        if (onlyCurr.getRecurrenceRule() == null) {
	            onlyCurr.removeFromCalendar();
	        } else {
				entries.deleteCalendarEntry(onlyCurr);
	        }
	        Stage window = (Stage) currBtn.getScene().getWindow();
	        window.close();
	    }

		public Entry<?> getEntryToDelete() {
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
