import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CombinedVolumeMixer extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: pink;");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("YouTube, SoundCloud, and Vimeo Volume Mixer");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        HBox playersBox = new HBox(30);
        playersBox.setAlignment(Pos.CENTER);

        YouTubeMixer youTubeMixer = new YouTubeMixer();
        SoundCloudMixer soundCloudMixer = new SoundCloudMixer();
        VimeoMixer vimeoMixer = new VimeoMixer();

        playersBox.getChildren().addAll(
                youTubeMixer.createYouTubeMixer(),
                soundCloudMixer.createSoundCloudMixer(),
                vimeoMixer.createVimeoMixer());

        root.getChildren().addAll(title, playersBox);

        Scene scene = new Scene(root, 1200, 600);
        primaryStage.setTitle("YouTube, SoundCloud, and Vimeo Volume Mixer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}