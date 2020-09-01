package logic.viewcontroller;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.factory.alertfactory.AlertFactory;
import logic.factory.viewfactory.ViewFactory;
import logic.factory.viewfactory.ViewType;
import logic.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Container extends Pane {

    private static final Logger LOGGER = LoggerFactory.getLogger(Container.class);
    private BorderPane main;
    private BorderPane topBar;
    private VBox topBox;

    // Map to holds the screen to be displayed
    private Map<ViewType, Pane> viewMap;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Save the current user id
    private int userId;
    // Save the current User Entity

    protected Container() {
        super();
        userId = 0;
        viewMap = new EnumMap<>(ViewType.class);
    }

    private static Container instance = null;

    public static synchronized Container getInstance() {
        if (instance == null) {
            instance = new Container();
        }
        return instance;
    }

    /*
     * Loads the fxml file, get the parent node and add it to the
     * screen collection. Finally, injects it to the controller.
     */

    public void loadView(ViewType type) {
        try {
            View view = ViewFactory.getInstance().createView(type);
            addView(type, view.getRoot());
            //view.getViewController().setMainParent(this);
        } catch (IOException e) {
            AlertFactory.getInstance().createAlert(e);
        }
    }

    private void addView(ViewType view, Pane rootNode) {
        viewMap.put(view, rootNode);
        //LOGGER.info(String.valueOf(viewMap.size()));
    }

    public Node getView(ViewType type) {
        return viewMap.get(type);
    }

    /* this method initialize the application's graphics.
     * */
    public void initView() {
        if (getChildren().isEmpty() && viewMap.isEmpty()) {  // Assuring that no view is loaded.
            // load the container view
            loadView(ViewType.CONTAINER);
            // get the container parent
            main = (BorderPane) viewMap.get(ViewType.CONTAINER);
            // adding to the scene graph the parent
            getChildren().add(main);
            LOGGER.info(main.getParent().toString());
            new FadeIn(getChildren().get(0));
            // load and set the login view in the application.
            loadView(ViewType.LOGIN);
            setView(ViewType.LOGIN);
            LOGGER.info(main.getChildren().toString());
        } /*else {
            throw InitGraphicsException();
        }*/
    }

    /*
    This method replace the main node of the BorderPane in Container.fxml
    with the view selected by the user.
     */
    public void setView(ViewType type) {
        Node currChild = viewMap.get(type);
        //LOGGER.info(currChild.getId());
        // checks if the selected view is loaded.
        if (currChild != null) {
            // checks if there is another loaded view.
            if (!main.getChildren().isEmpty()) { // there is another.
                // save the oldChild to make animation and to remove it easily.
                Node oldChild = main.getChildren().get(2);
                LOGGER.info(oldChild.getId());
                new FadeOut(oldChild).play();
                main.setCenter(null);
                // LOGGER.info(main.getChildren().toString());
                // add the current child selected by the user.
                main.setCenter(currChild);
                new FadeIn(main.getChildren().get(2)).play();
//                LOGGER.info(main.getChildren().toString());
//                LOGGER.info(main.getChildren().get(2).getId());
            } else { // there isn't.
                main.setCenter(currChild);
                new FadeIn(main.getChildren().get(0)).play();
            }
        }
    }


    public void addTopBar() {
        topBox.getChildren().add(topBar);
    }

    public void removeTopBar() {
        topBox.getChildren().remove(topBar);
    }

    public boolean unloadView(ViewType type) {
        if (viewMap.remove(type) == null) {
            LOGGER.info("the view {} doesn't exists", type);
            return false;
        } else {
            return true;
        }
    }

    public void logout() {
        getChildren().clear();
        viewMap.clear();
        initView();
    }

    public static List<Node> getAllNodes(Parent p) {
        ArrayList<Node> nodes = new ArrayList<>();
        addAllDescendents(p, nodes);
        return nodes;
    }

    private static void addAllDescendents(Parent p, ArrayList<Node> nodes) {
        for (Node n : p.getChildrenUnmodifiable()) {
            nodes.add(n);
            if (n instanceof Parent)
                addAllDescendents((Parent) n, nodes);
        }
    }

    public BorderPane getTopBar() {
        return topBar;
    }

    public VBox getTopBox() {
        return topBox;
    }

    public void setTopBar(BorderPane topBar) {
        this.topBar = topBar;
    }

    public void setTopBox(VBox topBox) {
        this.topBox = topBox;
    }
}
