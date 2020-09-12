package com.example.lichengnan.ui.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lichengnan.BaseActivity;
import com.example.lichengnan.R;
import com.example.lichengnan.application.EyepetizerApplication;
import com.example.lichengnan.widget.skidright.SkidRightLayoutManager;

/**
 * Created by 钉某人
 * github: https://github.com/DingMouRen
 * email: naildingmouren@gmail.com
 */

public class SkidRightActivity_1 extends BaseActivity {

    private RecyclerView mRecyclerView;
    private SkidRightLayoutManager mSkidRightLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skid_1);


        initView();
    }


    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);

        mSkidRightLayoutManager = new SkidRightLayoutManager(1.5f, 0.85f);
        mRecyclerView.setLayoutManager(mSkidRightLayoutManager);
        mRecyclerView.setAdapter(new MyAdapter());
    }

    /**
     * 适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private int[] imgs = {
                R.mipmap.skid_right_1,
                R.mipmap.skid_right_2,
                R.mipmap.skid_right_3,
                R.mipmap.skid_right_4,
                R.mipmap.skid_right_5,
                R.mipmap.skid_right_6,
                R.mipmap.skid_right_7,

        };
        String[] titles = {"Acknowl", "Belief", "Confidence", "Dreaming", "Happiness", "Confidence"};

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(EyepetizerApplication.context).inflate(R.layout.item_skid_right_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            Glide.with(EyepetizerApplication.context).load(imgs[position % 7]).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.imgBg);
            holder.tvTitle.setText(titles[position % 6]);
            holder.imgBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SkidRightActivity_1.this, SkidRightActivity_2.class);
                    intent.putExtra("img", imgs[position % 7]);
                    intent.putExtra("title", titles[position % 6]);
                    Pair<View, String> p1 = Pair.create((View)holder.imgBg, "img_view_1");
                    Pair<View, String> p2 = Pair.create((View)holder.tvTitle, "title_1");
                    Pair<View, String> p3 = Pair.create((View)holder.tvBottom, "tv_bottom");
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(SkidRightActivity_1.this, p1,p2,p3);
                    startActivity(intent,options.toBundle());
                }
            });
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgBg;
            TextView tvTitle;
            TextView tvBottom;

            public ViewHolder(View itemView) {
                super(itemView);
                imgBg = itemView.findViewById(R.id.img_bg);
                tvTitle = itemView.findViewById(R.id.tv_title);
                tvBottom = itemView.findViewById(R.id.tv_bottom);
            }
        }
    }
}
