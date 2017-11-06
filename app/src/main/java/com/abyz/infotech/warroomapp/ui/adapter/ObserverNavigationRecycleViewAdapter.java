package com.abyz.infotech.warroomapp.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.common.CircleTransform;
import com.abyz.infotech.warroomapp.common.Constants;
import com.abyz.infotech.warroomapp.common.SharedPreferencesUtility;
import com.abyz.infotech.warroomapp.greenDAO.GreenDaoManager;
import com.abyz.infotech.warroomapp.ui.model.NavigationItemModel;
import com.abyz.infotech.warroomapp.ui.model.UserModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class ObserverNavigationRecycleViewAdapter extends RecyclerView.Adapter<ObserverNavigationRecycleViewAdapter.ItemViewHolder> {

	private final Context mContext;
	List<NavigationItemModel> mNavigationItemModels;
	private OnNavigationItemSelectedListener listener;
	private List<UserModel> userModels = null;
	String usergender;



	public ObserverNavigationRecycleViewAdapter(List<NavigationItemModel> navigationItemModels, Context context) {
		mNavigationItemModels = navigationItemModels;
		this.mContext = context;
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resources resources = mContext.getResources();
		float pxFloat = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, resources.getDisplayMetrics());
		if (viewType == 1) {
			View viewItemHeader = layoutInflater.inflate(R.layout.cardview_navigation_drawer, null);
			viewItemHeader.setMinimumWidth((int) pxFloat);
			return new ItemViewHolder(viewItemHeader, viewType);
		} else if (viewType == 0) {
			View viewItemHeader = layoutInflater.inflate(R.layout.navigation_drawer_header, null);
			viewItemHeader.setMinimumWidth((int) pxFloat);
			return new ItemViewHolder(viewItemHeader, viewType);

		} else if (viewType == 8) {
			View viewItemHeader = layoutInflater.inflate(R.layout.navigation_drawer_footer, null);
			viewItemHeader.setMinimumWidth((int) pxFloat);
			return new ItemViewHolder(viewItemHeader, viewType);
		}
		return null;
	}

	@Override
	public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {
		if (position !=0 && position !=8) {
			itemViewHolder.itemImageView.setImageResource(mNavigationItemModels.get(position - 1).getItemResourceID());
			itemViewHolder.itemTextView.setText(mNavigationItemModels.get(position - 1).getItemTitle());
		} else {
			if (position == 0) {
				try {

				itemViewHolder.uerTypename.setText("Observer");

					String addresh = (SharedPreferencesUtility.getPrefString(mContext, Constants.SHARED_PREFERENCE_ADDRESH));
					String image = (SharedPreferencesUtility.getPrefString(mContext, Constants.SHARED_PREFERENCE_IMAGE));
					String name = (SharedPreferencesUtility.getPrefString(mContext, Constants.SHARED_PREFERENCE_NAME));

					Picasso.with(mContext)
							.load(Constants.PICASSO_BASE_URL + image)
							.transform((Transformation) new CircleTransform())
							.fit()
							.centerCrop()
							.into(itemViewHolder.profilePictureImageView);
					itemViewHolder.useraddresh.setText(addresh);
					itemViewHolder.userNameTextView.setText(name);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{



			}}
	}

	@Override
	public int getItemViewType(int position) {
		// if (position == 0) return 0;
		//  else return 1;

		if(position == 8){
			return 8;
		}else
		{
			if(position == 0){

				return 0;
			}else
			{
				return 1;
			}
		}
	}

	@Override
	public int getItemCount() {
		return mNavigationItemModels.size() + 2;
	}

	public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener listener) {
		this.listener = listener;
	}

	public interface OnNavigationItemSelectedListener {
		void onItemSelected(View view, int position);
	}

	public class ItemViewHolder extends RecyclerView.ViewHolder {
		TextView itemTextView;
		ImageView itemImageView;
		ImageView profilePictureImageView;
		TextView userNameTextView,uerTypename,useraddresh;

		public ItemViewHolder(View itemView, int type) {
			super(itemView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {


					if (listener != null) {
						listener.onItemSelected(v, getLayoutPosition());
					}
				}
			});
			if (type == 1) {
				itemTextView = (TextView) itemView.findViewById(R.id.item_title);
				itemImageView = (ImageView) itemView.findViewById(R.id.item_icon);
			} else if (type == 0) {

				Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(),
						R.drawable.rahul_gandhi);



				profilePictureImageView = (ImageView) itemView.findViewById(R.id.profile_image);


				//profilePictureImageView.setImageBitmap(getCircleBitmap(bm));
				userNameTextView = (TextView) itemView.findViewById(R.id.text_view_username);
				uerTypename = (TextView) itemView.findViewById(R.id.text_view_uertypename);
				useraddresh = (TextView) itemView.findViewById(R.id.text_view_useraddresh);


			}


		}
	}


}
