package com.hadippa;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * Created by Palak on 10/10/15.
 */
public class CustomTextView extends AppCompatTextView {


    public CustomTextView(Context context) {
        super(context);
        setTypeface(context,null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context,attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context,attrs);
    }

    private void setTypeface(Context context, AttributeSet attributeSet) {
        if (context != null && !isInEditMode() && attributeSet != null) {

            TypedArray typedArray = context.obtainStyledAttributes(attributeSet,R.styleable.CustomTextView,0,0);
            try{
                String customFont = typedArray.getString(R.styleable.CustomTextView_customFont);
                if(customFont != null && !customFont.isEmpty()){
                    setTypeface(Typeface.createFromAsset(context.getAssets(), customFont));
                }
                else{
                    setTypeface(Typeface.createFromAsset(context.getAssets(), context.getString(R.string.proxima_reg)));
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                typedArray.recycle();
            }
        }
    }
}