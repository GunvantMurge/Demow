

package com.abyz.infotech.warroomapp.ui.model;

import com.google.gson.Gson;

public class NavigationItemModel {
	private int mItemResourceID;
	private String mItemTitle;

	public NavigationItemModel(int itemResourceID, String itemTitle) {
		mItemResourceID = itemResourceID;
		mItemTitle = itemTitle;
	}

	public int getItemResourceID () {
		return mItemResourceID;
	}

	public void setItemResourceID (int itemResourceID) {
		mItemResourceID = itemResourceID;
	}

	public String getItemTitle () {
		return mItemTitle;
	}

	public void setItemTitle (String itemTitle) {
		mItemTitle = itemTitle;
	}

	@Override
	public String toString () {
		return new Gson().toJson(this);
	}

}
