package com.live.quanmin.autopicker;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.live.quanmin.autopicker.tiker.TickerUtils;
import com.live.quanmin.autopicker.tiker.TickerView;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();

    private TickerView mTickerView;
    private int val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTickerView = (TickerView) findViewById(R.id.ticker);

        mTickerView.setCharacterList(NUMBER_LIST);
        mTickerView.setAnimationDuration(500);

//        final int digits = RANDOM.nextInt(2) + 6;
//        mTickerView.setText(getRandomNumber(digits));
    }

    @Override
    protected void onResume() {
        val = 0;
        super.onResume();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        val = val + 1111;
                        mTickerView.setText(1314 + val + "", true);
                    }
                }, 2000);
    }
}
