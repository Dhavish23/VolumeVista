import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class YouTubeMixer {
    private WebView youTubeWebView;
    private WebEngine youTubeWebEngine;

    public VBox createYouTubeMixer() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("YouTube");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        youTubeWebView = new WebView();
        youTubeWebEngine = youTubeWebView.getEngine();

        String videoId = "AOkR3FEOUPE";
        String playlistId = "RDAOkR3FEOUPE";
        String embedHTML = "<html><body style='margin:0;padding:0;overflow:hidden;'>" +
                "<iframe id='player' width='400' height='250' " +
                "src='https://www.youtube.com/embed/" + videoId +
                "?enablejsapi=1&list=" + playlistId + "&start_radio=1' " +
                "frameborder='0' allowfullscreen></iframe>" +
                "</body></html>";
        youTubeWebEngine.loadContent(embedHTML);

        VBox youtubeSliderBox = VolumeSlider.createVerticalSlider("YouTube Volume", youTubeWebEngine, true);

        root.getChildren().addAll(title, youTubeWebView, youtubeSliderBox);
        return root;
    }
}
