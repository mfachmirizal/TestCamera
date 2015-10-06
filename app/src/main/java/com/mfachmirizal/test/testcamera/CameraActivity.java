package com.mfachmirizal.test.testcamera;

/**
 * private android.hardware.Camera mCameraDevice;

 try {
 mCameraDevice = android.hardware.Camera.open();
 } catch (RuntimeException e) {
 Log.e(TAG, "fail to connect Camera", e);
 // Throw exception
 }
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mfachmirizal.test.testcamera.util.UtilitasGambar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int OPEN_CAMERA_CAPTURE = 70;

    public static final String IS_ACTION_VIEW = "IS_ACTION_VIEW";
    public static final String GET_IMAGE_PATH = "imagepath";
    public static final String GET_BITMAP = "bitmapfromcamera";
    public static final String GET_PHOTO_FILE = "getPhotoFile";
    public static final String ERROR_MESSAGE = "errMessage";


    String mCurrentPhotoPath;
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_camera);

        Intent intent = getIntent();
        boolean isActionView = intent.getBooleanExtra(IS_ACTION_VIEW, false);

        dispatchTakePictureIntent(isActionView);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Intent intentToBack=new Intent();
//            //Bundle extras = data.getExtras();
//            //Bitmap imageBitmap = (Bitmap) extras.get("data");
//            if (mCurrentPhotoPath != null) {
//                //Toast.makeText(this,"imageBitmap null dr camera",Toast.LENGTH_SHORT).show();
//                intentToBack.putExtra(GET_IMAGE_PATH,mCurrentPhotoPath );
//                //intentToBack.putExtra(GET_BITMAP, imageBitmap);
//                setResult(RESULT_OK, intentToBack);
//            }
//            else {
//                setResult(RESULT_CANCELED, intentToBack);
//            }
//            //mImageView.setImageBitmap(imageBitmap);
//            //Toast.makeText(this,"Oke",Toast.LENGTH_SHORT).show();
//
//
//            //setResult(RESULT_OK, intentToBack);
//        }
//        finish();
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String errMessage = "";
        Intent intentToBack=new Intent();
        if (requestCode == REQUEST_TAKE_PHOTO && photoFile != null && mCurrentPhotoPath != null && resultCode == RESULT_OK ) // && resultCode == RESULT_OK )
        {
            try
            {

             boolean sukses = new UtilitasGambar().compressAndReplaceJPEG(this,photoFile,70);
             if (!sukses) {
                 errMessage = "Compress Image Gagal !";
                 intentToBack.putExtra(ERROR_MESSAGE,errMessage);
                 setResult(RESULT_CANCELED, intentToBack);
                 finish();
             }


                intentToBack.putExtra(GET_IMAGE_PATH, mCurrentPhotoPath);
                intentToBack.putExtra(ERROR_MESSAGE, errMessage);
                intentToBack.putExtra(GET_PHOTO_FILE, photoFile);
                //intentToBack.putExtra(GET_BITMAP, imageBitmap);

                setResult(RESULT_OK, intentToBack);

            }  catch (IOException ie)
            {
                errMessage = ie.getMessage();
                intentToBack.putExtra(ERROR_MESSAGE, errMessage);
                setResult(RESULT_CANCELED, intentToBack);
                ie.printStackTrace();
                finish();
            }
            //return;
        }
        else if (photoFile == null || mCurrentPhotoPath == null) {
            if (photoFile == null) errMessage = "File"; else errMessage="Path";
            intentToBack.putExtra(ERROR_MESSAGE, errMessage + " Poto null !");
            setResult(RESULT_CANCELED, intentToBack);
        }
        else if (requestCode == RESULT_FIRST_USER) { //1
            //abaikan, batal capture image tanpa pesan apapun
            photoFile.delete();
            setResult(RESULT_FIRST_USER, intentToBack);
        }
        else  {
            errMessage = "Error ! : "+requestCode;
            intentToBack.putExtra(ERROR_MESSAGE, errMessage);
            setResult(RESULT_CANCELED, intentToBack);
        }
        finish();
    }



/*
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
*/
    private void dispatchTakePictureIntent(boolean isActionView) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            //File photoFile = null;
            try {
                photoFile = createImageFile(isActionView);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("CameraActivity","Error,dispatchTakePictureIntent : "+ex.getMessage());
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }else {
                Log.e("photoFileKosong", "photoFileKosong, mengulang proses ambil gambar");
            }
        }
    }


    //method custom

    private File createImageFile(boolean isActionView) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        String imageFileName = /*timeStamp +*/"TTGSecurity_"+timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        File image = new File(storageDir + File.separator + imageFileName+".jpg");
        image.createNewFile();

//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );

        // Save a file: path for use with ACTION_VIEW intents
        String av = "";
        if (isActionView) {
            av = "file:";
        }
        mCurrentPhotoPath = av + image.getAbsolutePath();
        return image;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
