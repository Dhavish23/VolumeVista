package JavaProjects.Sem2.CombinedCode;

import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.Pointer;

public class AudioManagerHelper {
    static {
        Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
    }

    public static void setMasterVolume(double volumePercent) {
        try {
            double finalVolume = volumePercent / 100.0;
            String command = String.format(
                "powershell.exe (Get-WmiObject -Namespace root\\cimv2 -Class Win32_SoundDevice | ForEach-Object {Set-Volume -Volume %.2f})",
                finalVolume * 100
            );
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}