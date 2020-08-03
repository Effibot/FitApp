package logic.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.controller.GymPageController;
import logic.controller.MainController;
import logic.factory.viewfactory.ViewFactory;

public class GymPageViewController {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ImageView userIcon;

	@FXML
	private ImageView sideUserIcon;

	@FXML
	private Label sideUsername;

	@FXML
	private Label sideGymName;

	@FXML
	private Label sideGymStreet;

	private MainController ctrl;
	private ViewFactory factory;
	private GymPageController gymCtrl;

	private void fillGraphics() {
		sideGymName.setText(gymCtrl.getGym().getGymName());
		sideGymName.setWrapText(true);
		sideUsername.setText(gymCtrl.getManager().getName());
		sideUsername.setWrapText(true);
		sideGymStreet.setText(gymCtrl.getGym().getStreet());
		sideGymStreet.setWrapText(true);
	}

	@FXML
	void initialize() {
		assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert userIcon != null : "fx:id=\"userIcon\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert sideUserIcon != null : "fx:id=\"sideUserIcon\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert sideUsername != null : "fx:id=\"sideUsername\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert sideGymName != null : "fx:id=\"sideGymName\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert sideGymStreet != null : "fx:id=\"sideGymStreet\" was not injected: check your FXML file 'GymPage.fxml'.";
		ctrl = MainController.getInstance();
		factory = ViewFactory.getInstance();
		gymCtrl = new GymPageController(ctrl.getId());
		fillGraphics();
	}
}
