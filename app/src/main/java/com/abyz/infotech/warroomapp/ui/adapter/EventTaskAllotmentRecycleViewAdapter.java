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
import com.abyz.infotech.warroomapp.ui.model.EventTaskAllotmentModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class EventTaskAllotmentRecycleViewAdapter extends RecyclerView.Adapter<EventTaskAllotmentRecycleViewAdapter.ItemViewHolder> {

    private Activity mActivity;
    private List<EventTaskAllotmentModel> eventTaskAllotmentModels;
    TextView mVolunteerNames, mEventNames, mTaskNames, mStartDates, mEndDate, mStartTime, mEndTimem, mUserMobileNo;
    private ImageView mUserPhoto;
    private String mMobileNo;

    public EventTaskAllotmentRecycleViewAdapter(List<EventTaskAllotmentModel> eventTaskAllotmentModel, Activity activity) {
        this.eventTaskAllotmentModels = eventTaskAllotmentModel;
        this.mActivity = activity;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_event_task_allotment_list, viewGroup, false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {

        Log.d("data", "eventTaskAllotmentModels ---->>>> " + eventTaskAllotmentModels);
        itemViewHolder.mVolunteerName.setText(eventTaskAllotmentModels.get(position).getUsrName());
        itemViewHolder.mTaskName.setText(eventTaskAllotmentModels.get(position).getTskName());
        itemViewHolder.mEventName.setText(eventTaskAllotmentModels.get(position).getEventName());
        if (eventTaskAllotmentModels.get(position).getTaStatusId() == 5) {
            itemViewHolder.mTaskstatus.setText("Complete");
        } else {
            itemViewHolder.mTaskstatus.setText("Pending");
        }

    }

    @Override
    public int getItemCount() {
        return eventTaskAllotmentModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mVolunteerName, mEventName, mTaskName, mTaskstatus;
        ImageView imageView_next;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mVolunteerName = (TextView) itemView.findViewById(R.id.volunteers_name);
            mEventName = (TextView) itemView.findViewById(R.id.event_name);
            mTaskName = (TextView) itemView.findViewById(R.id.task_name);
            imageView_next = (ImageView) itemView.findViewById(R.id.imageview_next);
            mTaskstatus = (TextView) itemView.findViewById(R.id.task_status);

            imageView_next.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.imageview_next) {


                Bitmap bm = BitmapFactory.decodeResource(mActivity.getResources(),
                        R.drawable.ic_event_image);

                final Dialog dialog1 = new Dialog(mActivity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.popup_event_task_allotment_detaild);

                mVolunteerNames = (TextView) dialog1.findViewById(R.id.volunteer_name);
                mEventNames = (TextView) dialog1.findViewById(R.id.event_name);
                mTaskNames = (TextView) dialog1.findViewById(R.id.task_name);
                mStartDates = (TextView) dialog1.findViewById(R.id.text_view_start_date);
                mEndDate = (TextView) dialog1.findViewById(R.id.text_view_end_date);
                mStartTime = (TextView) dialog1.findViewById(R.id.text_view_start_time);
                mEndTimem = (TextView) dialog1.findViewById(R.id.text_view_end_time);
                mUserPhoto = (ImageView) dialog1.findViewById(R.id.image);
                mUserMobileNo = (TextView) dialog1.findViewById(R.id.user_mobile_no);
                ImageView callphone = (ImageView) dialog1.findViewById(R.id.call_phone);


                mVolunteerNames.setText(eventTaskAllotmentModels.get(getLayoutPosition()).getUsrName());
                mEventNames.setText(eventTaskAllotmentModels.get(getLayoutPosition()).getEventName());
                mTaskNames.setText(eventTaskAllotmentModels.get(getLayoutPosition()).getTskName());
                mStartDates.setText(Constants.getStringDataFromTimestamp(eventTaskAllotmentModels.get(getLayoutPosition()).getTaStartDate()));

                mEndDate.setText(Constants.getStringDataFromTimestamp(eventTaskAllotmentModels.get(getLayoutPosition()).getTaEndDate()));
                mStartTime.setText(Constants.getStringTimeFromTimestamp1(eventTaskAllotmentModels.get(getLayoutPosition()).getTaStartTime()));
                mEndTimem.setText(Constants.getStringTimeFromTimestamp1(eventTaskAllotmentModels.get(getLayoutPosition()).getTaEndTime()));
                mUserMobileNo.setText(eventTaskAllotmentModels.get(getLayoutPosition()).getUsrMobile());

                Picasso.with(mActivity)
                        .load(Constants.PICASSO_BASE_URL + eventTaskAllotmentModels.get(getLayoutPosition()).getUsrImage())
                        .transform((Transformation) new CircleTransform())
                        .fit()
                        .centerCrop()
                        .into(mUserPhoto);


                mMobileNo = (eventTaskAllotmentModels.get(getLayoutPosition()).getUsrMobile());


                // mUserPhoto.setImageBitmap(getCircleBitmap(bm));

                callphone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mMobileNo));
                        mActivity.startActivity(intent);

                    }
                });

                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog1.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER_HORIZONTAL;

                dialog1.show();
                dialog1.getWindow().setLayout(lp.width = WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            }

        }

    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }
}
