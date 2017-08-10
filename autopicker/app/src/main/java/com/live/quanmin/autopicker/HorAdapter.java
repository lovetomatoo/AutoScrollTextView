package com.live.quanmin.autopicker;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guo_hx on 2017/8/10 12:14.
 */

class HorAdapter extends RecyclerView.Adapter<HorAdapter.HorHolder> {

    private final ArrayList<String> listFilt;
    private Context context;
    private Integer number;
    private final List<String> listNumber;

    public HorAdapter(Context context, Integer number) {
        this.context = context;
        this.number = number;

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
        double speed = 0.1f / Math.pow(20, position - listNumber.size() + 1);
        Log.i("HorAdapter", "speed == " + speed);
        layoutManager.setSpeed(speed);
        holder.mRvItem.setLayoutManager(layoutManager);
        List<String> scrollNumber = NumberUtil.getScrollNumber(listNumber.get(position), listFilt.get(position));
        holder.mRvItem.setAdapter(new VerAdapter(scrollNumber));//todo

//        if (position == listNumber.size() - 1) {
        holder.mRvItem.smoothScrollToPosition(scrollNumber.size());
//        }
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
