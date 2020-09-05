package com.example.lichengnan.download;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * @author fengli
 * @date 2019/11/29
 * @description 下载文件
 */
public class DownLoadManager {
    private static DownLoadManager instance;

    private static Retrofit retrofit;

    private DownLoadManager(String url) {
        buildNetWork(url);
    }

    public static DownLoadManager getInstance(String url) {
        if (instance == null) {
            instance = new DownLoadManager(url);
        }
        return instance;
    }

    //下载
    public void load(String downUrl, final ProgressCallBack callBack) {
//        retrofit.create(ApiService.class)
//                .downloadFile(downUrl)
//                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
//                .observeOn(Schedulers.io()) //指定线程保存文件
//                .doOnNext(responseBody -> callBack.saveFile(responseBody))
//                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
//                .subscribe(new DownLoadSubscriber<>(callBack));
        retrofit.create(ApiService.class).downloadFile(downUrl).subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        callBack.saveFile(responseBody);
                    }
                }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callBack.onError(throwable);
            }
        }).observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        callBack.onSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void buildNetWork(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }


//    /**
//     * 将输入流写入文件
//     *
//     * @param inputString
//     * @param filePath
//     */
//    private void writeFile(InputStream inputString, String filePath) {
//
//        File file = new File(filePath);
//        if (file.exists()) {
//            file.delete();
//        }
//
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(file);
//
//            byte[] b = new byte[1024];
//
//            int len;
//            while ((len = inputString.read(b)) != -1) {
//                fos.write(b,0,len);
//            }
//            inputString.close();
//            fos.close();
//
//        } catch (FileNotFoundException e) {
//            listener.onFail("FileNotFoundException");
//        } catch (IOException e) {
//            listener.onFail("IOException");
//        }
//
//    }
}
