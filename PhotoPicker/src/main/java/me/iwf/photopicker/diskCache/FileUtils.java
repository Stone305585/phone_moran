package me.iwf.photopicker.diskCache;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by stone on 2016/3/18.
 */
public class FileUtils {


    // =======================================
    // ============== 序列化 数据 读写 =============
    // =======================================

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        try {
            if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) && context.getExternalCacheDir() != null) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
            return new File(cachePath + File.separator + uniqueName);
        } catch (NullPointerException e) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                cachePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
            return new File(cachePath + File.separator + uniqueName);
        }
    }

}
