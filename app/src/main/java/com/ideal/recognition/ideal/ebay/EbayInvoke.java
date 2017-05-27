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

package com.ideal.recognition.ideal.ebay;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

public class EbayInvoke {
	private static String appID;
	private static String ebayURL;

    String[] appIDs = {"IdealDea-Ideal-PRD-fdbc19de5-b72a4736",
                        "IdealDea-Ideal-PRD-a090b840d-cf7305ff",
                        "IdealDea-Ideal-PRD-3090b840d-e710d994",
                        "IdealDea-Ideal-PRD-f090b840d-b227cbdb",
                        "IdealDea-Ideal-PRD-c090b840d-bf00a22f",
                        "IdealDea-Ideal-PRD-00916cf10-6756910f",
                        "IdealDea-Ideal-PRD-c090b840d-6aa80594",
                        "IdealDea-Ideal-PRD-4eff4a8b1-6b4432a3",
                        "IdealDea-Ideal-PRD-c090330f6-a1cecd2d"};

	//private Resources resources;
    private final String template = "^1services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&GLOBAL-ID=EBAY-IT&SECURITY-APPNAME=^2&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=^3&paginationInput.entriesPerPage=10&affiliate.networkId=9&affiliate.trackingId=5336695910&affiliate.customId=ebaydemo&sortOrder=StartTimeNewest";

	public EbayInvoke(){
		appID = ""; //MINE
        Log.i("[APPID]",appID);
		ebayURL = "http://svcs.ebay.it/";
	}
    public EbayInvoke(int i){
        appID = appIDs[i];
        Log.i("[APPID]",appID);
        ebayURL = "http://svcs.ebay.it/";
    }

    public String getEbayUrl(String keyword){
		CharSequence requestURL=TextUtils.expandTemplate(template,ebayURL,appID,keyword);
		return(requestURL.toString());
	}
}