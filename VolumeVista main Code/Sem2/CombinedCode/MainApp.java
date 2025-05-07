package JavaProjects.Sem2.CombinedCode;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        new CombinedVolumeMixer().start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}