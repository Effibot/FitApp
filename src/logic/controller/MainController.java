package logic.controller;

import animatefx.animation.FadeIn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import logic.view.View;

public class MainController {
	private static MainController instance;
	private  BorderPane container;
	private  BorderPane topBar;
	private  VBox topBox;
	private int id = 0; 

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	private MainController() {}

	public static synchronized MainController getInstance() {
		if(MainController.instance == null) {
			MainController.instance = new MainController();
		}
		return MainController.instance;
	}
	


	public BorderPane getContainer() {
		return container;
	}

	public void setContainer(BorderPane container) {
		this.container = container;
	}

	public BorderPane getTopBar() {
		return topBar;
	}

	public void setTopBar(BorderPane topBar) {
		this.topBar = topBar;
	}

	public VBox getTopBox() {
		return topBox;
	}

	public void setTopBox(VBox topBox) {
		this.topBox = topBox;
	}

	public void replace(BorderPane container, View node) {
		container.setCenter(node.getRoot());
		new FadeIn(container.getCenter()).play();
	}
	

}


