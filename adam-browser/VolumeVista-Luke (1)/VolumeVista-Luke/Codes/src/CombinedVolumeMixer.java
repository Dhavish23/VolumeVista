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
        // Main vertical container for the application
        VBox root = new VBox(20); // Add spacing between elements
        root.setPadding(new Insets(20)); // Add padding around the edges
        root.setStyle("-fx-background-color: pink;"); // Set background color
        root.setAlignment(Pos.CENTER); // Center all elements in the VBox

        // Title label for the application
        Label title = new Label("YouTube, SoundCloud, and Browser Volume Mixer");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;"); // Set font size and style

        // Horizontal container for the media players
        HBox playersBox = new HBox(30); // Add spacing between players
        playersBox.setAlignment(Pos.CENTER); // Center the players horizontally

        // Create instances of the individual mixers
        YouTubeMixer youTubeMixer = new YouTubeMixer(); // YouTube player
        SoundCloudMixer soundCloudMixer = new SoundCloudMixer(); // SoundCloud player
        VimeoMixer vimeoMixer = new VimeoMixer(); // Browser player

        // Add the individual mixers to the horizontal container
        playersBox.getChildren().addAll(
                soundCloudMixer.createSoundCloudMixer(), // Add SoundCloud mixer
                youTubeMixer.createYouTubeMixer(), // Add YouTube mixer
                vimeoMixer.createVimeoMixer() // Add Browser mixer
        );

        // Add the title and players container to the main container
        root.getChildren().addAll(title, playersBox);

        // Create the scene and set it on the primary stage
        Scene scene = new Scene(root, 1200, 600); // Set the scene size
        primaryStage.setTitle("Volume Vista"); // Set the window title
        primaryStage.setScene(scene); // Attach the scene to the stage
        primaryStage.show(); // Show the stage
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
