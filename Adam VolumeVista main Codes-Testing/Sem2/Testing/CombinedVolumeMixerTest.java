package JavaProjects.Sem2.Testing;

// Import necessary JavaFX classes
import JavaProjects.Sem2.CombinedCode.CombinedVolumeMixer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll; // to use the BeforeAll annotation
import org.junit.jupiter.api.Test; // test annotation

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the CombinedVolumeMixer class.
 * It uses JUnit 5 for testing JavaFX applications.
 */

class CombinedVolumeMixerTest {

    // This test class is designed to test the CombinedVolumeMixer class
    @BeforeAll // This method is executed once, before all tests in this class
    static void initJavaFX() {
        setupJavaFX();  // Ensure JavaFX is initialized before running tests
    }

    // This method ensures that JavaFX is initialized properly
    static void setupJavaFX() {
        if (!Platform.isFxApplicationThread()) { // Check if the current thread is not the JavaFX application thread
            Platform.startup(() -> {}); // Start JavaFX application thread if not already running
        }
    }

    // This test method checks if the CombinedVolumeMixer class initializes
    @Test
    void testCombinedVolumeMixerInitialization() {
        CombinedVolumeMixer mixer = new CombinedVolumeMixer();
        assertNotNull(mixer, "CombinedVolumeMixer should initialize properly.");
    }

    // This test method checks if the start method of CombinedVolumeMixer executes without exceptions
    @Test
    void testStartMethod() {
        Platform.runLater(() -> {
            CombinedVolumeMixer mixer = new CombinedVolumeMixer();
            Stage stage = new Stage();
            assertDoesNotThrow(() -> mixer.start(stage), "CombinedVolumeMixer's start method should execute without exceptions.");
        });
    }

    // This test method checks if the back button in the CombinedVolumeMixer works correctly
    @Test
    void testThemeToggle() {
        Platform.runLater(() -> { // This method runs on the JavaFX application thread
            CombinedVolumeMixer mixer = new CombinedVolumeMixer();
            Stage stage = new Stage(); // Create a new stage for the mixer
            mixer.start(stage);

            VBox root = (VBox) stage.getScene().getRoot(); // Get the root layout of the scene
            Button themeToggle = (Button) root.lookup(".button:contains('Switch to Dark Mode')");
            assertNotNull(themeToggle, "Theme toggle button should exist.");

            themeToggle.fire(); // Simulate a button click
            assertTrue(mixer.darkMode, "Dark mode should be enabled after toggling.");
        });
    }

    // This test method checks if the back button in the CombinedVolumeMixer works correctly
    @Test
    void testMuteAllButton() {
        Platform.runLater(() -> {
            CombinedVolumeMixer mixer = new CombinedVolumeMixer();
            Stage stage = new Stage();
            mixer.start(stage);

            VBox root = (VBox) stage.getScene().getRoot(); // Get the root layout of the scene
            Button muteAllButton = (Button) root.lookup(".button:contains('Mute All')");
            assertNotNull(muteAllButton, "Mute All button should exist.");

            muteAllButton.fire(); // Simulate a button click
            assertTrue(mixer.isMuted, "All players should be muted after clicking the button.");
        });
    }

    // This test method checks if the back button in the CombinedVolumeMixer works correctly
    @Test
    void testMiniModeToggle() {
        Platform.runLater(() -> {
            CombinedVolumeMixer mixer = new CombinedVolumeMixer();
            Stage stage = new Stage();
            mixer.start(stage); // Start the mixer

            VBox root = (VBox) stage.getScene().getRoot(); // Get the root layout of the scene
            Button miniModeButton = (Button) root.lookup(".button:contains('Mini Toolbar Mode')");
            assertNotNull(miniModeButton, "Mini Toolbar Mode button should exist.");

            miniModeButton.fire(); // Simulate a button click
            assertTrue(mixer.isMiniMode, "Mini mode should be enabled after toggling.");
        });
    }
}