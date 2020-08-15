package logic.view.calendarview;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import logic.factory.calendarviewfactory.CalendarViewType;
import logic.factory.alertfactory.AlertFactory;

public class CalendarView {
	private Parent root;
	private Object controller;
	public Object getCurrentController() {
		return this.controller;
	}

	public void setCurrentController(Object controller) {
		this.controller = controller;
	}

	public CalendarView(CalendarViewType view) {
		try {
			FXMLLoader loader = new FXMLLoader(CalendarViewType.getUrl(view));
		
			setRoot(loader.load());
			this.controller = loader.getController();

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
