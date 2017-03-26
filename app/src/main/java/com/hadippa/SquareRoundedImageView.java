package com.hadippa;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by Palak on 30-03-2016.
 */
public class SquareRoundedImageView extends RoundedImageView {

    public SquareRoundedImageView(Context context) {
        super(context);
    }

    public SquareRoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

}