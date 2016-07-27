package com.hadippa;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * Created by Palak on 10/10/15.
 */
public class BoldTextView extends AppCompatTextView {


    public BoldTextView(Context context) {
        super(context);
        setTypeface(context);
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public BoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(Hadippa.getBold());
        }
    }
}