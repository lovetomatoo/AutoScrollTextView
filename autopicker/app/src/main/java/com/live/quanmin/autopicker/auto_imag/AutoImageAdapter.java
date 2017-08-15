package com.live.quanmin.autopicker.auto_imag;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.live.quanmin.autopicker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guo_hx on 2017/8/14 10:22.
 */

public class AutoImageAdapter extends RecyclerView.Adapter<AutoImageAdapter.Holder> {

    private ArrayList<Integer> listFilt;
    private List<Integer> listNumber;
    private Integer mNumber;

    public AutoImageAdapter(Integer number) {
        mNumber = number;

        listNumber = new ArrayList<>();//[1, 3, 1, 4]
        String numberString = number + "";
        for (int i = 0; i < numberString.length(); i++) {
            try {
                listNumber.add(i, Integer.parseInt(numberString.charAt(i) + ""));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        listFilt = new ArrayList<>();//[1, 13, 131, 1314]
        for (int i = 0; i <= listNumber.size(); i++) {
            Log.d("guohongxin", listFilt.size() + "----------------");
            String s = number + "";
            if (i >= 1) {
                String substring = s.substring(0, i);
                Log.i("HorAdapter", substring);
                try {
                    listFilt.add(Integer.parseInt(substring));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override

    public AutoImageAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_scroll_image, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final AutoImageAdapter.Holder holder, final int position) {

        //整数圈
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.mIvScroll, "translationY", -65, -710);
        objectAnimator.setInterpolator(new LinearInterpolator());
        final int repeatCount = listFilt.get(position) / 10;
        if (position != 0) {
            objectAnimator.setRepeatCount(repeatCount - 1);
        }
        final long duration;
        if (position == 0) {
            duration = 0;
        } else {
            duration = (repeatCount * 10 * mNumber * 10) / (repeatCount * 10 + listNumber.get(position)) / repeatCount;//这个，需要解释一下
        }
        objectAnimator.setDuration(duration);
        Log.d("guohongxin", "信息： position = " + position + "duration  = " + duration + "repeatCount = " + repeatCount);
        objectAnimator.start();
        //剩余的不到一圈
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                int px = -65 * (listNumber.get(position) + 1);
                ObjectAnimator objectAnimator_surplus = ObjectAnimator.ofFloat(holder.mIvScroll, "translationY", -65, px);
                int duration_surplus;
                if (position == 0) {
                    duration_surplus = mNumber * 10;
                } else {
                    duration_surplus = (listNumber.get(position) * mNumber * 10) / (repeatCount * 10 + listNumber.get(position)) / repeatCount;//这个，需要解释一下
                }
                objectAnimator_surplus.setDuration(duration_surplus);
                Log.d("guohongxin", "最后一圈信息： position = " + position + "duration  = " + duration + "px = " + px);
                objectAnimator_surplus.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return listNumber == null ? 0 : listNumber.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private ImageView mIvScroll;

        public Holder(View itemView) {
            super(itemView);
            mIvScroll = (ImageView) itemView.findViewById(R.id.iv_scroll);
        }
    }
}
