package com.example.lichengnan.widget.slide;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 钉某人
 * github: https://github.com/DingMouRen
 * email: naildingmouren@gmail.com
 */


public interface OnSlideListener {

    void onSliding(RecyclerView.ViewHolder viewHolder, float ratio, int direction);

    void onSlided(RecyclerView.ViewHolder viewHolder, int direction);

    void onClear();

}
