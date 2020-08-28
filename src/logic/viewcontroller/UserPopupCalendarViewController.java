package logic.viewcontroller;

import java.time.format.DateTimeFormatter;

import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl.EntryDetailsPopOverContentParameter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.facade.calendar.EntryCalendar;
import logic.facade.calendar.EntryCustom;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;

public class UserPopupCalendarViewController {

	@FXML
	private VBox box;

	@FXML
	private JFXTextField courseNameId;

	@FXML
	private JFXTextField dateId;

	@FXML
	private HBox timeStart;

	@FXML
	private JFXTimePicker timeId;

	@FXML
	private JFXTimePicker timeId1;

	@FXML
	private Label trainerLbl;

	@FXML
	private Label gymLbl;

	@FXML
	private JFXTextArea textArea;

	@FXML
	private StackPane stackpane;

	@FXML
	private JFXButton deleteBtn;

	@FXML
	private JFXButton eMailBtn;
	ViewFactory viewFactory = ViewFactory.getInstance();

	private EntryCustom<?> selectedEntry;
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private EntryCalendar entries;

	
	@FXML
	void delete(MouseEvent event) {
		Stage window = new Stage();
		window.initStyle(StageStyle.TRANSPARENT);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(335);
		window.setMinHeight(150);

		viewFactory.create(ViewType.DELETEPOPUP);
		PopupDeleteController popupDeleteController = (PopupDeleteController) viewFactory.getCurrentController();
		popupDeleteController.setEntryToDelete(selectedEntry);
		System.out.println(selectedEntry.getId());
		Scene scene = new Scene(viewFactory.getRoot());
		window.setScene(scene);
		window.showAndWait();
	}

	@FXML
	void sendEmail(MouseEvent event) {
		Stage emlStage = new Stage();
		emlStage.initStyle(StageStyle.TRANSPARENT);
		emlStage.initModality(Modality.APPLICATION_MODAL);
		emlStage.setMinWidth(335);
		emlStage.setMinHeight(150);



		viewFactory.create(ViewType.EMAIL);
		EmailViewController emailViewController = (EmailViewController) viewFactory.getCurrentController();
		/// Da mettere

		emailViewController.setEvent(selectedEntry.getCalendar().getName(),
				selectedEntry.getStartTime().toString());
		Scene scene = new Scene(viewFactory.getRoot());
		emlStage.setScene(scene);
		emlStage.showAndWait();
	}
	@FXML
    void setTimeEntry(ActionEvent event) {

    }

	public void setParam(EntryCustom<?> currEntryCustom) {

		this.selectedEntry = currEntryCustom;
		courseNameId.setText(selectedEntry.getEntry().getCalendar().getName());
		courseNameId.setEditable(false);
		dateId.setText(selectedEntry.getStartDate().format(dateFormatter));
		timeId.set24HourView(true);
		timeId.setValue(selectedEntry.getStartTime());
		timeId.setDefaultColor(Color.valueOf("#009688"));
		timeId1.set24HourView(true);
		timeId1.setValue(selectedEntry.getEndTime());
		timeId1.setDefaultColor(Color.valueOf("#009688"));
		dateId.setEditable(false);
		timeId.setEditable(false);
		timeId1.setEditable(false);
		dateId.setDisable(true);
		timeId.setDisable(true);
		timeId1.setDisable(true);

		gymLbl.setText(selectedEntry.getSession().getGym());
		trainerLbl.setText(selectedEntry.getSession().getTrainername());
		
	}

}
