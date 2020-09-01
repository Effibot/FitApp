package logic.controller;

import animatefx.animation.FadeIn;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import logic.bean.MainBean;
import logic.view.View;

public class MainController {
    private static MainController instance;
    private BorderPane container;
    private BorderPane topBar;
    private VBox topBox;
    private Scene scene;
    private int id = 0;
    private MainBean bean;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private MainController() {

    }

    public static synchronized MainController getInstance() {
        if (MainController.instance == null) {
            MainController.instance = new MainController();
        }
        return MainController.instance;
    }

    public BorderPane getContainer() {
        return container;
    }

//	public void setContainer() {
//		this.container = container;
//	}

    public BorderPane getTopBar() {
        return topBar;
    }

//	public void setTopBar() {
//		this.topBar = topBar;
//	}

    public VBox getTopBox() {
        return topBox;
    }

//	public void setTopBox() {
//		this.topBox = topBox;
//	}

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void replace(BorderPane container, View node) {

        container.setCenter(node.getRoot());
        new FadeIn(container.getCenter()).play();
    }

    public void setBean(MainBean bean) {
        this.bean = bean;
        this.container = bean.getContainer();
        this.topBar = bean.getTopBar();
        this.topBox = bean.getTopBox();

    }

    public MainBean getBean() {
        return bean;
    }

}
