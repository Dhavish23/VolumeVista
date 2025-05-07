package JavaProjects.Sem2;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class VimeoMixer {
    private WebView vimeoWebView;
    private WebEngine vimeoWebEngine;

    public VBox createVimeoMixer() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Vimeo");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        vimeoWebView = new WebView();
        vimeoWebEngine = vimeoWebView.getEngine();

        String videoId = "76979871";
        String embedHTML = "<html><body style='margin:0;padding:0;'>" +
                "<iframe id='vimeo-player' width='400' height='250' " +
                "src='https://player.vimeo.com/video/" + videoId + "?title=0&byline=0&portrait=0' " +
                "frameborder='0' allowfullscreen></iframe>" +
                "<script src='https://player.vimeo.com/api/player.js'></script>" +
                "<script>var iframe = document.getElementById('vimeo-player');" +
                "var player = new Vimeo.Player(iframe);" +
                "window.setVolume = function(volume) { player.setVolume(volume / 100); };" +
                "</script></body></html>";
        vimeoWebEngine.loadContent(embedHTML);

        VBox vimeoSliderBox = VolumeSlider.createVerticalSlider("Vimeo Volume", vimeoWebEngine, false);

        root.getChildren().addAll(title, vimeoWebView, vimeoSliderBox);
        return root;
    }
}
