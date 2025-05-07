package JavaProjects.Sem2.Testing;

import JavaProjects.Sem2.CombinedCode.YouTubeMixer;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for YouTubeMixer.
 * This class contains unit tests for the YouTubeMixer class.
 * It uses JUnit 5 for testing JavaFX applications.
 */

class YouTubeMixerTest {

    // Test class for YouTubeMixer
    @BeforeAll
    static void setupJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {}); // Start JavaFX application thread if not already running
        }
    }

    // Test to check if the YouTubeMixer creates a non-null VBox
    @Test
    void testCreateYouTubeMixer() {
        Platform.runLater(() -> { // This method runs on the JavaFX application thread
            YouTubeMixer youTubeMixer = new YouTubeMixer(); // Create an instance of YouTubeMixer
            VBox youTubeBox = youTubeMixer.createYouTubeMixer(); // Create a YouTube mixer
            assertNotNull(youTubeBox, "YouTubeMixer should create a non-null VBox."); // Check if the VBox is not null
        });
    }

    // Test to check if the WebEngine is initialized correctly
    @Test
    void testWebEngineInitialization() {
        Platform.runLater(() -> {
            YouTubeMixer youTubeMixer = new YouTubeMixer();
            youTubeMixer.createYouTubeMixer();
            WebEngine engine = youTubeMixer.getEngine();
            assertNotNull(engine, "WebEngine should be initialized.");
        });
    }

    // Test to check if the play button action executes without errors
    @Test
    void testPlayButtonAction() {
        Platform.runLater(() -> {
            YouTubeMixer youTubeMixer = new YouTubeMixer();
            VBox youTubeBox = youTubeMixer.createYouTubeMixer();
            WebEngine engine = youTubeMixer.getEngine(); // Get the WebEngine instance

            Button playButton = (Button) youTubeBox.lookup(".button:contains('▶')"); // Find the play button
            assertNotNull(playButton, "Play button should exist."); // Check if the play button is not null

            playButton.fire();
            assertDoesNotThrow(() -> engine.executeScript("document.getElementById('player').contentWindow.postMessage('{\"event\":\"command\",\"func\":\"playVideo\",\"args\":\"\"}', '*');"), "Play button action should execute without errors.");
        });
    }

    // Test to check if the pause button action executes without errors
    @Test
    void testPauseButtonAction() {
        Platform.runLater(() -> {
            YouTubeMixer youTubeMixer = new YouTubeMixer();
            VBox youTubeBox = youTubeMixer.createYouTubeMixer();
            WebEngine engine = youTubeMixer.getEngine();

            Button pauseButton = (Button) youTubeBox.lookup(".button:contains('⏸')");
            assertNotNull(pauseButton, "Pause button should exist.");

            pauseButton.fire();
            assertDoesNotThrow(() -> engine.executeScript("document.getElementById('player').contentWindow.postMessage('{\"event\":\"command\",\"func\":\"pauseVideo\",\"args\":\"\"}', '*');"), "Pause button action should execute without errors.");
        });
    }

    // Test to check if the mute button action executes without errors
    @Test
    void testMuteButtonAction() {
        Platform.runLater(() -> {
            YouTubeMixer youTubeMixer = new YouTubeMixer();
            VBox youTubeBox = youTubeMixer.createYouTubeMixer();
            WebEngine engine = youTubeMixer.getEngine();

            Button muteButton = (Button) youTubeBox.lookup(".button:contains('🔇')");
            assertNotNull(muteButton, "Mute button should exist.");

            muteButton.fire(); // Simulate a button click
            assertDoesNotThrow(() -> engine.executeScript("document.getElementById('player').contentWindow.postMessage('{\"event\":\"command\",\"func\":\"mute\",\"args\":\"\"}', '*');"), "Mute button action should execute without errors.");
        });
    }
}