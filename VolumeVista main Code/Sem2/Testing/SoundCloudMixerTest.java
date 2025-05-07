package JavaProjects.Sem2.Testing;

import JavaProjects.Sem2.CombinedCode.SoundCloudMixer;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SoundCloudMixer.
 */
class SoundCloudMixerTest {

    // Test class for SoundCloudMixer
    @BeforeAll
    static void setupJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    // Test to check if the SoundCloudMixer creates a non-null VBox
    @Test
    void testCreateSoundCloudMixer() {
        Platform.runLater(() -> {
            SoundCloudMixer mixer = new SoundCloudMixer(); // Create an instance of SoundCloudMixer
            VBox soundCloudBox = mixer.createSoundCloudMixer(); // Call the method to create the SoundCloud mixer
            assertNotNull(soundCloudBox, "SoundCloudMixer should create a non-null VBox.");
        });
    }

    // Test to check if the WebEngine is initialized correctly
    @Test
    void testWebEngineInitialization() {
        Platform.runLater(() -> { // This method runs on the JavaFX application thread
            SoundCloudMixer mixer = new SoundCloudMixer();
            mixer.createSoundCloudMixer();
            WebEngine engine = mixer.getEngine(); // Get the WebEngine instance
            assertNotNull(engine, "WebEngine should be initialized."); // Check if the WebEngine is not null
        });
    }

    // Test to check if the play button action executes without errors
    @Test
    void testPlayButtonAction() {
        Platform.runLater(() -> {
            SoundCloudMixer mixer = new SoundCloudMixer();
            VBox soundCloudBox = mixer.createSoundCloudMixer();
            WebEngine engine = mixer.getEngine();

            Button playButton = (Button) soundCloudBox.lookup(".button:contains('▶')");
            assertNotNull(playButton, "Play button should exist.");

            playButton.fire();
            assertDoesNotThrow(() -> engine.executeScript("window.playTrack();"), "Play button action should execute without errors.");
        });
    }

    // Test to check if the pause button action executes without errors
    @Test
    void testPauseButtonAction() {
        Platform.runLater(() -> {
            SoundCloudMixer mixer = new SoundCloudMixer();
            VBox soundCloudBox = mixer.createSoundCloudMixer();
            WebEngine engine = mixer.getEngine();

            Button pauseButton = (Button) soundCloudBox.lookup(".button:contains('⏸')");
            assertNotNull(pauseButton, "Pause button should exist.");

            pauseButton.fire();
            assertDoesNotThrow(() -> engine.executeScript("window.pauseTrack();"), "Pause button action should execute without errors.");
        });
    }

    // Test to check if the mute button action executes without errors
    @Test
    void testMuteButtonAction() {
        Platform.runLater(() -> { // This method runs on the JavaFX application thread
            SoundCloudMixer mixer = new SoundCloudMixer();
            VBox soundCloudBox = mixer.createSoundCloudMixer(); // Create the SoundCloud mixer
            WebEngine engine = mixer.getEngine(); // Get the WebEngine instance

            Button muteButton = (Button) soundCloudBox.lookup(".button:contains('🔇')"); // Find the mute button in the layout
            assertNotNull(muteButton, "Mute button should exist."); // Check if the mute button is not null

            muteButton.fire(); // Simulate a button click
            assertDoesNotThrow(() -> engine.executeScript("window.muteTrack();"), "Mute button action should execute without errors.");
        });
    }
}