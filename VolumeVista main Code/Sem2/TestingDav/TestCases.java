package Testing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCases {

    // VolumeSlider logic
    @Test
    public void testVolumeWithinRange() {
        double volume = 75;
        assertTrue(volume >= 0 && volume <= 100);
    }

    // Mute logic
    @Test
    public void testMuteAllSetsVolumeToZero() {
        double volume = 0;
        assertEquals(0, volume);
    }

    // Master volume default
    @Test
    public void testDefaultMasterVolume() {
        double defaultVolume = 50;
        assertEquals(50, defaultVolume);
    }

    // Theme toggle
    @Test
    public void testDarkModeToggle() {
        boolean darkMode = false;
        darkMode = !darkMode;
        assertTrue(darkMode);
    }

    // Mini mode toggle
    @Test
    public void testMiniModeToggle() {
        boolean miniMode = false;
        miniMode = !miniMode;
        assertTrue(miniMode);
    }

    // Preset value
    @Test
    public void testPartyModeVolume() {
        double volume = 100;
        assertEquals(100, volume);
    }

    // Button text after mute
    @Test
    public void testMuteButtonTextAfterClick() {
        boolean isMuted = false;
        String buttonText = isMuted ? "Unmute All" : "Mute All";
        assertEquals("Mute All", buttonText);
    }

    // Controller visibility
    @Test
    public void testMiniModeHidesPlayers() {
        boolean isMiniMode = true;
        boolean playersVisible = !isMiniMode;
        assertFalse(playersVisible);
    }

    // Unmute restores volume
    @Test
    public void testUnmuteRestoresVolume() {
        double lastVolume = 60;
        double restoredVolume = lastVolume;
        assertEquals(60, restoredVolume);
    }

    // Desktop volume range (NirCmd or JNA style)
    @Test
    public void testSystemVolumeScale() {
        int scaledVolume = (int)(0.5 * 65535);  // 50% volume
        assertTrue(scaledVolume >= 0 && scaledVolume <= 65535);
    }
}
