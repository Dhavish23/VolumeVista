package JavaProjects.Sem2.Testing;

import JavaProjects.Sem2.CombinedCode.VolumeSlider;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for VolumeSlider.
 * This class contains unit tests for the VolumeSlider class.
 * It uses JUnit 5 for testing JavaFX applications.
 */

class VolumeSliderTest {

    // Test class for VolumeSlider
    @BeforeAll
    static void setupJavaFX() {
        if (!Platform.isFxApplicationThread()) { // Check if the current thread is not the JavaFX application thread
            Platform.startup(() -> {}); // Start JavaFX application thread if not already running
        }
    }

    // Test to check if the VolumeSlider creates a non-null VBox
    @Test
    void testCreateSlider() {
        Platform.runLater(() -> {
            DummyWebEngine dummyWebEngine = new DummyWebEngine(new WebEngine());
            VBox sliderBox = VolumeSlider.createSlider("Test Slider", dummyWebEngine.getWebEngine(), false);

            assertNotNull(sliderBox, "Slider VBox should not be null.");
            assertEquals(2, sliderBox.getChildren().size(), "Slider VBox should contain a label and a slider.");
        });
    }

    // Test to check if the slider value change executes the correct script
    @Test
    void testSliderValueChange() {
        Platform.runLater(() -> {
            DummyWebEngine dummyWebEngine = new DummyWebEngine(new WebEngine()); // Create a dummy WebEngine
            VBox sliderBox = VolumeSlider.createSlider("Test Slider", dummyWebEngine.getWebEngine(), false); // Create a slider box

            Slider slider = (Slider) sliderBox.getChildren().get(1); // Get the slider from the VBox
            slider.setValue(75); // Set the slider value to 75

            assertEquals("window.setVolume(75.0);", dummyWebEngine.getLastExecutedScript(),
                    "The executed script should match the expected volume change."); // Check if the script matches
        });
    }

    // Test to check if the YouTube slider value change executes the correct script
    @Test
    void testYouTubeSliderValueChange() {
        Platform.runLater(() -> {
            DummyWebEngine dummyWebEngine = new DummyWebEngine(new WebEngine()); // Create a dummy WebEngine
            VBox sliderBox = VolumeSlider.createSlider("YouTube Slider", dummyWebEngine.getWebEngine(), true); // Create a YouTube slider box

            Slider slider = (Slider) sliderBox.getChildren().get(1);
            slider.setValue(30);

            assertTrue(dummyWebEngine.getLastExecutedScript().contains("setVolume"),
                    "The executed script should include the 'setVolume' command.");
            assertTrue(dummyWebEngine.getLastExecutedScript().contains("30.0"),
                    "The executed script should include the correct volume value.");
        });
    }

    // Dummy WebEngine implementation using composition
    static class DummyWebEngine {
        private final WebEngine webEngine;
        private String lastExecutedScript;

        public DummyWebEngine(WebEngine webEngine) {
            this.webEngine = webEngine;
        }

        public void executeScript(String script) {
            this.lastExecutedScript = script;
            webEngine.executeScript(script);
        }

        public String getLastExecutedScript() {
            return lastExecutedScript;
        }

        public WebEngine getWebEngine() {
            return webEngine;
        }
    }
}