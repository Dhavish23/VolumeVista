import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.IUnknown;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT.HRESULT;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.platform.win32.MMDeviceAPI;
import com.sun.jna.platform.win32.MMDeviceAPI.IMMDevice;
import com.sun.jna.platform.win32.MMDeviceAPI.IMMDeviceEnumerator;
import com.sun.jna.platform.win32.MMDeviceAPI.IMMDeviceCollection;
import com.sun.jna.platform.win32.WinDef.DWORD;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class WindowsAudioMixer {

    // Interface for Windows Core Audio API
    public interface IAudioEndpointVolume extends IUnknown {
        HRESULT GetMasterVolumeLevelScalar(FloatByReference volume); // Get volume
        HRESULT SetMasterVolumeLevelScalar(float volume); // Set volume
    }

    public VBox createWindowsAudioMixer() {
        VBox root = new VBox(10); // Spacing between elements
        root.setAlignment(Pos.CENTER); // Center elements

        Label title = new Label("System Audio");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Slider volumeSlider = new Slider(0, 100, 50); // Volume slider
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(25);
        volumeSlider.setBlockIncrement(10);

        FloatByReference currentVolume = new FloatByReference();
        IAudioEndpointVolume endpointVolume = getAudioEndpointVolume();
        if (endpointVolume != null) {
            HRESULT result = endpointVolume.GetMasterVolumeLevelScalar(currentVolume);
            if (COMUtils.SUCCEEDED(result)) {
                volumeSlider.setValue(currentVolume.getValue() * 100); // Set slider
            }
        }

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            float volume = newVal.floatValue() / 100; // Convert to scalar
            if (endpointVolume != null) {
                endpointVolume.SetMasterVolumeLevelScalar(volume); // Update volume
            }
        });

        root.getChildren().addAll(title, volumeSlider);
        return root;
    }

    private IAudioEndpointVolume getAudioEndpointVolume() {
        HRESULT hr = Ole32.INSTANCE.CoInitializeEx(null, Ole32.COINIT_APARTMENTTHREADED);
        if (!COMUtils.SUCCEEDED(hr)) {
            System.err.println("COM init failed: " + hr.intValue());
            return null;
        }

        try {
            IMMDeviceEnumerator deviceEnumerator = COMUtils.createInstance(
                MMDeviceAPI.CLSID_MMDeviceEnumerator,
                IMMDeviceEnumerator.class
            );

            IMMDevice device = deviceEnumerator.GetDefaultAudioEndpoint(
                MMDeviceAPI.EDataFlow.eRender,
                MMDeviceAPI.ERole.eMultimedia
            );

            return device.Activate(
                MMDeviceAPI.IID_IAudioEndpointVolume,
                new DWORD(0),
                null
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Ole32.INSTANCE.CoUninitialize();
        }
    }
}