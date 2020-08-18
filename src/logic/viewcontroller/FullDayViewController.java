package logic.viewcontroller;


import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.RequestEvent;
import com.calendarfx.view.page.DayPage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.calendarutility.CalendarInitializer;


public class FullDayViewController {

	@FXML
	private Button backBtn;

	@FXML
	private HBox dayPageBox;
	private DayPage dayPage;
	private CalendarSource calendarSource;
	CalendarInitializer calendar = CalendarInitializer.getSingletonInstance();


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
		dayPage.bind(CalendarInitializer.getSingletonInstance().getMonthPage().getBoundDateControls().get(0), true);
		
		
		dayPageBox.getChildren().add(dayPage);
		dayPage.setEntryDetailsPopOverContentCallback(param-> calendar.doubleClickEntry(param));
		dayPage.setEntryContextMenuCallback(param-> calendar.rightClickEntry(param));
	}



	public void setDaySources(CalendarSource calendarSource, RequestEvent event ) {
		dayPage.getCalendarSources().add(calendarSource);
		dayPage.setDate(event.getDate());



	}

}
