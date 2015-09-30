package com.mfachmirizal.test.testcamera.backgroundprocess;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpPostHC4;
//
//import org.apache.http.client.methods.HttpPostHC4;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.Header;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import com.loopj.android.http.*;
import com.mfachmirizal.test.testcamera.Tab1;
import com.mfachmirizal.test.testcamera.util.TetanggakuHttpURLConnection;
import com.mfachmirizal.test.testcamera.util.UtilitasGambar;

public class UploadImageIntentService  extends IntentService{

    public static final String REQUEST_BITMAP = "reqBitmap";
    public static final String REQUEST_SERVER_URL = "reqServerUrl";
    public static final String REQUEST_BITMAP_PATH = "reqBitmapPath";
    public static final String RESPONSE_BITMAP = "resBitmap";
    public static final String RESPONSE_BITMAP_PATH = "resBitmapPath";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";
    public static final String RESPONSE_STATUS_CODE = "responseStatusCode";

    String responseMessage = "";
    int status_code;
    String requestServerUrl;
    String requestBitmapPath;

    public UploadImageIntentService() {
        super("UploadImageIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();


        requestBitmapPath = (String) extras.get(REQUEST_BITMAP_PATH);
        requestServerUrl = (String) extras.get(REQUEST_SERVER_URL);

        String responseBitmapPath = requestBitmapPath;

        //upload1
        upload1(requestBitmapPath);

        //upload2
//        Bitmap bmp = new UtilitasGambar().ambilBitmap(requestBitmapPath);
//        upload2(requestServerUrl,bmp,"upload");
//        status_code = new TetanggakuHttpURLConnection().uploadFile(requestServerUrl,requestBitmapPath);
//
//        if (status_code != 200) {
//            responseMessage = "Upload Error ! : "+status_code;
//        }
//        else {
//            responseMessage = "Upload Gambar Berhasil !";
//        }


        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Tab1.UploadImageReceiver.PROCESS_ACTION_UPLOADIMAGE_PATH);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(RESPONSE_BITMAP_PATH, responseBitmapPath);
        broadcastIntent.putExtra(RESPONSE_MESSAGE, responseMessage);
        broadcastIntent.putExtra(RESPONSE_STATUS_CODE, status_code);
        sendBroadcast(broadcastIntent);

    }

    protected void upload1(String requestBitmapPath) {
        try {
//            Authenticator.setDefault(new Authenticator() {
//                @Override
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    String LOGIN = "Openbravo";
//                    String PWD = "openbravo";
//                    return new PasswordAuthentication(LOGIN, PWD.toCharArray());
//                }
//            });
            SyncHttpClient client = new SyncHttpClient();
            client.setBasicAuth("Openbravo","openbravo");
            client.setTimeout(50);
//            client.setBasicAuth("Openbravo","openbravo");
            RequestParams params = new RequestParams();
//            params.put("l", "Openbravo");
//            params.put("p", "openbravo");
            params.put("psn", "uiiiiiiiiiiiii");
//            params.put("or", "0");
            params.put("gambar", new File(requestBitmapPath));


            client.post(requestServerUrl, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    throwable.printStackTrace();
                    responseMessage = "Failure. (" + statusCode + ") (" + requestServerUrl + ") : " + throwable.getMessage();
                    status_code = statusCode;
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    responseMessage = "Success (" + statusCode + ") : " + responseString;
                    status_code = statusCode;
                }
            });


        }catch (Exception e) {
            e.printStackTrace();
            responseMessage = e.getMessage();
        }
    }
/**/
/*
    protected void upload2(String url,Bitmap bm,String namafile) {
        HttpPost httppost = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        //MultipartEntityBuilder entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        HttpClient httpClient = HttpClientBuilder.create().build();
        if(bm!=null){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 75, bos);
            byte[] data = bos.toByteArray();
            ByteArrayBody bab = new ByteArrayBody(data, namafile+".jpg");
            builder.addPart("file", bab);
        }
        try {
            StringBody suser = new StringBody("Openbravo", ContentType.TEXT_PLAIN);
            builder.addPart("l", suser);

            StringBody spass = new StringBody("openbravo", ContentType.TEXT_PLAIN);
            builder.addPart("p", spass);


        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        httppost.setEntity(builder.build());
        try {
            httpClient.execute(httppost);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }/**/

}