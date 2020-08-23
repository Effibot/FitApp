package logic.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.viewfactory.ViewType;

public class View {
	private Parent root;

	protected View(ViewType view) {
		try {
			FXMLLoader loader = new FXMLLoader(ViewType.getUrl(view));
			setRoot(loader.load());
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
			e.printStackTrace();
		}
	}

	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}
}