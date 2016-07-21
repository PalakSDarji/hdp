package com.hadippa.fragments.search;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonclasses.connection.ConnectionDetector;
import com.hadippa.R;

/**
 * Created by alm-android on 01-12-2015.
 */

public class SearchTag extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public static RecyclerView mRecyclerView;


    public static Snackbar snackbar = null;

    public static RelativeLayout linearMain;

    CustomAdapter customAdapter;

    public static SearchTag newInstance(int page, String title) {
        SearchTag fragmentFirst = new SearchTag();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.follow, null, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearMain = (RelativeLayout) view.findViewById(R.id.linearMain);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(2));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new CustomAdapter());


        return view;

    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    void checkConnection(final int page) {

        if (ConnectionDetector.isConnectedToInternet(getActivity())) {

        } else {
            snackbar = Snackbar
                    .make(linearMain, "No Internet Connection.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Try Again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                            checkConnection(page);
                        }
                    });

            snackbar.show();
        }
    }


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_grid_item, viewGroup, false);

            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");


        }

        @Override
        public int getItemCount() {


            return 50;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /*  private final TextView id;
          private final ImageView foodImage;
          private final TextView tvDonarName;
          private final TextView tvDonarPh, tvAddress, tvFoodfor, tvStatus;
          private final View typeView;*/
        private final ImageView image_view;

        //   TextView tvFollowUnfollow;
        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            //  tvFollowUnfollow = (TextView) v.findViewById(R.id.tvFollowUnfollow);

            int width = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 3;

            image_view = (ImageView) v.findViewById(R.id.image_view);
            image_view.setLayoutParams(new LinearLayout.LayoutParams(width, width));
/*
            id = (TextView) v.findViewById(R.id.tvId);

            foodImage = (ImageView) v.findViewById(R.id.profileImage);*/
           /* tvDonarName = (TextView) v.findViewById(R.id.tvDonarName);
            tvDonarPh = (TextView) v.findViewById(R.id.tvDonarPh);
            tvAddress = (TextView) v.findViewById(R.id.tvAddress);
            tvFoodfor = (TextView) v.findViewById(R.id.tvFoodfor);
            tvStatus = (TextView) v.findViewById(R.id.tvStatus);
            typeView = (View) v.findViewById(R.id.typeView);*/


        }

        public ImageView getImage_view() {
            return image_view;
        }
      /*  public TextView getTvStatus() {
            return tvStatus;
        }

        public TextView getName() {
            return tvDonarName;
        }

        public TextView getId() {
            return id;
        }

        public ImageView getProfileImage() {
            return foodImage;
        }


        public TextView getTvDonarPh() {
            return tvDonarPh;
        }

        public TextView getTvAddress() {
            return tvAddress;
        }

        public TextView getTvFoodfor() {
            return tvFoodfor;
        }

        public View getTypeView() {
            return typeView;
        }
*/

    }


}



