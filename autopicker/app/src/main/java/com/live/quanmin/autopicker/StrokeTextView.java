package com.live.quanmin.autopicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by wei on 16-5-3.
 */
public class StrokeTextView extends TextView {

    Typeface typeface;
    TextPaint m_TextPaint;
    int mInnerColor;
    int mOuterColor;
    int mShadowColor=0x15ff5253;

    public StrokeTextView(Context context, int outerColor, int innnerColor) {

        super(context);
        m_TextPaint = this.getPaint();
        this.mInnerColor = innnerColor;
        this.mOuterColor = outerColor;

        initView();
    }

    public StrokeTextView(Context context, AttributeSet attrs) {

        super(context, attrs);
        m_TextPaint = this.getPaint();
        //获取自定义的XML属性名称
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
        //获取对应的属性值
        this.mInnerColor = typedArray.getColor(R.styleable.StrokeTextView_innnerColor, 0xffffff);
        this.mOuterColor = typedArray.getColor(R.styleable.StrokeTextView_outerColor, 0xffffff);
//
        initView();
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle, int outerColor, int innnerColor) {

        super(context, attrs, defStyle);
        m_TextPaint = this.getPaint();
        this.mInnerColor = innnerColor;
        this.mOuterColor = outerColor;
        initView();
    }

    private void initView()
    {
        Log.i("StrokeTextView", "initView");
//        typeface=Typeface.createFromAsset(getContext().getAssets(), "ArialBlack.ttf");
//        //字体
//        setTypeface(typeface,Typeface.ITALIC);
    }

    private boolean m_bDrawSideLine = true; // 默认采用描边

    /**
     *
     */
    @Override
    protected void onDraw(Canvas canvas) {

        if (m_bDrawSideLine) {
            // 描外层
            // super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归
            m_TextPaint.setStrokeWidth(4); // 描边宽度
            setTextColorUseReflection(mOuterColor);
            m_TextPaint.setShadowLayer(
                    5f,
                    0f,
                    2f,
                    mShadowColor);// 字体的阴影效果，可以忽略
            m_TextPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 描边种类
            m_TextPaint.setFakeBoldText(true); // 外层text采用粗体
            super.onDraw(canvas);

            // 描内层，恢复原先的画笔

            // super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归
            m_TextPaint.setFakeBoldText(false);
            setTextColorUseReflection(mInnerColor);
            m_TextPaint.setStrokeWidth(0);
            m_TextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            m_TextPaint.setFakeBoldText(false);
            //m_TextPaint.setShadowLayer(3, 3, 3, 0x30d54040);

        }
        super.onDraw(canvas);
    }

    public void setInnerColor(int color)
    {
        mInnerColor=color;
    }

    public void setDefaultInnerCol()
    {
        mInnerColor=getResources().getColor(R.color.gift_orange_text);
    }

    /**
     * 使用反射的方法进行字体颜色的设置
     *
     * @param color
     */
    private void setTextColorUseReflection(int color) {

        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        m_TextPaint.setColor(color);
    }


    //强制让宽度变大一些
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int width = MeasureSpec.getSize(widthMeasureSpec) + 10;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }





    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 100;
        if(specMode == MeasureSpec.AT_MOST){
            result= specSize;
        }
        else if(specMode == MeasureSpec.EXACTLY)
        {
                    result = specSize;
        }
        return result;
    }


    private int measureWidth(int measureSpec) {


        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = 100;


        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;

        } else if
                (specMode == MeasureSpec.EXACTLY) {
                    result= specSize;
        }
        return result;
    }

    public void setShadowColor(int shadowColor) {

        this.mShadowColor = shadowColor;
    }

    public void setDefaultShadow()
    {
        mShadowColor=0x15ff5253;
    }

    public void setBold(boolean b) {
        if(b) {
//            setTypeface(typeface, Typeface.BOLD);
        }
        else
        {
//            setTypeface(typeface, Typeface.NORMAL);
        }

    }
}
