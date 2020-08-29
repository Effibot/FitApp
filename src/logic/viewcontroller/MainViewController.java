package logic.viewcontroller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.bean.MainBean;
import logic.controller.MainController;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;
import logic.view.View;

public class MainViewController implements Initializable {

	@FXML
	private AnchorPane main;

	@FXML
	private HBox topMenu;

	@FXML
	private BorderPane container;

	@FXML
	private ImageView btnReduce;

	@FXML
	private ImageView btnClose;

	@FXML
	private ImageView logOutIcon;

	@FXML
	private BorderPane topBar;

	@FXML
	private VBox topBox;

	private MainController ctrl;
	private ViewFactory factory;

	@FXML
	private void onMouseClickedEvent(MouseEvent event) {
		if (event.getSource() == btnClose) {
			System.exit(0);
		}
		if (event.getSource() == btnReduce) {
			Stage stage = (Stage) main.getScene().getWindow();
			stage.setIconified(true);
		}
		if (event.getSource() == logOutIcon) {
			try {
				topBox.getChildren().remove(topBar);
				ctrl.replace(ctrl.getContainer(), factory.createView(ViewType.LOGIN));
			} catch (IOException e) {
				AlertFactory.getInstance().createAlert(e);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ctrl = MainController.getInstance();
		factory = ViewFactory.getInstance();
//		ctrl.setContainer(container);
//		ctrl.setTopBar(topBar);
//		ctrl.setTopBox(topBox);
		topBox.getChildren().remove(topBar);
		MainBean bean = new MainBean();
		bean.setContainer(container);
		bean.setTopBar(topBar);
		bean.setTopBox(topBox);
		ctrl.setBean(bean);
		// main = (AnchorPane) resources.getObject("main");
		View subview;
		try {
			subview = factory.createView(ViewType.LOGIN);
			ctrl.replace(ctrl.getContainer(), subview);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}