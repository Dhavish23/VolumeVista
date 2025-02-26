import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class SoundCloudMixer {
    private WebView soundCloudWebView;
    private WebEngine soundCloudWebEngine;

    public VBox createSoundCloudMixer() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("SoundCloud");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        soundCloudWebView = new WebView();
        soundCloudWebEngine = soundCloudWebView.getEngine();

        String embedHTML = "<html><body style='margin:0;padding:0;'>" +
                "<iframe id='sc-player' width='400' height='250' " +
                "scrolling='no' frameborder='no' " +
                "src='https://w.soundcloud.com/player/?url=https%3A//soundcloud.com/kylie-minogue-official/cant-get-you-out-of-my-head&color=%23ff5500&auto_play=false'>"
                +
                "</iframe>" +
                "<script src='https://w.soundcloud.com/player/api.js'></script>" +
                "<script>var widgetIframe = document.getElementById('sc-player');" +
                "var widget = SC.Widget(widgetIframe);" +
                "window.setVolume = function(volume) { widget.setVolume(volume); };" +
                "</script></body></html>";
        soundCloudWebEngine.loadContent(embedHTML);

        VBox soundCloudSliderBox = VolumeSlider.createVerticalSlider("SoundCloud Volume", soundCloudWebEngine, false);

        root.getChildren().addAll(title, soundCloudWebView, soundCloudSliderBox);
        return root;
    }
}
