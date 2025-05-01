import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;

public class VolumeSlider {

    public static VBox createSlider(String labelText, WebEngine webEngine, boolean isYouTube) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px;");

        Slider slider = new Slider(0, 100, 50);
        slider.setOrientation(javafx.geometry.Orientation.HORIZONTAL);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(25);
        slider.setBlockIncrement(10);
        slider.setPrefWidth(150);

        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double volume = newVal.doubleValue();
            if (isYouTube) {
                webEngine.executeScript("document.getElementById('player').contentWindow.postMessage(" +
                        "'{\"event\":\"command\",\"func\":\"setVolume\",\"args\":[" + volume + "]}'" + ", '*');");
            } else {
                webEngine.executeScript("window.setVolume(" + volume + ");");
            }
        });

        container.getChildren().addAll(label, slider);
        return container;
    }
}
