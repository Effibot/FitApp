package logic.viewcontroller;

import java.time.LocalDate;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.RequestEvent;
import com.calendarfx.view.page.DayPage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class FullDayViewController {
	
	@FXML
    private Button backBtn;

    @FXML
    private HBox dayPageBox;
    private DayPage dayPage;
    CalendarSource calendarSource;
    

    @FXML
    void closeFullDay(MouseEvent event) {
    	Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }	
    
 
    @FXML
    void initialize() {
  
    	dayPage = new DayPage();
    	dayPage.setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
    	dayPage.setMinWidth(340);
    	dayPageBox.getChildren().add(dayPage);
    	
    	
    }

	

	public void setDaySources(CalendarSource calendarSource, RequestEvent event ) {
    	dayPage.getCalendarSources().add(calendarSource);
		dayPage.setDate(event.getDate());
		

		
	}
	
}
