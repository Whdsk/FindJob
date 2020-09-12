package com.example.lichengnan.ui.activity;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lichengnan.R;

/**
 * Created by 钉某人
 * github: https://github.com/DingMouRen
 * email: naildingmouren@gmail.com
 */


public class SkidRightActivity_2 extends AppCompatActivity {
    private ImageView mImgBg;
    private ImageView mImgGif;
    private TextView mTvTitle;
    private  int mImgPath;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skid_2);
        mImgBg = findViewById(R.id.img_bg);
        mTvTitle = findViewById(R.id.tv_title);
        mImgGif = findViewById(R.id.img_gif);
        if (getIntent() != null){
            mImgPath = getIntent().getIntExtra("img", R.mipmap.skid_right_3);
            String title = getIntent().getStringExtra("title");
            mTvTitle.setText(title);
            Glide.with(this).load(mImgPath).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(mImgBg);
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   Glide.with(SkidRightActivity_2.this).load(mImgPath).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(mImgGif);
               }
           },1000);

        }
    }



    @Override
    public void onBackPressed() {
        mImgGif.setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }
}
