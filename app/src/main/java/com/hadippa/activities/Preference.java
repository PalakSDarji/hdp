package com.hadippa.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hadippa.R;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Created by HP on 11-07-2016.
 */


public class Preference extends Activity {

    RangeSeekBar rangeSeekBar;
    DiscreteSeekBar discreteBarkms;
    TextView tvAge, tvDistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);

        rangeSeekBar = (RangeSeekBar) findViewById(R.id.ageRange);
        discreteBarkms = (DiscreteSeekBar)findViewById(R.id.discreteBarkms);

        tvAge = (TextView)findViewById(R.id.tvAge);
        tvDistance = (TextView)findViewById(R.id.tvDistance);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                tvAge.setText(rangeSeekBar.getSelectedMinValue()+" - "+rangeSeekBar.getSelectedMaxValue());
            }
        });

        discreteBarkms.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                tvDistance.setText(String.valueOf(value));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
    }
}
