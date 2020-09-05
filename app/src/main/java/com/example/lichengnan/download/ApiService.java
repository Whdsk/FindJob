package com.example.lichengnan.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author gaohangbo on 2018/10/31
 **/
public interface ApiService {

    /**
     * 下载文件
     *
     * @param path
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String path);
}

