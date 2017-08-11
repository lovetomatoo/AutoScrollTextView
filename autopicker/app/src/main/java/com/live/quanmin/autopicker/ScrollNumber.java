package com.live.quanmin.autopicker;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        if (number > 99999) {
            throw new RuntimeException("number can not > 99999 exception");
        }

        mRvRoot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvRoot.setAdapter(new HorAdapter(getContext(), number));
    }


    class HorAdapter extends RecyclerView.Adapter<HorAdapter.HorHolder> {

        private final ArrayList<String> listFilt;
        private Context context;
        private final List<String> listNumber;

        public HorAdapter(Context context, Integer number) {
            this.context = context;

            listNumber = new ArrayList<>();//[1, 3, 1, 4]
            String numberString = number + "";
            for (int i = 0; i < numberString.length(); i++) {
                listNumber.add(i, numberString.charAt(i) + "");
            }

            listFilt = new ArrayList<>();//[1, 13, 131, 1314]
            for (int i = 0; i <= listNumber.size(); i++) {
                Log.d("guohongxin", listFilt.size() + "----------------");
                String s = number + "";
                if (i >= 1) {
                    String substring = s.substring(0, i);
                    Log.i("HorAdapter", substring);
                    listFilt.add(substring);
                }

            }
        }

        @Override
        public HorAdapter.HorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_scroll_number, null);
            return new HorHolder(view);
        }

        @Override
        public void onBindViewHolder(HorAdapter.HorHolder holder, int position) {
            ScrollSpeedLinearLayoutManager layoutManager = new ScrollSpeedLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            Log.i("HorAdapter", "0.3除以多少 =  " + Math.pow(10, position - listNumber.size() + 1));
            double speed = 0.1f / Math.pow(10, position - listNumber.size() + 1);
            Log.i("HorAdapter", "speed == " + speed);
            layoutManager.setSpeed(speed);
            holder.mRvItem.setLayoutManager(layoutManager);
            List<String> scrollNumber = NumberUtil.getScrollNumber(listNumber.get(position), listFilt.get(position));
            holder.mRvItem.setAdapter(new VerAdapter(scrollNumber));//todo

            holder.mRvItem.smoothScrollToPosition(scrollNumber.size());
        }

        @Override
        public int getItemCount() {
            return listNumber == null ? 0 : listNumber.size();
        }

        class HorHolder extends RecyclerView.ViewHolder {

            private RecyclerView mRvItem;

            public HorHolder(View itemView) {
                super(itemView);
                mRvItem = (RecyclerView) itemView.findViewById(R.id.rv_root);
            }
        }

    }

    public class VerAdapter extends RecyclerView.Adapter<VerAdapter.VerHolder> {

        private List<String> list;

        public VerAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public VerAdapter.VerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_ver, null);
            return new VerHolder(view);
        }

        @Override
        public void onBindViewHolder(VerAdapter.VerHolder holder, int position) {
            holder.mTv.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        class VerHolder extends RecyclerView.ViewHolder {

            private TextView mTv;

            public VerHolder(View itemView) {
                super(itemView);
                mTv = (TextView) itemView.findViewById(R.id.tv);
            }
        }
    }

}
