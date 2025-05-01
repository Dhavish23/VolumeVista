import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

public class CombinedVolumeMixer extends Application {

    private boolean darkMode = false;
    private boolean isMuted = false;
    private boolean isMiniMode = false;

    private WebEngine ytEngine;
    private WebEngine scEngine;
    private WebEngine vmEngine;

    private double lastMasterVolume = 50;

    private HBox playersBox;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: pink;");

        Label title = new Label("YouTube, SoundCloud, and Vimeo Volume Mixer");
        title.getStyleClass().add("label-title");

        // Theme toggle button
        Button themeToggle = new Button("Switch to Dark Mode");

        // Mute/Unmute All button
        Button muteAllButton = new Button("Mute All");

        // Mini Toolbar Mode button
        Button miniModeButton = new Button("Mini Toolbar Mode");

        // Open Volume Controller button
        Button openControllerButton = new Button("🎛 Open Volume Controller");
        openControllerButton.setStyle("-fx-font-size: 14px;");

        // Theme button logic
        themeToggle.setOnAction(e -> {
            darkMode = !darkMode;
            if (darkMode) {
                root.setStyle("-fx-background-color: #1e1e1e;");
                title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");
                themeToggle.setText("Switch to Light Mode");
            } else {
                root.setStyle("-fx-background-color: pink;");
                title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
                themeToggle.setText("Switch to Dark Mode");
            }
        });

        //  Open Controller button logic 
        openControllerButton.setOnAction(e -> {
            try {
                VolumeMediaController volumePage = new VolumeMediaController();
                volumePage.start(primaryStage); // Reuse same window
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox topControls = new HBox(10, themeToggle, muteAllButton, miniModeButton, openControllerButton);
        topControls.setAlignment(Pos.CENTER);

        // Master Volume
        Label masterLabel = new Label("Master Volume");
        masterLabel.setStyle("-fx-font-size: 18px;");

        Slider masterSlider = new Slider(0, 100, 50);
        masterSlider.setShowTickLabels(true);
        masterSlider.setShowTickMarks(true);
        masterSlider.setMajorTickUnit(25);
        masterSlider.setBlockIncrement(10);
        masterSlider.setPrefWidth(300);

        Label masterValueLabel = new Label("50");
        masterValueLabel.setStyle("-fx-font-size: 16px;");

        HBox masterSliderRow = new HBox(10, masterSlider, masterValueLabel);
        masterSliderRow.setAlignment(Pos.CENTER);

        VBox masterControl = new VBox(5, masterLabel, masterSliderRow);
        masterControl.setAlignment(Pos.CENTER);

        muteAllButton.setOnAction(e -> {
            if (!isMuted) {
                lastMasterVolume = masterSlider.getValue();
                masterSlider.setValue(0);
                isMuted = true;
                muteAllButton.setText("Unmute All");
            } else {
                masterSlider.setValue(lastMasterVolume);
                isMuted = false;
                muteAllButton.setText("Mute All");
            }
        });

        // Preset Buttons
        Button musicModeButton = new Button("🎧 Music Mode");
        Button movieModeButton = new Button("🎬 Movie Mode");
        Button partyModeButton = new Button("🎉 Party Mode");

        musicModeButton.setStyle("-fx-font-size: 14px;");
        movieModeButton.setStyle("-fx-font-size: 14px;");
        partyModeButton.setStyle("-fx-font-size: 14px;");

        musicModeButton.setOnAction(e -> masterSlider.setValue(50));
        movieModeButton.setOnAction(e -> masterSlider.setValue(80));
        partyModeButton.setOnAction(e -> masterSlider.setValue(100));

        HBox presetButtons = new HBox(10, musicModeButton, movieModeButton, partyModeButton);
        presetButtons.setAlignment(Pos.CENTER);

        // Players area
        playersBox = new HBox(30);
        playersBox.setAlignment(Pos.CENTER);

        YouTubeMixer youTubeMixer = new YouTubeMixer();
        SoundCloudMixer soundCloudMixer = new SoundCloudMixer();
        VimeoMixer vimeoMixer = new VimeoMixer();

        playersBox.getChildren().addAll(
            youTubeMixer.createYouTubeMixer(),
            soundCloudMixer.createSoundCloudMixer(),
            vimeoMixer.createVimeoMixer()
        );

        ytEngine = youTubeMixer.getEngine();
        scEngine = soundCloudMixer.getEngine();
        vmEngine = vimeoMixer.getEngine();

        miniModeButton.setOnAction(e -> {
            isMiniMode = !isMiniMode;
            if (isMiniMode) {
                playersBox.setVisible(false);
                title.setVisible(false);
                primaryStage.setAlwaysOnTop(true);
                primaryStage.setWidth(600);
                primaryStage.setHeight(250);
                miniModeButton.setText("Exit Toolbar Mode");
            } else {
                playersBox.setVisible(true);
                title.setVisible(true);
                primaryStage.setAlwaysOnTop(false);
                primaryStage.setWidth(1200);
                primaryStage.setHeight(600);
                miniModeButton.setText("Mini Toolbar Mode");
            }
        });

        masterSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double volume = newVal.doubleValue();
            masterValueLabel.setText(String.valueOf((int) volume));

            ytEngine.executeScript("document.getElementById('player').contentWindow.postMessage(" +
                "'{\"event\":\"command\",\"func\":\"setVolume\",\"args\":[" + volume + "]}'" + ", '*');");

            scEngine.executeScript("window.setVolume(" + volume + ");");
            vmEngine.executeScript("window.setVolume(" + volume + ");");
        });

        root.getChildren().addAll(title, topControls, masterControl, presetButtons, playersBox);

        Scene scene = new Scene(root, 1200, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); //CSS 

        primaryStage.setScene(scene);
        primaryStage.setTitle("YouTube, SoundCloud, and Vimeo Volume Mixer");
        primaryStage.show();
    }
}
