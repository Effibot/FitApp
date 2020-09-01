package logic.factory.viewfactory;

import java.net.URL;

public enum ViewType {
    LOGIN(0, "/logic/fxml/Login.fxml"),
    GYMPAGE(1, "/logic/fxml/GymPage.fxml"),
    USERPAGE(2, "/logic/fxml/UserPage.fxml"),
    BOOKINGFORM(3, "/logic/fxml/BookingForm.fxml"),
    BOOKINGONMAP(4, "/logic/fxml/BookingOnMap.fxml"),
    SIGNUP(5, "/logic/fxml/SignUp.fxml"),
    EMAIL(6, "/logic/fxml/EmailPopup.fxml"),
    REVIEW(7, "/logic/fxml/reviewGym.fxml"),
    WRITEREVIEWS(8, "/logic/fxml/submitReview.fxml"),
    MAINPOPUP(9, "/logic/fxml/gymPopUp.fxml"),
    DELETEPOPUP(10, "/logic/fxml/popupDelete.fxml"),
    USERPOPUP(11, "/logic/fxml/userPopup.fxml"),
    CONTAINER(12, "/logic/fxml/Container.fxml");
    /* MAX SUBVIEW DIM: 900 x 522
     * */

    private final int type;
    private final String path;

    private ViewType(int t, String p) {
        this.type = t;
        this.path = p;
    }

    public static ViewType getView(int type) {
        for (ViewType subview : ViewType.values()) if (subview.type == type) return subview;
        throw new IllegalArgumentException("Unable to find the requested view(" + type + ")");
    }

    public int getType() {
        return type;
    }

    public static URL getUrl(ViewType subview) {
        return ViewType.class.getResource(subview.path);
    }
}