package com.ideal.recognition.ideal.amazon;

import android.util.Log;

import de.codecrafters.apaarb.*;
import static de.codecrafters.apaarb.ItemInformation.ATTRIBUTES;
import static de.codecrafters.apaarb.ItemInformation.IMAGES;

/**
 * Created by recognition on 14/05/17.
 */

public class AmazonInvoke {

    public AmazonInvoke(){

    }

    public String getAmazonUrl(String keyword){
        Log.d("[AMAZON SEARCH]", "ENTERING");

        AmazonWebServiceAuthentication myAuthentication = AmazonWebServiceAuthentication.create("key", "key", "key");
        //ItemId myEan  = ItemId.createEan(keyword);
        final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch(keyword)
                .includeInformationAbout(IMAGES)
                .includeInformationAbout(ATTRIBUTES)
                .createSecureRequestUrlFor(AmazonWebServiceLocation.IT, myAuthentication);
        return requestUrl;
    }

}
