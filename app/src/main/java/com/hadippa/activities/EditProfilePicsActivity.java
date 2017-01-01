package com.hadippa.activities;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hadippa.R;
import com.hadippa.model.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class EditProfilePicsActivity extends AppCompatActivity {

    private static final int PIC_ITEM = 0;
    private static final int ADD_ITEM = 1;

    public static RecyclerView mRecyclerView;
    ArrayList<String> pics;
    ProgressBar progressBar;
    CustomAdapter customAdapter;
    private ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_people);

        ButterKnife.bind(this);

        pics = new ArrayList<>();
        for(int i = 0;i<6;i++){
            pics.add("pic "+i);
        }
        pics.add("add_buttom");


        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        ((TextView)findViewById(R.id.tvHeader)).setText(getString(R.string.edit_photo));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (customAdapter != null) {
                    switch (customAdapter.getItemViewType(position)) {
                        case ADD_ITEM:
                            return 3;
                        case PIC_ITEM:
                            return 1; //number of columns of the grid
                        default:
                            return -1;
                    }
                } else {
                    return -1;
                }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);

        customAdapter = new CustomAdapter(this,pics);
        mRecyclerView.setAdapter(customAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        LayoutInflater inflator;
        List<String> alPics;

        CustomAdapter(Context context, List<String> alPics){

            inflator = LayoutInflater.from(context);
            this.alPics = alPics;
        }

        @Override
        public int getItemViewType(int position) {
            if(position == alPics.size()-1){
                return ADD_ITEM;
            }
            return PIC_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v;

            if(viewType == ADD_ITEM){
                v = inflator.inflate(R.layout.item_add_pic, viewGroup, false);
                return new AddViewHolder(v);
            }
            else{
                v = inflator.inflate(R.layout.joined_people_item, viewGroup, false);
                return new PicViewHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

            if(viewHolder instanceof PicViewHolder){

                final String pic = alPics.get(position);

                PicViewHolder picViewHolder = (PicViewHolder) viewHolder;
                picViewHolder.text_view.setVisibility(View.GONE);
            }
            else  if(viewHolder instanceof AddViewHolder){

                AddViewHolder addViewHolder = (AddViewHolder) viewHolder;
                //do something..
            }
        }

        @Override
        public int getItemCount() {

            return alPics.size();
        }
    }

    public class PicViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image_view;
        private TextView text_view;

        public PicViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    /*Intent intent1 = new Intent(ChatPlusActivity.this, ChatActivity.class);
                    intent1.putExtra("contact",contacts.get(getAdapterPosition()));
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EditProfilePicsActivity.this);
                    LinearLayout optionsEditPhoto = (LinearLayout) LayoutInflater.from(EditProfilePicsActivity.this).inflate(R.layout.options_edit_photo,null);

                    optionsEditPhoto.findViewById(R.id.tvSetMainPhoto).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO do something to set this pic as main pic
                            if(bottomSheetDialog!= null && bottomSheetDialog.isShowing()) bottomSheetDialog.dismiss();
                        }
                    });

                    optionsEditPhoto.findViewById(R.id.tvDeletePhoto).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO do something to delete this pic
                            if(bottomSheetDialog!= null && bottomSheetDialog.isShowing()) bottomSheetDialog.dismiss();
                        }
                    });
                    bottomSheetDialog.setContentView(optionsEditPhoto);
                    bottomSheetDialog.show();

                    return false;
                }
            });
            image_view = (ImageView) v.findViewById(R.id.image_view);
            text_view = (TextView) v.findViewById(R.id.text_view);
        }
    }


    public class AddViewHolder extends RecyclerView.ViewHolder
    {
        private RelativeLayout rlContainer;

        public AddViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    /*Intent intent1 = new Intent(ChatPlusActivity.this, ChatActivity.class);
                    intent1.putExtra("contact",contacts.get(getAdapterPosition()));
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                    Toast.makeText(getApplicationContext(),"Add photo",Toast.LENGTH_LONG).show();
                }
            });

            rlContainer = (RelativeLayout) v.findViewById(R.id.rlContainer);
        }
    }
}
