package gui;

import crossPlatform.AudioController;
import crossPlatform.AudioControllerFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class VimeoMixer {

    private final AudioController audioController;

    public VimeoMixer() {
        this.audioController = AudioControllerFactory.getAudioController();
    }

    public VBox createVimeoMixer() {
        // Create a WebView for the browser
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");

        // Create a volume slider
        Slider volumeSlider = new Slider(0, 100, audioController.getVolume());
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            audioController.setVolume(newVal.intValue());
        });

        // Create a label for the volume slider
        Label volumeLabel = new Label("Volume Control");

        // Layout for the volume slider
        VBox volumeControl = new VBox(10, volumeLabel, volumeSlider);
        volumeControl.setAlignment(Pos.CENTER);

        // Layout for the browser and volume control
        HBox layout = new HBox(20, webView, volumeControl);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Main container
        VBox root = new VBox(20, layout);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        return root;
    }
}