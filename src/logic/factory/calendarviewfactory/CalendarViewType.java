package logic.factory.calendarviewfactory;

import java.net.URL;


public enum CalendarViewType {
	EMAIL(0,"/logic/fxml/EmailPopup.fxml"),
	REWIES(1,"/logic/fxml/reviewGym.fxml"),
	WRITEREWIES(2,"/logic/fxml/submitReview.fxml"),
	MAINPOPUP(3,"/logic/fxml/popupUser.fxml"),
	DELETEPOPUP(4,"/logic/fxml/popupDelete.fxml"),
	FULLDAY(5,"/logic/fxml/FullDay.fxml");
	
	
	
	
	
	private final int type;
	private final String path;
	private CalendarViewType(int t,  String p){
		this.type = t;
		this.path = p;
	}
	public static CalendarViewType getView(int type) {
		for(CalendarViewType subview : CalendarViewType.values()) if(subview.type == type) return subview;
		throw new IllegalArgumentException("Unable to find the requested view("+type+")");
	}

	public int getType() {
		return type;
	}

	public static URL getUrl(CalendarViewType subview){
		return CalendarViewType.class.getResource(subview.path);
	}
}
