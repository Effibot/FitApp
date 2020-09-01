package logic.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.bean.MainBean;
import logic.controller.MainController;
import logic.factory.viewfactory.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ContainerViewController implements Initializable, ViewController {
    Container mainParent;

    @Override
    public void setMainParent(Container mainParent) {
        this.mainParent = mainParent;
        Container.getInstance().setTopBar(topBar);
        Container.getInstance().setTopBox(topBox);
    }

    @FXML
    private HBox topMenu;

    @FXML
    private BorderPane container;

    @FXML
    private ImageView btnReduce;

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView logOutIcon;

    @FXML
    private BorderPane topBar;

    @FXML
    private VBox topBox;

    private MainController ctrl;
    private ViewFactory factory;

    @FXML
    private void onMouseClickedEvent(MouseEvent event) {
        if (event.getSource() == btnClose) {
            System.exit(0);
        }
        if (event.getSource() == btnReduce) {
            Stage stage = (Stage) container.getScene().getWindow();
            stage.setIconified(true);
        }
        if (event.getSource() == logOutIcon) {
            //try {
            mainParent.removeTopBar();
            //mainParent.logout();

            //mainParent.setView(ViewType.LOGIN);
            mainParent.logout();
            //ctrl.replace(ctrl.getContainer(), factory.createView(ViewType.LOGIN));
            //} catch (IOException e) {
            //	AlertFactory.getInstance().createAlert(e);
            //}
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ctrl = MainController.getInstance();
        factory = ViewFactory.getInstance();
        topBox.getChildren().remove(topBar);
        MainBean bean = new MainBean();
        bean.setContainer(container);
        bean.setTopBar(topBar);
        bean.setTopBox(topBox);
        ctrl.setBean(bean);
    }
}