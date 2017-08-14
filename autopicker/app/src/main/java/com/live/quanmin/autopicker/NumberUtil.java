package com.live.quanmin.autopicker;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guo_hx on 2017/8/10 12:29.
 */

public class NumberUtil {

    static List<String> getScrollNumber(String number, String numberSum) {
        Log.i("NumberUtil", "传进来的 number == " + number + ", numberSum ==" + numberSum);
        ArrayList<String> nineList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            nineList.add(i + "");
        }

        ArrayList<String> listData = new ArrayList<>();
        int i1 = 0;
        try {
            i1 = Integer.parseInt(numberSum) / 10;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        for (int i2 = 0; i2 < i1; i2++) {
            listData.addAll(nineList);
        }
        try {
            for (int i = 0; i <= Integer.parseInt(number); i++) {
                listData.add(i + "");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Log.i("NumberUtil", "最后的List的size == " + listData.size());
        return listData;
    }

    static List<String> getNewList(String number, String numberSum) {
        ArrayList<String> listData = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            listData.add(i + "");
        }
        return listData;
    }
}
