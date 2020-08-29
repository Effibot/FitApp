package logic.bean;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainBean {

	private BorderPane container;
	private BorderPane topBar;
	private VBox topBox;

	public VBox getTopBox() {
		return topBox;
	}

	public void setTopBox(VBox topBox) {
		this.topBox = topBox;
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

}
