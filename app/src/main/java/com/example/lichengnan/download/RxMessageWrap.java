package com.example.lichengnan.download;

import android.os.Bundle;

/**
 * @author zhengxiaoke
 * @date 2020/5/12 5:31 PM
 * @describe
 */
public class RxMessageWrap {
    private String action;
    private Bundle data;

    public RxMessageWrap(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Bundle getData() {
        return data;
    }

    public void setData(Bundle data) {
        this.data = data;
    }
}
