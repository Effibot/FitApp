package logic.view.calendarview;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import logic.factory.calendarviewfactory.CalendarViewType;
import logic.factory.alertfactory.AlertFactory;

public class CalendarView {
	private Parent root;
	Object controller;
	public Object getController() {
		return controller;
	}

	public void setController(Object controller) {
		this.controller = controller;
	}

	protected CalendarView(CalendarViewType view) {
		try {
			FXMLLoader loader = new FXMLLoader(CalendarViewType.getUrl(view));
			Object controller = loader.getController();
			setRoot(loader.load());
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}
}
