package logic.factory.viewfactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import logic.factory.alertfactory.AlertFactory;
import logic.view.*;

import java.io.IOException;

public class ViewFactory {
    private static ViewFactory instance = null;
    private Parent root;
    private Object controller;

    private ViewFactory() {
    }

    public static synchronized ViewFactory getInstance() {
        if (instance == null) {
            instance = new ViewFactory();
        }
        return instance;
    }

    public View createView(ViewType view) throws IOException {
        switch (view) {
            case LOGIN:
                return new LoginView(view);
            case GYMPAGE: // return gymPage
                return new GymPageView(view);
            case USERPAGE: // return userPage
                return new UserPageView(view);
            case BOOKINGFORM:    //return Training Form
                return new BookingFormView(view);
            case BOOKINGONMAP: //return BookingOnMap
                return new BookingOnMapView(view);
            case SIGNUP: //return SignUp view
                return new SignUpView(view);
            case EMAIL:     // return Email view
                return new EmailView(view);
            case REVIEW: // return review view
                return new ReviewView(view);
            case WRITEREVIEWS: // return write review view
                return new WriteReviewView(view);
            case MAINPOPUP: // return main popup view
                return new MainPopupView(view);
            case DELETEPOPUP:    //return delete popup view
                return new DeletePopupView(view);
            case CONTAINER:
                return new ContainerView(view);
            default:
                throw new IOException("Factory exception: view not found");
        }
    }

    public void create(ViewType view) {
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