// Import necessary JavaFX classes
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;

public class VolumeSlider {
    // Method to create a vertical volume slider
    public static VBox createVerticalSlider(String labelText, WebEngine webEngine, boolean isYouTube) {
        // Create a VBox layout with 10px spacing between elements
        VBox sliderBox = new VBox(10);
        // Align elements in the VBox to the center
        sliderBox.setAlignment(Pos.CENTER);

        // Create a label for the slider and set its style
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px;");

        // Create a vertical slider with a range from 0 to 100 and initial value of 50
        Slider slider = new Slider(0, 100, 50);
        slider.setOrientation(javafx.geometry.Orientation.HORIZONTAL);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(25);
        slider.setBlockIncrement(10);

        // Add a listener to the slider to handle volume changes
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue();
            if (isYouTube) {
                // Execute JavaScript to set the volume for YouTube player
                webEngine.executeScript("document.getElementById('player').contentWindow.postMessage(" +
                        "'{\"event\":\"command\",\"func\":\"setVolume\",\"args\":[" + volume + "]}', '*');");
            } else {
                // Execute JavaScript to set the volume for other players
                webEngine.executeScript("window.setVolume(" + volume + ");");
            }
        });

        // Add the slider and label to the VBox
        sliderBox.getChildren().addAll(slider, label);
        // Return the VBox as the root node
        return sliderBox;
    }
}