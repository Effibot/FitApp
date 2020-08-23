package logic.viewcontroller;

import java.io.IOException;

import com.jfoenix.controls.JFXListView;

import javafx.fxml.FXML;
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


	private MainController ctrl = MainController.getInstance();
	private ViewFactory factory = ViewFactory.getInstance();
	private MapController mapController = MapController.getSingletonInstance();
	
    @FXML
    void goBack(MouseEvent event) {
    	try {

    		listCell.getItems().clear();
    		mapController.wipeAll();
			ctrl.replace(ctrl.getContainer(), factory.createView(ViewType.BOOKINGFORM));
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}
    }
   
   
  
   
    





	@FXML
    void initialize() {
    		BookingFormController bookingFormController = BookingFormController.getSingletoneInstance();
    		BookingOnMapBean bookingOnMapBean = bookingFormController.getBookingOnMapBean();
    		String date = bookingOnMapBean.getDate();
    		String time = bookingOnMapBean.getTime();

    		int event = bookingOnMapBean.getEvent();

    		double radius = bookingOnMapBean.getRadius();
    		MapInitializer map = new MapInitializer(date, time, event, radius);
  
    		map.setListCell(listCell);
			mapBox.getChildren().add(map.getView());
    }
}
