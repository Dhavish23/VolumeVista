package gui;

import crossPlatform.AudioController;
import crossPlatform.AudioControllerFactory;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;

public class VolumeSlider {
    public static VBox createHorizontalSlider(String labelText, WebEngine webEngine, boolean isMuted) {
        // Create a label for the slider
        Label label = new Label(labelText);

        // Create a slider for volume control
        Slider slider = new Slider(0, 100, isMuted ? 0 : 50);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        // Add a listener to update the volume using the WebEngine
        if (webEngine != null) {
            slider.valueProperty().addListener((obs, oldVal, newVal) -> {
                String js = "setVolume(" + (newVal.doubleValue() / 100) + ");";
                webEngine.executeScript(js);
            });
        } else {
            // Use system volume control if WebEngine is not provided
            AudioController audioController = AudioControllerFactory.getAudioController();
            slider.setValue(audioController.getVolume());
            slider.valueProperty().addListener((obs, oldVal, newVal) -> {
                audioController.setVolume(newVal.intValue());
            });
        }

        // Create a VBox to hold the label and slider
        VBox vbox = new VBox(10, label, slider);
        vbox.setStyle("-fx-alignment: center;");

        return vbox;
    }
}