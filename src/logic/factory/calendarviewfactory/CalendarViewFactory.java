package logic.factory.calendarviewfactory;

import java.io.IOException;

import logic.view.calendarview.CalendarView;
import logic.view.calendarview.DeletePopupView;
import logic.view.calendarview.EmailView;
import logic.view.calendarview.FullDayView;
import logic.view.calendarview.MainPopupView;
import logic.view.calendarview.ReviewView;
import logic.view.calendarview.WriteReviewView;

public class CalendarViewFactory {
	private static CalendarViewFactory instance = null;
	private CalendarViewFactory() {}
	public static synchronized CalendarViewFactory getInstance() {
		if(instance == null) {
			instance = new CalendarViewFactory();
		}
		return instance;
	}
	public CalendarView createView(CalendarViewType view) throws IOException{
		switch(view.getType()) {
		case 0:	 // return Email view
			return new EmailView(view);
		case 1: // return review view
			return new ReviewView(view);
		case 2: // return write review view
			return new WriteReviewView(view);
		case 3: // return main popup view
			return new MainPopupView(view);
		case 4:	//return delete popup view
			return new DeletePopupView(view);
		case 5:	//return full day view
			return new FullDayView(view);
		default:
			throw new IOException("Factory exception: view not found");
		}
	}
}
