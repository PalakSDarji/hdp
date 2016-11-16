package com.hadippa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hadippa.R;
import com.hadippa.model.Zodiac;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.tvZodiac)
    TextView tvZodiac;

    @BindView(R.id.llRadio0)
    LinearLayout llRadio0;

    @BindView(R.id.llRadio1)
    LinearLayout llRadio1;

    @BindView(R.id.radio0)
    ImageView radio0;

    @BindView(R.id.radio1)
    ImageView radio1;

    @BindView(R.id.vSepZodiac)
    View vSepZodiac;

    private List<Zodiac> zodiacList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llRadio0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio0.setSelected(true);
                radio1.setSelected(false);
                tvZodiac.setVisibility(View.GONE);
                vSepZodiac.setVisibility(View.GONE);

            }
        });

        llRadio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio0.setSelected(false);
                radio1.setSelected(true);
                tvZodiac.setVisibility(View.VISIBLE);
                vSepZodiac.setVisibility(View.VISIBLE);
                try {
                    findZodiac();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        zodiacList = new ArrayList<>();
        zodiacList.add(new Zodiac("Aries","March 21","April 19"));
        zodiacList.add(new Zodiac("Taurus","April 20","May 20"));
        zodiacList.add(new Zodiac("Gemini","May 21","June 20"));
        zodiacList.add(new Zodiac("Cancer","June 21","July 22"));
        zodiacList.add(new Zodiac("Leo","July 23","August 22"));
        zodiacList.add(new Zodiac("Virgo","August 23","September 22"));
        zodiacList.add(new Zodiac("Libra","September 23","October 22"));
        zodiacList.add(new Zodiac("Scorpio","October 23","November 21"));
        zodiacList.add(new Zodiac("Sagittarius","November 22","December 21"));
        zodiacList.add(new Zodiac("Capricorn","December 22","January 19"));
        zodiacList.add(new Zodiac("Aquarius","January 20","February 18"));
        zodiacList.add(new Zodiac("Pisces","February 19","March 20"));



    }

    private void findZodiac() throws ParseException {

        //TODO : this birthdate is for xml demo purpose only as of now. Fill it with user's birthdate later while integrating APIs in the same format or change the format of date formater below.
        String birthDate = "12/05/1992";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");

        Date date = null;
        try {
            date = simpleDateFormat.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }//asd

        if(date == null){
            //TODO print some msg about failure of the process. otherwise continue
            Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
            return;
        }

        for(Zodiac zodiac : zodiacList){
            Log.v("Zodiac1"," Zodiac1 : " + zodiac);

            Date startDate = sdf.parse(zodiac.getStartDate());
            Date endDate = sdf.parse(zodiac.getEndDate());

            Calendar calendarStart = Calendar.getInstance();
            calendarStart.setTime(startDate);
            calendarStart.set(Calendar.YEAR,0);

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(endDate);
            calendarEnd.set(Calendar.YEAR,0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.YEAR,0);

            Log.v("Zodiac1"," date : " + calendarStart.getTime()+ ", "+ calendarEnd.getTime() + ", "+ calendar.getTime());
            if(calendar.getTime().after(calendarStart.getTime()) && calendar.getTime().before(calendarEnd.getTime())){

                Log.v("Zodiac1","enter");
                tvZodiac.setText(""+zodiac.getZodiacName());
                break;
            }
        }

    }
}
