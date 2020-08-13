package logic.viewcontroller;

import java.io.IOException;
import java.util.ResourceBundle;

import com.calendarfx.view.page.MonthPage;

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
    private Pane calendarBox;

    @FXML
    private Button openCalendar;
    
    private MonthPage calendar;
   
    
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
	private void showCalendar() {
		if(!calendar.isVisible()) {
			calendar.setVisible(true);
			openCalendar.setText("Close Calendar");
		} else {
			calendar.setVisible(false);
			openCalendar.setText("Open Calendar");
		}
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
		calendar = new MonthPage();
		calendar.setMaxSize(680, 502);
		calendar.setMinSize(680, 502);
		calendarBox.getChildren().add(calendar);
		calendar.setVisible(false);
	}
}