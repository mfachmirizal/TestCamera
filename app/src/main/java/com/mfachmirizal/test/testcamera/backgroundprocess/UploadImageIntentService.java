package com.mfachmirizal.test.testcamera.backgroundprocess;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mfachmirizal.test.testcamera.Tab1;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class UploadImageIntentService  extends IntentService{

    public static final String REQUEST_BITMAP = "reqBitmap";
    public static final String REQUEST_SERVER_URL = "reqServerUrl";
    public static final String REQUEST_BITMAP_PATH = "reqBitmapPath";
    //public static final String REQUEST_BITMAP_NAME = "reqBitmapName";

    public static final String RESPONSE_BITMAP = "resBitmap";
    public static final String RESPONSE_BITMAP_PATH = "resBitmapPath";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";
    public static final String RESPONSE_STATUS_CODE = "responseStatusCode";

    String responseMessage = "";
    int status_code;
    String requestServerUrl;
    String requestBitmapPath;
    String requestBitmapname;

    public UploadImageIntentService() {
        super("UploadImageIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        requestBitmapPath = (String) extras.get(REQUEST_BITMAP_PATH);
        requestServerUrl = (String) extras.get(REQUEST_SERVER_URL);

        //upload dengan menggunakan library loopj
        try {
            //AsyncHttpClient client = new AsyncHttpClient(); <-- ini untuk get
            SyncHttpClient client = new SyncHttpClient();
            client.setBasicAuth("Openbravo","openbravo");
            client.setTimeout(53);
            RequestParams params = new RequestParams();
            params.put("gambar", new File(requestBitmapPath));


            client.post(requestServerUrl, params, new TextHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode,Header[] headers, String response) {
                    responseMessage = "Success : " + response;
                    status_code = statusCode;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
                    e.printStackTrace();
                    if (statusCode == 0) {
                        responseMessage = "Gagal Terhubung Ke server, silahkan hubungi developer Tripad bila masih berlanjut";
                        status_code = statusCode;
                    }
                    else {
                        responseMessage = "Failure. (" + statusCode + ") : " + e.getMessage();
                        status_code = statusCode;
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
            responseMessage = e.getMessage();
        }
 
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
        broadcastIntent.putExtra(RESPONSE_BITMAP_PATH, requestBitmapPath);
        broadcastIntent.putExtra(RESPONSE_MESSAGE, responseMessage);
        broadcastIntent.putExtra(RESPONSE_STATUS_CODE, status_code);
        sendBroadcast(broadcastIntent);

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