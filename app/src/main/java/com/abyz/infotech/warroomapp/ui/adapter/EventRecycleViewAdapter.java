package com.abyz.infotech.warroomapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.CircleTransform;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.ui.fragment.EventDetailsFragment;
import com.abyz.infotech.warroomapp.ui.model.EventModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class EventRecycleViewAdapter extends RecyclerView.Adapter<EventRecycleViewAdapter.ItemViewHolder> {

    private Activity mActivity;
    private List<EventModel> eventModels;
    View view;
    ByteArrayOutputStream bytearrayoutputstream;
    File file;
    FileOutputStream fileoutputstream;
    private String imagename;
    public EventRecycleViewAdapter(List<EventModel> eventModels1, Activity activity) {
        this.eventModels = eventModels1;
        this.mActivity = activity;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_event_list, viewGroup, false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, final int position) {

        itemViewHolder.mEventDate.setText(Constants.getStringDataFromTimestamp(eventModels.get(position).getEventDate()));
        itemViewHolder.mEventTime.setText(Constants.getStringTimeFromTimestamp1(eventModels.get(position).getEventTime()));
        String Location = (String.format("%s %s %s", (eventModels.get(position).getEventLocation()),
                                                  (eventModels.get(position).getDistname()),
                                                  (eventModels.get(position).getStatename())));
        itemViewHolder.mLocation.setText(Location);
        itemViewHolder.mEvantName.setText(eventModels.get(position).getEventName());
        imagename = eventModels.get(position).getEventImage();

        Picasso.with(mActivity)
                .load(Constants.PICASSO_BASE_URL + eventModels.get(position).getEventImage())
                .transform((Transformation) new CircleTransform())
                .fit()
                .centerCrop()
                .into(itemViewHolder.mImage);

        Picasso.with(mActivity)
                .load(Constants.PICASSO_BASE_URL + eventModels.get(position).getEventImage())
               // .transform((Transformation) new CircleTransform())
                .fit()
                .placeholder(R.drawable.progress_animation )
                .centerCrop()
                .into(itemViewHolder.mEventImage);

    }

    @Override
    public int getItemCount() {
        return eventModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mEventDate,mEventTime,mEvantName,mLocation;
        ImageView mImage,imageView_next,mEventImage;
        public ItemViewHolder(View itemView) {
            super(itemView);
            Bitmap bm = BitmapFactory.decodeResource(mActivity.getResources(),
                    R.drawable.ic_event_image);
            mEventDate = (TextView) itemView.findViewById(R.id.view_date);
            mEventTime = (TextView) itemView.findViewById(R.id.view_time);
            mEvantName = (TextView) itemView.findViewById(R.id.event_name);
            mLocation = (TextView) itemView.findViewById(R.id.view_location);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            imageView_next = (ImageView) itemView.findViewById(R.id.imageview_next);
            mEventImage = (ImageView) itemView.findViewById(R.id.event_image);
           // mImage.setImageBitmap(getCircleBitmap(bm));
            imageView_next.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


            try {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            android.support.v4.app.FragmentTransaction fragmentTransaction=((AppCompatActivity) mActivity).getSupportFragmentManager().beginTransaction();
            EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("event_id", eventModels.get(getLayoutPosition()).getEventId());
            bundle.putString("event_name", eventModels.get(getLayoutPosition()).getEventName());
            bundle.putLong("event_date", eventModels.get(getLayoutPosition()).getEventDate());
            bundle.putLong("event_time", eventModels.get(getLayoutPosition()).getEventTime());
            bundle.putString("event_image", eventModels.get(getLayoutPosition()).getEventImage());
            bundle.putString("event_desc", eventModels.get(getLayoutPosition()).getEventDesc());
            bundle.putString("event_location", eventModels.get(getLayoutPosition()).getEventLocation());

            eventDetailsFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, eventDetailsFragment);
            fragmentTransaction.addToBackStack("").commit();

        }

}}
