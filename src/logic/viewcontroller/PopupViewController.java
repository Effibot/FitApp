package logic.viewcontroller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.calendarutility.Calendars;
import logic.calendarutility.Entries;
import logic.factory.calendarviewfactory.CalendarViewFactory;
import logic.factory.calendarviewfactory.CalendarViewType;
import logic.view.calendarview.CalendarView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

public class PopupViewController {
	  private static PopupViewController instance = null;
	    @FXML
	    private MenuItem style1;
	    @FXML
	    private MenuItem style2;
	    @FXML
	    private MenuItem style3;
	    @FXML
	    private MenuItem style4;
	    @FXML
	    private MenuItem style5;
	    @FXML
	    private MenuItem style6;
	    @FXML
	    private MenuItem style7;
	    @FXML
	    private JFXTextField courseNameId;
	    @FXML
	    private JFXTextField dateId;
	    @FXML
	    private JFXTimePicker timeId;
	    @FXML
	    private JFXTimePicker timeId1;
	    @FXML
	    private MenuItem daily;
	    @FXML
	    private MenuItem weekly;
	    @FXML
	    private MenuItem monthly;
	    @FXML
	    private JFXButton saveBtn;
	    @FXML
	    private JFXButton deleteBtn;
	    @FXML
	    private MenuButton intervalMenu;
	    @FXML
	    private JFXButton eMailBtn;
	    private Entry selectedEntry;
	    private Calendars calendars;
	    private Entries entries;
	    Logger logger = Logger.getLogger("IOException");
	    private DateControl.EntryDetailsPopOverContentParameter param;
	    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        CalendarViewFactory calendarViewFactory = CalendarViewFactory.getInstance();



	    public DateControl.EntryDetailsPopOverContentParameter getParam() {
	        return param;
	    }

	    public void setParam(DateControl.EntryDetailsPopOverContentParameter param) {
	        this.param = param;
	    }

	    public PopupViewController() {
	        calendars = Calendars.getSingletonInstance();
	        entries = Entries.getSingletonInstance();
	       // entries.setEntry(2020,5,20,18,20);
	    }

	    public void setSelectedEvent() {

	        selectedEntry = this.getParam().getEntry();
	    }



	    @FXML
	    public void setRecurrence(ActionEvent event) {
	        Object obj = event.getSource();
	        String rrule = null;
	        LocalDate startDay = selectedEntry.getStartDate();
	        if(obj.hashCode() == daily.hashCode()){

	            rrule = "RRULE:FREQ=DAILY;INTERVAL=7;COUNT=4;";


	        }
	        else if(obj.hashCode() == weekly.hashCode()) {
	            LocalDate weeklyDay = startDay.plusDays(7);
	            String weeklyFormatted = weeklyDay.format(dateTimeFormatter);
	            rrule = "RRULE:FREQ=DAILY;UNTIL=" + weeklyFormatted.replace("-", "");


	        }
	        if(obj.hashCode() == monthly.hashCode()){
	            LocalDate monthlyDay = startDay.plusMonths(1);
	            String monthlyFormatted = monthlyDay.format(dateTimeFormatter);
	            rrule = "RRULE:FREQ=DAILY;UNTIL=" + monthlyFormatted.replace("-", "");


	        }
	        selectedEntry.setRecurrenceRule(rrule);

	        intervalMenu.setText(daily.getText());
	        

	    }


	    @FXML
	    public void delete(MouseEvent event) {

	            try {
	                Stage window = new Stage();
	                window.initStyle(StageStyle.TRANSPARENT);
	                window.initModality(Modality.APPLICATION_MODAL);
	                window.setMinWidth(335);
	                window.setMinHeight(150);
					/*
					 * FXMLLoader rootFXML = new
					 * FXMLLoader(getClass().getResource("/logic/fxml/popupDelete.fxml")); Parent
					 * root = rootFXML.load(); PopupDeleteController popupDeleteController =
					 * rootFXML.getController();
					 * popupDeleteController.setEntryToDelete(selectedEntry);
					 */
	                CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.DELETEPOPUP);
	                PopupDeleteController popupDeleteController = (PopupDeleteController) calendarView.getController();
	                popupDeleteController.setEntryToDelete(selectedEntry);
	                Scene scene = new Scene(calendarView.getRoot());
	                window.setScene(scene);
	                window.showAndWait();
	            } catch (IOException ex) {
	                logger.log(Level.SEVERE,ex.toString());
	            }

	    }



	    @FXML
	    private void save(MouseEvent event) {
	        //Requesting to Database
	    }

	    public void setNameEvent(){
	        courseNameId.setText(selectedEntry.getCalendar().getName());
	        courseNameId.setEditable(false);

	    }

	    public void setDateEvent(){
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
	    }

	    public void setDetailsPopup() {
	        this.setNameEvent();
	        this.setDateEvent();
	    }



	    @FXML
	    void setStyleEntry(ActionEvent event) {
	        Object obj = event.getSource();
	        Calendar calMenuItem = calendars.getCalendar(0);

	        if(obj.hashCode() == style1.hashCode()){
	            calMenuItem = calendars.getCalendar(0);

	        }
	        if(obj.hashCode() == style2.hashCode()){
	            calMenuItem = calendars.getCalendar(1);

	        }
	        if(obj.hashCode() == style3.hashCode()){
	            calMenuItem = calendars.getCalendar(2);

	        }
	        if(obj.hashCode() == style4.hashCode()){
	            calMenuItem = calendars.getCalendar(3);

	        }
	        if(obj.hashCode() == style5.hashCode()){
	            calMenuItem = calendars.getCalendar(4);

	        }
	        if(obj.hashCode() == style6.hashCode()){
	            calMenuItem = calendars.getCalendar(5);

	        }
	        if(obj.hashCode() == style7.hashCode()){
	            calMenuItem = calendars.getCalendar(6);

	        }
	        selectedEntry.setCalendar(calMenuItem);
	        selectedEntry.setTitle(calMenuItem.getName());
	        courseNameId.setText(calMenuItem.getName());


	    }


	    @FXML
	    public void setTimeEntry(ActionEvent event) {
	        Object obj = event.getSource();
	        LocalTime time;

	        if(obj.hashCode() == timeId.hashCode()){
	            time = timeId.getValue();
	            selectedEntry.changeStartTime(time);

	        }
	        else if(obj.hashCode() == timeId1.hashCode()){
	            time = timeId1.getValue();
	            selectedEntry.changeEndTime(time);
	        }
	    }

	    @FXML
	    void sendEmail(MouseEvent event) {
	        try {

	            Stage emlStage = new Stage();
	            emlStage.initStyle(StageStyle.TRANSPARENT);
	            emlStage.initModality(Modality.APPLICATION_MODAL);
	            emlStage.setMinWidth(335);
	            emlStage.setMinHeight(150);

				/*
				 * FXMLLoader rootFXML = new
				 * FXMLLoader(getClass().getResource("/logic/fxml/EmailPopup.fxml")); Parent
				 * rootEmail = rootFXML.load(); EmailViewController emailViewController =
				 * rootFXML.getController(); //emailController.setParameters(selectedEntry);
				 */
                CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.EMAIL);
                EmailViewController emailViewController = (EmailViewController) calendarView.getController();
                
                ///Da mettere
	            Scene scene = new Scene(calendarView.getRoot());
	            emlStage.setScene(scene);
	            emlStage.showAndWait();

	        } catch (IOException ex) {
	            logger.log(Level.SEVERE,ex.toString());
	        }
	    }

	    public static synchronized PopupViewController getSingletonInstance() {
	        if (PopupViewController.instance == null)
	            PopupViewController.instance = new PopupViewController();
	        return instance;
	    }
}
