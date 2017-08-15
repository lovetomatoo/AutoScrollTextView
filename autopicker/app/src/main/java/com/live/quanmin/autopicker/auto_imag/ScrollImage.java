package com.live.quanmin.autopicker.auto_imag;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.live.quanmin.autopicker.R;

import java.util.List;

/**
 * Created by guo_hx on 2017/8/14 9:58.
 */

public class ScrollImage extends RelativeLayout {

    private RecyclerView mIvScrollImage;

    public ScrollImage(Context context) {
        super(context);
        init();
    }

    public ScrollImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //无敌是多么寂寞  无尽的寂寞。。。
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_scroll_image, this);
        mIvScrollImage = (RecyclerView) findViewById(R.id.rv_scroll_image);
        mIvScrollImage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void setData(Integer number) {

        mIvScrollImage.setAdapter(new AutoImageAdapter(number));
    }
}
