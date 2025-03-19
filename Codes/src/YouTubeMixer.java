import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class YouTubeMixer {
    private WebView youTubeWebView;
    private WebEngine youTubeWebEngine;

    public VBox createYouTubeMixer() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("YouTube Player");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        youTubeWebView = new WebView();
        youTubeWebEngine = youTubeWebView.getEngine();

        // Load the YouTube player HTML
        youTubeWebEngine.loadContent(
            "<html>" +
            "<body>" +
            "<div id='player'></div>" +
            "<script>" +
            "var tag = document.createElement('script');" +
            "tag.src = 'https://www.youtube.com/iframe_api';" +
            "var firstScriptTag = document.getElementsByTagName('script')[0];" +
            "firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);" +
            "var player;" +
            "function onYouTubeIframeAPIReady() {" +
            "player = new YT.Player('player', {" +
            "height: '390'," +
            "width: '640'," +
            "videoId: ''," + // Initially empty
            "events: {" +
            " 'onReady': onPlayerReady" +
            "       }" +
            "    });" +
            "  }" +
            "  function onPlayerReady(event) {" +
            "    console.log('Player is ready');" +
            "  }" +
            "  function loadVideo(videoId) {" +
            "    player.loadVideoById(videoId);" +
            "  }" +
            "</script>" +
            "</body>" +
            "</html>"
        );

        // Input field for YouTube URL
        TextField urlInput = new TextField();
        urlInput.setPromptText("Enter YouTube URL");

        // Button to load a new video
        Button loadVideoButton = new Button("Load Video");
        loadVideoButton.setOnAction(e -> {
            String url = urlInput.getText().trim();
            String videoId = extractVideoIdFromUrl(url);
            if (videoId != null) {
                youTubeWebEngine.executeScript("loadVideo('" + videoId + "');");
            } else {
                System.out.println("Invalid YouTube URL");
            }
        });

        root.getChildren().addAll(title, youTubeWebView, urlInput, loadVideoButton);
        return root;
    }

    // Method to extract the video ID from a YouTube URL
    private String extractVideoIdFromUrl(String url) {
        try {
            if (url.contains("youtube.com/watch?v=")) {
                return url.split("v=")[1].split("&")[0];
            } else if (url.contains("youtu.be/")) {
                return url.split("youtu.be/")[1].split("\\?")[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if the URL is invalid
    }
}