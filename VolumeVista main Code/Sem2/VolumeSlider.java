package JavaProjects.Sem2;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;

public class VolumeSlider {
    public static VBox createVerticalSlider(String labelText, WebEngine webEngine, boolean isYouTube) {
        VBox sliderBox = new VBox(10);
        sliderBox.setAlignment(Pos.CENTER);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px;");

        Slider slider = new Slider(0, 100, 50);
        slider.setOrientation(javafx.geometry.Orientation.VERTICAL);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(25);
        slider.setBlockIncrement(10);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue();
            if (isYouTube) {
                webEngine.executeScript("document.getElementById('player').contentWindow.postMessage(" +
                        "'{\"event\":\"command\",\"func\":\"setVolume\",\"args\":[" + volume + "]}', '*');");
            } else {
                webEngine.executeScript("window.setVolume(" + volume + ");");
            }
        });

        sliderBox.getChildren().addAll(slider, label);
        return sliderBox;
    }
}
