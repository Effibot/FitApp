package logic.viewcontroller;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    private HBox calendarBox;

	private MainController ctrl = MainController.getInstance();
	private ViewFactory factory = ViewFactory.getInstance();
	private User user;

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

//	public void fillActiveSessions() {
//		SessionDAO sDao = SessionDAO.getInstance();
//		// get the id list of booked sessions
//		ArrayList<Integer> bookings = (ArrayList<Integer>) sDao.getBooking(user.getId());
//		// get the actual list
//		ArrayList<Session> list = (ArrayList<Session>) sDao.getSessionList(bookings);
//		CardController cc = new CardController();
//		if (!list.isEmpty()) {
//			for (Session s : list) {
//				// add the card to the list
//				sessionList.getItems().add(cc.createCard(s));
//				// set selection mode to single
//				sessionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//			}
//		}
//	}

	@FXML
	void initialize() {
		assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert userIcon != null : "fx:id=\"userIcon\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert bookSession != null : "fx:id=\"bookSession\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert sideUserIcon != null : "fx:id=\"sideUserIcon\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert sideUsername != null : "fx:id=\"sideUsername\" was not injected: check your FXML file 'UserPage.fxml'.";

		user = UserDAO.getInstance().getUserEntity(ctrl.getId());
		sideUsername.setText(user.getName());
	}
}