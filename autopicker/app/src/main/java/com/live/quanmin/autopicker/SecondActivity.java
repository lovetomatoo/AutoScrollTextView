package com.live.quanmin.autopicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by guo_hx on 2017/8/10 11:44.
 */

public class SecondActivity extends AppCompatActivity {

    private ScrollNumber mSn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mSn = (ScrollNumber) findViewById(R.id.sn);
        mSn.setNumber(99);
    }
}
