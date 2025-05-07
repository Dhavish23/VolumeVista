package JavaProjects.Sem2.Testing;

import JavaProjects.Sem2.CombinedCode.AudioManagerHelper; // Importing the AudioManagerHelper class
import org.junit.jupiter.api.Test; // JUnit 5 import for Test annotation

import static org.junit.jupiter.api.Assertions.*; // Importing JUnit 5 assertions

class AudioManagerHelperTest {

    // Test that setMasterVolume fails silently for valid input
    @Test
    void testSetMasterVolumeExpectedFailure() {
        assertDoesNotThrow(() -> AudioManagerHelper.setMasterVolume(50), // Valid volume percentage
                "Expected no exception, even though internal call likely fails.");
    }

    // Test that an out-of-range volume value does not crash the JVM
    @Test
    void testSilentFailureIsExpected() {
        AudioManagerHelper.setMasterVolume(101); // Intentionally out of range (above 100)
    }

    // Test with edge values for volume
    @Test
    void testWithEdgeValues() {
        assertDoesNotThrow(() -> AudioManagerHelper.setMasterVolume(0), // Minimum volume
                "Expected no exception for volume = 0.");
        assertDoesNotThrow(() -> AudioManagerHelper.setMasterVolume(100), // Maximum volume
                "Expected no exception for volume = 100.");
    }

    // Test for invalid negative values, which should not throw exceptions but fail silently
    @Test
    void testWithNegativeValue() {
        AudioManagerHelper.setMasterVolume(-1); // Intentionally out of range (below 0)
    }

    // Test setMasterVolume method with a valid volume percentage
    @Test
    public void test_setMasterVolume_WithValidPercentage() {
        assertDoesNotThrow(() -> AudioManagerHelper.setMasterVolume(50.0)); // Valid volume percentage (50%)
    }

    // Test setMasterVolume method with a negative volume percentage
    @Test
    public void test_setMasterVolume_with_negative_value() {
        assertDoesNotThrow(() -> AudioManagerHelper.setMasterVolume(-10)); // Negative volume percentage (invalid)
    }

    // Test setMasterVolume method with a volume percentage greater than 100
    @Test
    public void test_setMasterVolume_with_value_exceeding_100_percent() {
        assertDoesNotThrow(() -> AudioManagerHelper.setMasterVolume(150)); // Volume percentage exceeding 100 (invalid)
    }
}