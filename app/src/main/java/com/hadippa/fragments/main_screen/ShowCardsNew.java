package com.hadippa.fragments.main_screen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.activities.MyPlan;
import com.hadippa.tindercard.FlingCardListener;
import com.hadippa.tindercard.SwipeFlingAdapterView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowCardsNew.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowCardsNew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowCardsNew extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DialogPlus dialog = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean menuOpened = false;
    public static MyAppAdapter myAppAdapter;
    private ArrayList<Data> al;
    private SwipeFlingAdapterView flingContainer;


    private OnFragmentInteractionListener mListener;
   // ArrayList<String> al;
   // CustomBaseAdapter arrayAdapter;

    ImageButton swipeLeft,swipeRight;
    private SwipeDeck cardStack;
    Dialog dialog1;
    RelativeLayout relFab;
    ImageButton imageOptions;
    FloatingActionsMenu multiple_actions;

    int i = 0;
   // FloatingActionsMenu multiple_actions;
    ArrayList<String> namesArray = new ArrayList<>();
    ArrayList<Drawable> drawables = new ArrayList<>();
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

        public List<Data> parkingList;
        public Context context;

        private class ViewHolder {
            ImageView imageView;
            TextView tvFollowling;
            TextView txtDesc;
        }

        public void remove(int i) {
            parkingList.remove(i);
            notifyDataSetChanged();
        }

        private MyAppAdapter(List<Data> apps, Context context) {
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (convertView == null) {

                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.tvFollowling = (TextView) convertView.findViewById(R.id.tvFollowling);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.coverImage);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvFollowling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showDialog(Gravity.BOTTOM,true,false,false);
                }
            });
            //viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            //Glide.with(MainActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return convertView;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_show_cards, container, false);

        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        al = new ArrayList<>();
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));

        myAppAdapter = new MyAppAdapter(al, getActivity());
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

                Log.d("LIST", "removed object!");
                myAppAdapter.remove(0);
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                makeToast(getActivity(), "Left!");
                /*al.remove(0);
                myAppAdapter.notifyDataSetChanged();*/
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(getActivity(), "Right!");
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

        swipeLeft = (ImageButton)view.findViewById(R.id.imageLeft);
        swipeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectLeft();
            }
        });

        swipeRight = (ImageButton)view.findViewById(R.id.imageRight);
        swipeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectRight();
            }
        });

        imageOptions = (ImageButton)view.findViewById(R.id.imageOptions);

        imageOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

        multiple_actions = (FloatingActionsMenu)view.findViewById(R.id.multiple_actions);

        /*fabSpeedDial = (FabSpeedDial) view.findViewById(R.id.fab1);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {

            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {

                //Do something with yout menu items, or return false if you don't want to show them
                //logv("On Prepare menu called");
               // dumbView.setVisibility(View.VISIBLE);
                menuOpened = true;
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.actionMyPlan) {
                    *//*showCategoryCreationDialog();
                    dumbView.setVisibility(View.INVISIBLE);*//*
                    menuOpened = false;
                    return true;
                }

                if (menuItem.getItemId() == R.id.actionFollowing) {

                    //dumbView.setVisibility(View.INVISIBLE);
                   //startActivity(new Intent(MainActivity.this, AddHashtagActivity.class));
                    menuOpened = false;
                    return true;
                }

                if (menuItem.getItemId() == R.id.actionToday) {

                    *//*dumbView.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(MainActivity.this, LinkContentActivity.class);
                    intent.putExtra("comingFrom","thisApp");
                    startActivity(intent);*//*
                    menuOpened = false;
                    return true;
                }

                if (menuItem.getItemId() == R.id.actionNearby) {

                    //dumbView.setVisibility(View.INVISIBLE);
                    //startActivity(new Intent(MainActivity.this, AddHashtagActivity.class));
                    menuOpened = false;
                    return true;
                }

                if (menuItem.getItemId() == R.id.actionCLose) {

                    //dumbView.setVisibility(View.INVISIBLE);
                    //startActivity(new Intent(MainActivity.this, AddHashtagActivity.class));
                    menuOpened = false;
                    return true;
                }

                return false;
            }

            @Override
            public void onMenuClosed() {

                *//*logv("On menu closed called");
                dumbView.setVisibility(View.INVISIBLE);*//*
            }
        });

        cardStack = (SwipeDeck) view.findViewById(R.id.swipe_deck);*/

        namesArray.add("Sahil");
        namesArray.add("Kartik");
        namesArray.add("Nilam");
        namesArray.add("Jaimin");
        namesArray.add("Jay");
        namesArray.add("Krunal");
        namesArray.add("AliAkbar");
        namesArray.add("Jabir");

        drawables.add(getResources().getDrawable(R.drawable.ic_avatar3));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar2));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar1));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar4));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar5));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar2));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar4));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar3));

        namesArray.add("Sahil");
        namesArray.add("Kartik");
        namesArray.add("Nilam");
        namesArray.add("Jaimin");
        namesArray.add("Jay");
        namesArray.add("Krunal");
        namesArray.add("AliAkbar");
        namesArray.add("Jabir");

        drawables.add(getResources().getDrawable(R.drawable.ic_avatar3));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar2));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar1));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar4));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar5));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar2));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar4));
        drawables.add(getResources().getDrawable(R.drawable.ic_avatar3));


        imageOptions = (ImageButton)view.findViewById(R.id.imageOptions);

        imageOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

/*

        al = new ArrayList<String>();
        al.add("php");
        al.add("c");
        al.add("python");
        al.add("java");

        //choose your favorite adapter
        arrayAdapter = new CustomBaseAdapter();

        //set the listener and the adapter
        cardStack.setAdapter(arrayAdapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }

            @Override
            public void cardActionDown() {
                Log.i("fff", "cardActionDown");// cardStack.swipeTopCardRight(180);
            }

            @Override
            public void cardActionUp() {
                Log.i("fff", "cardActionUp");

             //   cardStack.swipeTopCardLeft(180);
            }

        });
        */
        multiple_actions = (FloatingActionsMenu)view.findViewById(R.id.multiple_actions);

        return view;
    }


    static void makeToast(Context ctx, String s){
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

   /* public class CustomBaseAdapter extends BaseAdapter {


        *//*private view holder class*//*
        private class ViewHolder {
            ImageView imageView;
            TextView tvFollowling;
            TextView txtDesc;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            LayoutInflater mInflater = getActivity().getLayoutInflater();
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_match_parent_sample,parent, false);
                holder = new ViewHolder();
               // holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
                holder.tvFollowling = (TextView) convertView.findViewById(R.id.tvFollowling);
                holder.imageView = (ImageView) convertView.findViewById(R.id.coverImage);


                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

           *//* holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth(),
                    getWindowManager().getDefaultDisplay().getWidth()));*//*

            holder.tvFollowling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showDialog(Gravity.BOTTOM,true,false,false);
                }
            });
            return convertView;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return Long.parseLong(null);
        }
    }
*/
    void showOptionsDialog(){


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

        TextView vMyPlan  =  (TextView)dialog1.findViewById(R.id.vMyPlan);
        vMyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

                Intent intent = new Intent(getActivity(), MyPlan.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        TextView vfollowling  =  (TextView)dialog1.findViewById(R.id.vfollowling);
        vfollowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

               /* Intent intent = new Intent(getActivity(), MyPlan.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });

        TextView vToday  =  (TextView)dialog1.findViewById(R.id.vToday);
        vToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

                /*Intent intent = new Intent(getActivity(), MyPlan.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });

        TextView vNearby  =  (TextView)dialog1.findViewById(R.id.vNearby);
        vNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
/*
                Intent intent = new Intent(getActivity(), MyPlan.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });

        TextView vClose  =  (TextView)dialog1.findViewById(R.id.vClose);
        vClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    private void showDialog(int gravity, boolean showHeader, boolean showFooter, boolean expanded) {
        boolean isGrid;
        Holder holder;

                holder = new GridHolder(4);
                isGrid = true;


        OnBackPressListener onBackPressListener  = new OnBackPressListener() {
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

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), isGrid);

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

        View  header = getActivity().getLayoutInflater().inflate(R.layout.header,null);
        ImageView showMore = (ImageView)header.findViewById(R.id.dismiss);
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
                    @Override public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
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


    public class SimpleAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private boolean isGrid;

        public SimpleAdapter(Context context, boolean isGrid) {
            layoutInflater = LayoutInflater.from(context);
            this.isGrid = isGrid;
        }

        @Override
        public int getCount() {
            return 16;
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
                if (isGrid) {
                    view = layoutInflater.inflate(R.layout.simple_grid_item, parent, false);
                }

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
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

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

}
