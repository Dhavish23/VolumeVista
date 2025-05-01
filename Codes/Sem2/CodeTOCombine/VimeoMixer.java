import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class VimeoMixer {
    private WebView vimeoWebView;
    private WebEngine vimeoWebEngine;

    public VBox createVimeoMixer() {
        Label title = new Label("Vimeo");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        vimeoWebView = new WebView();
        vimeoWebEngine = vimeoWebView.getEngine();

        String embedHTML = "<html><body style='margin:0;padding:0;'>"
                + "<iframe id='vimeo-player' width='400' height='250' "
                + "src='https://player.vimeo.com/video/76979871?title=0&byline=0&portrait=0' frameborder='0' allowfullscreen></iframe>"
                + "<script src='https://player.vimeo.com/api/player.js'></script>"
                + "<script>var iframe = document.getElementById('vimeo-player');"
                + "var player = new Vimeo.Player(iframe);"
                + "window.setVolume = function(volume) { player.setVolume(volume / 100); };"
                + "window.playTrack = function() { player.play(); };"
                + "window.pauseTrack = function() { player.pause(); };"
                + "window.muteTrack = function() { player.setVolume(0); };"
                + "</script></body></html>";

        vimeoWebEngine.loadContent(embedHTML);

        VBox slider = VolumeSlider.createVerticalSlider("Vimeo Volume", vimeoWebEngine, false);

        Button playButton = new Button("▶");
        Button pauseButton = new Button("⏸");
        Button muteButton = new Button("🔇");

        playButton.setOnAction(e -> vimeoWebEngine.executeScript("window.playTrack();"));
        pauseButton.setOnAction(e -> vimeoWebEngine.executeScript("window.pauseTrack();"));
        muteButton.setOnAction(e -> vimeoWebEngine.executeScript("window.muteTrack();"));

        HBox controls = new HBox(10, playButton, pauseButton, muteButton);
        controls.setAlignment(Pos.CENTER);

        VBox card = new VBox(10, title, vimeoWebView, slider, controls);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("card");

        return card;
    }

    public WebEngine getEngine() {
        return vimeoWebEngine;
    }
}
