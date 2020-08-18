package logic.viewcontroller;

import com.jfoenix.controls.JFXCheckBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import logic.controller.MainController;
import logic.controller.SignUpController;
import logic.entity.dao.UserDAO;
import logic.factory.alertfactory.AlertFactory;
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

	private MainController main;
	private SignUpController suCtrl;

	@FXML
	public void isManager(ActionEvent event) {
		if (managerCheck.isSelected()) {
			setManager.setVisible(true);
		} else if (!managerCheck.isSelected()) {
			setManager.setVisible(false);
		}
	}

	@FXML
	public void back(ActionEvent event) {
		if (event.getSource().equals(backBtn)) {
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

	public void addGuestListener(TextField field, Label label) {
		suCtrl.setUpListener(field).addListener((observable, oldValue, newValue) -> {
			if (newValue.toLowerCase().contentEquals("guest")) {
				label.setVisible(true);
			} else if (!newValue.toLowerCase().contentEquals("guest")) {
				label.setVisible(false);
			}
		});
	}

	public void addCheckListener(TextField field, Label label, String check, TextField textCheck) {
		suCtrl.setUpListener(field).addListener((observable, oldValue, newValue) -> {
			if (suCtrl.findNode(field, check)) {
				if (!newValue.contentEquals(textCheck.getText())) {
					label.setVisible(true);
				} else if (newValue.contentEquals(textCheck.getText())) {
					label.setVisible(false);
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
		main = MainController.getInstance();
		suCtrl = new SignUpController();
		addGuestListener(username, guestLabel);
		addCheckListener(confirmEmail, confirmEmailLabel, confirmEmail.getId(), email);
		addCheckListener(confirmPass, confirmPassLabel, confirmPass.getId(), password);
		setEmail();
		makeBinding();

	}

	public void makeBinding() {
		if (!managerCheck.isSelected()) {
			BooleanBinding userBinding = username.textProperty().isEmpty().or(confirmEmail.textProperty().isEmpty()
					.or(password.textProperty().isEmpty().or(confirmPass.textProperty().isEmpty())));
			confirmBtn.disableProperty().bind(userBinding);
		} else {
			BooleanBinding userBinding = username.textProperty().isNotEmpty().or(confirmEmail.textProperty()
					.isNotEmpty().or(password.textProperty().isNotEmpty().or(confirmPass.textProperty().isNotEmpty()
							.or(gymName.textProperty().isNotEmpty().or(gymStreet.textProperty().isNotEmpty())))));
			confirmBtn.disableProperty().bind(userBinding);
		}
	}
}
