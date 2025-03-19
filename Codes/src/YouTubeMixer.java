import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class YouTubeMixer {
    private WebView youTubeWebView; // WebView to display the YouTube player
    private WebEngine youTubeWebEngine; // WebEngine to control the WebView content

    public VBox createYouTubeMixer() {
        // Main container for the YouTube mixer
        VBox root = new VBox(20); // Adds spacing between the elements
        root.setAlignment(Pos.CENTER); // Centers all elements

        // Title for the YouTube mixer
        Label title = new Label("YouTube Player");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Initialize the WebView and WebEngine
        youTubeWebView = new WebView();
        youTubeWebEngine = youTubeWebView.getEngine();

        // Load the YouTube player HTML into the WebView
        youTubeWebEngine.loadContent(
            "<html>" +
            "<body style='margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100%;'>" +
            "<div id='player'></div>" + // Placeholder for player
            "<script>" +
            "var tag = document.createElement('script');" + // Load the YouTube IFrame API
            "tag.src = 'https://www.youtube.com/iframe_api';" +
            "var firstScriptTag = document.getElementsByTagName('script')[0];" +
            "firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);" +
            "var player;" +
            "function onYouTubeIframeAPIReady() {" + // Initialize the YouTube player
            "player = new YT.Player('player', {" +
            "height: '390'," + // Player height
            "width: '640'," + // Player width
            "videoId: ''," + // Initially no video is loaded
            "events: {" +
            " 'onReady': onPlayerReady" + // Triggers an event when the player is ready
            "       }" +
            "    });" +
            "  }" +
            "  function onPlayerReady(event) {" +
            "    console.log('Player is ready');" + // Log when the player is ready
            "  }" +
            "  function loadVideo(videoId) {" +
            "    player.loadVideoById(videoId);" + // Load a video by its ID
            "  }" +
            "  function setVolume(volume) {" +
            "    if (player) player.setVolume(volume);" + // Set the player's volume
            "  }" +
            "</script>" +
            "</body>" +
            "</html>"
        );

        // Input field for pasting in a YouTube URL
        TextField urlInput = new TextField();
        urlInput.setPromptText("Enter YouTube URL"); // Default text for the input field

        // Button to load a new video
        Button loadVideoButton = new Button("Load Video");
        loadVideoButton.setOnAction(e -> {
            String url = urlInput.getText().trim(); // Get the URL entered by the user
            String videoId = extractVideoIdFromUrl(url); // Extract the video ID from the URL
            if (videoId != null) {
                youTubeWebEngine.executeScript("loadVideo('" + videoId + "');"); // Load the video in the player
            } else {
                System.out.println("Invalid YouTube URL"); // Print an error if the URL is invalid
            }
        });

        // Horizontal volume slider to control the player's volume
        Slider volumeSlider = new Slider(0, 100, 50); // Volume sliders min and max volumes
        volumeSlider.setShowTickLabels(true); // Show tick labels on the slider
        volumeSlider.setShowTickMarks(true); // Show tick marks on the slider
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            youTubeWebEngine.executeScript("setVolume(" + newVal.intValue() + ");"); // Update the player's volume
        });

        // Horizontal box for the URL input field and the load button
        HBox controlsBox = new HBox(10, urlInput, loadVideoButton); // Add spacing between elements
        controlsBox.setAlignment(Pos.CENTER); // Center it horizontally

        // Vertical box for the volume slider and its label
        VBox sliderBox = new VBox(10, new Label("Volume"), volumeSlider); // Add spacing between elements
        sliderBox.setAlignment(Pos.CENTER); // Center the slider and label

        // Add all components to the main container
        root.getChildren().addAll(title, youTubeWebView, controlsBox, sliderBox);
        return root; // Return the main container
    }

    // Method to extract the video ID from a YouTube URL
    private String extractVideoIdFromUrl(String url) {
        try {
            if (url.contains("youtube.com/watch?v=")) {
                return url.split("v=")[1].split("&")[0]; // Extract video ID from standard YouTube URL
            } else if (url.contains("youtu.be/")) {
                return url.split("youtu.be/")[1].split("\\?")[0]; // Extract video ID from shortened YouTube URL
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace if an error occurs
        }
        return null; // Return null if the URL is invalid
    }
}