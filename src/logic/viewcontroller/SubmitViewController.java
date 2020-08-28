package logic.viewcontroller;
import java.io.IOException;

import com.calendarfx.model.Entry;
import com.jfoenix.controls.JFXButton;

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
import javafx.util.Duration;
import logic.factory.alertfactory.AlertFactory;


import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;

public class SubmitViewController {
	  @FXML
	    private Label reviewLbl;

	    @FXML
	    private Button backBtn;
	    @FXML
	    private AnchorPane anchorPane;
	    @FXML
	    private JFXButton submitBtn;
	    Scene prevScene;
	    Entry currEntry;
	    ViewFactory viewFactory = ViewFactory.getInstance();
	    public void backScn(MouseEvent event) throws IOException {
			/*
			 * FXMLLoader fxmlLoader = new
			 * FXMLLoader(getClass().getResource("/logic/fxml/reviewGym.fxml")); Parent root
			 * = fxmlLoader.load();
			 */
           viewFactory.create(ViewType.REWIES);

	        Scene scene = backBtn.getScene();

	        viewFactory.getRoot().translateYProperty().set(scene.getHeight());

	        StackPane parentContainer = (StackPane) scene.getRoot();

	        parentContainer.getChildren().add(viewFactory.getRoot());
	        Timeline timeLine = new Timeline();
	        KeyValue kv = new KeyValue(viewFactory.getRoot().translateYProperty(), 0, Interpolator.EASE_OUT);
	        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
	        timeLine.getKeyFrames().add(kf);
	        timeLine.setOnFinished(event1 -> {
	            parentContainer.getChildren().remove(anchorPane);
	        });
	        timeLine.play();

	    }

	    public void submitReview(MouseEvent event) {
	        //Saving on Database

	        //
	        submitBtn.setOnAction(event1 -> {
	            try {
	                this.backScn(event);
	            } catch (IOException e) {
	            	AlertFactory.getInstance().createAlert(e);
	            }
	        });
	        submitBtn.fire();

	    }


	    public void setScene(Entry currEntry) {
	        this.currEntry = currEntry;
	    }
}
