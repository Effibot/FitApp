package logic.factory.viewfactory;

import java.io.IOException;

import logic.view.BookingFormView;
import logic.view.BookingOnMapView;
import logic.view.GymPageView;
import logic.view.LoginView;
import logic.view.TrainingConfView;
import logic.view.TrainingFormView;
import logic.view.UserPageView;
import logic.view.View;

public class ViewFactory {
	private static ViewFactory instance = null;
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
		case 7: //return TraningCOnf
			return new BookingOnMapView(view);
		default:	//return booking on Map
			throw new IOException("Factory exception: view not found");
		}
	}
}