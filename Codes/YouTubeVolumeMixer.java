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

public class YouTubeVolumeMixer extends Application {

    private WebView youTubeWebView; // Display YouTube content
    private WebEngine webEngine; // Handle JavaScript execution

    @Override
    public void start(Stage primaryStage) {
        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4;");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("YouTube Volume Mixer");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Set up the YouTube video player
        youTubeWebView = new WebView();
        webEngine = youTubeWebView.getEngine();

        // This is the youtube application where we have the video ID and we can did the
        // size
        String videoId = "AOkR3FEOUPE"; // Video ID
        String playlistId = "RDAOkR3FEOUPE"; // Playlist ID
        String embedHTML = "<html><body style='margin:0;padding:0;overflow:hidden;'>"
                + "<iframe id='player' width='40%' height='80%' "
                + "src='https://www.youtube.com/embed/" + videoId
                + "?enablejsapi=1&list=" + playlistId + "&start_radio=1' "
                + "frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' "
                + "allowfullscreen></iframe>"
                + "</body></html>";
        webEngine.loadContent(embedHTML);

        // Create YouTube volume slider
        VBox youtubeSliderBox = createYouTubeVolumeSlider();

        // Add all components to the main layout
        root.getChildren().addAll(title, youTubeWebView, youtubeSliderBox);

        // Set up and display the application window
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("YouTube Volume Mixer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a slider to control YouTube volume.
     * 
     * @return A layout with the slider and label
     */
    private VBox createYouTubeVolumeSlider() {
        VBox sliderBox = new VBox(10);
        sliderBox.setAlignment(Pos.CENTER);

        // Label for YouTube volume slider
        Label label = new Label("YouTube Volume");
        label.setStyle("-fx-font-size: 14px;");

        // Slider to adjust YouTube player volume
        Slider slider = new Slider(0, 100, 50); // Range: 0-100, default: 50
        slider.setOrientation(javafx.geometry.Orientation.VERTICAL);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(25);
        slider.setBlockIncrement(10);

        // Listener to control YouTube volume
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue();
            executeJavaScript("document.getElementById('player').contentWindow.postMessage(" +
                    "'{\"event\":\"command\",\"func\":\"setVolume\",\"args\":[" + volume + "]}', '*');");
        });

        sliderBox.getChildren().addAll(slider, label);
        return sliderBox;
    }

    /**
     * Executes JavaScript code in the YouTube WebView.
     * 
     * @param script JavaScript command
     */
    private void executeJavaScript(String script) {
        webEngine.executeScript(script);
    }

    public static void main(String[] args) {
        // Start the application
        launch(args);
    }
}
