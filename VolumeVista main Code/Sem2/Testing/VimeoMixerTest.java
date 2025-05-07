package JavaProjects.Sem2.Testing;

import JavaProjects.Sem2.CombinedCode.VimeoMixer;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for VimeoMixer.
 */

class VimeoMixerTest {

    // Test class for VimeoMixer
    @BeforeAll
    static void setupJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    // Test to check if the VimeoMixer creates a non-null VBox
    @Test
    void testCreateVimeoMixer() {
        Platform.runLater(() -> {
            VimeoMixer vimeoMixer = new VimeoMixer();
            VBox vimeoBox = vimeoMixer.createVimeoMixer();
            assertNotNull(vimeoBox, "VimeoMixer should create a non-null VBox.");
        });
    }

    // Test to check if the WebEngine is initialized correctly
    @Test
    void testWebEngineInitialization() {
        Platform.runLater(() -> {
            VimeoMixer vimeoMixer = new VimeoMixer();
            vimeoMixer.createVimeoMixer();
            WebEngine engine = vimeoMixer.getEngine();
            assertNotNull(engine, "WebEngine should be initialized.");
        });
    }

    // Test to check if the play button action executes without errors
    @Test
    void testPlayButtonAction() {
        Platform.runLater(() -> {
            VimeoMixer vimeoMixer = new VimeoMixer();
            VBox vimeoBox = vimeoMixer.createVimeoMixer(); // Create the Vimeo mixer
            WebEngine engine = vimeoMixer.getEngine(); // Get the WebEngine instance

            Button playButton = (Button) vimeoBox.lookup(".button:contains('▶')"); // Find the play button
            assertNotNull(playButton, "Play button should exist."); // Check if the play button is not null

            playButton.fire(); // Simulate a button click
            assertDoesNotThrow(() -> engine.executeScript("window.playTrack();"), "Play button action should execute without errors.");
        });
    }

    // Test to check if the pause button action executes without errors
    @Test
    void testPauseButtonAction() {
        Platform.runLater(() -> {
            VimeoMixer vimeoMixer = new VimeoMixer();
            VBox vimeoBox = vimeoMixer.createVimeoMixer();
            WebEngine engine = vimeoMixer.getEngine();

            Button pauseButton = (Button) vimeoBox.lookup(".button:contains('⏸')");
            assertNotNull(pauseButton, "Pause button should exist.");

            pauseButton.fire();
            assertDoesNotThrow(() -> engine.executeScript("window.pauseTrack();"), "Pause button action should execute without errors.");
        });
    }

    // Test to check if the mute button action executes without errors
    @Test
    void testMuteButtonAction() {
        Platform.runLater(() -> {
            VimeoMixer vimeoMixer = new VimeoMixer();
            VBox vimeoBox = vimeoMixer.createVimeoMixer();
            WebEngine engine = vimeoMixer.getEngine();

            Button muteButton = (Button) vimeoBox.lookup(".button:contains('🔇')");
            assertNotNull(muteButton, "Mute button should exist.");

            muteButton.fire();
            assertDoesNotThrow(() -> engine.executeScript("window.muteTrack();"), "Mute button action should execute without errors.");
        });
    }
}