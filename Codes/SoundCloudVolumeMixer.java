package JavaProjects;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SoundCloudVolumeMixer extends Application {

    private WebView soundCloudWebView; // WebView to embed SoundCloud player
    private WebEngine webEngine; // WebEngine to control JavaScript execution

    @Override
    public void start(Stage primaryStage) {
        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4;");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("SoundCloud Volume Mixer");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Set up the SoundCloud WebView
        soundCloudWebView = new WebView();
        webEngine = soundCloudWebView.getEngine();

        // Load SoundCloud embed iframe with Widget API
        String embedHTML = "<html><body style='margin:0;padding:0;'>"
                + "<iframe id='sc-widget' width='100%' height='166' "
                + "scrolling='no' frameborder='no' "
                + "src='https://w.soundcloud.com/player/?url=https%3A//soundcloud.com/kylie-minogue-official/cant-get-you-out-of-my-head&color=%23ff5500&auto_play=false'>"
                + "</iframe>"
                + "<script src='https://w.soundcloud.com/player/api.js'></script>"
                + "<script>"
                + "var widget = SC.Widget(document.getElementById('sc-widget'));"
                + "function setVolume(volume) { widget.setVolume(volume); }"
                + "</script>"
                + "</body></html>";
        webEngine.loadContent(embedHTML);

        // Create the volume slider
        VBox volumeSliderBox = createVolumeSlider();

        // Add all components to the layout
        root.getChildren().addAll(title, soundCloudWebView, volumeSliderBox);

        // Set up and display the application window
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setTitle("SoundCloud Volume Mixer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a slider to control the SoundCloud player's volume.
     *
     * @return A layout containing the slider and its label
     */
    private VBox createVolumeSlider() {
        VBox sliderBox = new VBox(10);
        sliderBox.setAlignment(Pos.CENTER);

        // Label for the volume slider
        Label label = new Label("Volume");
        label.setStyle("-fx-font-size: 14px;");

        // Slider to control SoundCloud volume
        Slider slider = new Slider(0, 100, 50); // Range: 0-100, default: 50
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(25);
        slider.setBlockIncrement(10);

        // Listener to update the SoundCloud volume via JavaScript
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue(); // Volume in percentage
            setSoundCloudVolume(volume);
        });

        sliderBox.getChildren().addAll(slider, label);
        return sliderBox;
    }

    /**
     * Sets the volume of the SoundCloud player using JavaScript.
     *
     * @param volume Volume level (0.0 to 100.0)
     */
    private void setSoundCloudVolume(double volume) {
        String script = "setVolume(" + volume + ");";
        webEngine.executeScript(script);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
