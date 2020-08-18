package logic.viewcontroller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import logic.bean.LoginBean;
import logic.controller.LoginController;
import logic.controller.MainController;
import logic.entity.dao.UserDAO;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;
import logic.view.View;

public class LoginViewController {
	private final Logger logger = Logger.getLogger(getClass().getName());
	@FXML
	private HBox topMenu;
	@FXML
	private Button btnLogIn;
	@FXML
	private Button btnNoAcc;
	@FXML
	private Button btnSignUp;
	@FXML
	private ImageView btnBack;
	@FXML
	private Circle btnClose;
	@FXML
	private Circle btnExpand;
	@FXML
	private Circle btnReduce;
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPwd;
	@FXML
	private TextField tfEmailAddr;
	@FXML
	private Pane pnSignUp;
	@FXML
	private Pane pnSignIn;
	@FXML
	private AnchorPane anchRoot;
	
	private MainController ctrl;
	private LoginController logCtrl; 
	@FXML
	private void handleButtonEvent(ActionEvent event) throws IOException {
		if (event.getSource().equals(btnNoAcc)) {
			loginAnimation(true);
		}
		if (event.getSource().equals(btnSignUp)) {
			String email = tfEmailAddr.getText();
			if (!email.equals("")) {
				Integer pwd = logCtrl.generateRandomDigits(8);
				UserDAO.getInstance().signUp(email, pwd.toString());
				//EmailController.getSingletoneInstance()
				logger.log(Level.INFO, "Sending email to: {}", email);
			}
		}
		if (event.getSource().equals(btnLogIn)) {
			loginTransitions();
		}
	}
	
	@FXML
	private void onEnter(KeyEvent key) throws IOException {
		if (key.getCode().equals(KeyCode.ENTER)) {
			loginTransitions();
		}
	}

	private void loginTransitions() throws IOException {
		String username = tfUsername.getText();
		String password = tfPwd.getText();
		if (!username.equals("") && !password.equals("")) {
			
			LoginBean bean = new LoginBean(username, password);
			if (logCtrl.checkAuthentication(bean)) {
				MainController.getInstance().setId(bean.getId());
				ViewFactory factory = ViewFactory.getInstance();
				View view;
				if(bean.getUsername().toLowerCase().contentEquals("guest")) {
					view = factory.createView(ViewType.SIGNUP);
				} else if (bean.getType()) {
					view = factory.createView(ViewType.GYMPAGE);
				} else {
					view = factory.createView(ViewType.USERPAGE);
				}
				ctrl.replace(ctrl.getContainer(), view);
				ctrl.getTopBox().getChildren().add(ctrl.getTopBar());
			}
		} 
	}

	@FXML
	private void handleMouseEvent(MouseEvent event) {
		if (event.getSource() == btnClose) {
			System.exit(0);
		}
		if (event.getSource().equals(btnBack)) {
			loginAnimation(false);
		}
	}

	private void loginAnimation(boolean animation) {
		if (animation) {
			new ZoomOut(pnSignIn).play();
			pnSignIn.toBack();
			new ZoomIn(pnSignUp).play();
			pnSignUp.setVisible(true);
			pnSignUp.toFront();
			pnSignUp.setDisable(false);
		} else {
			new ZoomOut(pnSignUp).play();
			pnSignUp.toBack();
			new ZoomIn(pnSignIn).play();
			pnSignIn.toFront();
			pnSignUp.setDisable(true);
		}
	}

	@FXML
	void initialize() {
		assert topMenu != null : "fx:id=\"topMenu\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnReduce != null : "fx:id=\"btnReduce\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnExpand != null : "fx:id=\"btnExpand\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'scene.fxml'.";
		assert pnSignUp != null : "fx:id=\"pnSignUp\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnSignUp != null : "fx:id=\"btnSignUp\" was not injected: check your FXML file 'scene.fxml'.";
		assert tfEmailAddr != null : "fx:id=\"tfEmailAddr\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'scene.fxml'.";
		assert pnSignIn != null : "fx:id=\"pnSignIn\" was not injected: check your FXML file 'scene.fxml'.";
		assert tfUsername != null : "fx:id=\"tfUsername\" was not injected: check your FXML file 'scene.fxml'.";
		assert tfPwd != null : "fx:id=\"tfPwd\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnLogIn != null : "fx:id=\"btnLogIn\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnNoAcc != null : "fx:id=\"btnNoAcc\" was not injected: check your FXML file 'scene.fxml'.";
		
		ctrl = MainController.getInstance();
		logCtrl = new LoginController();
	}
}
