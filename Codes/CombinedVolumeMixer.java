package JavaProjects;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class CombinedVolumeMixer extends Application {

    private WebView youTubeWebView; // YouTube WebView
    private WebView soundCloudWebView; // SoundCloud WebView
    private WebEngine youTubeWebEngine; // YouTube WebEngine
    private WebEngine soundCloudWebEngine; // SoundCloud WebEngine

    @Override
    public void start(Stage primaryStage) {
        // Main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4;");
        root.setAlignment(Pos.CENTER);

        // Title for the application
        Label title = new Label("YouTube and SoundCloud Volume Mixer");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // Horizontal layout for players
        HBox playersBox = new HBox(30);
        playersBox.setAlignment(Pos.CENTER);

        // YouTube Mixer
        VBox youTubeMixer = createYouTubeMixer();

        // SoundCloud Mixer
        VBox soundCloudMixer = createSoundCloudMixer();

        // Add players to the horizontal layout
        playersBox.getChildren().addAll(youTubeMixer, soundCloudMixer);

        // Add components to the main layout
        root.getChildren().addAll(title, playersBox);

        // Set up and display the application window
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("YouTube and SoundCloud Volume Mixer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the YouTube mixer layout.
     *
     * @return VBox containing YouTube mixer components
     */
    private VBox createYouTubeMixer() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("YouTube");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        youTubeWebView = new WebView();
        youTubeWebEngine = youTubeWebView.getEngine();

        // YouTube Embed HTML
        String videoId = "AOkR3FEOUPE"; // Replace with your YouTube video ID
        String playlistId = "RDAOkR3FEOUPE"; // Replace with your YouTube playlist ID
        String embedHTML = "<html><body style='margin:0;padding:0;overflow:hidden;'>"
                + "<iframe id='player' width='400' height='250' "
                + "src='https://www.youtube.com/embed/" + videoId
                + "?enablejsapi=1&list=" + playlistId + "&start_radio=1' "
                + "frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' "
                + "allowfullscreen></iframe>"
                + "</body></html>";
        youTubeWebEngine.loadContent(embedHTML);

        VBox youtubeSliderBox = createVerticalSlider("YouTube Volume", youTubeWebEngine, true);

        root.getChildren().addAll(title, youTubeWebView, youtubeSliderBox);
        return root;
    }

    /**
     * Creates the SoundCloud mixer layout.
     *
     * @return VBox containing SoundCloud mixer components
     */
    private VBox createSoundCloudMixer() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("SoundCloud");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        soundCloudWebView = new WebView();
        soundCloudWebEngine = soundCloudWebView.getEngine();

        // SoundCloud Embed HTML with Widget API integration
        String embedHTML = "<html><body style='margin:0;padding:0;'>"
                + "<iframe id='sc-player' width='400' height='250' "
                + "scrolling='no' frameborder='no' "
                + "src='https://w.soundcloud.com/player/?url=https%3A//soundcloud.com/kylie-minogue-official/cant-get-you-out-of-my-head&color=%23ff5500&auto_play=false'>"
                + "</iframe>"
                + "<script src='https://w.soundcloud.com/player/api.js'></script>"
                + "<script>"
                + "var widgetIframe = document.getElementById('sc-player');"
                + "var widget = SC.Widget(widgetIframe);"
                + "window.setVolume = function(volume) { widget.setVolume(volume); };"
                + "</script></body></html>";
        soundCloudWebEngine.loadContent(embedHTML);

        VBox soundCloudSliderBox = createVerticalSlider("SoundCloud Volume", soundCloudWebEngine, false);

        root.getChildren().addAll(title, soundCloudWebView, soundCloudSliderBox);
        return root;
    }

    /**
     * Creates a vertical slider for volume control.
     *
     * @param labelText Label text for the slider
     * @param webEngine WebEngine for JavaScript execution
     * @param isYouTube Whether the slider is for YouTube
     * @return VBox containing the vertical slider
     */
    private VBox createVerticalSlider(String labelText, WebEngine webEngine, boolean isYouTube) {
        VBox sliderBox = new VBox(10);
        sliderBox.setAlignment(Pos.CENTER);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px;");

        Slider slider = new Slider(0, 100, 50); // Range: 0-100, default: 50
        slider.setOrientation(javafx.geometry.Orientation.VERTICAL); // Vertical slider
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(25);
        slider.setBlockIncrement(10);

        // Listener to control volume
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue();
            if (isYouTube) {
                executeJavaScript(webEngine, "document.getElementById('player').contentWindow.postMessage(" +
                        "'{\"event\":\"command\",\"func\":\"setVolume\",\"args\":[" + volume + "]}', '*');");
            } else {
                executeJavaScript(webEngine, "window.setVolume(" + volume + ");");
            }
        });

        sliderBox.getChildren().addAll(slider, label);
        return sliderBox;
    }

    /**
     * Executes JavaScript code in the specified WebEngine.
     *
     * @param webEngine WebEngine instance
     * @param script    JavaScript code
     */
    private void executeJavaScript(WebEngine webEngine, String script) {
        webEngine.executeScript(script);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
