package com.example.azadmin.mytimer;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by lcadmin on 2016/3/25.
 */
public class Utils {
    public static Uri getPhotoUri(){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (dir.exists() == false){
            dir.mkdir();
        }
        File file = new File(dir, "my_photo.png");
        return Uri.fromFile(file);
    }
}
