package com.hadippa.fragments.main_screen;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daprlabs.cardstack.SwipeDeck;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.activities.HomeScreen;
import com.hadippa.activities.LoginActivity;
import com.hadippa.activities.MyPlan;
import com.hadippa.fragments.signup.SignUp_Step1;
import com.hadippa.fragments.signup.SignUp_Step3;
import com.hadippa.model.DataModel;
import com.hadippa.tindercard.FlingCardListener;
import com.hadippa.tindercard.SwipeFlingAdapterView;
import com.hadippa.twowaygrid.TwoWayAdapterView;
import com.hadippa.twowaygrid.TwoWayGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowCardsNew.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowCardsNew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowCardsNew extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DialogPlus dialog = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView horizontal_recycler_view;

    private boolean menuOpened = false;
    public static MyAppAdapter myAppAdapter;
    private ArrayList<DataModel> al;
    private SwipeFlingAdapterView flingContainer;


    private OnFragmentInteractionListener mListener;
    // ArrayList<String> al;
    // CustomBaseAdapter arrayAdapter;

    ImageView swipeLeft, swipeRight;
    private SwipeDeck cardStack;
    Dialog dialog1;
    RelativeLayout relFab;
    RelativeLayout rlFix;
    FloatingActionsMenu multiple_actions;

    int i = 0;
    // FloatingActionsMenu multiple_actions;
    ArrayList<String> namesArray = new ArrayList<>();
    ArrayList<Drawable> drawables = new ArrayList<>();
    private SlidingUpPanelLayout mLayout;
    private View mMapCover;

    public ShowCardsNew() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowCards.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowCardsNew newInstance(String param1, String param2) {
        ShowCardsNew fragment = new ShowCardsNew();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;
    }

    public class MyAppAdapter extends BaseAdapter {

        public List<DataModel> parkingList = new ArrayList<>();
        public Context context;


        public void remove(int i) {

        }

        private MyAppAdapter(List<DataModel> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            LinearLayout llGoing;
            ImageView imageView, coverImage;
            TextView tvGoing, tvName_Age, tvDistance,
                    tvActivityName, tvActivtyTime, tvActivtyDate, tvAddress, tvCount;
            TextView txtDesc;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (convertView == null) {

                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.llGoing = (LinearLayout) convertView.findViewById(R.id.llGoing);
                viewHolder.tvGoing = (TextView) convertView.findViewById(R.id.tvGoing);
                viewHolder.tvName_Age = (TextView) convertView.findViewById(R.id.tvName_Age);
                viewHolder.tvDistance = (TextView) convertView.findViewById(R.id.tvDistance);
                viewHolder.tvActivityName = (TextView) convertView.findViewById(R.id.tvActivityName);
                viewHolder.tvActivtyTime = (TextView) convertView.findViewById(R.id.tvActivtyTime);
                viewHolder.tvActivtyDate = (TextView) convertView.findViewById(R.id.tvActivityDate);
                viewHolder.tvCount = (TextView) convertView.findViewById(R.id.tvCount);
                viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
                viewHolder.coverImage = (ImageView) convertView.findViewById(R.id.coverImage);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            DataModel dataModel = parkingList.get(position);

            viewHolder.tvName_Age.setText(dataModel.getUser().getFirst_name()
                    + " " + dataModel.getUser().getLast_name() + ", " +
                    "" + dataModel.getUser().getAge());

            viewHolder.tvActivityName.setText(dataModel.getActivity_details().getActivity_name());
            viewHolder.tvActivtyTime.setText(dataModel.getActivity_time());
            viewHolder.tvActivtyDate.setText(convertDate(dataModel.getActivity_date()));
            viewHolder.tvAddress.setText(dataModel.getActivity_location());
            viewHolder.tvGoing.setText(String.valueOf(dataModel.getPeople_going_count().size()) + " Going");
            viewHolder.tvCount.setText(String.valueOf(dataModel.getId()));

            Glide.with(context)
                    .load(dataModel.getUser().getProfile_photo())
                    .into(viewHolder.coverImage);

            viewHolder.llGoing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myAppAdapter.getItem(position);

                    showTwoWayGrid(posts.get(position));
                }
            });
            //viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            //Glide.with(MainActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return convertView;
        }
    }

    String convertDate(String dateInputString) {

        String stringDate = null;
        try {
            // obtain date and time from initial string
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).parse(dateInputString);
            // set date string
            stringDate = new SimpleDateFormat("dd MMM", Locale.US).format(date).toUpperCase(Locale.ROOT);
            // set time string

        } catch (ParseException e) {
            // wrong input
        }

        return stringDate;

    }

    public static List<DataModel> posts;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_show_cards, container, false);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

      //  getPreferences();
        Type listType = new TypeToken<ArrayList<DataModel>>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder.create();
        posts = new ArrayList<>();

        ArrayList<DataModel> dataModels = gson.fromJson(sp.getString("posts", ""), listType);
        if(dataModels != null && dataModels.size()>0){
            posts.addAll(dataModels);
        }

        Log.d("posts>>", sp.getString("posts", ""));
        horizontal_recycler_view = (RecyclerView) view.findViewById(R.id.horizontal_recycler_view);

        mLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);

        mMapCover = view.findViewById(R.id.mapCover);
        mMapCover.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.v(AppConstants.DEBUG_TAG, "mMapCover onTouch called");

                if (mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    return true;
                }

                return false;
            }
        });
        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        myAppAdapter = new MyAppAdapter(posts, getActivity());
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

                Log.d("LIST", "removed object!");
              //  myAppAdapter.remove(0);
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                makeToast(getActivity(),""+ posts.get(0).getId());

                activityJoinDecline(posts.get(0).getId(),AppConstants.ACTIVITY_REQUEST_DECLINE);
                posts.remove(i);
                myAppAdapter.notifyDataSetChanged();

                // myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(getActivity(), "Right!");
                activityJoinDecline(posts.get(0).getId(),AppConstants.ACTIVITY_REQUEST_JOIN);
                posts.remove(i);
                myAppAdapter.notifyDataSetChanged();
                /*al.remove(0);
                myAppAdapter.notifyDataSetChanged();*/
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                // view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                //View view = flingContainer.getSelectedView();
                //view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });

        swipeLeft = (ImageView) view.findViewById(R.id.imageLeft);
        swipeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectLeft();
            }
        });

        swipeRight = (ImageView) view.findViewById(R.id.imageRight);
        swipeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectRight();
            }
        });

        rlFix = (RelativeLayout) view.findViewById(R.id.rlFix);

        rlFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

        //multiple_actions = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
       // multiple_actions = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);

        return view;
    }


    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    void showOptionsDialog() {


        /*Bitmap f = fastblur(v,15);*/
        dialog1 = new Dialog(getActivity(), R.style.DialogSlideAnim);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.options_dialog);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog1.getWindow().setAttributes(params);
        //Drawable d = new BitmapDrawable(getResources(), blurred);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

        RelativeLayout rlMyPlan = (RelativeLayout) dialog1.findViewById(R.id.rlMyPlan);
        rlMyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

                Intent intent = new Intent(getActivity(), MyPlan.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        RelativeLayout rlFollowling = (RelativeLayout) dialog1.findViewById(R.id.rlFollowling);
        rlFollowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

               /* Intent intent = new Intent(getActivity(), MyPlan.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });

        RelativeLayout rlToday = (RelativeLayout) dialog1.findViewById(R.id.rlToday);
        rlToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

                /*Intent intent = new Intent(getActivity(), MyPlan.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });

        RelativeLayout rlNearby = (RelativeLayout) dialog1.findViewById(R.id.rlNearby);
        rlNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
/*
                Intent intent = new Intent(getActivity(), MyPlan.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });

        RelativeLayout rlClose = (RelativeLayout) dialog1.findViewById(R.id.rlClose);
        rlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    private void showTwoWayGrid(DataModel dataModel) {


        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);

        dataModel.getPeople_going();
        //Sample arraylist..
        List<DoublePeople> doublePeoples = new ArrayList<>();
        doublePeoples.add(new DoublePeople(new People("palak", ""), new People("darji", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));
        doublePeoples.add(new DoublePeople(new People("Sahil", ""), new People("bhai", "")));

        HorizontalAdapter adapter = new HorizontalAdapter(doublePeoples);
        horizontal_recycler_view.setAdapter(adapter);


        adapter.notifyDataSetChanged();

        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    public boolean checkPanelState() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return true;
        } else {
            return false;
        }
    }

    private void showDialog(int gravity, boolean showHeader, boolean showFooter, boolean expanded) {
        boolean isGrid;
        Holder holder;

        holder = new GridHolder(4);
        isGrid = true;


        OnBackPressListener onBackPressListener = new OnBackPressListener() {
            @Override
            public void onBackPressed(DialogPlus dialogPlus) {
                dialogPlus.dismiss();
            }
        };
        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                //        switch (view.getId()) {
                //          case R.id.header_container:
                //            Toast.makeText(MainActivity.this, "Header clicked", Toast.LENGTH_LONG).show();
                //            break;
                //          case R.id.like_it_button:
                //            Toast.makeText(MainActivity.this, "We're glad that you like it", Toast.LENGTH_LONG).show();
                //            break;
                //          case R.id.love_it_button:
                //            Toast.makeText(MainActivity.this, "We're glad that you love it", Toast.LENGTH_LONG).show();
                //            break;
                //          case R.id.footer_confirm_button:
                //            Toast.makeText(MainActivity.this, "Confirm button clicked", Toast.LENGTH_LONG).show();
                //            break;
                //          case R.id.footer_close_button:
                //            Toast.makeText(MainActivity.this, "Close button clicked", Toast.LENGTH_LONG).show();
                //            break;
                //        }
                //        dialog.dismiss();
            }
        };

        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.text_view);
                String clickedAppName = textView.getText().toString();
                //        dialog.dismiss();
                //        Toast.makeText(MainActivity.this, clickedAppName + " clicked", Toast.LENGTH_LONG).show();
            }
        };

        OnDismissListener dismissListener = new OnDismissListener() {
            @Override
            public void onDismiss(DialogPlus dialog) {
                //        Toast.makeText(MainActivity.this, "dismiss listener invoked!", Toast.LENGTH_SHORT).show();
            }
        };

        OnCancelListener cancelListener = new OnCancelListener() {
            @Override
            public void onCancel(DialogPlus dialog) {
                //        Toast.makeText(MainActivity.this, "cancel listener invoked!", Toast.LENGTH_SHORT).show();
            }
        };

        SimpleAdapter adapter = new SimpleAdapter(getActivity());

        if (showHeader && !showFooter) {
            showNoFooterDialog(holder, gravity, adapter, clickListener, itemClickListener, dismissListener, cancelListener, onBackPressListener,
                    expanded);
            return;
        }


    }


    private void showNoFooterDialog(Holder holder, int gravity, final BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener, final OnBackPressListener onBackPressListener,
                                    boolean expanded) {

        View header = getActivity().getLayoutInflater().inflate(R.layout.header, null);
        ImageView showMore = (ImageView) header.findViewById(R.id.dismiss);
        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressListener.onBackPressed(dialog);
                //  adapter.notifyDataSetChanged();
            }
        });
        dialog = DialogPlus.newDialog(getActivity())
                .setContentHolder(holder)
                .setHeader(header)
                //  .setFooter(footer)

                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                    }
                })
                .setOnDismissListener(dismissListener)
                .setOnCancelListener(cancelListener)
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {
                        dialogPlus.dismiss();
                    }
                })
                .setExpanded(true)

                .create();
        dialog.show();
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<DoublePeople> horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView text_view1;
            private TextView text_view2;
            private ImageView image_view1;
            private ImageView image_view2;

            public MyViewHolder(View view) {
                super(view);
                text_view1 = (TextView) view.findViewById(R.id.text_view1);
                text_view2 = (TextView) view.findViewById(R.id.text_view2);
                image_view1 = (ImageView) view.findViewById(R.id.image_view1);
                image_view2 = (ImageView) view.findViewById(R.id.image_view2);
            }
        }


        public HorizontalAdapter(List<DoublePeople> horizontalList) {
            this.horizontalList = horizontalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.two_grid_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Log.v("hadippa", "onBindViewHolder");
            holder.text_view1.setText(horizontalList.get(position).getPeople1().getName());
            holder.text_view2.setText(horizontalList.get(position).getPeople1().getName());

            holder.text_view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), holder.text_view1.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            holder.text_view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), holder.text_view2.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }


    public class SimpleAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public SimpleAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            View view = convertView;

            if (view == null) {
                view = layoutInflater.inflate(R.layout.simple_grid_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
                viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.textView.setText(namesArray.get(position));
            viewHolder.imageView.setImageDrawable(drawables.get(position));
            return view;
        }

        class ViewHolder {
            TextView textView;
            ImageView imageView;
        }
    }

    public Bitmap fastblur(Bitmap sentBitmap, int radius) {
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    private void activityJoinDecline(String activity_id,String requestFor) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("activity_id", activity_id);

            requestParams.add("access_token", (sp.getString("access_token", "")));

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + requestFor, requestParams,
                new ActivityJoinDecline());

    }

    class ActivityJoinDecline extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //  dataScroll.setVisibility(View.GONE);
            AppConstants.showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
            Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));

//            updateDonut((int) ((totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                Log.d("async_step_2", "success" + response);
                if(jsonObject.getBoolean("success")){

                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

          //  AppConstants.showSnackBar(mainRel,"Could not register. try again");
        }

    }

    //GET PREFERENCS
    private void getPreferences()
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try
        {

            requestParams.add("access_token",sp.getString("access_token",""));


            Log.d("request>>",requestParams.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.PREFERENCES, requestParams,
                new GetPreferences());
    }

    class GetPreferences extends AsyncHttpResponseHandler
    {

        @Override
        public void onStart()
        {
            super.onStart();

            //  AppConstants.showProgressDialog(Preference.this, "Please Wait");

        }


        @Override
        public void onFinish()
        {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
            Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);

                if(jsonObject.getBoolean("success")){
                    editor.putString("user_preference",jsonObject.getString("user_preference"));
                    editor.commit();
                }

                Log.d("async","success"+response);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("async","success exc  >>"+ e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferences();
    }
}
