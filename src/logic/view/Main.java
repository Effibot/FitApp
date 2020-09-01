package logic.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.factory.alertfactory.AlertFactory;
import logic.viewcontroller.Container;

public class Main extends Application {
    private double offsetX;
    private double offsetY;

    @Override
    public void start(Stage mainStage) throws Exception {
        try {
            Container mainContainer = Container.getInstance();
//			mainContainer.loadView(ViewType.CONTAINER);
//			mainContainer.setView(ViewType.CONTAINER);
            mainContainer.initView();
            Group root = new Group();
            root.getChildren().addAll(mainContainer);
            Scene mainView = new Scene(root);
            //mainContainer.loadView(ViewType.LOGIN);
            //mainContainer.setView(ViewType.LOGIN);
            mainStage.setScene(mainView);
            mainStage.initStyle(StageStyle.UNDECORATED);
            root.setOnMousePressed(event -> {
                offsetX = event.getSceneX();
                offsetY = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                mainStage.setX(event.getScreenX() - offsetX);
                mainStage.setY(event.getScreenY() - offsetY);
            });
            mainStage.show();
        } catch (IllegalStateException e) {
            AlertFactory.getInstance().createAlert(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
