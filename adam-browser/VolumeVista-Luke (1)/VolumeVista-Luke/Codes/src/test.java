import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        Engine engine = Engine.newInstance(
                EngineOptions.newBuilder(RenderingMode.HARDWARE_ACCELERATED)
                        .licenseKey("OK6AEKNYF37GRFRN5NXJ7E036DQYVLMYXV6YMUYXDHTA6B8QDT7STA65INM69D0N7E9FP3V8R4ZWI47KV3CB4TL5L6NK5IMWS7J0WU4NZGG0GVYJ1K3MH3BE7VTU3OFB822AB30WSQ17BH4QC")
                        .build());

        Browser browser = engine.newBrowser();
        BrowserView view = BrowserView.newInstance(browser);

        StackPane root = new StackPane(view);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("JxBrowser in JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        browser.navigation().loadUrl("https://www.google.com");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
