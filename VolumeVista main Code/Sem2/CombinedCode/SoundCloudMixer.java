package JavaProjects.Sem2.CombinedCode;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class SoundCloudMixer {
    private WebView webView;
    private WebEngine webEngine;

    public VBox createSoundCloudMixer() {
        Label title = new Label("SoundCloud");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        webView = new WebView();
        webEngine = webView.getEngine();

        String embedHTML = "<html><body style='margin:0;padding:0;'>"
                + "<iframe id='sc-player' width='400' height='250' scrolling='no' frameborder='no' "
                + "src='https://w.soundcloud.com/player/?url=https%3A//soundcloud.com/kylie-minogue-official/cant-get-you-out-of-my-head&color=%23ff5500&auto_play=false'></iframe>"
                + "<script src='https://w.soundcloud.com/player/api.js'></script>"
                + "<script>"
                + "var widgetIframe = document.getElementById('sc-player');"
                + "var widget = SC.Widget(widgetIframe);"
                + "window.setVolume = function(vol) { widget.setVolume(vol); };"
                + "window.playTrack = function() { widget.play(); };"
                + "window.pauseTrack = function() { widget.pause(); };"
                + "window.muteTrack = function() { widget.setVolume(0); };"
                + "</script></body></html>";

        webEngine.loadContent(embedHTML);

        VBox slider = VolumeSlider.createSlider("SoundCloud Volume", webEngine, false);

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