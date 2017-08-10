/**
 * Copyright (C) 2016 Robinhood Markets, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.live.quanmin.autopicker.tiker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * In ticker, each character in the rendered text is represented by a {@link TickerColumn}. The
 * column can be seen as a column of text in which we can animate from one character to the next
 * by scrolling the column vertically. The {@link TickerColumnManager} is then a
 * manager/convenience class for handling a list of {@link TickerColumn} which then combines into
 * the entire string we are rendering.
 *
 * @author Jin Cao, Robinhood
 */
class TickerColumnManager {
    final ArrayList<TickerColumn> tickerColumns = new ArrayList<>();
    private Context context;
    private View view;
    private final TickerDrawMetrics metrics;

    // The character list that dictates how to transition from one character to another.
    private char[] characterList;
    // A minor optimization so that we can cache the indices of each character.
    private Map<Character, Integer> characterIndicesMap;
    private int currentAnimNumIndex;

    TickerColumnManager(Context context, View view, TickerDrawMetrics metrics) {
        this.context = context;
        this.view = view;
        this.metrics = metrics;
    }

    /**
     * @see {@link TickerView#setCharacterList(char[])}.
     */
    void setCharacterList(char[] characterList) {
        this.characterList = characterList;
        this.characterIndicesMap = new HashMap<>(characterList.length);

        for (int i = 0; i < characterList.length; i++) {
            characterIndicesMap.put(characterList[i], i);
        }
    }

    /**
     * @return whether or not {@param text} should be debounced because it's the same as the
     * current target text of this column manager.
     */
    boolean shouldDebounceText(char[] text) {
        final int newTextSize = text.length;
        if (newTextSize == tickerColumns.size()) {
            for (int i = 0; i < newTextSize; i++) {
                if (text[i] != tickerColumns.get(i).getTargetChar()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Tell the column manager the new target text that it should display.
     */
    void setText(char[] text) {
        if (characterList == null) {
            throw new IllegalStateException("Need to call setCharacterList(char[]) first.");
        }

        // First remove any zero-width columns
        for (int i = 0; i < tickerColumns.size(); ) {
            final TickerColumn tickerColumn = tickerColumns.get(i);
            if (tickerColumn.getCurrentWidth() > 0) {
                i++;
            } else {
                tickerColumns.remove(i);
            }
        }

        // Use Levenshtein distance algorithm to figure out how to manipulate the columns
        final int[] actions = LevenshteinUtils.computeColumnActions(getCurrentText(), text);
        int columnIndex = 0;
        int textIndex = 0;
        for (int i = 0; i < actions.length; i++) {
            switch (actions[i]) {
                case LevenshteinUtils.ACTION_INSERT:
                    tickerColumns.add(columnIndex,
                            new TickerColumn(characterList, characterIndicesMap, metrics));
                case LevenshteinUtils.ACTION_SAME:
                    tickerColumns.get(columnIndex).setTargetChar(text[textIndex]);
                    columnIndex++;
                    textIndex++;
                    break;
                case LevenshteinUtils.ACTION_DELETE:
                    tickerColumns.get(columnIndex).setTargetChar(TickerUtils.EMPTY_CHAR);
                    columnIndex++;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown action: " + actions[i]);
            }
        }
    }

    void onAnimationEnd() {
        for (int i = 0, size = tickerColumns.size(); i < size; i++) {
            final TickerColumn column = tickerColumns.get(i);
            column.onAnimationEnd();
        }
    }

    void setAnimationProgress(final float animationProgress) {
//        for (int i = 0, size = tickerColumns.size(); i < size; i++) {
//            final TickerColumn column = tickerColumns.get(i);
//            view.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    column.setAnimationProgress(animationProgress);
//                }
//            }, 200);
////            column.setAnimationProgress(animationProgress);
//        }
        currentAnimNumIndex = tickerColumns.size() - 1;
        Log.i("guohongxin", "currentAnimNumIndex == " + currentAnimNumIndex + "thread == " + Thread.currentThread().getName());
        TickerColumn currentAnimNum = tickerColumns.get(currentAnimNumIndex);
        currentAnimNum.setOnAnimEndListener(new TickerColumn.OnAnimEndListener() {
            @Override
            public void OnAnimEnd() {
                TickerColumn currentAnimNum = tickerColumns.get(currentAnimNumIndex);
                currentAnimNum.setAnimationProgress(animationProgress);
            }
        });
        Log.i("guohongxin", "currentAnimNum == " + currentAnimNum + "thread == " + Thread.currentThread().getName());
        currentAnimNum.setAnimationProgress(animationProgress);
    }

    float getMinimumRequiredWidth() {
        float width = 0f;
        for (int i = 0, size = tickerColumns.size(); i < size; i++) {
            width += tickerColumns.get(i).getMinimumRequiredWidth();
        }
        return width;
    }

    float getCurrentWidth() {
        float width = 0f;
        for (int i = 0, size = tickerColumns.size(); i < size; i++) {
            width += tickerColumns.get(i).getCurrentWidth();
        }
        return width;
    }

    char[] getCurrentText() {
        final int size = tickerColumns.size();
        final char[] currentText = new char[size];
        for (int i = 0; i < size; i++) {
            currentText[i] = tickerColumns.get(i).getCurrentChar();
        }
        return currentText;
    }

    /**
     * This method will draw onto the canvas the appropriate UI state of each column dictated
     * by {@param animationProgress}. As a side effect, this method will also translate the canvas
     * accordingly for the draw procedures.
     */
    void draw(Canvas canvas, Paint textPaint) {
        for (int i = 0, size = tickerColumns.size(); i < size; i++) {
            final TickerColumn column = tickerColumns.get(i);
            column.draw(canvas, textPaint);
            canvas.translate(column.getCurrentWidth(), 0f);
        }
    }
}
