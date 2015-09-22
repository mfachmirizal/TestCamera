package com.mfachmirizal.test.testcamera.backgroundprocess;

import java.io.ByteArrayOutputStream;
import java.io.File;
import org.apache.http.Header;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import com.loopj.android.http.*;
import com.mfachmirizal.test.testcamera.Tab1;

public class UploadImageIntentService  extends IntentService{

    public static final String REQUEST_BITMAP = "reqBitmap";
    public static final String REQUEST_BITMAP_PATH = "reqBitmapPath";
    public static final String RESPONSE_BITMAP = "resBitmap";
    public static final String RESPONSE_BITMAP_PATH = "resBitmapPath";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";


    public UploadImageIntentService() {
        super("UploadImageIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();


        String requestBitmapPath = (String) extras.get(REQUEST_BITMAP_PATH);
        String responseBitmapPath = requestBitmapPath;

        String responseMessage = "";

        try {
/*
            SyncHttpClient client = new SyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("l", "Openbravo");
            params.put("p", "openbravo");
            //params.put("image", new File(imagePath));

            client.post("http://example.com", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    // error handling
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    // success
                }
            });

*/

        }catch (Exception e) {
            e.printStackTrace();
            responseMessage = e.getMessage();
        }


        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Tab1.UploadImageReceiver.PROCESS_ACTION_UPLOADIMAGE_PATH);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(RESPONSE_BITMAP_PATH, responseBitmapPath);
        broadcastIntent.putExtra(RESPONSE_MESSAGE, responseMessage);
        sendBroadcast(broadcastIntent);

    }

}