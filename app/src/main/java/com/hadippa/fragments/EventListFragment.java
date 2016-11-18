package com.hadippa.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.activities.CoffeeActivity;
import com.hadippa.activities.EntertainmentActivity;
import com.hadippa.activities.EventDetailsActivity;
import com.hadippa.activities.EventListActivity;
import com.hadippa.activities.PostActivity;
import com.hadippa.model.Contact;
import com.hadippa.model.Event;
import com.hadippa.model.MeraEventPartyModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Palak on 26-10-2016.
 */

public class EventListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = EventListFragment.class.getSimpleName();

    @Nullable @BindView(R.id.rv_event_list)
    public RecyclerView rvEventList;

    @Nullable @BindView(R.id.srl_event_list)
    public SwipeRefreshLayout srlEventList;

    public static EventAdapter adapter;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        ButterKnife.bind(this, view);


        srlEventList.setOnRefreshListener(this);

        adapter = new EventAdapter(getActivity(),EventListActivity.postBeanList);


        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvEventList.setLayoutManager(mLayoutManager);
        rvEventList.setItemAnimator(new DefaultItemAnimator());
        rvEventList.setAdapter(adapter);

        /*rvEventList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");

                            EventListActivity eventListActivity= new EventListActivity();
                            eventListActivity.prepareThings(EventListActivity.pageNumber);
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });*/


        return view;
    }

    @Override
    public void onRefresh() {

    }

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>{

        private List<MeraEventPartyModel.DataBean> list;

        private Context mContext;

        public EventAdapter(Context context, List<MeraEventPartyModel.DataBean> events) {
            this.list = events;
            mContext = context;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);

            return new EventHolder(itemView);
        }

        @Override
        public void onBindViewHolder(EventHolder holder,final int position) {

            holder.rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                    intent.putExtra("activity_id", getArguments().getInt("activity_id"));
                    intent.putExtra("data", EventListActivity.postBeanList.get(position));
                    intent.putExtra("latitude", getArguments().getString("latitude"));
                    intent.putExtra("longitude", getArguments().getString("longitude"));
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
            holder.tvPrice.setText(EventListActivity.postBeanList.get(position).getTicket_currencyCode()+" "+EventListActivity.postBeanList.get(position).getTicket_price());
            holder.tvEventName.setText(EventListActivity.postBeanList.get(position).getTitle());
            holder.tvAddress.setText(EventListActivity.postBeanList.get(position).getAddress1());
            holder.timings.setText(EventListActivity.postBeanList.get(position).getStartDate()+" - "+EventListActivity.postBeanList.get(position).getEndDate());
            holder.tvDistance.setText(AppConstants.distanceMeasure(Double.parseDouble(getArguments().getString("latitude")),
                    Double.parseDouble(getArguments().getString("longitude")),
                    (EventListActivity.postBeanList.get(position).getLatitude()),
                    (EventListActivity.postBeanList.get(position).getLongitude())) + " kms");

            if(EventListActivity.postBeanList.get(position).getBannerPath().isEmpty() || EventListActivity.postBeanList.get(position).getBannerPath().equals("")){
                holder.profileImage.setImageResource(R.drawable.place_holder);
            }else {
                Glide.with(getActivity())
                        .load(EventListActivity.postBeanList.get(position).getBannerPath())
                        .into(holder.profileImage);
            }
        }

        public MeraEventPartyModel.DataBean getItem(int position){
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setData(List<MeraEventPartyModel.DataBean> data) {
            list = data;
            notifyDataSetChanged();
        }

        public class EventHolder extends RecyclerView.ViewHolder {

            CustomTextView tvEventName,tvAddress,tvOnwards,timings,tvDistance,tvPrice;
            RelativeLayout rlContainer;
            RoundedImageView profileImage;

            public EventHolder(View view) {
                super(view);
                rlContainer = (RelativeLayout) view.findViewById(R.id.rlContainer);
                tvEventName = (CustomTextView) view.findViewById(R.id.tvEventName);
                tvAddress = (CustomTextView) view.findViewById(R.id.tvAddress);
                tvOnwards = (CustomTextView) view.findViewById(R.id.tvOnwards);
                timings = (CustomTextView) view.findViewById(R.id.timings);
                tvDistance = (CustomTextView)view.findViewById(R.id.tvDistance);
                tvPrice = (CustomTextView) view.findViewById(R.id.tvPrice);
                profileImage = (RoundedImageView)view.findViewById(R.id.profileImage);
            }
        }

    }

}
