package org.example.demo;

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
import java.io.IOException;

public class HelloApplication extends Application {

    private WebView youTubeWebView; // YouTube WebView
    private WebView soundCloudWebView; // SoundCloud WebView
    private WebView vimeoWebView; // Vimeo WebView

    private WebEngine soundCloudWebEngine; // SoundCloud WebEngine
    private WebEngine vimeoWebEngine; // Vimeo WebEngine

    @Override
    public void start(Stage stage) throws IOException {
        // Main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4;");
        root.setAlignment(Pos.CENTER);

        // Title for the application
        Label title = new Label("YouTube, SoundCloud, and Vimeo Volume Mixer");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // Horizontal layout for players
        HBox playersBox = new HBox(30);
        playersBox.setAlignment(Pos.CENTER);

        // YouTube Mixer
        VBox youTubeMixer = createYouTubeMixer();

        // SoundCloud Mixer
        VBox soundCloudMixer = createSoundCloudMixer();

        // Vimeo Mixer
        VBox vimeoMixer = createVimeoMixer();

        // Add players to the horizontal layout
        playersBox.getChildren().addAll(youTubeMixer, soundCloudMixer, vimeoMixer);

        // Add components to the main layout
        root.getChildren().addAll(title, playersBox);

        // Set up and display the application window
        Scene scene = new Scene(root, 1200, 600);
        stage.setTitle("YouTube, SoundCloud, and Vimeo Volume Mixer");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createYouTubeMixer() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("YouTube");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        youTubeWebView = new WebView();
        WebEngine youTubeWebEngine = youTubeWebView.getEngine();

        String videoId = "AOkR3FEOUPE";
        String playlistId = "RDAOkR3FEOUPE";
        String embedHTML = "<html><body style='margin:0;padding:0;overflow:hidden;'>" +
                "<iframe id='player' width='400' height='250' " +
                "src='https://www.youtube.com/embed/" + videoId +
                "?enablejsapi=1&list=" + playlistId + "&start_radio=1' " +
                "frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' " +
                "allowfullscreen></iframe>" +
                "</body></html>";
        youTubeWebEngine.loadContent(embedHTML);

        VBox youtubeSliderBox = createVerticalSlider("YouTube Volume", youTubeWebEngine, true);

        root.getChildren().addAll(title, youTubeWebView, youtubeSliderBox);
        return root;
    }

    private VBox createSoundCloudMixer() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("SoundCloud");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        soundCloudWebView = new WebView();
        soundCloudWebEngine = soundCloudWebView.getEngine();

        String embedHTML = "<html><body style='margin:0;padding:0;'>" +
                "<iframe id='sc-player' width='400' height='250' " +
                "scrolling='no' frameborder='no' " +
                "src='https://w.soundcloud.com/player/?url=https%3A//soundcloud.com/kylie-minogue-official/cant-get-you-out-of-my-head&color=%23ff5500&auto_play=false'>" +
                "</iframe>" +
                "<script src='https://w.soundcloud.com/player/api.js'></script>" +
                "<script>" +
                "var widgetIframe = document.getElementById('sc-player');" +
                "var widget = SC.Widget(widgetIframe);" +
                "window.setVolume = function(volume) { widget.setVolume(volume); };" +
                "</script></body></html>";
        soundCloudWebEngine.loadContent(embedHTML);

        VBox soundCloudSliderBox = createVerticalSlider("SoundCloud Volume", soundCloudWebEngine, false);

        root.getChildren().addAll(title, soundCloudWebView, soundCloudSliderBox);
        return root;
    }

    private VBox createVimeoMixer() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Vimeo");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        vimeoWebView = new WebView();
        vimeoWebEngine = vimeoWebView.getEngine();

        String videoId = "76979871"; // Replace with your Vimeo video ID
        String embedHTML = "<html><body style='margin:0;padding:0;'>" +
                "<iframe id='vimeo-player' width='400' height='250' " +
                "src='https://player.vimeo.com/video/" + videoId + "?title=0&byline=0&portrait=0' " +
                "frameborder='0' allow='autoplay; fullscreen; picture-in-picture' allowfullscreen></iframe>" +
                "<script src='https://player.vimeo.com/api/player.js'></script>" +
                "<script>" +
                "var iframe = document.getElementById('vimeo-player');" +
                "var player = new Vimeo.Player(iframe);" +
                "window.setVolume = function(volume) { player.setVolume(volume / 100); };" +
                "</script></body></html>";
        vimeoWebEngine.loadContent(embedHTML);

        VBox vimeoSliderBox = createVerticalSlider("Vimeo Volume", vimeoWebEngine, false);

        root.getChildren().addAll(title, vimeoWebView, vimeoSliderBox);
        return root;
    }

    private VBox createVerticalSlider(String labelText, WebEngine webEngine, boolean isYouTube) {
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
                executeJavaScript(webEngine, "document.getElementById('player').contentWindow.postMessage(" +
                        "'{\"event\":\"command\",\"func\":\"setVolume\",\"args\":[" + volume + "]}', '*');");
            } else {
                executeJavaScript(webEngine, "window.setVolume(" + volume + ");");
            }
        });

        sliderBox.getChildren().addAll(slider, label);
        return sliderBox;
    }

    private void executeJavaScript(WebEngine webEngine, String script) {
        webEngine.executeScript(script);
    }

    public static void main(String[] args) {
        launch(args);
    }
}