package com.live.quanmin.autopicker.auto_imag;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private LinkedList<Integer> mIntegerLinkedList;

    private boolean adapterIsFree = true;

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

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_scroll_image, this);
        mIvScrollImage = (RecyclerView) findViewById(R.id.rv_scroll_image);
        mIvScrollImage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new AutoImageAdapter();
        mIvScrollImage.setAdapter(mAdapter);

        mIntegerLinkedList = new LinkedList<>();
        mAdapter.setOnOneAnimFinishListener(new AutoImageAdapter.OnOneAnimFinishListener() {
            @Override
            public void OnOneAnimFinish() {
                Log.d(TAG, mIntegerLinkedList.toString());
                adapterIsFree = true;
                if (mIntegerLinkedList.size() > 0) {
                    mIntegerLinkedList.remove(0);
                }
                if (mIntegerLinkedList.size() > 0 && mAdapter.isFree()) {
                    mAdapter.setData(mIntegerLinkedList.get(0));
                }
            }
        });
    }

    public void setData(Integer number) {
        mIntegerLinkedList.add(number);
        if (mAdapter.isFree() && adapterIsFree) {
            if (mIntegerLinkedList.size() > 0) {
                mAdapter.setData(mIntegerLinkedList.get(0));
            }
            adapterIsFree = false;
        }
    }

}
