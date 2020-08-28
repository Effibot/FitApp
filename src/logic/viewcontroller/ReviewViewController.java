package logic.viewcontroller;

import org.controlsfx.control.Rating;

import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;
public class ReviewViewController {
	@FXML
    private StackPane stackPane;

    @FXML
    private Label reviewLbl;

    @FXML
    private Button backBtn;

    @FXML
    private JFXListView<String> listView;

    @FXML
    private JFXButton writeBtn;
    @FXML
    private AnchorPane anchorPane;

    Entry currEntry;
    ViewFactory viewFactory = ViewFactory.getInstance();
    public ReviewViewController(){

    }

    public  ReviewViewController(Entry entry) {


    }

    @FXML
    void closeReviews(MouseEvent event) {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void writeReview(MouseEvent event) {
        /*
		 * FXMLLoader rootFXML = new FXMLLoader(getClass().getResource("/logic/fxml/submitReview.fxml")); 
		 * Parent root = rootFXML.load(); 
		 * SubmitViewController submitViewController = rootFXML.getController();
		 * submitViewController.setScene(currEntry);
		 */
		viewFactory.create(ViewType.WRITEREWIES);
//            SubmitViewController submitViewController = (SubmitViewController) calendarView.getController();
//			submitViewController.setScene(currEntry);
		Scene scene = writeBtn.getScene();
		viewFactory.getRoot().translateYProperty().set(scene.getHeight());
		stackPane.getChildren().add(viewFactory.getRoot());
		Timeline timeLine = new Timeline();
		KeyValue kv = new KeyValue(viewFactory.getRoot().translateYProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
		timeLine.getKeyFrames().add(kf);
		timeLine.setOnFinished(event1 -> {
		    stackPane.getChildren().remove(anchorPane);
		});
		timeLine.play();
    }

    public void setView(Entry entry) {
        this.currEntry = entry;

        //Richiesta al dao per la palestra

        //caricamento delle recensioni

        //
        Rating rating = new Rating();

        listView.getItems().addAll("CIAO","CIAO","CIAO","CIAO","CIAO");

    }

	public void setTypeView(boolean userProperty) {
		if (userProperty)
			writeBtn.setVisible(false);
		else
			writeBtn.setVisible(true);

	}
}
