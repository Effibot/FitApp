package logic.controller;

import logic.bean.BookingOnMapBean;

public class BookingOnMapController {
	private static BookingOnMapController instance = null;
	private BookingOnMapBean bookingOnMapBean;
	
	protected BookingOnMapController() {
		bookingOnMapBean = new BookingOnMapBean();
	}
	
	public BookingOnMapBean getBookingOnMapBean() {
		return bookingOnMapBean;
	}
	

	public static synchronized BookingOnMapController getSingletoneInstance() {
		if(BookingOnMapController.instance == null)
			BookingOnMapController.instance = new BookingOnMapController();
		return BookingOnMapController.instance;
	}
}
