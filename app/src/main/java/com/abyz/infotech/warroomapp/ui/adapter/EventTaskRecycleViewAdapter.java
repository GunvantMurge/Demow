package com.abyz.infotech.warroomapp.ui.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.CircleTransform;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.ui.model.EventModel;
import com.abyz.infotech.warroomapp.ui.model.EventTaskAllotmentModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class EventTaskRecycleViewAdapter extends RecyclerView.Adapter<EventTaskRecycleViewAdapter.ItemViewHolder> {

    private Activity mActivity;
    private List<EventModel> eventModels;

    public EventTaskRecycleViewAdapter(List<EventModel> eventModel, Activity activity) {
        this.eventModels = eventModel;
        this.mActivity = activity;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_event_task_list, viewGroup, false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {


        itemViewHolder.mTaskName.setText(eventModels.get(position).getTaskName());
        itemViewHolder.mEventName.setText(eventModels.get(position).getEventName());

        Log.d("mEventName","mEventName  "+eventModels.get(position).getEventName());


    }

    @Override
    public int getItemCount() {
        return eventModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mEventName, mTaskName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mEventName = (TextView) itemView.findViewById(R.id.event_name);
            mTaskName = (TextView) itemView.findViewById(R.id.task_name);


        }


    }


}
