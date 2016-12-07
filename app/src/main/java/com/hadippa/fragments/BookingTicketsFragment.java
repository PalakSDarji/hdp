package com.hadippa.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hadippa.R;
import com.hadippa.fragments.search.SearchPeople;
import com.hadippa.model.SearchModel;

import java.util.ArrayList;

/**
 * Created by Palak on 07-12-2016.
 */

public class BookingTicketsFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public RelativeLayout relMain;

    public Context context;

    public ProgressBar progressBar;
    public CustomAdapter customAdapter;
    private ArrayList<String> tickets;


    public BookingTicketsFragment newInstance(int page, String title) {
        BookingTicketsFragment fragment = new BookingTicketsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.follow, container, false);

        context = getActivity();

        tickets = new ArrayList<>();
        tickets.add("");
        tickets.add("");
        tickets.add("");
        tickets.add("");
        tickets.add("");
        tickets.add("");
        tickets.add("");
        tickets.add("");
        tickets.add("");
        tickets.add("");
        tickets.add("");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) view.findViewById(R.id.relMain);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        CustomAdapter customAdapter = new CustomAdapter();
        mRecyclerView.setAdapter(customAdapter);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        progressBar.setVisibility(View.GONE);

        return view;

    }

    public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.booking_tickets_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            //final String s = tickets.get(position);
        }

        @Override
        public int getItemCount() {

            return tickets.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //private final TextView id;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            //id = (TextView) v.findViewById(R.id.tvId);
        }
    }

}
