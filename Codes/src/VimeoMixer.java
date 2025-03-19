import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class VimeoMixer {
    private WebView vimeoWebView; // WebView to display the Vimeo player
    private WebEngine vimeoWebEngine; // WebEngine to control the WebView content

    public VBox createVimeoMixer() {
        // Main container for the Vimeo mixer
        VBox root = new VBox(10); // Add spacing between elements
        root.setAlignment(Pos.CENTER); // Centers all elements 

        // Title for the Vimeo mixer
        Label title = new Label("Vimeo");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); // Set font size and style

        // Initialize the WebView and WebEngine
        vimeoWebView = new WebView();
        vimeoWebEngine = vimeoWebView.getEngine();

        // Vimeo video ID to embed
        String videoId = "76979871"; // Replace with the desired Vimeo video ID

        // Embed Vimeo player using HTML
        String embedHTML = "<html><body style='margin:0;padding:0;'>" +
                "<iframe id='vimeo-player' width='400' height='250' " + // Embed Vimeo player iframe
                "src='https://player.vimeo.com/video/" + videoId + "?title=0&byline=0&portrait=0' " + // Vimeo video URL
                "frameborder='0' allowfullscreen></iframe>" +
                "<script src='https://player.vimeo.com/api/player.js'></script>" + // Load Vimeo Player API
                "<script>" +
                "var iframe = document.getElementById('vimeo-player');" + // Get the iframe element
                "var player = new Vimeo.Player(iframe);" + // Initialize the Vimeo player
                "window.setVolume = function(volume) { player.setVolume(volume / 100); };" + // Define a function to set the volume
                "</script></body></html>";
        vimeoWebEngine.loadContent(embedHTML); // Load the HTML content into the WebView

        // Create a vertical slider for controlling the Vimeo player's volume
        VBox vimeoSliderBox = VolumeSlider.createHorizontalSlider("Vimeo Volume", vimeoWebEngine, false);

        // Add the title, player, and volume slider to the main container
        root.getChildren().addAll(title, vimeoWebView, vimeoSliderBox);

        // Return the main container
        return root;
    }
}