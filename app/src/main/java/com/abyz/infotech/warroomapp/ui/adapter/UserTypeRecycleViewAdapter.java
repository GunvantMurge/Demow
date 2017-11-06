package com.abyz.infotech.warroomapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.ui.activity.NavigationActivity;
import com.abyz.infotech.warroomapp.ui.fragment.EventDetailsFragment;
import com.abyz.infotech.warroomapp.ui.model.EventModel;
import com.abyz.infotech.warroomapp.ui.model.UserProfileModel;

import java.util.List;

public class UserTypeRecycleViewAdapter extends RecyclerView.Adapter<UserTypeRecycleViewAdapter.ItemViewHolder> {

    private Activity mActivity;
    private List<UserProfileModel> userProfileModels;

    public UserTypeRecycleViewAdapter(List<UserProfileModel> userProfileModelList, Activity activity) {
        this.userProfileModels = userProfileModelList;
        this.mActivity = activity;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_profiles_names, viewGroup, false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {

        //itemViewHolder.mEventDate.setText(userProfileModels.get(position).getUsrTypeId());

        switch (userProfileModels.get(position).getUsrTypeId())
        {
            case 3:
                itemViewHolder.mTypeName.setText(userProfileModels.get(position).getUsrTypeName());
                itemViewHolder.icon.setImageResource(R.mipmap.ic_v);
                break;

            case 4:
                itemViewHolder.mTypeName.setText(userProfileModels.get(position).getUsrTypeName());
                itemViewHolder.icon.setImageResource(R.mipmap.ic_s);
                break;

            case 5:
                itemViewHolder.mTypeName.setText(userProfileModels.get(position).getUsrTypeName());
                itemViewHolder.icon.setImageResource(R.mipmap.ic_o);
                break;

            case 6:
                itemViewHolder.mTypeName.setText(userProfileModels.get(position).getUsrTypeName());
                itemViewHolder.icon.setImageResource(R.mipmap.ic_c);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return userProfileModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTypeName;
        ImageView icon;
        LinearLayout ChangeProfile;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTypeName = (TextView) itemView.findViewById(R.id.item_title);
            icon = (ImageView) itemView.findViewById(R.id.item_icon);
            ChangeProfile = (LinearLayout) itemView.findViewById(R.id.change_profile);
            ChangeProfile.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


            SharedPreferencesUtility.savePrefInt(mActivity, Constants.SHARED_PREFERENCE_USER_TYPE_ID, userProfileModels.get(getLayoutPosition()).getUsrTypeId());
            SharedPreferencesUtility.savePrefInt(mActivity, Constants.CHECKEVENT, 0);
            SharedPreferencesUtility.savePrefInt(mActivity, Constants.CHECKTASK, 0);

            GreenDaoManager greenDaoManager = null;
            try {
                greenDaoManager = GreenDaoManager.getInstance(mActivity);
                greenDaoManager.deletep();
            }catch (Exception e){

            }

            Intent intent = new Intent(mActivity, NavigationActivity.class);
            mActivity.startActivity(intent);
            mActivity.finish();


        }

    }
}
