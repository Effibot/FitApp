package logic.viewcontroller;

import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import logic.bean.BookingOnMapBean;
import logic.controller.BookingFormController;
import logic.controller.MainController;
import logic.controller.MapController;
import logic.entity.dao.GymDAO;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;
import logic.maputil.MapInitializer;

public class BookingOnMapViewController {

    @FXML
    private Hyperlink viewRecorder;

    @FXML
    private ImageView userIcon;


    @FXML
    private Button backBtn;

    @FXML
    private JFXListView<Label> listCell;

    @FXML
    private HBox mapBox;
    private String date = null;
    private String time = null;
    private String event = null;
    
	private MapController mapController = MapController.getSingletonInstance();
	private GymDAO dao = GymDAO.getInstance();
	private MainController ctrl = MainController.getInstance();
	private ViewFactory factory = ViewFactory.getInstance();
	
    @FXML
    void goBack(MouseEvent event) {
    	try {
			ctrl.replace(ctrl.getContainer(), factory.createView(ViewType.BOOKINGFORM));
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}
    }
   
    public void setListView() {
    	
	    	List<String> gymsNameCell = mapController.listGymName();
	    	for(String s: gymsNameCell) {
	    		Label lbl = new Label();
	    		lbl.setText(s);
	    		listCell.getItems().add(lbl);
	    	}
    }
    
    @FXML
    void initialize() {
    	//try {
    		BookingFormController bookingFormController = BookingFormController.getSingletoneInstance();
    		BookingOnMapBean bookingOnMapBean = bookingFormController.getBookingOnMapBean();
    		date = bookingOnMapBean.getDate();
    		time = bookingOnMapBean.getTime();
    		event = bookingOnMapBean.getEvent();
    		System.out.println("arrivo qui");
    		MapInitializer map = new MapInitializer();
			mapBox.getChildren().add(map.getView());
    }
}
