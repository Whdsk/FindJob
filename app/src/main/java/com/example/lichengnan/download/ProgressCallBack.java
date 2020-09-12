package com.example.lichengnan.download;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 *
 * @author fengli
 * @date 2019/11/29
 */
public abstract class ProgressCallBack {

    /**
     * 本地文件存放路径-系统根目录
     */
    //private String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String destFileDir = AppFileHelper.getAppDownLoadPath();
    /**
     * 文件名
     */
    private String destFileName;
    private Disposable mSubscription;

    protected ProgressCallBack(String destFileName) {
        this.destFileName = destFileName;
        subscribeLoadProgress();
    }

    public abstract void onSuccess();

    public abstract void progress(long progress, long total);

    public void onStart() {
    }

    void onCompleted() {
    }

    public abstract void onError(Throwable e);

    void saveFile(ResponseBody body) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            unsubscribe();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                //LogUtil.e("ProgressCallBack", "saveFile" + e.getMessage());
            }
        }
    }

    /**
     * 订阅加载的进度条
     */
    private void subscribeLoadProgress() {
        mSubscription = RxBus.getDefault().toObservable(DownLoadStateBean.class)
                .observeOn(AndroidSchedulers.mainThread()) //回调到主线程更新UI
                .subscribe(progressLoadBean -> progress(progressLoadBean.getBytesLoaded(), progressLoadBean.getTotal()));
        //将订阅者加入管理站
        RxSubscriptions.add(mSubscription);
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    private void unsubscribe() {
        RxSubscriptions.remove(mSubscription);
    }
}