package crossPlatform;

public class AudioControllerFactory {
    public static AudioController getAudioController() {
        // Get the operating system name
        String os = System.getProperty("os.name").toLowerCase();

        // Return the appropriate implementation based on the OS
        if (os.contains("win")) {
            return new WindowsAudioController();
        }
        else {
            throw new UnsupportedOperationException("Unsupported OS: " + os);
        }
    }
}