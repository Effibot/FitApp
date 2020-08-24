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

	public DayPage getDayPage() {
		return dayPage;
	}

	private CalendarInitializer calendar = CalendarInitializer.getSingletonInstance();
	private boolean userProperty;


	@FXML
	void closeFullDay(MouseEvent event) {
		Stage stage = (Stage) backBtn.getScene().getWindow();
		stage.close();
	}	


	public void setDaySources(CalendarSource calendarSource, RequestEvent event, boolean userProperty) {
		String className = new Exception().getStackTrace()[1].getClassName();
		System.out.println(className);
		dayPage.getCalendarSources().add(calendarSource);
		dayPage.setDate(event.getDate());
		this.userProperty = userProperty;


	}
	@FXML
	void initialize() {

		dayPage = new DayPage();
		dayPage.setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
		dayPage.setMinWidth(340);
		dayPage.bind(calendar.getMonthPage().getBoundDateControls().get(0), true);
		dayPage.setEntryDetailsPopOverContentCallback(param -> calendar.doubleClickEntry(param, this.userProperty));
		dayPage.setEntryContextMenuCallback(param -> calendar.rightClickEntry(param, this.userProperty));
		dayPageBox.getChildren().add(dayPage);

	}
}
