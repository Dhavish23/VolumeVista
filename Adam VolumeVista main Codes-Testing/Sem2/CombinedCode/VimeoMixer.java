package JavaProjects.Sem2.CombinedCode;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class VimeoMixer {
    private WebView webView;
    private WebEngine webEngine;

    public VBox createVimeoMixer() {
        Label title = new Label("Vimeo");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        webView = new WebView();
        webEngine = webView.getEngine();

        String embedHTML = "<html><body style='margin:0;padding:0;'>"
                + "<iframe id='vimeo-player' src='https://player.vimeo.com/video/76979871?api=1&player_id=vimeo-player'"
                + " width='400' height='250' frameborder='0' allow='autoplay; fullscreen'></iframe>"
                + "<script src='https://player.vimeo.com/api/player.js'></script>"
                + "<script>"
                + "var player = new Vimeo.Player(document.getElementById('vimeo-player'));"
                + "window.setVolume = function(vol) { player.setVolume(vol / 100); };"
                + "window.playTrack = function() { player.play(); };"
                + "window.pauseTrack = function() { player.pause(); };"
                + "window.muteTrack = function() { player.setVolume(0); };"
                + "</script></body></html>";

        webEngine.loadContent(embedHTML);

        VBox slider = VolumeSlider.createSlider("Vimeo Volume", webEngine, false);

        Button playButton = new Button("▶");
        Button pauseButton = new Button("⏸");
        Button muteButton = new Button("🔇");

        playButton.setOnAction(e -> webEngine.executeScript("window.playTrack();"));
        pauseButton.setOnAction(e -> webEngine.executeScript("window.pauseTrack();"));
        muteButton.setOnAction(e -> webEngine.executeScript("window.muteTrack();"));

        HBox controls = new HBox(10, playButton, pauseButton, muteButton);
        controls.setAlignment(Pos.CENTER);

        VBox card = new VBox(10, title, webView, slider, controls);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("card");

        return card;
    }

    public WebEngine getEngine() {
        return webEngine;
    }
}