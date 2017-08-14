package com.live.quanmin.autopicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.live.quanmin.autopicker.auto_imag.ScrollImage;

/**
 * Created by guo_hx on 2017/8/14 9:56.
 */

public class ThirdActivity extends AppCompatActivity {

    private ScrollImage mScrollImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mScrollImage = (ScrollImage) findViewById(R.id.scroll_image);
        mScrollImage.setData(9999);
    }
}
