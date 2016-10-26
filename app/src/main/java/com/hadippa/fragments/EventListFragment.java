package com.hadippa.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.Contact;
import com.hadippa.model.Event;

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

    private EventAdapter adapter;
    private List<Event> events = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        ButterKnife.bind(this, view);

        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());

        srlEventList.setOnRefreshListener(this);

        adapter = new EventAdapter(getActivity(),events);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvEventList.setLayoutManager(mLayoutManager);
        rvEventList.setItemAnimator(new DefaultItemAnimator());
        rvEventList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onRefresh() {

    }

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>{

        private List<Event> list;

        private Context mContext;

        public EventAdapter(Context context, List<Event> events) {
            this.list = events;
            mContext = context;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);

            return new EventHolder(itemView);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {

        }

        public Event getItem(int position){
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setData(List<Event> data) {
            list = data;
            notifyDataSetChanged();
        }

        public class EventHolder extends RecyclerView.ViewHolder {

            CustomTextView tvEventName;

            public EventHolder(View view) {
                super(view);

                tvEventName = (CustomTextView) view.findViewById(R.id.tvEventName);
            }
        }

    }

}
