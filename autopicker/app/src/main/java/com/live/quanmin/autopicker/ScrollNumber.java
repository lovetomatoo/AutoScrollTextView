package com.live.quanmin.autopicker;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guo_hx on 2017/8/10 11:45.
 */

public class ScrollNumber extends RelativeLayout {

    private RecyclerView mRvRoot;

    public ScrollNumber(Context context) {
        super(context);
        init();
    }

    public ScrollNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_scroll_number, this);
        mRvRoot = (RecyclerView) findViewById(R.id.rv_root);


    }


    //-----------------------------PUBLIC METHOD------------------------------------

    public void setNumber(Integer number) {

        mRvRoot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvRoot.setAdapter(new HorAdapter(getContext(), number));
    }


}
