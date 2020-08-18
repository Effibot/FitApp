package logic.controller;

import java.util.ArrayList;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.entity.dao.UserDAO;

public class SignUpController {
	private Boolean[] confirmCheck = new Boolean[6];
	// ObservableList<TextField> field = FXCollections.observableList(new
	// ArrayList<TextField>());

	public SignUpController() {
		super();
		fillCheck();
	}

//	public void setField(TextField... elements) {
//		this.field.addAll(elements);
//	}

	public StringProperty setUpListener(TextField field) {
		StringProperty sp = new SimpleStringProperty(this, "sp", "");
		field.textProperty().bindBidirectional(sp);
		return sp;
	}

	public String retrieveEmail(int id) {
		return UserDAO.getInstance().getEmailById(id);
	}

	public boolean findNode(TextField field, String s) {
		return field.getId().equals(s);
	}

	private void fillCheck() {
		for (int i = 0; i < 6; i++) {
			confirmCheck[i] = false;
		}
	}

	public void setOneCheck(int i, boolean b) {
		confirmCheck[i] = b;
	}

	public Boolean[] getConfirmCheck() {
		return confirmCheck;
	}

	public void setConfirmCheck(Boolean[] confirmCheck) {
		this.confirmCheck = confirmCheck;
	}

}
