package com.hadippa.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.utils.OnOkClickListener;

import java.nio.channels.spi.AbstractSelectionKey;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EntertainmentActivity extends BaseActionsActivity {

    private RecyclerView listPlay;
    private TextView tvHeader;
    CustomAdapter customAdapter = new CustomAdapter();
    private ArrayList<String> alCities;

    @BindView(R.id.tvTabMovie)
    TextView tvTabMovie;
    private AlertDialog alertDialog;
    @BindView(R.id.llInvite)
    RelativeLayout llInvite;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        ButterKnife.bind(this);

        setCurrentActionView(this,R.id.tvTabMovie);

        alCities = new ArrayList<>();
        alCities.add("Mumbai");
        alCities.add("Vadodara");
        alCities.add("Ahmedabad");
        alCities.add("Surat");
        alCities.add("Banglore");
        alCities.add("Jaipur");
        alCities.add("Chennai");
        alCities.add("Pune");

        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.v("click", "onFocusChange");
                if(hasFocus){
                    Log.v("click", "onFocusChange true");
                    rlSearch.setPressed(true);
                }
                else{
                    Log.v("click", "onFocusChange false");
                    rlSearch.setPressed(false);
                }
            }
        });


        tvHeader = (TextView) findViewById(R.id.tvHeader);

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        listPlay = (RecyclerView) findViewById(R.id.listPlay);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listPlay.setLayoutManager(mLayoutManager);
        listPlay.setAdapter(customAdapter);

        llInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupList(EntertainmentActivity.this, alCities, getString(R.string.choose_city), new OnOkClickListener() {
                    @Override
                    public void onOkClick(String dataSelected) {

                        //TODO change_city web call here
                    }
                });
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int)ev.getRawX();
                int rawY = (int)ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }*/

    private void showPopupList(Context context, final List<String> list, String title, final OnOkClickListener onOkClickListener) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.popup_listview, null);
        ListView listView = (ListView) view.findViewById(R.id.lvItems);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,R.layout.item_city, R.id.txtName, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(alertDialog != null) alertDialog.cancel();
                onOkClickListener.onOkClick(list.get(position));
            }
        });

        builder.setTitle(title);
        builder.setView(view);

        alertDialog = builder.show();
    }


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movies, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlData;

        public ViewHolder(final View v) {
            super(v);
            rlData = (RelativeLayout)v.findViewById(R.id.rlData);
            rlData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(EntertainmentActivity.this, PlayDetailActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    /*ImageView relativeLayout = (ImageView) v.findViewById(R.id.frame);
                    if(relativeLayout.getDrawable().getConstantState()==(getResources().getDrawable(R.drawable.rounded_movie_deselect).getConstantState())){
                        relativeLayout.setImageResource(R.drawable.rounded_movie_select);
                    }else {
                        relativeLayout.setImageResource(R.drawable.rounded_movie_deselect);
                    }*/
                }
            });
        }

        public RelativeLayout getRlData() {
            return rlData;
        }


    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(getCurrentFocus().getRootView(),intent.getExtras().getString("messageData"));
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }

}
