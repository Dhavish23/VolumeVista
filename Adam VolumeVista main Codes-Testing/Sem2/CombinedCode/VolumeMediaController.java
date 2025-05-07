package JavaProjects.Sem2.CombinedCode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VolumeMediaController extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #1c1c1c;");
        root.setAlignment(Pos.CENTER_LEFT);

        Button backButton = new Button("⬅ Back");
        backButton.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            try {
                CombinedVolumeMixer combinedPage = new CombinedVolumeMixer();
                combinedPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Label title = new Label("Volume & Media Controller");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Master volume control
        Label masterLabel = new Label("Master Volume");
        masterLabel.setStyle("-fx-text-fill: white;");
        Slider masterSlider = new Slider(0, 100, 72);
        Label masterPercent = new Label("72%");
        masterPercent.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        masterSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            masterPercent.setText(String.format("%.0f%%", newVal.doubleValue()));
            setSystemVolume(newVal.doubleValue()); // Connect slider to real system volume
        });

        HBox masterRow = new HBox(10, masterLabel, masterSlider, masterPercent);
        masterRow.setAlignment(Pos.CENTER_LEFT);

        // Chrome volume control
        Label chromeLabel = new Label("Chrome");
        chromeLabel.setStyle("-fx-text-fill: white;");
        Slider chromeSlider = new Slider(0, 100, 50);
        HBox chromeRow = new HBox(10, chromeLabel, chromeSlider);
        chromeRow.setAlignment(Pos.CENTER_LEFT);

        // Spotify volume control
        Label spotifyLabel = new Label("Spotify");
        spotifyLabel.setStyle("-fx-text-fill: white;");
        Slider spotifySlider = new Slider(0, 100, 50);
        HBox spotifyRow = new HBox(10, spotifyLabel, spotifySlider);
        spotifyRow.setAlignment(Pos.CENTER_LEFT);

        // System volume control
        Label systemLabel = new Label("System");
        systemLabel.setStyle("-fx-text-fill: white;");
        Slider systemSlider = new Slider(0, 100, 50);
        HBox systemRow = new HBox(10, systemLabel, systemSlider);
        systemRow.setAlignment(Pos.CENTER_LEFT);

        // Mode buttons
        Label modeLabel = new Label("Active Mode");
        modeLabel.setStyle("-fx-text-fill: white;");
        Button gamingMode = new Button("Gaming Mode");
        Button musicMode = new Button("Music Mode");
        Button movieMode = new Button("Movie Mode");

        gamingMode.setStyle("-fx-background-color: #333; -fx-text-fill: white;");
        musicMode.setStyle("-fx-background-color: #333; -fx-text-fill: white;");
        movieMode.setStyle("-fx-background-color: #333; -fx-text-fill: white;");

        VBox modeButtons = new VBox(10, gamingMode, musicMode, movieMode);
        modeButtons.setAlignment(Pos.CENTER_LEFT);

        HBox modeRow = new HBox(30, modeLabel, modeButtons);
        modeRow.setAlignment(Pos.CENTER_LEFT);

        Button muteButton = new Button("\uD83D\uDD0A");
        muteButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 22px;");

        VBox allControls = new VBox(15,
            backButton,
            title,
            masterRow,
            chromeRow,
            spotifyRow,
            systemRow,
            modeRow,
            muteButton
        );
        allControls.setAlignment(Pos.TOP_LEFT);

        root.getChildren().add(allControls);

        Scene scene = new Scene(root, 400, 320);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Volume & Media Controller");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setSystemVolume(double percent) {
        try {
            // Convert percent to scale for nircmd (65535 is the max for system volume)
            int volumeValue = (int) (65535 * (percent / 100.0));  // Max volume value is 65535
            String command = "C:\\Cnircmd\\nircmd.exe setsysvolume " + volumeValue;  // Updated path to C:\Cnircmd
            Runtime.getRuntime().exec(command);  // Execute the nircmd command
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
