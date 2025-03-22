import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VimeoMixer {

    private Browser browser;

    public VBox createVimeoMixer() {
        // Initialize JxBrowser Engine with a valid license key
        Engine engine = Engine.newInstance(
                EngineOptions.newBuilder(RenderingMode.HARDWARE_ACCELERATED)
                        .licenseKey("OK6AEKNYF37GRFRN5NXJ7E036DQYVLMYXV6YMUYXDHTA6B8QDT7STA65INM69D0N7E9FP3V8R4ZWI47KV3CB4TL5L6NK5IMWS7J0WU4NZGG0GVYJ1K3MH3BE7VTU3OFB822AB30WSQ17BH4QC")
                        .build());

        // Create a browser instance
        browser = engine.newBrowser();
        BrowserView browserView = BrowserView.newInstance(browser);

        // Main vertical container for the application
        VBox root = new VBox(20); // Add spacing between elements
        root.setPadding(new Insets(20)); // Add padding around the edges
        root.setStyle("-fx-background-color: pink;"); // Set background color
        root.setAlignment(Pos.CENTER); // Center all elements in the VBox

        // Title label for the application
        Label title = new Label("Browser Player");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;"); // Set font size and style

        // Volume Slider for controlling browser volume
        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Execute JavaScript to adjust the volume of the browser content
            browser.mainFrame().ifPresent(frame -> {
                String js = "document.querySelector('audio') ? document.querySelector('audio').volume = " + newVal.doubleValue() / 100;
                frame.executeJavaScript(js);
            });
        });

        // Create a control box with the volume slider
        VBox controls = new VBox(10, new Label("Volume Control"), volumeSlider);
        controls.setAlignment(Pos.CENTER);

        // Create the overall layout (HBox to put the browser and controls side by side)
        HBox layout = new HBox(20, browserView, controls);
        layout.setAlignment(Pos.CENTER);
        browserView.setPrefSize(600, 400); // Explicitly set the size of the browser view

        // Add the title and players container to the main container
        root.getChildren().addAll(title, layout);

        // Load a URL in the browser (example: Google)
        browser.navigation().loadUrl("https://www.google.com");

        return root;
    }
}
