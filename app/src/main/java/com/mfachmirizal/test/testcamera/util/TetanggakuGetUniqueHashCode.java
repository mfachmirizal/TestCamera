package com.mfachmirizal.test.testcamera.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * Created by mfachmirizal on 07-Oct-15.
 *
 */
public class TetanggakuGetUniqueHashCode {
    public TetanggakuGetUniqueHashCode() {
    }

    public String getThisDeviceUniqueHashCode(ContextWrapper cw) {
        final TelephonyManager tm = (TelephonyManager)cw.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(cw.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }
}
