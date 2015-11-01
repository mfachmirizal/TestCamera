package com.mfachmirizal.test.testcamera.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

/**
 * Created by mfachmirizal on 07-Oct-15.
 *
 */
public class TetanggakuGetIPList {
    public TetanggakuGetIPList() {
    }

    public String[] getLocalIpAddress2()
    {
        ArrayList<String> addresses = new ArrayList<String>();
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    if (!inetAddress.isLoopbackAddress())
//                    {
                        addresses.add(inetAddress.getHostAddress().toString());
                   // }
                }
            }
        }
        catch (SocketException ex)
        {
            Log.e("aw", ex.toString());
        }
        return addresses.toArray(new String[addresses.size()]);
    }

    public String[] getLocalIpAddress()
    {
        ArrayList<String> addresses = new ArrayList<String>();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        Log.i("DEBUG-LIST-IP",(inetAddress.getHostAddress().toString()));;
                        addresses.add(inetAddress.getHostAddress().toString());
                    }
                }
            }
        } catch (SocketException ex) {
            String LOG_TAG = null;
            Log.e(LOG_TAG, ex.toString());
        }
        return addresses.toArray(new String[0]);
    }

    public String getListingIp(Context c) {
        WifiManager mWifiManager = (WifiManager) c.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
        Log.i("IP in Mask Integer", mWifiInfo.getIpAddress() + "");
        Log.i("IP Address", intToIP(mWifiInfo.getIpAddress()) + "");

        return null;
    }

    public String intToIP(int i) {
        return (( i & 0xFF)+ "."+((i >> 8 ) & 0xFF)+
                "."+((i >> 16 ) & 0xFF)+"."+((i >> 24 ) & 0xFF));
    }

    //cara 2
    public void subnet() throws UnknownHostException, SocketException
    {
        Enumeration e = NetworkInterface.getNetworkInterfaces();
//        Data dt = new Data();
//        Client cl = new Client();
//        dt.setClientObject(cl);
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                String ip = i.getHostAddress();

                String sip = ip.substring(0, ip.indexOf('.',ip.indexOf('.',ip.indexOf('.')+1) + 1) + 1);
                try {
                    for(int it=1;it<=255;it++)
                    {
                        sip = sip+it;
                        InetAddress.getByName(sip).isReachable(100);
                        Log.i("DEBUG-LIST-IP",sip+" is online");
                        //cl.ask(sip);

                    }
                } catch (IOException e1) {
                    Log.i("DEBUG-LIST-IP", sip + " : "+e1.getMessage());
                }
            }
        }
    }
}
