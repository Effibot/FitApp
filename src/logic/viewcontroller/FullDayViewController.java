package logic.viewcontroller;


import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.RequestEvent;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.facade.calendar.CalendarBehaviour;


public class FullDayViewController {

	@FXML
	private Button backBtn;

	@FXML
	private HBox dayPageBox;
	private DayPage dayPage;

	private CalendarBehaviour calendarBehaviour;

	@FXML
	void closeFullDay(MouseEvent event) {
		Stage stage = (Stage) backBtn.getScene().getWindow();
		stage.close();
	}	


	public void setDaySources(CalendarSource calendarSource, RequestEvent event, MonthPage monthPage) {
		String className = new Exception().getStackTrace()[1].getClassName();
		System.out.println(className);
		dayPage.getCalendarSources().add(calendarSource);
		dayPage.setDate(event.getDate());


		dayPage.setEntryDetailsPopOverContentCallback(
				param -> calendarBehaviour.doubleClickEntry(param));
		dayPage.setEntryContextMenuCallback(param -> calendarBehaviour.rightClickEntry(param));

	}

	public DayPage getDayPage() {
		return dayPage;
	}

	@FXML
	void initialize() {
		calendarBehaviour = CalendarBehaviour.getSingletoneInstance();
		dayPage = new DayPage();
		dayPage.setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
		dayPage.setMinWidth(340);

		dayPageBox.getChildren().add(dayPage);

	}
}
