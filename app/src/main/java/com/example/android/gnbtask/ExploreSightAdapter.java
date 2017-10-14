package com.example.android.gnbtask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created on 10/13/2017.
 */

public class ExploreSightAdapter extends RecyclerView.Adapter<ExploreSightAdapter.SightViewHolder> {

    //Tag constant to use when logging
    private static final String TAG = ExploreSightAdapter.class.getSimpleName();

    //Global variable for the context to be used by Picasso when loading images
    private Context mContext;

    //Global variable for an onClickListener
    final private ListItemClickListener mOnClickListener;


    //Member variable of List item to take object of type ExploreSight to use in the UI
    private List<ExploreSight> mList;

    //Number of items in list
    int itemCount = 10;

    private Bitmap bitmapImage = null;

    //An interface that will receive the click message and be Overridden in MainActivity
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex, String description, int price, String imageUrl);
    }

    public ExploreSightAdapter(List<ExploreSight> list, ListItemClickListener listener){
        mList = list;
        mOnClickListener = listener;
    }

    @Override
    public SightViewHolder onCreateViewHolder (ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdResource = R.layout.place_item_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdResource, viewGroup, shouldAttachToParentImmediately);
        SightViewHolder viewHolder = new SightViewHolder(view);

        mContext = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (final SightViewHolder holder, int position)
    {

        Log.v(TAG, "Context: " + mContext.toString());

        //Create two String variables for the TextView setting
        String placeDescription;
        String imageUrl;
        int price;
        int id;
        int height;

        //Variable to hold the current ExploreSight
        ExploreSight currentSight;

        //Set currentSight to current position's ExploreSight variable
        currentSight = mList.get(position);

        image currentImage = currentSight.getImage();


        //Extract variables from the ExploreSight Object
        placeDescription = currentSight.getPlaceDescription();
        imageUrl = currentImage.getUrl();
        price = currentSight.getPrice();
        id = currentSight.getId();
        height = Integer.valueOf(currentImage.getHeight());

        //Set current View with data obtained from the API
        holder.listPriceTextView.setText(String.valueOf(price) + "$");
        holder.listDescriptionTextView.setText(placeDescription);
        Picasso.with(mContext).setLoggingEnabled(true);
        Picasso.with(mContext).load(imageUrl).resize(150, height).into(holder.listImageView);

        /*
        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                exception.printStackTrace();
                Log.e(TAG, "Picasso Error: ", exception);
            }
        });
        builder.build().load(imageUrl).into(holder.listImageView);
        */

        //Glide.with(mContext).load(imageUrl).placeholder(R.mipmap.ic_launcher_round).into(holder.listImageView);

    }

    @Override
    public int getItemCount() {
        return itemCount;
    }


    class SightViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        //Declare the TextViews and ImageView within the RecyclerView Item
        TextView listPriceTextView;
        ImageView listImageView;
        TextView listDescriptionTextView;

        //Declare Constructor for the SightViewHolder
        public SightViewHolder(View itemView) {
            super(itemView);

            //Get a reference of the TextViews and ImageView to be used in setting data
            listPriceTextView = (TextView) itemView.findViewById(R.id.tv_explore_price);

            listImageView = (ImageView) itemView.findViewById(R.id.iv_explore_image);

            listDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_explore_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItem = getAdapterPosition();
            ExploreSight currentSight = mList.get(clickedItem);

            String description = currentSight.getPlaceDescription();
            int price = currentSight.getPrice();
            image image = currentSight.getImage();
            String imageUrl = image.getUrl();

            mOnClickListener.onListItemClick(clickedItem, description, price, imageUrl);
        }
    }
}
