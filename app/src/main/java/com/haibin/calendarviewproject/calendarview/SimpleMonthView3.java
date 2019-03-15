package com.haibin.calendarviewproject.calendarview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;
import com.haibin.calendarviewproject.R;

public class SimpleMonthView3 extends MonthView implements Checkable {
    private String TAG = "SimpleMonthView3";
    private int mRadius;

    private final static float BOUNCE_VALUE = 0.2f;

    private Drawable checkDrawable;

    private Paint bitmapPaint;
    private Paint bitmapEraser;
    private Paint checkEraser;
    private Paint borderPaint;

    private Bitmap drawBitmap;
    private Bitmap checkBitmap;
    private Canvas bitmapCanvas;
    private Canvas checkCanvas;

    private float progress;
    private ObjectAnimator checkAnim;

    private boolean attachedToWindow;
    private boolean isChecked;

    //package, public, private, protected
    public void setDate(int year, int month) {
        super.initMonthWithDate(year, month);
    }

    private int size = 5;
    private int bitmapColor = 0xFF3F51B5;
    private int borderColor = 0xFFFFFFFF;

    public SimpleMonthView3(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Log.i(TAG, "SimpleMonthView3");
        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
        //4.0以上硬件加速会导致无效
        mSelectedPaint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.SOLID));

        checkDrawable = context.getResources().getDrawable(R.mipmap.check);
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //suvini
        bitmapEraser = new Paint(Paint.ANTI_ALIAS_FLAG); //suvini
        bitmapEraser.setColor(0);
        bitmapEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        checkEraser = new Paint(Paint.ANTI_ALIAS_FLAG);  //suvini
        checkEraser.setColor(0);
        checkEraser.setStyle(Paint.Style.STROKE);
        checkEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //suvini
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dp(2));
    }

    public SimpleMonthView3(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    @Override
    protected void onPreviewHook() {
//        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
//        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLoopStart(int x, int y) {

    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
//         canvas.drawCircle(cx, cy, mRadius, mSelectedPaint); //為被選擇的日期畫圈標示
        //不要效果，改畫一般字，字體顏色到xml去改
        canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY - mItemHeight / 3,
                calendar.isCurrentDay() ? mCurDayTextPaint :
                        calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        Log.i(TAG, "onnnnnnnnnnnnnnnnDrawSelected");
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        // canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
        Log.i(TAG, "onDrawScheme");
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;
//suvini
        drawBitmap = Bitmap.createBitmap(dp(size), dp(size), Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(drawBitmap);
        checkBitmap = Bitmap.createBitmap(dp(size), dp(size), Bitmap.Config.ARGB_8888);
        checkCanvas = new Canvas(checkBitmap);
        if (getVisibility() != VISIBLE) {
            return;
        }
        checkEraser.setStrokeWidth(size);

        drawBitmap.eraseColor(0);
        float rad = getMeasuredWidth() / 2;

        float bitmapProgress = progress >= 0.5f ? 1.0f : progress / 0.5f;
        float checkProgress = progress < 0.5f ? 0.0f : (progress - 0.5f) / 0.5f;
        float p = isChecked ? progress : (1.0f - progress);

        if (p < BOUNCE_VALUE) {
            rad -= dp(2) * p;
        } else if (p < BOUNCE_VALUE * 2) {
            rad -= dp(2) - dp(2) * p;
        }
        int a = x + (cx) / 10;

        //suvini
        //1被選中的日期
        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY - mItemHeight / 3, mSelectTextPaint);
            bitmapPaint.setColor(bitmapColor);
//            bitmapCanvas.drawCircle(a, mTextBaseLine + y + mItemHeight / 10, rad, bitmapPaint);
//            bitmapCanvas.drawCircle(a, mTextBaseLine + y + mItemHeight / 10, rad * (1 - bitmapProgress), bitmapEraser);
            canvas.drawCircle(cx, baselineY + mItemHeight / 20, mItemWidth / 4, bitmapPaint); //填滿
            canvas.drawCircle(cx, baselineY + mItemHeight / 20, mItemWidth / 4, borderPaint);
            //  bitmapCanvas.drawCircle(cx, baselineY + 50, mItemWidth / 4, bitmapEraser);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.check);
            canvas.drawBitmap(bitmap, cx - mItemWidth / 9, baselineY - mItemHeight / 13, borderPaint);

        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY - mItemHeight / 3,
                    calendar.isCurrentDay() ? mCurDayTextPaint : calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY - mItemHeight / 3,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);

            canvas.drawCircle(cx, baselineY + mItemHeight / 20, mItemWidth / 4, borderPaint);
        }
    }

    //suvini
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        attachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        attachedToWindow = false;
    }


    public void setProgress(float value) {
        if (progress == value) {
            return;
        }
        progress = value;
        invalidate();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getProgress() {
        return progress;
    }

    public void setCheckedColor(int value) {
        bitmapColor = value;
    }

    public void setBorderColor(int value) {
        borderColor = value;
        borderPaint.setColor(borderColor);
    }

    private void cancelAnim() {
        if (checkAnim != null) {
            checkAnim.cancel();
        }
    }

    private void addAnim(boolean isChecked) {
        checkAnim = ObjectAnimator.ofFloat(this, "progress", isChecked ? 1.0f : 0.0f);
        checkAnim.setDuration(300);
        checkAnim.start();
    }

    public void setChecked(boolean checked, boolean animated) {
        if (checked == isChecked) {
            return;
        }
        isChecked = checked;

        if (attachedToWindow && animated) {
            Log.i(TAG, " addAnim(checked)：run ");
            addAnim(checked);
        } else {
            cancelAnim();
            Log.i(TAG, "cancelAnim()：");
            setProgress(checked ? 1.0f : 0.0f);
        }
    }


    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    @Override
    public void setChecked(boolean b) {
        setChecked(b, true);
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    public int dp(float value) {
        if (value == 0) {
            return 0;
        }
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE && drawBitmap == null) {
            drawBitmap = Bitmap.createBitmap(dp(size), dp(size), Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(drawBitmap);
            checkBitmap = Bitmap.createBitmap(dp(size), dp(size), Bitmap.Config.ARGB_8888);
            checkCanvas = new Canvas(checkBitmap);
        }
    }
}
