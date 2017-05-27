/*
ebayDemo - Demo of how to call the eBay API and display the results on an Android device
Imported from TurboGrafx16Collector - Mobile application to manage a collection of TurboGrafx-16 games
Copyright (C) 2010 Hugues Johnson

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software 
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package com.ideal.recognition.ideal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListingArrayAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<Listing> mDataSource;
	private LayoutInflater mInflater;
	
	public ListingArrayAdapter(Context context, ArrayList listings){
		mContext = context;
		this.mDataSource = listings;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	//1
	@Override
	public int getCount() {
		return mDataSource.size();
	}

	//2
	@Override
	public Object getItem(int position) {
		return mDataSource.get(position);
	}

	//3
	@Override
	public long getItemId(int position) {
		return position;
	}

	//4
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get view for row item
		View rowView = mInflater.inflate(R.layout.list_item, parent, false);

		// Get title element
		TextView titleTextView =
				(TextView) rowView.findViewById(com.ideal.recognition.ideal.R.id.recipe_list_title);

// Get subtitle element
		TextView subtitleTextView =
				(TextView) rowView.findViewById(com.ideal.recognition.ideal.R.id.recipe_list_subtitle);

// Get detail element
		ImageView detailTextView =
				(ImageView) rowView.findViewById(com.ideal.recognition.ideal.R.id.recipe_list_detail);

// Get thumbnail element
		ImageView thumbnailImageView =
				(ImageView) rowView.findViewById(com.ideal.recognition.ideal.R.id.recipe_list_thumbnail);


// 1
        Listing item = (Listing) getItem(position);

// 2
        titleTextView.setText(item.getTitle());
        subtitleTextView.setText(item.getCurrentPrice());
		if(item.getSource().contains("Amazon"))
        	detailTextView.setImageResource(R.drawable.ic_amazon);
		else if(item.getSource().contains("Ebay"))
			detailTextView.setImageResource(R.drawable.ic_ebay);
// 3
        Picasso.with(mContext).load(item.getImageUrl()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);


		return rowView;
	}
}