package logic.viewcontroller;

import java.io.IOException;
import java.util.ResourceBundle;

import com.calendarfx.view.page.MonthPage;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import logic.controller.MainController;
import logic.entity.User;
import logic.entity.dao.UserDAO;
import logic.facade.calendar.CalendarFacade;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;

public class UserPageViewController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ImageView userIcon;

	@FXML
	private Button bookSession;

	@FXML
	private ImageView sideUserIcon;

	@FXML
	private Label sideUsername;

	@FXML
	private Label sideStreet;


	@FXML
	private Pane calendarBox;

	@FXML
	private Button openCalendar;


	private MonthPage mPage;

	private MainController ctrl = MainController.getInstance();
	private ViewFactory factory = ViewFactory.getInstance();

	@FXML
	public void bookSession(ActionEvent event) {
		if(event.getSource().equals(bookSession)) {
			try {
				ctrl.replace(ctrl.getContainer(), factory.createView(ViewType.BOOKINGFORM));
			} catch (IOException e) {
				AlertFactory.getInstance().createAlert(e);
			}
		}
	}



	@FXML
	private void showCalendar(ActionEvent event){
		if(event.getSource().equals(openCalendar)) {
			if(!calendarBox.isVisible()) {
				new ZoomIn(calendarBox).play();
				calendarBox.setVisible(true);
				calendarBox.toFront();
				openCalendar.setText("Close Calendar");
			} else {
				new ZoomOut(calendarBox).play();
				calendarBox.toBack();
				openCalendar.setText("Open Calendar");
				calendarBox.setVisible(false);
				// calendarBox.setManaged(true);
			}
		}
	}

	private void calendarSetUp() {
		int userId = ctrl.getId();
		CalendarFacade calendarFacade = new CalendarFacade(false);
		mPage = calendarFacade.initializeCalendar(userId);
		calendarBox.getChildren().add(mPage);
		calendarBox.setVisible(false);
		mPage.getCalendars().get(0).addEventHandler(calendarFacade.getEventHandler());


	}

	@FXML
	void initialize() {
		assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert userIcon != null : "fx:id=\"userIcon\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert bookSession != null : "fx:id=\"bookSession\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert sideUserIcon != null : "fx:id=\"sideUserIcon\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert sideUsername != null : "fx:id=\"sideUsername\" was not injected: check your FXML file 'UserPage.fxml'.";
		User user = UserDAO.getInstance().getUserEntity(ctrl.getId());
		sideUsername.setText(user.getName());
		sideStreet.setText(user.getMyPosition());
		calendarSetUp();


	}
}