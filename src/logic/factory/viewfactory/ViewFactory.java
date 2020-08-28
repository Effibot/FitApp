package logic.factory.viewfactory;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import logic.factory.alertfactory.AlertFactory;
import logic.view.BookingFormView;
import logic.view.BookingOnCalendarView;
import logic.view.BookingOnMapView;
import logic.view.DeletePopupView;
import logic.view.EmailView;
import logic.view.GymPageView;
import logic.view.LoginView;
import logic.view.MainPopupView;
import logic.view.ReviewView;
import logic.view.SignUpView;
import logic.view.TrainingConfView;
import logic.view.TrainingFormView;
import logic.view.UserPageView;
import logic.view.View;
import logic.view.WriteReviewView;

public class ViewFactory {
	private static ViewFactory instance = null;
	private Parent root;
	private Object controller;
	private ViewFactory() {}
	public static synchronized ViewFactory getInstance() {
		if(instance == null) {
			instance = new ViewFactory();
		}
		return instance;
	}
	public View createView(ViewType view) throws IOException{
		switch(view.getType()) {
		case 0:
			return new LoginView(view);
		case 1: // return gymPage
			return new GymPageView(view);
		case 2: // return userPage
			return new UserPageView(view);
		case 3: // return offerTraining
			return new TrainingFormView(view);
		case 4:	//return Training Form
			return new BookingFormView(view);
		case 5:	//return Booking Form
			return new TrainingConfView(view);
		case 7: //return BookingOnMap
			return new BookingOnMapView(view);
		case 8: //return SignUp view
			return new SignUpView(view);
		case 9: //return BookingOnCalendar
			return new BookingOnCalendarView(view);
		case 10:	 // return Email view
			return new EmailView(view);
		case 11: // return review view
			return new ReviewView(view);
		case 12: // return write review view
			return new WriteReviewView(view);
		case 13: // return main popup view
			return new MainPopupView(view);
		case 14:	//return delete popup view
			return new DeletePopupView(view);

		case 15:
			return new UserPageView(view);
		default:	//return booking on Map
			throw new IOException("Factory exception: view not found");
		}
	}
	
	public void create(ViewType view)  {
		try {
			FXMLLoader loader = new FXMLLoader(ViewType.getUrl(view));
		
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
	public Object getCurrentController() {
		return this.controller;
	}

	public void setCurrentController(Object controller) {
		this.controller = controller;
	}

}