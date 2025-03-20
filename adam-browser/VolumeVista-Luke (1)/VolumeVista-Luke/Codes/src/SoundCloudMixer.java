import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class SoundCloudMixer {
    private WebView soundCloudWebView; // WebView to display the SoundCloud player
    private WebEngine soundCloudWebEngine; // WebEngine to control the WebView content

    public VBox createSoundCloudMixer() {
        // Main container for the SoundCloud mixer
        VBox root = new VBox(10); // Add spacing between elements
        root.setAlignment(Pos.CENTER); // Centers all elements

        // Title for the SoundCloud mixer
        Label title = new Label("SoundCloud");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); // Set font size and style

        // Initialize the WebView and WebEngine
        soundCloudWebView = new WebView();
        soundCloudWebEngine = soundCloudWebView.getEngine();

        // Embed SoundCloud player using HTML
        String embedHTML = "<html><body style='margin:0;padding:0;'>" +
                "<iframe id='sc-player' width='400' height='250' " + // Embed SoundCloud player iframe
                "scrolling='no' frameborder='no' " +
                "src='https://w.soundcloud.com/player/?url=https%3A//soundcloud.com/kylie-minogue-official/cant-get-you-out-of-my-head&color=%23ff5500&auto_play=false'>" +
                "</iframe>" +
                "<script src='https://w.soundcloud.com/player/api.js'></script>" + // Load SoundCloud API
                "<script>" +
                "var widgetIframe = document.getElementById('sc-player');" + // Get the iframe element
                "var widget = SC.Widget(widgetIframe);" + // Initialize the SoundCloud widget
                "window.setVolume = function(volume) { widget.setVolume(volume); };" + // Define a function to set the volume
                "</script></body></html>";
        soundCloudWebEngine.loadContent(embedHTML); // Load the HTML content into the WebView

        // Create a horizontal slider for controlling the SoundCloud player's volume
        VBox soundCloudSliderBox = VolumeSlider.createHorizontalSlider("SoundCloud Volume", soundCloudWebEngine, false);

        // Add the title, player, and volume slider to the main container
        root.getChildren().addAll(title, soundCloudWebView, soundCloudSliderBox);

        // Return the main container
        return root;
    }
}