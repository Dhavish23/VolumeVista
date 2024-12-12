package JavaProjects;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

public class SpotifyVolumeMixer extends Application {

    private static final String CLIENT_ID = "772ddec384e94fd0a6ded704e7a076fa";
    private static final String CLIENT_SECRET = "8f478ae7f56144d282e16d9e5ae40ca1";

    private static final String REDIRECT_URI = "http://localhost:8888/callback";

    private String accessToken;

    private WebView spotifyWebView; // WebView to embed Spotify player
    private WebEngine spotifyWebEngine;

    @Override
    public void start(Stage primaryStage) {
        if (CLIENT_ID == null || CLIENT_SECRET == null) {
            System.err.println("Client ID and Client Secret must be set.");
            return;
        }

        accessToken = getAccessToken(); // Retrieve Spotify access token

        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Spotify Volume Mixer");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Embed Spotify player
        spotifyWebView = new WebView();
        spotifyWebEngine = spotifyWebView.getEngine();

        // Load Spotify embed
        String embedHTML = "<html><body style='margin:0;padding:0;overflow:hidden;'>"
                + "<iframe src='https://open.spotify.com/embed/playlist/37i9dQZF1DXcBWIGoYBM5M' "
                + "width='100%' height='80%' frameborder='0' allowtransparency='true' allow='encrypted-media'></iframe>"
                + "</body></html>";
        spotifyWebEngine.loadContent(embedHTML);

        // Create Spotify volume slider
        VBox spotifySliderBox = createSpotifyVolumeSlider();

        // Add all components to the main layout
        root.getChildren().addAll(title, spotifyWebView, spotifySliderBox);

        // Set up and display the application window
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Spotify Volume Mixer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a slider to control Spotify volume.
     *
     * @return A layout with the slider and label
     */
    private VBox createSpotifyVolumeSlider() {
        VBox sliderBox = new VBox(10);
        sliderBox.setAlignment(Pos.CENTER);

        // Label for Spotify volume slider
        Label label = new Label("Spotify Volume");
        label.setStyle("-fx-font-size: 14px;");

        // Slider to adjust Spotify player volume
        Slider slider = new Slider(0, 100, 50); // Range: 0-100, default: 50
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(25);
        slider.setBlockIncrement(10);

        // Listener to control Spotify volume
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int volume = newValue.intValue();
            adjustSpotifyVolume(volume);
        });

        sliderBox.getChildren().addAll(slider, label);
        return sliderBox;
    }

    /**
     * Adjusts the Spotify player's volume using the Spotify Web API.
     *
     * @param volume Volume level (0-100)
     */
    private void adjustSpotifyVolume(int volume) {
        try {
            URL url = new URL("https://api.spotify.com/v1/me/player/volume?volume_percent=" + volume);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setDoOutput(true);

            // Send request
            OutputStream os = connection.getOutputStream();
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode != 204) {
                System.err.println("Failed to adjust volume. Response code: " + responseCode);
            } else {
                System.out.println("Spotify volume adjusted to: " + volume + "%");
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an access token from Spotify's Web API.
     *
     * @return Access token
     */
    private String getAccessToken() {
        try {
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic " +
                    Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String body = "grant_type=client_credentials";
            OutputStream os = connection.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            os.close();

            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Extract access token from JSON response
            String tokenResponse = response.toString();
            String token = tokenResponse.split("\"access_token\":\"")[1].split("\"")[0];

            connection.disconnect();
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
