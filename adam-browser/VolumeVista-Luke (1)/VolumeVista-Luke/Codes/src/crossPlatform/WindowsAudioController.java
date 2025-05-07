package crossPlatform;

import com.sun.jna.*;
import com.sun.jna.ptr.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.HRESULT;

/**
 * WindowsAudioController controls system volume on Windows using JNA and WASAPI.
 */
public class WindowsAudioController implements AudioController {

    // COM GUIDs for accessing Core Audio interfaces
    private static final Guid.CLSID CLSID_MMDeviceEnumerator = new Guid.CLSID("{BCDE0395-E52F-467C-8E3D-C4579291692E}");
    private static final Guid.IID IID_IMMDeviceEnumerator = new Guid.IID("{A95664D2-9614-4F35-A746-DE8DB63617E6}");
    private static final Guid.IID IID_IAudioEndpointVolume = new Guid.IID("{5CDF2C82-841E-4546-9722-0CF74078229A}");
    private static final int CLSCTX_ALL = WTypes.CLSCTX_INPROC_SERVER | WTypes.CLSCTX_LOCAL_SERVER;

    /**
     * Get current system master volume (0–100).
     */
    @Override
    public int getVolume() {
        try {
            FloatByReference level = new FloatByReference();
            getEndpointVolume().GetMasterVolumeLevelScalar(level); // Gets volume scalar (0.0 to 1.0)
            return Math.round(level.getValue() * 100);
        } catch (Exception e) {
            return 50; // Return fallback volume on error
        }
    }

    /**
     * Set system master volume to a value between 0 and 100.
     */
    @Override
    public void setVolume(int volume) {
        if (volume < 0 || volume > 100) throw new IllegalArgumentException("Volume must be 0–100.");
        try {
            getEndpointVolume().SetMasterVolumeLevelScalar(volume / 100f, null);
        } catch (Exception ignored) {}
    }

    /**
     * Initialize COM and return the IAudioEndpointVolume interface.
     */
    private IAudioEndpointVolume getEndpointVolume() throws Exception {
        // Initialize COM for this thread
        Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);

        // Create IMMDeviceEnumerator
        PointerByReference ppv = new PointerByReference();
        Ole32.INSTANCE.CoCreateInstance(CLSID_MMDeviceEnumerator, null, CLSCTX_ALL, IID_IMMDeviceEnumerator, ppv);
        IMMDeviceEnumerator enumerator = new IMMDeviceEnumerator(ppv.getValue());

        // Get the default audio output device
        PointerByReference ppDevice = new PointerByReference();
        enumerator.GetDefaultAudioEndpoint(0, 0, ppDevice); // eRender, eConsole
        IMMDevice device = new IMMDevice(ppDevice.getValue());

        // Activate the IAudioEndpointVolume interface from the device
        PointerByReference ppVolume = new PointerByReference();
        device.Activate(IID_IAudioEndpointVolume, CLSCTX_ALL, null, ppVolume);

        return new IAudioEndpointVolume(ppVolume.getValue());
    }

    // ========== COM Interface Wrappers ==========

    /**
     * Wrapper for the IMMDeviceEnumerator COM interface.
     */
    static class IMMDeviceEnumerator extends Unknown {
        public IMMDeviceEnumerator(Pointer p) { super(p); }

        // Gets the default audio device for rendering
        public HRESULT GetDefaultAudioEndpoint(int flow, int role, PointerByReference ppDevice) {
            return (HRESULT) _invokeNativeObject(3, new Object[]{getPointer(), flow, role, ppDevice}, HRESULT.class);
        }
    }

    /**
     * Wrapper for the IMMDevice COM interface.
     */
    static class IMMDevice extends Unknown {
        public IMMDevice(Pointer p) { super(p); }

        // Activates a specified interface on the device
        public HRESULT Activate(Guid.IID iid, int ctx, Pointer params, PointerByReference result) {
            return (HRESULT) _invokeNativeObject(3, new Object[]{getPointer(), iid, ctx, params, result}, HRESULT.class);
        }
    }

    /**
     * Wrapper for the IAudioEndpointVolume COM interface.
     */
    static class IAudioEndpointVolume extends Unknown {
        public IAudioEndpointVolume(Pointer p) { super(p); }

        // Get the current master volume scalar (0.0 to 1.0)
        public HRESULT GetMasterVolumeLevelScalar(FloatByReference vol) {
            return (HRESULT) _invokeNativeObject(5, new Object[]{getPointer(), vol}, HRESULT.class);
        }

        // Set the master volume scalar (0.0 to 1.0)
        public HRESULT SetMasterVolumeLevelScalar(float vol, Pointer ctx) {
            return (HRESULT) _invokeNativeObject(6, new Object[]{getPointer(), vol, ctx}, HRESULT.class);
        }
    }
}
