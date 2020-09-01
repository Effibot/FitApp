package logic.viewcontroller;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import logic.bean.EmailBean;
import logic.bean.LoginBean;
import logic.controller.EmailController;
import logic.controller.LoginController;
import logic.controller.MainController;
import logic.factory.viewfactory.ViewType;

import java.io.IOException;

public class LoginViewController implements ViewController {
    private Container mainParent;

    @Override
    public void setMainParent(Container mainParent) {
        this.mainParent = mainParent;
    }

    @FXML
    private Button btnLogIn;
    @FXML
    private Button btnNoAcc;
    @FXML
    private Button btnSignUp;
    @FXML
    private ImageView btnBack;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPwd;
    @FXML
    private TextField tfEmailAddr;
    @FXML
    private Pane pnSignUp;
    @FXML
    private Pane pnSignIn;


    private MainController ctrl;
    private LoginController logCtrl;

    @FXML
    private void handleButtonEvent(ActionEvent event) throws IOException {
        if (event.getSource().equals(btnNoAcc)) {
            loginAnimation(true);
        }
        if (event.getSource().equals(btnSignUp)) {
            String email = tfEmailAddr.getText();
            if (!email.equals("")) {
                String pwd = String.valueOf(logCtrl.generateRandomDigits(8));
                logCtrl.getBean().setUsername(email);
                logCtrl.getBean().setPassword(pwd);
                logCtrl.signUp();
                EmailController emailController = EmailController.getSingletoneInstance();
                EmailBean emailBean = emailController.getEmailBean();
                emailBean.setEmail(email);
                emailBean.setPwd(pwd);
                EmailController.getSingletoneInstance().sendEmail();
                this.loginAnimation(false);
            }
        }
        if (event.getSource().equals(btnLogIn)) {
            loginTransitions();
        }
    }

    @FXML
    private void onEnter(KeyEvent key) throws IOException {
        if (key.getCode().equals(KeyCode.ENTER)) {
            loginTransitions();
        }
    }

    private void loginTransitions() {
        String username = tfUsername.getText();
        String password = tfPwd.getText();
        if (!username.equals("") && !password.equals("")) {
            logCtrl.getBean().setUsername(username);
            logCtrl.getBean().setPassword(password);
            if (logCtrl.checkAuthentication()) {
                //MainController.getInstance().setId(bean.getId());
//				ViewFactory factory = ViewFactory.getInstance();
//				View view;\
                mainParent.setUserId(logCtrl.getBean().getId());
                if (logCtrl.isSigningUp()) {
                    mainParent.loadView(ViewType.SIGNUP);
                    mainParent.setView(ViewType.SIGNUP);
                    //view = factory.createView();
                } else if (logCtrl.getBean().getType()) {
                    mainParent.loadView(ViewType.GYMPAGE);
                    mainParent.setView(ViewType.GYMPAGE);
                } else {
                    mainParent.loadView(ViewType.USERPAGE);
                    mainParent.setView(ViewType.USERPAGE);
                }
//				ctrl.replace(ctrl.getContainer(), view);
                //ctrl.getTopBox().getChildren().add(ctrl.getTopBar());
                mainParent.addTopBar();
            }
        }
    }

    @FXML
    private void handleMouseEvent(MouseEvent event) {
        if (event.getSource().equals(btnBack)) {
            loginAnimation(false);
        }
    }

    private void loginAnimation(boolean animation) {
        if (animation) {
            new ZoomOut(pnSignIn).play();
            pnSignIn.toBack();
            new ZoomIn(pnSignUp).play();
            pnSignUp.setVisible(true);
            pnSignUp.toFront();
            pnSignUp.setDisable(false);
        } else {
            new ZoomOut(pnSignUp).play();
            pnSignUp.toBack();
            new ZoomIn(pnSignIn).play();
            pnSignIn.toFront();
            pnSignUp.setDisable(true);
        }
    }

    @FXML
    void initialize() {
        assert pnSignUp != null : "fx:id=\"pnSignUp\" was not injected: check your FXML file 'scene.fxml'.";
        assert btnSignUp != null : "fx:id=\"btnSignUp\" was not injected: check your FXML file 'scene.fxml'.";
        assert tfEmailAddr != null : "fx:id=\"tfEmailAddr\" was not injected: check your FXML file 'scene.fxml'.";
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'scene.fxml'.";
        assert pnSignIn != null : "fx:id=\"pnSignIn\" was not injected: check your FXML file 'scene.fxml'.";
        assert tfUsername != null : "fx:id=\"tfUsername\" was not injected: check your FXML file 'scene.fxml'.";
        assert tfPwd != null : "fx:id=\"tfPwd\" was not injected: check your FXML file 'scene.fxml'.";
        assert btnLogIn != null : "fx:id=\"btnLogIn\" was not injected: check your FXML file 'scene.fxml'.";
        assert btnNoAcc != null : "fx:id=\"btnNoAcc\" was not injected: check your FXML file 'scene.fxml'.";

        //ctrl = MainController.getInstance();
        //MainBean bean = ctrl.getBean();
        LoginBean loginBean = new LoginBean();
        logCtrl = new LoginController(loginBean);
        //logCtrl = new LoginController();

    }
}
