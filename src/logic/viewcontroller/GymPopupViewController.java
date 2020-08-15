package logic.viewcontroller;
import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.lynden.gmapsfx.javascript.object.Marker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.entity.Session;
import logic.factory.alertfactory.AlertFactory;


public class GymPopupViewController {

    @FXML
    private Button closeBtn;

    @FXML
    private Label gymPopupTitleLbl;

    @FXML
    private Label evtLbl;

    @FXML
    private Label timeLbl;

    @FXML
    private Label gymAddressLbl;

    @FXML
    private JFXTextArea txtArea;

    @FXML
    private JFXButton sendEmail;

    @FXML
    private JFXButton bookBtn;
    private String course;
    private String gym;
    private String time;
	    

	    @FXML
	    void bookingEvent(MouseEvent event) {
	        // DAO REQUEST TO BOOK//

	        Stage currStage = (Stage) bookBtn.getScene().getWindow();
	        currStage.close();
	    }
	    @FXML
	    void sendEmail(MouseEvent event) {
	    	try {

				Stage window = new Stage(); 
				window.initStyle(StageStyle.UNDECORATED);
				window.initModality(Modality.APPLICATION_MODAL); 
				window.setWidth(400);
				window.setHeight(300); 

				FXMLLoader rootFXML = new FXMLLoader(getClass().getResource("/logic/fxml/EmailPopup.fxml"));
				Parent root = rootFXML.load(); 
				EmailViewController emailViewController = rootFXML.getController();
				emailViewController.setEvent(course, time,gym);
				Scene scene = new Scene(root);
				window.setScene(scene); 
				window.show();
			} 
			catch (IOException ex) { AlertFactory.getInstance().createAlert(ex);

			}
	    }
	    @FXML
	    void closingPopup(MouseEvent event) {
	        Stage currStage = (Stage) closeBtn.getScene().getWindow();
	        currStage.close();
	    }
	   


	    public void setPopupView(Marker i, List<Session> list) {
	    	String gymName = i.getTitle();
	    	String addressGym;

	    	for(Session s: list) {
	    		if(s.getGym().equals(gymName) && !gymName.contentEquals("You are Here!")) {
	    			course=s.getCourseName();
	    			gym=gymName;
	    			time=s.printDuration(s.getDuration());
	    			addressGym=s.getStreet();
	    			timeLbl.setText(time);
	    			
	    			gymPopupTitleLbl.setText(gym +"-"+course);
	    			gymAddressLbl.setText(addressGym);
	    	        evtLbl.setText(course);
	    	        txtArea.setText(s.getDescription());

	    		}
	    	}
	        
	   
	    }
	    
	    

	
}
