package com.mfachmirizal.test.testcamera.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by mfachmirizal on 22-Sep-15.
 *
 */
public class UtilitasGambar {

    public UtilitasGambar() {

    }

    public Bitmap ambilBitmap(String path,int rotate) {
        File imgFile = new  File(path);
        if(imgFile.exists()){

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),options);
            myBitmap = rotateBitmap(myBitmap,rotate);

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

    /**
     *
     * @param ctx Android Context
     * @param photoFile File Photo yg akan di compress dan di replace
     * @param quality kualitas hasil compress
     * @return  false bila gagal dan true bila sukses
     * @throws IOException
     */
    public boolean compressAndReplaceJPEG(Context ctx,File photoFile,int quality) throws IOException{
        OutputStream outputStream = null;
        ByteArrayOutputStream baos = null;
        try {
        Bitmap cameraBmp = MediaStore.Images.Media.getBitmap(
                ctx.getApplicationContext().getContentResolver(),
                //.mainActivity.getContentResolver(),
                Uri.fromFile(photoFile));

        cameraBmp = ThumbnailUtils.extractThumbnail(cameraBmp, cameraBmp.getWidth(), cameraBmp.getHeight());
        // NOTE incredibly useful trick for cropping/resizing square
        // http://stackoverflow.com/a/17733530/294884

        Matrix m = new Matrix();
        m.postRotate( neededRotation(photoFile));

        cameraBmp = Bitmap.createBitmap(cameraBmp,
                0, 0, cameraBmp.getWidth(), cameraBmp.getHeight(),
                m, true);

        // to convert to bytes...
        baos = new ByteArrayOutputStream();
        cameraBmp.compress(Bitmap.CompressFormat.JPEG, quality, baos);

            outputStream = new FileOutputStream(photoFile);
            baos.writeTo(outputStream);
        } catch (FileNotFoundException x){
            x.printStackTrace();
            return false;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
        return true;
    }

    private int neededRotation(File ff)
    {
        try
        {

            ExifInterface exif = new ExifInterface(ff.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            { return 270; }
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            { return 180; }
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            { return 90; }
            return 0;

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException xxe)
        {
            xxe.printStackTrace();
        }
        return 0;
    }
}


