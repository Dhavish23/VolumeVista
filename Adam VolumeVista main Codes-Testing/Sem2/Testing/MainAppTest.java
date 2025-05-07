package JavaProjects.Sem2.Testing;

import JavaProjects.Sem2.CombinedCode.MainApp;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll; // import this import to use the BeforeAll annotation
import org.junit.jupiter.api.Test; // import this import to use the Test annotation

import static org.junit.jupiter.api.Assertions.*; // import this import to use the assertion methods

/**
 * This class contains unit tests for the MainApp class.
 * It uses JUnit 5 for testing JavaFX applications.
 */
class MainAppTest {

    // This method is executed before all tests in this class
    @BeforeAll
    static void setupJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {}); // Start JavaFX application thread if not already running
        }
    }

    // This test method checks if the MainApp class initializes
    @Test
    void testMainAppInitialization() {
        MainApp app = new MainApp();
        assertNotNull(app, "MainApp should initialize properly.");
    }

    // This test method checks if the start method of MainApp executes without exceptions
    @Test
    void testStartMethod() {
        Platform.runLater(() -> {
            MainApp app = new MainApp(); // Create an instance of MainApp
            Stage stage = new Stage();
            assertDoesNotThrow(() -> app.start(stage), "MainApp's start method should execute without exceptions.");
        });
    }
}