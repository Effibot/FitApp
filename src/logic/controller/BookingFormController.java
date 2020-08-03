package logic.controller;

import logic.bean.BookingOnMapBean;

public class BookingFormController {
	private static BookingFormController instance = null;
	private BookingOnMapBean bookingOnMapBean;

	public static synchronized BookingFormController getSingletoneInstance() {
		if(BookingFormController.instance == null)
			BookingFormController.instance = new BookingFormController();
		return BookingFormController.instance;
	}
	
	protected BookingFormController() {
		bookingOnMapBean = new BookingOnMapBean();
	}
	
	public BookingOnMapBean getBookingOnMapBean() {
		return bookingOnMapBean;
	}

	public void setBookingOnMapBean(BookingOnMapBean bookingOnMapBean) {
		this.bookingOnMapBean = bookingOnMapBean;
	}
}
