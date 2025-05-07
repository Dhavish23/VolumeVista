package JavaProjects.Sem2.Testing;

import JavaProjects.Sem2.CombinedCode.VolumeMediaController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for VolumeMediaController.
 * This class contains unit tests for the VolumeMediaController class.
 * It uses JUnit 5 for testing JavaFX applications.
 */

class VolumeMediaControllerTest {

    // Test class for VolumeMediaController
    @BeforeAll
    static void setupJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    // Test to check if the VolumeMediaController initializes
    @Test
    void testVolumeMediaControllerInitialization() {
        VolumeMediaController controller = new VolumeMediaController(); // Create an instance of VolumeMediaController
        assertNotNull(controller, "VolumeMediaController should initialize properly.");
    }

    // Test to check if the start method of VolumeMediaController executes without exceptions
    @Test
    void testStartMethod() {
        Platform.runLater(() -> { // This method runs on the JavaFX application thread
            VolumeMediaController controller = new VolumeMediaController(); // Create an instance of VolumeMediaController
            Stage stage = new Stage(); // Create a new stage for the controller
            assertDoesNotThrow(() -> controller.start(stage), "VolumeMediaController's start method should execute without exceptions.");
        });
    }

    // Test to check if the back button in the VolumeMediaController works correctly
    @Test
    void testBackButtonAction() {
        Platform.runLater(() -> {
            VolumeMediaController controller = new VolumeMediaController();
            Stage stage = new Stage();
            controller.start(stage);

            VBox root = (VBox) stage.getScene().getRoot();
            Button backButton = (Button) root.lookup(".button:contains('⬅ Back')");
            assertNotNull(backButton, "Back button should exist.");

            backButton.fire();
            assertTrue(stage.isShowing(), "Stage should remain open after back button action.");
        });
    }

    // Test to check if the master volume slider works correctly
    @Test
    void testMasterVolumeSlider() {
        Platform.runLater(() -> {
            VolumeMediaController controller = new VolumeMediaController();
            Stage stage = new Stage();
            controller.start(stage);

            VBox root = (VBox) stage.getScene().getRoot();
            Slider masterSlider = (Slider) root.lookup(".slider");
            assertNotNull(masterSlider, "Master volume slider should exist.");

            masterSlider.setValue(50);
            assertEquals(50, masterSlider.getValue(), "Master volume slider value should be updated.");
        });
    }

    // Test to check if the mode buttons work correctly
    @Test
    void testModeButtons() {
        Platform.runLater(() -> {
            VolumeMediaController controller = new VolumeMediaController(); // Create an instance of VolumeMediaController
            Stage stage = new Stage(); // Create a new stage for the controller
            controller.start(stage); // Start the controller

            VBox root = (VBox) stage.getScene().getRoot(); // Get the root layout of the scene
            Button gamingMode = (Button) root.lookup(".button:contains('Gaming Mode')"); // Find the gaming mode button
            Button musicMode = (Button) root.lookup(".button:contains('Music Mode')");
            Button movieMode = (Button) root.lookup(".button:contains('Movie Mode')");

            assertNotNull(gamingMode, "Gaming Mode button should exist."); // Check if the gaming mode button is not null
            assertNotNull(musicMode, "Music Mode button should exist.");
            assertNotNull(movieMode, "Movie Mode button should exist.");

            gamingMode.fire(); // Simulate a button click
            musicMode.fire();
            movieMode.fire();
        });
    }
}