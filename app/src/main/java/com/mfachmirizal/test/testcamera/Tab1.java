package com.mfachmirizal.test.testcamera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.*;
import com.mfachmirizal.test.testcamera.backgroundprocess.UploadImageIntentService;
import com.mfachmirizal.test.testcamera.util.UtilitasGambar;
import java.io.File;
/**
 * Created by Fachmi on 15/02/2015.
 *
 */
public class Tab1 extends Fragment {
    //static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap bitmapImage = null;
    String imagepath;
    Button tombolKamera;
    Button tombolUpload;
    ImageView thumbImg;
    ImageView thumbImgUpload;
    TextView textView;
    ImageView thumbImgTab2;
    ProgressBar progressBarUpload;
    File currentPhotoFile;

    private UploadImageReceiver receiver;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_1,container,false);

        tombolKamera = (Button) v.findViewById(R.id.tombolKamera);
        tombolUpload  = (Button) v.findViewById(R.id.tombolUpload);
        thumbImg     =  (ImageView)v.findViewById(R.id.thumbImg);
        thumbImgUpload = (ImageView)v.findViewById(R.id.thumbImgUpload);
        textView = (TextView)v.findViewById(R.id.textView);
        progressBarUpload = (ProgressBar) v.findViewById(R.id.progressBarUpload);

        IntentFilter filter = new IntentFilter(UploadImageReceiver.PROCESS_ACTION_UPLOADIMAGE_PATH);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new UploadImageReceiver();
        getActivity().getApplicationContext().registerReceiver(receiver, filter);

        //progressBarUpload.setVisibility(View.GONE);
        uploadStateWidget(false);

        tombolKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCamera =  new Intent(getActivity().getApplicationContext(), CameraActivity.class);
                if (currentPhotoFile != null) if (currentPhotoFile.exists()) currentPhotoFile.delete();
                toCamera.putExtra(CameraActivity.IS_ACTION_VIEW, false);
                startActivityForResult(toCamera,CameraActivity.OPEN_CAMERA_CAPTURE);
            }
        });

        tombolUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagepath != null) {
                    uploadStateWidget(true);
                    Intent uploadimageIntent = new Intent(getActivity().getApplicationContext(), UploadImageIntentService.class);
                    uploadimageIntent.putExtra(UploadImageIntentService.REQUEST_BITMAP_PATH, imagepath);
                    uploadimageIntent.putExtra(UploadImageIntentService.REQUEST_SERVER_URL, "http://192.168.1.168:8585/obdev/ws/com.tripad.tetanggaku.keamananku.uploadimage");
                    //uploadimageIntent.putExtra(UploadImageIntentService.REQUEST_SERVER_URL, "http://192.168.1.10:8585/obdevpancaran/ws/com.tripad.tetanggaku.security.mobile.uploadimage");

                    getActivity().getApplicationContext().startService(uploadimageIntent);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Image kosong !",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CameraActivity.OPEN_CAMERA_CAPTURE) {
            Bundle extras = data.getExtras();
            if (resultCode == getActivity().RESULT_OK) {
                //bitmapImage = (Bitmap) extras.get(CameraActivity.GET_BITMAP);
                imagepath = (String) extras.get(CameraActivity.GET_IMAGE_PATH);
                currentPhotoFile = (File) extras.get(CameraActivity.GET_PHOTO_FILE);
                //Toast.makeText(getActivity().getApplicationContext(), fromcamera+" edited", Toast.LENGTH_SHORT).show();
                //thumbImg.setImageBitmap(bitmapImage);
                Toast.makeText(getActivity().getApplicationContext(), "Gambar Path : "+imagepath, Toast.LENGTH_LONG).show();
                textView.setText(imagepath);
                try {
                    bitmapImage = new UtilitasGambar().ambilBitmap(imagepath,0); //defaultnya 90
                    thumbImg.setImageBitmap(bitmapImage);
                    //thumbImgTab2.setImageBitmap(bitmapImage);
                }
                catch (Exception exc) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error : "+exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else if (resultCode == getActivity().RESULT_FIRST_USER) {
                //abaikan, batal ambil capture camera
            }
            else {
                String msg = (String) extras.get(CameraActivity.ERROR_MESSAGE);
                Toast.makeText(getActivity().getApplicationContext(), "Error Camera Activity : "+msg,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class UploadImageReceiver extends BroadcastReceiver{

        public static final String PROCESS_ACTION_UPLOADIMAGE_PATH = "com.mfachmirizal.test.testcamera.intent.action.UPLOADIMAGE";

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle extras = intent.getExtras();


            String reponseMessage = (String) extras.get(UploadImageIntentService.RESPONSE_MESSAGE);
            int reponseStatusCode = (int) extras.get(UploadImageIntentService.RESPONSE_STATUS_CODE);

            if (reponseStatusCode == 200) {
                String responseBitmapPath = (String) extras.get(UploadImageIntentService.RESPONSE_BITMAP_PATH);
                thumbImgUpload.setImageBitmap(new UtilitasGambar().ambilBitmap(responseBitmapPath, 0));
                Toast.makeText(getActivity().getApplicationContext(),reponseMessage, Toast.LENGTH_LONG).show();
                currentPhotoFile.delete();
            }
            else {
                Toast.makeText(getActivity().getApplicationContext(), "Error : "+reponseMessage, Toast.LENGTH_LONG).show();
            }
            uploadStateWidget(false);
        }


    }

    @Override
    public void onDestroy() {
        if (currentPhotoFile != null) if (currentPhotoFile.exists()) currentPhotoFile.delete();
        getActivity().getApplicationContext().unregisterReceiver(receiver);
        super.onDestroy();
    }

    //custom method
    private void uploadStateWidget(boolean p) {
        if (p) {
            progressBarUpload.setVisibility(View.VISIBLE);
            tombolUpload.setVisibility(View.INVISIBLE);
        }
        else {
            progressBarUpload.setVisibility(View.INVISIBLE);
            tombolUpload.setVisibility(View.VISIBLE);
        }
    }


}
