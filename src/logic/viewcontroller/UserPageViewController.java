package logic.viewcontroller;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import com.calendarfx.view.page.MonthPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import logic.bean.UserBean;
import logic.controller.MainController;
import logic.controller.UserPageController;
import logic.facade.calendar.CalendarFacade;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;
import logic.model.entity.User;

import java.util.ResourceBundle;

public class UserPageViewController implements ViewController {
    Container mainParent;
    UserPageController controller;
    private User user;

    @Override
    public void setMainParent(Container mainParent) {
        this.mainParent = mainParent;
        controller = new UserPageController(new UserBean(this.mainParent.getUserId()));
        calendarSetUp();
        user = controller.getBean().getUser();
        sideUsername.setText(user.getName());
        sideStreet.setText(user.getMyPosition());
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private AnchorPane userAnchor;

    @FXML
    private ImageView userIcon;

    @FXML
    private Button bookSession;

    @FXML
    private ImageView sideUserIcon;

    @FXML
    private Label sideUsername;

    @FXML
    private Label sideStreet;

    @FXML
    private Pane calendarBox;

    @FXML
    private Button openCalendar;

    private MonthPage mPage;

    private MainController ctrl = MainController.getInstance();
    private ViewFactory factory = ViewFactory.getInstance();

    @FXML
    public void bookSession(ActionEvent event) {
        if (event.getSource().equals(bookSession)) {
//			try {
//				ctrl.replace(ctrl.getContainer(), factory.createView(ViewType.BOOKINGFORM));
//			} catch (IOException e) {
//				AlertFactory.getInstance().createAlert(e);
//			}
            mainParent.loadView(ViewType.BOOKINGFORM);
            mainParent.setView(ViewType.BOOKINGFORM);
        }
    }

    @FXML
    private void showCalendar(ActionEvent event) {
        if (event.getSource().equals(openCalendar)) {
            if (!calendarBox.isVisible()) {
                new ZoomIn(calendarBox).play();
                calendarBox.setVisible(true);
                calendarBox.toFront();
                openCalendar.setText("Close Calendar");
            } else {
                new ZoomOut(calendarBox).play();
                calendarBox.toBack();
                openCalendar.setText("Open Calendar");
                calendarBox.setVisible(false);
                // calendarBox.setManaged(true);
            }
        }
    }

    private void calendarSetUp() {
        CalendarFacade calendarFacade = new CalendarFacade(false);
        mPage = calendarFacade.initializeCalendar(controller.getBean().getUserId());
        calendarBox.getChildren().add(mPage);
        calendarBox.setVisible(false);
        mPage.getCalendars().get(0).addEventHandler(calendarFacade.getEventHandler());

    }

    @FXML
    void initialize() {
        assert userAnchor != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'UserPage.fxml'.";
        assert userIcon != null : "fx:id=\"userIcon\" was not injected: check your FXML file 'UserPage.fxml'.";
        assert bookSession != null : "fx:id=\"bookSession\" was not injected: check your FXML file 'UserPage.fxml'.";
        assert sideUserIcon != null : "fx:id=\"sideUserIcon\" was not injected: check your FXML file 'UserPage.fxml'.";
        assert sideUsername != null : "fx:id=\"sideUsername\" was not injected: check your FXML file 'UserPage.fxml'.";

        // da mettere in UserPageController
        // User user = UserDAO.getInstance().getUserEntity(ctrl.getId());
        // List<Integer> userSession =
        // SessionDAO.getInstance().getBookedSessionById(user.getId());

        // List<Session> s = new ArrayList<>();
        // for(Integer i : userSession){
        // s.add(SessionDAO.getInstance().getBookedSessionEntity(i));
        // }
        // user.setBookedSession(s);
        /////

        // User user = bean.getUser();

//		sideUsername.setText(user.getName());
//		sideStreet.setText(user.getMyPosition());
        System.out.println("loading user");

    }
}