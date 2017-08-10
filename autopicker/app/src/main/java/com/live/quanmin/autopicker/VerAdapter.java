package com.live.quanmin.autopicker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guo_hx on 2017/8/10 12:15.
 */

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
