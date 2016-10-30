package com.hadippa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hadippa.R;
import com.hadippa.fragments.main_screen.People;
import com.hadippa.model.PeopleModel;

import java.util.ArrayList;
import java.util.List;

public class InviteToJoinActivity extends AppCompatActivity {

    RecyclerView myRecycler;
    ImageView imageBack;
    private List<PeopleModel> peopleModels;
    private CustomAdapter adapter;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_to_join);

        peopleModels = new ArrayList<>();
        peopleModels.add(new PeopleModel("Palak Darji"));
        peopleModels.add(new PeopleModel("Kat Middleton"));
        peopleModels.add(new PeopleModel("Kareena Kapoor"));
        peopleModels.add(new PeopleModel("Kartick Mistry"));
        peopleModels.add(new PeopleModel("Angelina Joly"));
        peopleModels.add(new PeopleModel("Sania Mirza"));
        peopleModels.add(new PeopleModel("David Backham"));
        peopleModels.add(new PeopleModel("Shreya Ghosal"));
        peopleModels.add(new PeopleModel("Shilpa Shetty"));
        peopleModels.add(new PeopleModel("Tina Gupta"));
        peopleModels.add(new PeopleModel("Katrina Kaif"));
        peopleModels.add(new PeopleModel("Lonewolf Sniper"));
        peopleModels.add(new PeopleModel("CarryMinati"));

        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        myRecycler = (RecyclerView)findViewById(R.id.myRecycler);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(mLayoutManager);

        adapter = new CustomAdapter(peopleModels);
        myRecycler.setAdapter(adapter);
    }

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

        private static final String TAG = "CustomAdapter";

        private List<PeopleModel> originalData = null;
        private List<PeopleModel> filteredData = null;
        private LayoutInflater mInflater;
        private ItemFilter mFilter = new ItemFilter();

        CustomAdapter(List<PeopleModel> data){
            this.filteredData = data ;
            this.originalData = data ;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_invite_people, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            final PeopleModel peopleModel = filteredData.get(position);
            Log.d(TAG, "Element " + position + " set." + peopleModel.isChecked());

            if(peopleModel.isChecked()){
                viewHolder.rbButton.setPressed(true);
            }
            else{
                viewHolder.rbButton.setPressed(false);
            }

            viewHolder.rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(int i=0;i<filteredData.size();i++){
                        if(filteredData.get(i).getFirst_name().
                                equalsIgnoreCase(filteredData.get(position).getFirst_name())){
                            PeopleModel people = filteredData.get(i);
                            people.setChecked(!people.isChecked());
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            viewHolder.rbButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(int i=0;i<filteredData.size();i++){
                        if(filteredData.get(i).getFirst_name().
                                equalsIgnoreCase(filteredData.get(position).getFirst_name())){
                            PeopleModel people = filteredData.get(i);
                            people.setChecked(!people.isChecked());
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            viewHolder.tvName.setText(""+ peopleModel.getFirst_name());
        }

        @Override
        public int getItemCount() {

            return filteredData.size();
        }

        public Filter getFilter() {
            return mFilter;
        }

        private class ItemFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final List<PeopleModel> list = originalData;

                int count = list.size();
                final ArrayList<PeopleModel> nlist = new ArrayList<>(count);

                String filterableString ;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i).getFirst_name();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        nlist.add(list.get(i));
                    }
                }

                results.values = nlist;
                results.count = nlist.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<PeopleModel>) results.values;
                notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null) adapter.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlContainer;
        RadioButton rbButton;
        TextView tvName;

        public ViewHolder(final View v) {
            super(v);

            tvName = (TextView) v.findViewById(R.id.tvName);
            rlContainer = (RelativeLayout) v.findViewById(R.id.rlContainer);
            rbButton = (RadioButton) v.findViewById(R.id.rbButton);


        }

    }

}
