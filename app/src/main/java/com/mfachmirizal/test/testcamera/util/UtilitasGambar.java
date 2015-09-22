package com.mfachmirizal.test.testcamera.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by mfachmirizal on 22-Sep-15.
 */
public class UtilitasGambar {

    public UtilitasGambar() {

    }

    public Bitmap ambilBitmap(String path) {
        File imgFile = new  File(path);
        if(imgFile.exists()){

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),options);
            myBitmap = rotateBitmap(myBitmap,90);

//            Bitmap myBitmap = null;
//            try {
//                 myBitmap = getAndRotateBitmap(imgFile);
//            } catch (IOException ioe) {
//                ioe.printStackTrace();
//            }
            return myBitmap;
        }
        return null;
    }

    private Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth() , source.getHeight() , matrix, true);
    }
}


