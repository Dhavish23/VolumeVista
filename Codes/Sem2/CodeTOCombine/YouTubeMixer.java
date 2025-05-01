import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class YouTubeMixer {
    private WebView youTubeWebView;
    private WebEngine youTubeWebEngine;

    public VBox createYouTubeMixer() {
        Label title = new Label("YouTube");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        youTubeWebView = new WebView();
        youTubeWebEngine = youTubeWebView.getEngine();

        String videoId = "AOkR3FEOUPE";
        String playlistId = "RDAOkR3FEOUPE";
        String embedHTML = "<html><body style='margin:0;padding:0;overflow:hidden;'>"
                + "<iframe id='player' width='400' height='250' "
                + "src='https://www.youtube.com/embed/" + videoId
                + "?enablejsapi=1&list=" + playlistId + "&start_radio=1' "
                + "frameborder='0' allowfullscreen></iframe>"
                + "</body></html>";
        youTubeWebEngine.loadContent(embedHTML);

        VBox slider = VolumeSlider.createVerticalSlider("YouTube Volume", youTubeWebEngine, true);

        Button playButton = new Button("▶");
        Button pauseButton = new Button("⏸");
        Button muteButton = new Button("🔇");

        
        playButton.setOnAction(e -> youTubeWebEngine.executeScript(
                "document.getElementById('player').contentWindow.postMessage('{\"event\":\"command\",\"func\":\"playVideo\",\"args\":\"\"}', '*');"));
        pauseButton.setOnAction(e -> youTubeWebEngine.executeScript(
                "document.getElementById('player').contentWindow.postMessage('{\"event\":\"command\",\"func\":\"pauseVideo\",\"args\":\"\"}', '*');"));
        muteButton.setOnAction(e -> youTubeWebEngine.executeScript(
                "document.getElementById('player').contentWindow.postMessage('{\"event\":\"command\",\"func\":\"mute\",\"args\":\"\"}', '*');"));

        HBox controls = new HBox(10, playButton, pauseButton, muteButton);
        controls.setAlignment(Pos.CENTER);

        VBox card = new VBox(10, title, youTubeWebView, slider, controls);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("card");

        return card;
    }

    public WebEngine getEngine() {
        return youTubeWebEngine;
    }
}
