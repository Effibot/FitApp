package logic.viewcontroller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.bean.BookingOnMapBean;
import logic.controller.BookingFormController;
import logic.controller.MainController;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;

public class BookingFormViewController {

	@FXML
	private JFXDatePicker dateBtn;

	@FXML
	private JFXTimePicker timeBtn;

	@FXML
	private MenuButton eventMenu;

	@FXML
	private MenuItem evt1;

	@FXML
	private MenuItem evt2;

	@FXML
	private MenuItem evt3;

	@FXML
	private MenuItem evt4;

	@FXML
	private MenuItem evt5;

	@FXML
	private MenuItem evt6;

	@FXML
	private MenuItem evt7;

	@FXML
	private JFXToggleButton mapBtn;

	@FXML
	private Label radiousLbl;

	@FXML
	private JFXSlider slideBtn;

	@FXML
	private JFXButton searchBtn;

	@FXML
	private Button backBtn;

	private static final double MAX_DISTANCE = 30;
	private static final double MIN_DISTANCE = 5;
	private static final double DEFAULT_DISTANCE = 17;

	private MainController ctrl = MainController.getInstance();
	private ViewFactory factory = ViewFactory.getInstance();
	
	@FXML
    public void goBack(MouseEvent event) {
	  try {
			ctrl.replace(ctrl.getContainer(), factory.createView(ViewType.USERPAGE));
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}
    }

	
	@FXML
	public void onMap(MouseEvent event) {
		if (mapBtn.isSelected()) {
			slideBtn.setVisible(true);
			radiousLbl.setVisible(true);
		} else {
			slideBtn.setVisible(false);
			radiousLbl.setVisible(false);
		}
	}

	@FXML
	public void setEvent(ActionEvent event) {
		Object obj = event.getSource();
		String txt = null;
		if (obj.hashCode() == evt1.hashCode()) {
			txt = evt1.getText();

		}
		if (obj.hashCode() == evt2.hashCode()) {
			txt = evt2.getText();

		}
		if (obj.hashCode() == evt3.hashCode()) {
			txt = evt3.getText();

		}
		if (obj.hashCode() == evt4.hashCode()) {
			txt = evt4.getText();

		}
		if (obj.hashCode() == evt5.hashCode()) {
			txt = evt5.getText();

		}
		if (obj.hashCode() == evt6.hashCode()) {
			txt = evt6.getText();

		}
		if (obj.hashCode() == evt7.hashCode()) {
			txt = evt7.getText();

		}
		eventMenu.setText(txt);
	}

	@FXML
	public void goBooking(MouseEvent event) {
		LocalDate localDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		LocalDate sltDate = dateBtn.getValue();
		LocalTime sltTime = timeBtn.getValue();
		String str = null;

		if (sltDate == null || sltTime == null) {
			str = "Please insert date and time";
			// AlertFactory.getInstance().createAlert(str);
			return;
		}
		if ((sltDate.compareTo(localDate) < 0) || (sltTime.compareTo(localTime) < 0)) {
			str = "The date or time cannot be selected";
			// AlertFactory.getInstance().createAlert(str);
		} else {

			if (mapBtn.isSelected()) {

				try {

					BookingFormController bookFormController = BookingFormController.getSingletoneInstance();
					BookingOnMapBean bookingOnMapBean = bookFormController.getBookingOnMapBean();
					bookingOnMapBean.setDate(sltDate.toString());
					bookingOnMapBean.setDate(sltTime.toString());
					bookingOnMapBean.setEvent(eventMenu.getText());
					bookingOnMapBean.setRadius(slideBtn.getValue());
					ctrl.replace(ctrl.getContainer(), factory.createView(ViewType.BOOKINGONMAP));

				} catch (IOException e) {
					AlertFactory.getInstance().createAlert(e);
				}
			}
		}
	}

	@FXML
	public void initialize() {
		timeBtn.set24HourView(true);
		slideBtn.setMin(MIN_DISTANCE);
		slideBtn.setMax(MAX_DISTANCE);
		slideBtn.setValue(DEFAULT_DISTANCE);
		slideBtn.setVisible(false);
		radiousLbl.setVisible(false);
	}

}