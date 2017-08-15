package com.live.quanmin.autopicker.auto_imag;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.live.quanmin.autopicker.R;

import java.util.LinkedList;

/**
 * Created by guo_hx on 2017/8/14 9:58.
 */

public class ScrollImage extends RelativeLayout {

    private static final String TAG = ScrollImage.class.getSimpleName();

    private RecyclerView mIvScrollImage;
    private AutoImageAdapter mAdapter;
    private LinkedList<Integer> integerLinkedList;

    private boolean adapterIsFree = true;
    private int mNumber = 0;

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
        mAdapter = new AutoImageAdapter();
        mIvScrollImage.setAdapter(mAdapter);

        integerLinkedList = new LinkedList<>();
        mAdapter.setOnOneAnimFinishListener(new AutoImageAdapter.OnOneAnimFinishListener() {
            @Override
            public void OnOneAnimFinish() {
                Log.d(TAG, integerLinkedList.toString());
                adapterIsFree = true;
                if (integerLinkedList.size() > 0) {
                    integerLinkedList.remove(0);
                }
                if (integerLinkedList.size() > 0 && mAdapter.isFree()) {
                    mAdapter.setData(integerLinkedList.get(0));
                }
            }
        });
    }

    public void setData(Integer number) {//这里，因为产品的原因。写成只支持相同数字，连续。
        if (TextUtils.equals(mNumber + "", number + "") || mNumber == 0) {
            integerLinkedList.add(number);
        }
        mNumber = number;
        if (mAdapter.isFree() && adapterIsFree) {
            if (integerLinkedList.size() > 0) {
                mAdapter.setData(integerLinkedList.get(0));
            }
            adapterIsFree = false;
        }
    }

}
