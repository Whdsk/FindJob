package com.example.lichengnan.download;

import android.Manifest;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;


import com.example.lichengnan.application.EyepetizerApplication;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * author gaohangbo
 * date: 2018/7/10 0010.
 */
public class AppFileHelper {
    private static boolean OVERM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    private static final String TAG = "AppFileHelper";

    public static final String[] INTERNAL_STORAGE_PATHS = new String[]{"/mnt/", "/emmc/"};
    public static final String DATA_PATH = "data/";
    public static final String CACHE_PATH = "cache/";
    public static final String PIC_PATH = "pic/";
    public static final String CAMERA_PATH = "pic/camera/";
    public static final String LOG_PATH ="log/";
    public static final String DOWNLOAD_PATH = "download/";
    public static final String TEMP_PATH = "temp/";

    public static void initStoragePath(FragmentActivity activity) {
        if (OVERM) {
            RxPermissions rxPermissions = new RxPermissions(activity);
            rxPermissions
                    .requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(permission -> { // will emit 2 Permission objects
                        if (permission.granted) {
                            initStroagePathInternal();
                            // `permission.name` is granted !
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //T.showShort("权限被拒绝了,可能导致该应用无法正常使用");
                        } else {
                            //T.shortLong("请打开读写外部存储权限");
                        }
                    });
        }
        else {
            initStroagePathInternal();
        }
    }

    private static void initStroagePathInternal() {

        File dataDir = EyepetizerApplication.context.getExternalFilesDir(DATA_PATH);
        checkAndMakeDir(dataDir);

        File cacheDir = EyepetizerApplication.context.getExternalFilesDir(CACHE_PATH);
        checkAndMakeDir(cacheDir);

        File picDir = EyepetizerApplication.context.getExternalFilesDir(PIC_PATH);
        checkAndMakeDir(picDir);

        File cameraDir = EyepetizerApplication.context.getExternalFilesDir(CAMERA_PATH);
        checkAndMakeDir(cameraDir);

        File logDir = EyepetizerApplication.context.getExternalFilesDir(LOG_PATH);
        checkAndMakeDir(logDir);
    }

    private static void checkAndMakeDir(File file) {
        if (!file.exists()) {
            boolean result = file.mkdirs();
            Log.i(TAG, "mkdirs  >>>  " + file.getAbsolutePath() + "  success  >>  " + result);
        }
    }

    public static String getAppDataPath() {
        File dataDir = EyepetizerApplication.context.getExternalFilesDir(DATA_PATH);
        checkAndMakeDir(dataDir);
        Log.i(TAG, "getAppDataPath  >>>  " + dataDir.getAbsolutePath());
        return dataDir.getAbsolutePath();
    }


    public static String getAppCachePath() {
        File dataDir = EyepetizerApplication.context.getExternalFilesDir(CACHE_PATH);
        checkAndMakeDir(dataDir);
        Log.i(TAG, "getAppCachePath  >>>  " + dataDir.getAbsolutePath());
        return dataDir.getAbsolutePath();
    }

    public static String getAppPicPath() {
        File dataDir = EyepetizerApplication.context.getExternalFilesDir(PIC_PATH);
        checkAndMakeDir(dataDir);
        Log.i(TAG, "getAppPicPath  >>>  " + dataDir.getAbsolutePath());
        return dataDir.getAbsolutePath();
    }

    public static String getCameraPath() {
        File dataDir = EyepetizerApplication.context.getExternalFilesDir(CAMERA_PATH);
        checkAndMakeDir(dataDir);
        Log.i(TAG, "getCameraPath  >>>  " + dataDir.getAbsolutePath());
        return dataDir.getAbsolutePath();
    }

    public static String getAppLogPath() {
        File dataDir = EyepetizerApplication.context.getExternalFilesDir(LOG_PATH);
        checkAndMakeDir(dataDir);
        Log.i(TAG, "getCameraPath  >>>  " + dataDir.getAbsolutePath());
        return dataDir.getAbsolutePath();
    }


    public static String getAppDownLoadPath() {
        File dataDir = EyepetizerApplication.context.getExternalFilesDir(DOWNLOAD_PATH);
        checkAndMakeDir(dataDir);
        Log.i(TAG, "getAppDownLoadPath  >>>  " + dataDir.getAbsolutePath());
        return dataDir.getAbsolutePath();
    }

    public static String createShareImageName() {
        return createImageName(false);
    }

    public static String createImageName(boolean isJpg) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMdd_HHmmss", Locale.US);
        return dateFormat.format(date) + (isJpg ? ".jpg" : ".png");
    }

    public static String createCropImageName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMdd_HHmmss", Locale.US);
        return dateFormat.format(date) + "_crop.png";
    }

   public static void deleteFile() {
       String storagePath=null;
            //M开始用的filePorider
            if (!OVERM) {
                storagePath = FileUtils.getStoragePath(EyepetizerApplication.context, false);
            }
            if (TextUtils.isEmpty(storagePath)) {
                //没有路径就使用getExternalStorageDirectory
                storagePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                if (TextUtils.isEmpty(storagePath)) {
                    //依然没法创建路径则使用私有的
                    storagePath = EyepetizerApplication.context.getFilesDir().getAbsolutePath();
                }
            }
        storagePath = FileUtils.checkFileSeparator(storagePath);
        Log.i(TAG, "storagepath  >>  " + storagePath);

        File rootDir = new File(storagePath.concat("com.lixing.exampletest/"));
        if (rootDir.exists()) {
            FileUtil.deleteFile(storagePath.concat("com.lixing.exampletest/"));
        }
   }
}
