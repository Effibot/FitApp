package logic.viewcontroller;

import java.util.EnumMap;
import java.util.Map;

import com.calendarfx.view.page.MonthPage;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import logic.bean.GymPageBean;
import logic.controller.GymPageController;
import logic.controller.MainController;
import logic.controller.ManageTrainerController;
import logic.entity.Course;
import logic.entity.Trainer;
import logic.facade.calendar.CalendarFacade;
import logic.factory.alertfactory.AlertFactory;

public class GymPageViewController {

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

	@FXML
	void manageTrainer(ActionEvent event) {
		if (event.getSource().equals(manageTrainer)) {
			/*
			 * if (calendarBox.isVisible()) { swapAnimation(monthPage, tableAnchor, true); }
			 * else
			 */ if (!tableAnchor.isVisible()) {
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

	public void swapAnimation(Node a, Node b, boolean animation) {
		if (animation) {
			new ZoomOut(a).play();
			a.toBack();
			new ZoomIn(b).play();
			b.setVisible(true);
			b.toFront();
			b.setDisable(false);
		} else {
			new ZoomOut(b).play();
			b.toBack();
			new ZoomIn(a).play();
			a.toFront();
			b.setDisable(true);
		}
	}

	@FXML
	private void showCalendar(ActionEvent event) {
		if (event.getSource().equals(openCalendar)) {
			/*
			 * if (tableAnchor.isVisible()) { swapAnimation(tableAnchor, calendarBox, true);
			 * } else
			 */ if (!calendarBox.isVisible()) {
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

	private MainController ctrl;
	private GymPageBean bean;

	private GymPageController gymCtrl;
	private MonthPage monthPage;
	private ManageTrainerController mtCtrl;
	private BooleanProperty bp;

	private void fillGraphics() {
		sideGymName.setText(gymCtrl.getGym().getGymName());
		sideGymName.setWrapText(true);
		sideUsername.setText(gymCtrl.getManager().getName());
		sideUsername.setWrapText(true);
		sideGymStreet.setText(gymCtrl.getGym().getStreet());
		sideGymStreet.setWrapText(true);
	}

	public GymPageViewController() {
		ctrl = MainController.getInstance();
		gymCtrl = new GymPageController(ctrl.getId());
		bean = new GymPageBean();
		bean.setGymId(ctrl.getId());
	}

	private void initTable() {
		mtCtrl = new ManageTrainerController(bean);
		trainerName.setCellValueFactory(new PropertyValueFactory<>("name"));
		kickCol.setCellValueFactory(new PropertyValueFactory<>("kick"));
		boxeCol.setCellValueFactory(new PropertyValueFactory<>("boxe"));
		zumbaCol.setCellValueFactory(new PropertyValueFactory<>("zumba"));
		salsaCol.setCellValueFactory(new PropertyValueFactory<>("salsa"));
		functCol.setCellValueFactory(new PropertyValueFactory<>("funct"));
		walkCol.setCellValueFactory(new PropertyValueFactory<>("walk"));
		pumpCol.setCellValueFactory(new PropertyValueFactory<>("pump"));
		trainerTable.setItems(mtCtrl.getTrainerList());
		tableAnchor.setVisible(false);
		tableAnchor.setDisable(true);
		bindAdd();
	}

	@FXML
	public void manage(ActionEvent event) {
		if (event.getSource().equals(addButton)) {
			add();
		} else if (event.getSource().equals(editButton)) {
			edit();
		} else if (event.getSource().equals(deleteButton)) {
			ObservableList<Trainer> trainerSelected;
			ObservableList<Trainer> allTrainer;
			allTrainer = trainerTable.getItems();
			trainerSelected = trainerTable.getSelectionModel().getSelectedItems();
			// linee commentate per evitare di cancellare cose su db durante testing
			trainerSelected.forEach(allTrainer::remove);
			// TrainerDAO.getInstance().deleteTrainer(trainerSelected.get(0));
			System.out.println(trainerSelected.get(0));
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
			Map<Course, Boolean> course = new EnumMap<>(Course.class);
			course.put(Course.KICKBOXING, kickCheck.isSelected());
			course.put(Course.PUGILATO, boxeCheck.isSelected());
			course.put(Course.SALSA, salsaCheck.isSelected());
			course.put(Course.ZUMBA, zumbaCheck.isSelected());
			course.put(Course.FUNCTIONAL, functCheck.isSelected());
			course.put(Course.WALKING, walkCheck.isSelected());
			course.put(Course.PUMP, pumpCheck.isSelected());
			t.setName(nameField.getText());
			t.setGymId(ctrl.getId());
			t.setCourse(course);
			// commented line to prevent saving on db
//			TrainerDAO.getInstance().addTrainer(t);
//			t.setTrainerId(TrainerDAO.getInstance().getTrainerId(t));
			trainerTable.getItems().add(t);
			nameField.clear();
			kickCheck.selectedProperty().set(false);
			boxeCheck.selectedProperty().set(false);
			zumbaCheck.selectedProperty().set(false);
			salsaCheck.selectedProperty().set(false);
			functCheck.selectedProperty().set(false);
			walkCheck.selectedProperty().set(false);
			pumpCheck.selectedProperty().set(false);

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
		bp.addListener((obsV, oldV, newV) -> {
			addButton.setDisable(oldV);
		});
	}

	private void edit() {
		ObservableList<Trainer> trainerSelected;
		ObservableList<Trainer> allTrainer;
		allTrainer = trainerTable.getItems();
		trainerSelected = trainerTable.getSelectionModel().getSelectedItems();
		Map<Course, Boolean> course = new EnumMap<>(Course.class);
		course.put(Course.KICKBOXING, kickCheck.isSelected());
		course.put(Course.PUGILATO, boxeCheck.isSelected());
		course.put(Course.SALSA, salsaCheck.isSelected());
		course.put(Course.ZUMBA, zumbaCheck.isSelected());
		course.put(Course.FUNCTIONAL, functCheck.isSelected());
		course.put(Course.WALKING, walkCheck.isSelected());
		course.put(Course.PUMP, pumpCheck.isSelected());
		Trainer t = trainerSelected.get(0);
		t.setCourse(course);
		trainerSelected.forEach(allTrainer::remove);
		trainerTable.getItems().add(t);

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
		calendarSetup();

		calendarBox.getChildren().add(monthPage);

		fillGraphics();
		calendarBox.setVisible(false);
		// monthPage.setManaged(true);
		calendarBox.setDisable(true);
		initTable();
		// managerTrainer.toFront();
		// mtvc = new ManageTrainerViewController(bean);
//		mtv = new ManageTrainerView(ViewType.MANAGETRAINER);
//		calendarBox.getChildren().add(mtv.getRoot());

	}

	private void calendarSetup() {
		// TODO Auto-generated method stub
		int gymId = ctrl.getId();
		CalendarFacade calendarFacade = new CalendarFacade(true);

		monthPage = calendarFacade.initializeCalendar(gymId);

		monthPage.getCalendars().get(0).removeEventHandler(calendarFacade.getEventHandler());

	}

}
