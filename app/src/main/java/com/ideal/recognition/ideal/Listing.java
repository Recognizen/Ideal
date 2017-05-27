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


import android.util.Log;

public class Listing implements Comparable<Listing>{
	private String id;
	private String title;
	private String imageUrl;
	private String listingUrl;
	private String currentPrice;
	private String source;

	public String getSource(){return source;}
	public void setSource(String source){this.source = source;}
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public String getImageUrl(){
		return imageUrl;
	}
	public void setImageUrl(String imageUrl){
		//fix for Android 2.3
		CharSequence target="\\/";
		CharSequence replace="/";
		String fixedUrl=imageUrl.replace(target,replace);
		this.imageUrl=fixedUrl;
	}
	public String getListingUrl(){
		return listingUrl;
	}
	public void setListingUrl(String listingUrl){
		//fix for Android 2.3
		CharSequence target="\\/";
		CharSequence replace="/";
		String fixedUrl=listingUrl.replace(target,replace);
		this.listingUrl=fixedUrl;
	}
	public String getCurrentPrice(){
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice){
		this.currentPrice=currentPrice;
	}

	@Override
	public String toString() {
		return "Listing{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", listingUrl='" + listingUrl + '\'' +
				", currentPrice='" + currentPrice + '\'' +
				'}';
	}
	
	@Override
	public int compareTo(Listing another){

		Integer first = 99999;
		Integer second = 99999;

		if(another.getCurrentPrice() != null && another.getCurrentPrice().contains("EUR"))
			first = Integer.valueOf(another.getCurrentPrice().replace(" EUR","").replace(".","").replace(",","").trim());
		if(this.getCurrentPrice() != null && this.getCurrentPrice().contains("EUR"))
		second = Integer.valueOf(this.getCurrentPrice().replace(" EUR", "").replace(".","").replace(",", "").trim());

		return(first.compareTo(second));
	}
}