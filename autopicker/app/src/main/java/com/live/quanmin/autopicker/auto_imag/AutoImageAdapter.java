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
import com.live.quanmin.autopicker.util.SpDpPxUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guo_hx on 2017/8/14 10:22.
 */

public class AutoImageAdapter extends RecyclerView.Adapter<AutoImageAdapter.Holder> {

    private static final String TAG = AutoImageAdapter.class.getSimpleName();

    private Integer mNumber;
    private List<Integer> listNumber;
    private ArrayList<Integer> listFilt;

    private boolean isFree = true;
    private OnOneAnimFinishListener mOneAnimFinishListener;

    private int transYSingle = SpDpPxUtil.dip2px(21.5f);
    private int transYTotal = SpDpPxUtil.dip2px(237);

    public void setData(Integer number) {
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
            Log.d(TAG, "listFilt.size() = " + listFilt.size());
            String s = number + "";
            if (i >= 1) {
                String substring = s.substring(0, i);
                Log.i(TAG, substring);
                try {
                    listFilt.add(Integer.parseInt(substring));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        notifyDataSetChanged();
    }

    public boolean isFree() {
        return isFree;
    }

    public void setOnOneAnimFinishListener(OnOneAnimFinishListener oneAnimFinishListener) {
        mOneAnimFinishListener = oneAnimFinishListener;
    }

    public interface OnOneAnimFinishListener {
        void OnOneAnimFinish();
    }

    @Override
    public AutoImageAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_scroll_image, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final AutoImageAdapter.Holder holder, int position) {

        //整数圈
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.mIvScroll, "translationY", -transYSingle, -transYTotal);
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
        Log.d(TAG, "信息： position = " + position + "duration  = " + duration + "repeatCount = " + repeatCount);
        objectAnimator.start();
        //剩余的不到一圈
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (holder.getAdapterPosition() == listNumber.size() - 1) {
                    isFree = false;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (holder.getAdapterPosition() == -1) return;
                int px = -transYSingle * (listNumber.get(holder.getAdapterPosition()) + 1);
                ObjectAnimator objectAnimator_surplus = ObjectAnimator.ofFloat(holder.mIvScroll, "translationY", -transYSingle, px);
                int duration_surplus;
                if (holder.getAdapterPosition() == 0) {
                    duration_surplus = mNumber * 10;
                } else {
                    duration_surplus = (listNumber.get(holder.getAdapterPosition()) * mNumber * 10) / (repeatCount * 10 + listNumber.get(holder.getAdapterPosition())) / repeatCount;//这个，需要解释一下
                }
                objectAnimator_surplus.setDuration(duration_surplus);
                Log.d(TAG, "最后一圈信息： position = " + holder.getAdapterPosition() + "duration  = " + duration + "px = " + px);
                objectAnimator_surplus.start();
                objectAnimator_surplus.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (holder.getAdapterPosition() == listNumber.size() - 1) {
                            if (mOneAnimFinishListener != null) {
                                isFree = true;
                                Log.i(TAG, "OnOneAnimFinish---" + mNumber + "---" + Thread.currentThread().getName());
                                mOneAnimFinishListener.OnOneAnimFinish();
                            }
                        }
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
