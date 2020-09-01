package logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.viewfactory.ViewType;
import logic.viewcontroller.Container;
import logic.viewcontroller.ViewController;

import java.io.IOException;

public class View {
    private Pane root;
    private ViewController viewController;

    protected View(ViewType view) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewType.getUrl(view));
            setRoot(loader.load());
            viewController = loader.getController();
            viewController.setMainParent(Container.getInstance());
        } catch (IOException e) {
            AlertFactory.getInstance().createAlert(e);
        }
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public ViewController getViewController() {
        return viewController;
    }
}