package logic.viewcontroller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import logic.bean.SignUpBean;
import logic.controller.MainController;
import logic.controller.SignUpController;
import logic.entity.dao.GymDAO;
import logic.entity.dao.UserDAO;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.alertfactory.CustomAlertBox;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;
import logic.view.View;

public class SignUpViewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField username;

	@FXML
	private Label guestLabel;

	@FXML
	private TextField email;

	@FXML
	private Label emailLabel;

	@FXML
	private TextField confirmEmail;

	@FXML
	private Label confirmEmailLabel;

	@FXML
	private PasswordField password;

	@FXML
	private Label passLabel;

	@FXML
	private PasswordField confirmPass;

	@FXML
	private Label confirmPassLabel;

	@FXML
	private JFXCheckBox managerCheck;

	@FXML
	private TextField gymName;

	@FXML
	private TextField gymStreet;

	@FXML
	private Button backBtn;

	@FXML
	private Button confirmBtn;

	@FXML
	private HBox setManager;

	@FXML
	private TextField userStreet;

	@FXML
	private Label userStreetLabel;

	private MainController main;
	private SignUpController suCtrl;
	private boolean register = false;

	@FXML
	public void isManager(ActionEvent event) {
		if (managerCheck.isSelected()) {
			setManager.setVisible(true);
		} else if (!managerCheck.isSelected()) {
			setManager.setVisible(false);
			gymName.setText("");
			gymStreet.setText("");
		}
	}

	@FXML
	public void back(ActionEvent event) {
		if (event.getSource().equals(backBtn)) {
			registerUser();
			ViewFactory factory = ViewFactory.getInstance();
			View view;
			try {
				view = factory.createView(ViewType.LOGIN);
				main.getTopBox().getChildren().remove(main.getTopBar());
				main.replace(main.getContainer(), view);

			} catch (IOException e) {
				AlertFactory.getInstance().createAlert(e);
			}
		}
	}

	@FXML
	public void confirmAction(ActionEvent event) {
		if (event.getSource().equals(confirmBtn) && (!guestLabel.isVisible()) && (!confirmEmailLabel.isVisible())
				&& (!confirmPassLabel.isVisible())) {
			String title = "User Created";
			String header = "Hi " + username.getText() + ", welcome in FitApp!";
			String content = "Click Ok to proceed to the login page and enter in your private FitApp's space!.\n"
					+ "If You want to edit your informations, click cancel.";
			CustomAlertBox customBox = AlertFactory.getInstance().createAlert(AlertType.CONFIRMATION, title, header,
					content);
			register = true;
			customBox.display(backBtn);
			
		}
	}

	private void addGuestListener(TextField field, Label label) {
		suCtrl.setUpListener(field).addListener((observable, oldValue, newValue) -> {
			if (newValue.toLowerCase().contentEquals("guest")) {
				label.setVisible(true);
			} else if (!newValue.toLowerCase().contentEquals("guest")) {
				label.setVisible(false);
			}
		});
	}

	private void addCheckListener(TextField field, Label label, String check, TextField textCheck) {
		suCtrl.setUpListener(field).addListener((observable, oldValue, newValue) -> {
			if (suCtrl.findNode(field, check)) {
				if (!newValue.contentEquals(textCheck.getText())) {
					label.setVisible(true);
					if (check.contentEquals("confirmPass"))
						passLabel.setVisible(true);
				} else if (newValue.contentEquals(textCheck.getText())) {
					label.setVisible(false);
					if (check.contentEquals("confirmPass"))
						passLabel.setVisible(false);

				}
			}
		});
	}

	private void setEmail() {
		String s = suCtrl.retrieveEmail(main.getId());
		email.setText(s);
		email.setDisable(true);
		emailLabel.setText("the email was retrieve from the database, you can't edit");
		emailLabel.setVisible(true);
	}

	private void makeBinding() {
		if (!managerCheck.isSelected()) {
			BooleanBinding userBinding = username.textProperty().isEmpty()
					.or(confirmEmail.textProperty().isEmpty().or(password.textProperty().isEmpty()
							.or(confirmPass.textProperty().isEmpty().or(userStreet.textProperty().isEmpty()))));
			confirmBtn.disableProperty().bind(userBinding);
		} else {
			BooleanBinding userBinding = username.textProperty().isNotEmpty()
					.or(confirmEmail.textProperty().isNotEmpty().or(password.textProperty().isNotEmpty()
							.or(confirmPass.textProperty().isNotEmpty().or(gymName.textProperty().isNotEmpty().or(
									gymStreet.textProperty().isNotEmpty().or(userStreet.textProperty().isEmpty()))))));
			confirmBtn.disableProperty().bind(userBinding);
		}
	}

	private void registerUser() {
		if (register) {
			SignUpBean bean = new SignUpBean();
			bean.setUserId(main.getId());
			bean.setUsername(username.getText());
			bean.setEmail(email.getText());
			bean.setPwd(password.getText());
			bean.setUserStreet(userStreet.getText());
			bean.setIsManager(managerCheck.isSelected());
			if(bean.getIsManager()) {
				bean.setGymName(gymName.getText());
				bean.setGymStreet(gymStreet.getText());
			}
			//System.out.println("sto registrando");
			suCtrl.registerUser(bean);
		}
	}
	@FXML
	void initialize() {
		assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert guestLabel != null : "fx:id=\"guestLabel\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert emailLabel != null : "fx:id=\"emailLabel\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert confirmEmail != null : "fx:id=\"confirmEmail\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert confirmEmailLabel != null : "fx:id=\"confirmEmailLabel\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert passLabel != null : "fx:id=\"passLabel\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert confirmPass != null : "fx:id=\"confirmPass\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert confirmPassLabel != null : "fx:id=\"confirmPassLabel\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert managerCheck != null : "fx:id=\"managerCheck\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert gymName != null : "fx:id=\"gymName\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert gymStreet != null : "fx:id=\"GymStreet\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert confirmBtn != null : "fx:id=\"confirmBtn\" was not injected: check your FXML file 'SignUp.fxml'.";

		guestLabel.setVisible(false);
		emailLabel.setVisible(false);
		confirmEmailLabel.setVisible(false);
		passLabel.setVisible(false);
		confirmPassLabel.setVisible(false);
		setManager.setVisible(false);
		confirmBtn.setDisable(true);
		userStreetLabel.setVisible(false);
		main = MainController.getInstance();
		suCtrl = new SignUpController();
		addGuestListener(username, guestLabel);
		addCheckListener(confirmEmail, confirmEmailLabel, confirmEmail.getId(), email);
		addCheckListener(confirmPass, confirmPassLabel, confirmPass.getId(), password);
		setEmail();
		makeBinding();
		System.out.println(main.getId());
	}

}
