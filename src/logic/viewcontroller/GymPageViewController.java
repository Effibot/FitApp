package logic.viewcontroller;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import com.calendarfx.view.page.MonthPage;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import logic.bean.GymPageBean;
import logic.controller.GymPageController;
import logic.controller.MainController;
import logic.controller.ManageTrainerController;
import logic.facade.calendar.CalendarFacade;
import logic.factory.alertfactory.AlertFactory;
import logic.model.dao.SessionDAO;
import logic.model.dao.TrainerDAO;
import logic.model.entity.Course;
import logic.model.entity.Trainer;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GymPageViewController implements ViewController {
    Container mainParent;

    @Override
    public void setMainParent(Container mainParent) {
        this.mainParent = mainParent;
        bean = new GymPageBean();
        bean.setUserId(mainParent.getUserId());
        gymCtrl = new GymPageController(bean);
        calendarSetup();
        calendarBox.getChildren().add(monthPage);
        fillGraphics();
        calendarBox.setVisible(false);
        calendarBox.setDisable(true);
        initTable();
    }

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane calendarBox;

    @FXML
    private Button manageTrainer;

    @FXML
    private AnchorPane tableAnchor;

    @FXML
    private TextField nameField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private CheckBox kickCheck;

    @FXML
    private CheckBox boxeCheck;

    @FXML
    private CheckBox zumbaCheck;

    @FXML
    private CheckBox salsaCheck;

    @FXML
    private CheckBox functCheck;

    @FXML
    private CheckBox walkCheck;

    @FXML
    private CheckBox pumpCheck;
    @FXML
    private TableView<Trainer> trainerTable;

    @FXML
    private TableColumn<Trainer, String> trainerName;

    @FXML
    private TableColumn<Trainer, Boolean> kickCol;

    @FXML
    private TableColumn<Trainer, Boolean> boxeCol;

    @FXML
    private TableColumn<Trainer, Boolean> zumbaCol;

    @FXML
    private TableColumn<Trainer, Boolean> salsaCol;

    @FXML
    private TableColumn<Trainer, Boolean> functCol;

    @FXML
    private TableColumn<Trainer, Boolean> walkCol;

    @FXML
    private TableColumn<Trainer, Boolean> pumpCol;

    @FXML
    private ImageView sideUserIcon;

    @FXML
    private Label sideUsername;

    @FXML
    private Label sideGymName;

    @FXML
    private Label sideGymStreet;

    @FXML
    private Button openCalendar;

    @FXML
    private Button viewReview;

    private MainController ctrl;
    private GymPageBean bean;
    private GymPageController gymCtrl;
    private MonthPage monthPage;
    private ManageTrainerController mtCtrl;
    private BooleanProperty bp;
    private ObservableList<Trainer> trainerSelected;
    private ObservableList<Trainer> allTrainer;
    private List<CheckBox> checkList;
    private String[] propertyName = {"kick", "boxe", "zumba", "salsa", "funct", "walk", "pump"};
    private List<TableColumn<Trainer, Boolean>> colList = new ArrayList<>();

    public GymPageViewController() {
        //ctrl = MainController.getInstance();

    }

    @FXML
    void manageTrainer(ActionEvent event) {
        if (event.getSource().equals(manageTrainer)) {
            if (!tableAnchor.isVisible()) {
                if (calendarBox.isVisible())
                    openCalendar.fireEvent(new ActionEvent());
                new ZoomIn(tableAnchor).play();
                tableAnchor.setVisible(true);
                tableAnchor.setDisable(false);
                tableAnchor.toFront();
            } else if (tableAnchor.isVisible()) {
                tableAnchor.setVisible(false);
                tableAnchor.setDisable(true);
                tableAnchor.toBack();
            }
        }
    }

    @FXML
    private void showCalendar(ActionEvent event) {
        if (event.getSource().equals(openCalendar)) {
            if (!calendarBox.isVisible()) {
                if (tableAnchor.isVisible())
                    manageTrainer.fireEvent(new ActionEvent());
                new ZoomIn(calendarBox).play();
                calendarBox.setVisible(true);
                calendarBox.setDisable(false);
                calendarBox.toFront();
                openCalendar.setText("Close Calendar");
            } else {
                new ZoomOut(calendarBox).play();
                calendarBox.toBack();
                openCalendar.setText("Open Calendar");
                calendarBox.setVisible(false);
                calendarBox.setDisable(true);
                // calendarBox.setManaged(true);
            }
        }
    }

    @FXML
    void viewReview(ActionEvent event) {
        // to be implemented
    }

    @FXML
    public void manage(ActionEvent event) {
        if (event.getSource().equals(addButton)) {
            add();
        } else if (event.getSource().equals(editButton)) {
            edit();
        } else if (event.getSource().equals(deleteButton)) {
            delete();
        }
    }

    private void add() {
        if (!ManageTrainerController.isAlpha(nameField.getText()) && nameField.getText().trim().isEmpty()) {
            AlertFactory.getInstance()
                    .createAlert(AlertType.INFORMATION, "Trainer Name Input Error",
                            "Be carefoul, only characters are allowed for trainer name",
                            "Your input is: "
                                    + (nameField.getText().trim().isEmpty() ? "empty string" : nameField.getText()))
                    .display();
        } else {
            // add to table and save to db
            Trainer t = new Trainer();
            Map<Course, Boolean> course = createCourse();
            t.setName(nameField.getText());
            t.setGymId(gymCtrl.getGym().getGymId());
            t.setCourse(course);
            // commented line to prevent saving on db
            // TrainerDAO.getInstance().addTrainer(t);
            // t.setTrainerId(TrainerDAO.getInstance().getTrainerId(t));
            trainerTable.getItems().add(t);
            nameField.clear();
            for (CheckBox c : checkList) {
                c.selectedProperty().set(false);
            }
        }

    }

    private void bindAdd() {
        addButton.setDisable(true);
        BooleanBinding checkBinding = kickCheck.selectedProperty()
                .or(boxeCheck.selectedProperty().or(
                        zumbaCheck.selectedProperty().or(salsaCheck.selectedProperty().or(functCheck.selectedProperty()
                                .or(walkCheck.selectedProperty().or(pumpCheck.selectedProperty()))))));
        bp = new SimpleBooleanProperty();
        bp.bind(checkBinding);
        bp.addListener((obsV, oldV, newV) -> addButton.setDisable(oldV));
    }

    private void edit() {
        if (!trainerSelected.isEmpty()) {
            if (SessionDAO.getInstance().hasSession(trainerSelected.get(0).getTrainerId())) {
                AlertFactory.getInstance()
                        .createAlert(AlertType.INFORMATION, "Trainer With Sessions",
                                "You are trying to delete a Trainer with active sessions",
                                "Open your calendar and delete the involved session instead and retry.")
                        .display();
            } else {
                Map<Course, Boolean> course = createCourse();
                Trainer t = trainerSelected.get(0);
                t.setCourse(course);
                trainerSelected.forEach(allTrainer::remove);
                TrainerDAO.getInstance().deleteTrainer(t.getTrainerId());
                trainerTable.getItems().add(t);
            }
        } else {
            AlertFactory.getInstance().createAlert(AlertType.INFORMATION, "No Trainer Selected",
                    "You haven't selected a trainer yet, select one!", "").display();
        }

    }

    private void delete() {
        if (!trainerSelected.isEmpty()) {
            if (SessionDAO.getInstance().hasSession(trainerSelected.get(0).getTrainerId())) {
                AlertFactory.getInstance()
                        .createAlert(AlertType.INFORMATION, "Trainer With Sessions",
                                "You are trying to delete a Trainer with active sessions",
                                "Open your calendar and delete the involved session instead and retry.")
                        .display();
            }
        } else {
            // linee commentate per evitare di cancellare cose su db durante testing
            trainerSelected.forEach(allTrainer::remove);
            // TrainerDAO.getInstance().deleteTrainer(trainerSelected.get(0).getTrainerId());
        }
    }

    private void initTable() {
        mtCtrl = new ManageTrainerController(bean);
        colList.add(kickCol);
        colList.add(boxeCol);
        colList.add(zumbaCol);
        colList.add(salsaCol);
        colList.add(functCol);
        colList.add(walkCol);
        colList.add(pumpCol);
        checkList = new ArrayList<>();
        checkList.add(kickCheck);
        checkList.add(boxeCheck);
        checkList.add(zumbaCheck);
        checkList.add(salsaCheck);
        checkList.add(functCheck);
        checkList.add(walkCheck);
        checkList.add(pumpCheck);
        trainerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        for (TableColumn<Trainer, Boolean> column : colList) {
            column.setCellValueFactory(new PropertyValueFactory<>(propertyName[colList.indexOf(column)]));
        }
        trainerTable.setItems(mtCtrl.getTrainerList());
        tableAnchor.setVisible(false);
        tableAnchor.setDisable(true);
        bindAdd();
        allTrainer = trainerTable.getItems();
        trainerSelected = trainerTable.getSelectionModel().getSelectedItems();
    }

    private void calendarSetup() {
        int gymId = mainParent.getUserId();
        CalendarFacade calendarFacade = new CalendarFacade(true);

        monthPage = calendarFacade.initializeCalendar(gymId);
        monthPage.getCalendars().get(0).addEventHandler(calendarFacade.getEventHandler());

        // monthPage.getCalendars().get(0).removeEventHandler(calendarFacade.getEventHandler());
    }

    private Map<Course, Boolean> createCourse() {
        Map<Course, Boolean> course = new EnumMap<>(Course.class);
        for (Course c : Course.values()) {
            course.put(c, checkList.get(c.getCourseNumber()).isSelected());
        }
        return course;
    }

    private void fillGraphics() {
        sideGymName.setText(gymCtrl.getGym().getGymName());
        sideGymName.setWrapText(true);
        sideUsername.setText(gymCtrl.getManager().getName());
        sideUsername.setWrapText(true);
        sideGymStreet.setText(gymCtrl.getGym().getStreet());
        sideGymStreet.setWrapText(true);
    }

    @FXML
    void initialize() {

        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert tableAnchor != null : "fx:id=\"tableAnchor\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert trainerTable != null : "fx:id=\"trainerTable\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert trainerName != null : "fx:id=\"trainerName\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert kickCol != null : "fx:id=\"kickCol\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert boxeCol != null : "fx:id=\"boxeCol\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert zumbaCol != null : "fx:id=\"zumbaCol\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert salsaCol != null : "fx:id=\"salsaCol\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert functCol != null : "fx:id=\"functCol\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert walkCol != null : "fx:id=\"walkCol\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert pumpCol != null : "fx:id=\"pumpCol\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert calendarBox != null : "fx:id=\"calendarBox\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert sideUserIcon != null : "fx:id=\"sideUserIcon\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert sideUsername != null : "fx:id=\"sideUsername\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert sideGymName != null : "fx:id=\"sideGymName\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert sideGymStreet != null : "fx:id=\"sideGymStreet\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert manageTrainer != null : "fx:id=\"manageTrainer\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert openCalendar != null : "fx:id=\"openCalendar\" was not injected: check your FXML file 'GymPage.fxml'.";
        assert viewReview != null : "fx:id=\"viewReview\" was not injected: check your FXML file 'GymPage.fxml'.";

    }
}
