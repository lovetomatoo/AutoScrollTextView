package com.live.quanmin.autopicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.live.quanmin.autopicker.auto_imag.ScrollImage;

/**
 * Created by guo_hx on 2017/8/14 9:56.
 */

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {

    private ScrollImage mScrollImage;
    private EditText mEtInput;
    private Button mBtnOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mEtInput = (EditText) findViewById(R.id.et_input);
        mBtnOk = (Button) findViewById(R.id.btn_ok);

        mScrollImage = (ScrollImage) findViewById(R.id.scroll_image);

        mBtnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                String strInput = mEtInput.getText().toString().trim();
                if (TextUtils.isEmpty(strInput)) {
                    Toast.makeText(this, "sb", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int number = Integer.parseInt(strInput);
                    mScrollImage.setData(number);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
