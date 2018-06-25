package PilotProject;

import PilotProject.Sample.ListChromosome;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Window/window.fxml"));

        BorderPane rootElement = loader.load();

        primaryStage.setScene(new Scene(rootElement){{
            getStylesheets().add(getClass().getResource("Window/application.css").toExternalForm());
        }});

        PilotProject.Window.Controller controller = loader.getController();
        controller.setSimpleAlgorithm(new ListChromosome(){{
            add(-2, 0);
            add(-1, -2);
            add(0, -1);
            add(2, 1);
        }}, controller);
        primaryStage.setOnCloseRequest(we -> controller.closeRequest());

        primaryStage.show();
    }
}