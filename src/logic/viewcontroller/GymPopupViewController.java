package logic.viewcontroller;

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
import logic.factory.alertfactory.AlertFactory;
import logic.model.entity.Session;

import java.io.IOException;
import java.util.List;


public class GymPopupViewController implements ViewController {
    Container mainParent;

    @Override
    public void setMainParent(Container mainParent) {
        this.mainParent = mainParent;
    }

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
    @FXML
    private Label trainerLbl;
    @FXML
    private Label individualLbl;

    private String course;
    private String gym;
    private String time;
    private static final String SOLOCOURSE = "INDIVIDUAL COURSE";
    private static final String GROUPCOURSE = "GROUP COURSE";

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
            emailViewController.setEvent(course, time, gym);
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            AlertFactory.getInstance().createAlert(ex);

        }
    }

    @FXML
    void closingPopup(MouseEvent event) {
        Stage currStage = (Stage) closeBtn.getScene().getWindow();
        currStage.close();
    }


    public void setPopupView(Marker i, List<Session> list) {
        String titlePopup = i.getTitle();
        String addressGym;

        for (Session s : list) {
            if ((s.getGym() + "\t" + s.getCourseName()).equals(titlePopup)
                    && !titlePopup.contentEquals("You are Here!")) {

                course = s.getCourseName();
                gym = titlePopup;
                time = s.printDuration(s.getDuration());
                addressGym = s.getStreet();
                timeLbl.setText(time);
                trainerLbl.setText(s.getTrainername());
                if (s.isIndividual()) {
                    individualLbl.setText(SOLOCOURSE);
                } else {
                    individualLbl.setText(GROUPCOURSE);
                }
                gymPopupTitleLbl.setText(gym);
                gymAddressLbl.setText(addressGym);
                evtLbl.setText(course);
                txtArea.setText(s.getDescription());

            }
        }


    }


}
