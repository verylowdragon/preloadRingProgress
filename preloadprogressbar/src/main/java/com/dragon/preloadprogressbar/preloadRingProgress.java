package com.dragon.preloadprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/************************************
 * *** 作者: 10倍效率程序员       ****
 * *** 日期: 2017/8/4              ****
 * *** 电话: 15209553709          ****
 * *** QQ:   844732317            ****
 ***********************************/

public class preloadRingProgress extends View{
    private Paint mBackPaint;
    private Paint mFrontPaint;
    private Paint mTextPaint;
    private int colorResous;
    private Context context;
    private float mStrokeWidth = 10;
    private float mAlphaStrokeWidth = 12;
    private float mHalfStrokeWidth = mStrokeWidth / 2;
    private float mRadius = 60;
    //文字大小
    //文字的笔画宽度
    private float textSize = 45;
    private RectF mRect;
    private int mProgress = 0;
    //目标值，想改多少就改多少
    private int mMax = 150;
    private int mWidth;
    private int mHeight;
    private callBackAty callBack;

    //获取屏幕的宽高  重新计算自定义控件的大小
    public void reSetViewSize(int colorResous,Context context,callBackAty callBack){
        this.callBack = callBack;
        this.context = context;
        this.colorResous = colorResous;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;
        switch (dpi){
            case 480 :
                mStrokeWidth = 10;
                mHalfStrokeWidth = mStrokeWidth / 2;
                mRadius = 80;
                textSize = 45;
                break;
            case 160:
                mStrokeWidth = 4;
                mHalfStrokeWidth = mStrokeWidth / 2;
                mRadius = 30;
                textSize = 15;
                break;
            case 240:
                mStrokeWidth = 5;
                mHalfStrokeWidth = mStrokeWidth / 2;
                mRadius = 40;
                textSize = 22;
                break;
            case 320:
                mStrokeWidth = 8;
                mHalfStrokeWidth = mStrokeWidth / 2;
                mRadius = 60;
                textSize = 30;
                break;
            case 640:
                mStrokeWidth = 16;
                mHalfStrokeWidth = mStrokeWidth / 2;
                mRadius = 120;
                textSize = 60;
                break;
            default:
                break;
        }
        init();
    }
    public preloadRingProgress(Context context) {
        super(context);
        // init();
    }
    public preloadRingProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        //  init();
    }
    public preloadRingProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //  init();
    }
    //初始化画笔
    private void init() {
        mBackPaint = new Paint();
        mBackPaint.setColor(getResources().getColor(colorResous));
        mBackPaint.setAntiAlias(true);
        mBackPaint.setStyle(Paint.Style.STROKE);
        mBackPaint.setStrokeWidth(mStrokeWidth);
        mFrontPaint = new Paint();
        mFrontPaint.setColor(getResources().getColor(colorResous));
        mFrontPaint.setAntiAlias(true);
        mFrontPaint.setStyle(Paint.Style.STROKE);
        mFrontPaint.setStrokeWidth(mStrokeWidth);
        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(colorResous));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);
        //调用系统的api设置控件的宽高
        setMeasuredDimension(mWidth, mHeight);
    }
    @Override
    protected void onDraw(final Canvas canvas) {
        initRect();
        final float alpha = mProgress / (float)mMax * 180;
        canvas.drawArc(mRect , 90+alpha , alpha , false , mFrontPaint);
        //左边一半
        canvas.drawArc(mRect,  alpha-90,  alpha, false , mFrontPaint);
        Rect bounds = new Rect();
        mTextPaint.getTextBounds("跳过",0,2,bounds);
        canvas.drawText("跳过", (mWidth/2) , (mHeight/2) + (bounds.height()/2), mTextPaint);
        if (mProgress < mMax) {
            mProgress += 1;
            invalidate();
        }else {
            //TODO 编写一个回调方法 回调界面
            callBack.handleAty();
            mProgress = 0;
            invalidate();
        }
    }
    public int getRealSize(int measureSpec){
        int result = 1;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        //根据数值计算线的宽度和半径的大小
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            //自己计算
            result = (int) (mRadius * 2 + mStrokeWidth);
        } else {
            result = size;
        }
        return result;
    }
    private void initRect() {
        if (mRect == null) {
            mRect = new RectF();
            int viewSize = (int) (mRadius * 2);
            int left = (mWidth - viewSize) / 2;
            int top = (mHeight - viewSize) / 2;
            int right = left + viewSize;
            int bottom = top + viewSize;
            mRect.set(left, top, right, bottom);
        }
    }
    public interface callBackAty{
        public void handleAty();
    }
}
