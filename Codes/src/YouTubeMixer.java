import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class YouTubeMixer {
    private WebView youTubeWebView;
    private WebEngine youTubeWebEngine;
    private static final String API_KEY = "AIzaSyDsRAQMYF_oZc0rYgAzFF9mJcRvf86t0-M";
    private String currentVideoId;

    // Method to create the YouTube mixer ui
    public VBox createYouTubeMixer() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        // The title of the YouTube mixer
        Label title = new Label("YouTube");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // initialize WebView and WebEngine
        youTubeWebView = new WebView();
        youTubeWebEngine = youTubeWebView.getEngine();

        // Load the youTube URL
        String initialURL = "https://www.youtube.com";
        youTubeWebEngine.load(initialURL);

        // Add listener to detect when the page has loaded
        youTubeWebEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) youTubeWebEngine.executeScript("window");
                window.setMember("java", new JavaBridge());
                youTubeWebEngine.executeScript(
                    "window.addEventListener('load', function() {" +
                    "  var videoId = new URL(window.location.href).searchParams.get('v');" +
                    "  if (videoId) {" +
                    "    java.setVideoId(videoId);" +
                    "  }" +
                    "});"
                );
            }
        });

        // Create volume slider for YouTube
        VBox youtubeSliderBox = VolumeSlider.createVerticalSlider("YouTube Volume", youTubeWebEngine, true);

        // Pause button to pause the video
        Button pauseButton = new Button("Pause Video");
        pauseButton.setOnAction(e -> youTubeWebEngine.executeScript("if (typeof player !== 'undefined') player.pauseVideo();"));

        // Add components to the root VBox
        root.getChildren().addAll(title, youTubeWebView, youtubeSliderBox, pauseButton);
        return root;
    }

    // JavaScript bridge class to communicate with the web page
    public class JavaBridge {
        public void setVideoId(String videoId) {
            currentVideoId = videoId;
            String videoTitle = fetchVideoTitle(videoId);
            javafx.application.Platform.runLater(() -> {
                Label title = new Label("YouTube: " + videoTitle);
                title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            });
            youTubeWebEngine.executeScript(
                "var tag = document.createElement('script');" +
                "tag.src = 'https://www.youtube.com/iframe_api';" +
                "var firstScriptTag = document.getElementsByTagName('script')[0];" +
                "firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);" +
                "var player;" +
                "function onYouTubeIframeAPIReady() {" +
                "  player = new YT.Player('player', {" +
                "    videoId: '" + videoId + "'," +
                "    events: {" +
                "      'onReady': onPlayerReady" +
                "    }" +
                "  });" +
                "}" +
                "function onPlayerReady(event) {" +
                "  event.target.playVideo();" +
                "}" +
                "function pauseVideo() {" +
                "  player.pauseVideo();" +
                "}" +
                "onYouTubeIframeAPIReady();"
            );
        }
    }

    // Method to get the videos title using YouTube API 
    private String fetchVideoTitle(String videoId) {
        try {
            String url = "https://www.googleapis.com/youtube/v3/videos?id=" + videoId + "&key=" + API_KEY + "&part=snippet";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject json = new JSONObject(content.toString());
            return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title");
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Title";
        }
    }
}