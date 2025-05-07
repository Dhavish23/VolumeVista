import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        Engine engine = Engine.newInstance(
                EngineOptions.newBuilder(RenderingMode.HARDWARE_ACCELERATED)
                        .licenseKey("OK6AEKNYF37GRFRN5NXJ7E036DQYVLMYXV6YMUYXDHTA6B8QDT7STA65INM69D0N7E9FP3V8R4ZWI47KV3CB4TL5L6NK5IMWS7J0WU4NZGG0GVYJ1K3MH3BE7VTU3OFB822AB30WSQ17BH4QC")
                        .build());

        Browser browser = engine.newBrowser();
        BrowserView view = BrowserView.newInstance(browser);

        StackPane root = new StackPane(view);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("JxBrowser in JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        browser.navigation().loadUrl("https://www.google.com");
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class VolumeSlider {

        // Method to create a horizontal volume slider
        public static VBox createHorizontalSlider(String labelText, WebEngine webEngine, boolean isYouTube) {
            VBox sliderBox = new VBox(10); // Vertical box to hold the slider and label
            sliderBox.setAlignment(Pos.CENTER); // Center the elements in the VBox

            // Label for the slider
            Label label = new Label(labelText);
            label.setStyle("-fx-font-size: 14px;"); // Set font size for the label

            // Create a horizontal slider
            Slider slider = new Slider(0, 100, 50); // The sliders max and min volumes
            slider.setOrientation(javafx.geometry.Orientation.HORIZONTAL); // Set slider orientation to horizontal
            slider.setShowTickMarks(true); // Show tick marks on the slider
            slider.setShowTickLabels(true); // Show tick labels on the slider
            slider.setMajorTickUnit(25); // Set major tick unit to 25
            slider.setBlockIncrement(10); // Set block increment to 10

            // Add a listener to handle slider value changes
            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                double volume = newValue.doubleValue(); // Get the new slider value as volume
                if (isYouTube) {
                    // If the slider is for YouTube, send a message to the YouTube player to set the volume
                    webEngine.executeScript("document.getElementById('player').contentWindow.postMessage(" +
                            "'{\"event\":\"command\",\"func\":\"setVolume\",\"args\":[" + volume + "]}', '*');");
                } else {
                    // If the slider is for other players (e.g., SoundCloud, Vimeo), call the setVolume function
                    webEngine.executeScript("window.setVolume(" + volume + ");");
                }
            });

            // Add the slider and label to the VBox
            sliderBox.getChildren().addAll(slider, label);

            // Return the VBox containing the slider and label
            return sliderBox;
        }
    }
}
