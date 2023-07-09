package de.fk;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloGluon extends Application {

    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() throws Exception {
        appManager.addViewFactory(HOME_VIEW, () -> {

            StackPane pane = new StackPane();
//            pane.getChildren().add(swingNode);

//            stage.setTitle("Swing in JavaFX");
//            stage.setScene(new Scene(pane, 250, 150));
//            stage.show();
//
//            ImageView imageView = new ImageView(new Image(HelloGluon.class.getResourceAsStream("openduke.png")));
//            imageView.setFitHeight(200);
//            imageView.setPreserveRatio(true);
//
//            Label label = new Label("Hello, Notes222!");

            VBox root = new VBox(20, pane);
            root.setAlignment(Pos.CENTER);

            View view = new View(root);

            return view;
        });
    }

    private void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        appManager.start(stage);
    }

}