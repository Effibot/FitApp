package logic.factory.viewfactory;

import java.net.URL;

public enum ViewType {
	LOGIN(0, "/logic/fxml/Login.fxml"),
	GYMPAGE(1, "/logic/fxml/GymPage.fxml"),
	USERPAGE(2, "/logic/fxml/UserPage.fxml"),
	OFFERTRAININGFORM(3, "/logic/fxml/OfferTrainingForm.fxml"),
	BOOKINGFORM(4, "/logic/fxml/BookingForm.fxml"),
	OFFERTRAININGCONFIRMATION(5, "/logic/fxml/OfferTrainingConfirmation.fxml"),
	CARD(6, "/logic/fxml/Card.fxml"),
	BOOKINGONMAP(7, "/logic/fxml/BookingOnMap.fxml"),
	SIGNUP(8, "/logic/fxml/SignUp.fxml"),
	BOOKINGONCALENDAR(9, "/logic/fxml/BookingOnCalendar.fxml"),
	EMAIL(10,"/logic/fxml/EmailPopup.fxml"),
	REWIES(11,"/logic/fxml/reviewGym.fxml"),
	WRITEREWIES(12,"/logic/fxml/submitReview.fxml"),
	MAINPOPUP(13,"/logic/fxml/gymPopUp.fxml"),
	DELETEPOPUP(14,"/logic/fxml/popupDelete.fxml"),
	USERPOPUP(15,"/logic/fxml/userPopup.fxml");
	/* MAX SUBVIEW DIM: 900 x 522
	 * */

	private final int type;
	private final String path;
	private ViewType(int t, String p){
		this.type = t;
		this.path = p;
	}
	public static ViewType getView(int type) {
		for(ViewType subview : ViewType.values()) if(subview.type == type) return subview;
		throw new IllegalArgumentException("Unable to find the requested view("+type+")");
	}

	public int getType() {
		return type;
	}

	public static URL getUrl(ViewType subview){
		return ViewType.class.getResource(subview.path);
	}
}