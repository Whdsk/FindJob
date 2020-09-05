package com.example.lichengnan.download;

/**
 * created by gaohangbo on 2020/6/11
 * description 刷新进度
 */
public class RxMessageRating {
    public RxMessageRating(String messageType, boolean hasRecode, float rating) {
        this.messageType=messageType;
        this.hasRecode=hasRecode;
        this.rating = rating;
    }

    private String messageType;
    private float rating;
    private boolean hasRecode;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isHasRecode() {
        return hasRecode;
    }

    public void setHasRecode(boolean hasRecode) {
        this.hasRecode = hasRecode;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
