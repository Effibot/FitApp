package logic.viewcontroller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.bean.EmailBean;
import logic.controller.EmailController;


public class EmailViewController {

	@FXML
	private Button closeBtn;

	@FXML
	private Label gymEmailTitle;

	@FXML
	private Label evtLbl;

	@FXML
	private Label timeLbl;

	@FXML
	private JFXTextField subjectTXTField;

	@FXML
	private JFXButton sendEmail;
	@FXML
	private JFXTextArea txtArea;


	private String gymName;


	@FXML
	void closingPopup(MouseEvent event) {
		Stage stage = (Stage) closeBtn.getScene().getWindow();
		stage.close();


	}



	@FXML
	void sendEmail(MouseEvent event) {
		EmailController emailController = EmailController.getSingletoneInstance();

		EmailBean emailBean = emailController.getEmailBean();

		String subject = subjectTXTField.getText(); 
		String msg = txtArea.getText();
		if(!subject.equals("") && !msg.equals("")) {
			emailBean.setGym(gymName);
			emailBean.setSubject(subject);
			emailBean.setMsg(msg);
			emailController.sendEmail();
		}


		Stage stage = (Stage) sendEmail.getScene().getWindow(); 
		stage.close(); }


	public void setEvent(String course,String time, String gym) {
		gymEmailTitle.setText("Email to: "+gym+" course: "+course);
		evtLbl.setText(course);
		timeLbl.setText(time);
		gymName =gym;
	}






}






