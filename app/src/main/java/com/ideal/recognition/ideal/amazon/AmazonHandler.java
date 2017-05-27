package com.ideal.recognition.ideal.amazon;

import android.util.Log;

import com.ideal.recognition.ideal.Listing;

import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class AmazonHandler extends DefaultHandler {

    private List<Listing> listings;
    //private String tempVal;
    private StringBuilder tempVal = new StringBuilder();
    private Listing tempEmp;
    private String fprice = null;
    private String currency = null;
    private boolean mediumImage = false;

    public AmazonHandler() {
        listings = new ArrayList<Listing>();
    }

    public List<Listing> getListings() {
        return listings;
    }

    // Event Handlers
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // reset
        //tempVal = "";
        tempVal.setLength(0);
        if (qName.equalsIgnoreCase("Item")) {
            tempEmp = new Listing();
            mediumImage = false;
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        //tempVal.append(ch, start, length);
        tempVal.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("Item")) {
            // add it to the list
            listings.add(tempEmp);
            tempEmp.setSource("Amazon");
        } else if (qName.equalsIgnoreCase("Title")) {
            tempEmp.setTitle(tempVal.toString());
        } else if (qName.equalsIgnoreCase("DetailPageURL")) {
            if(tempEmp.getListingUrl() == null){
                tempEmp.setListingUrl(tempVal.toString());
            }
        } else if (qName.equalsIgnoreCase("MediumImage")) {
            mediumImage = true;
        }
        else if (qName.equalsIgnoreCase("URL")) {
            if(mediumImage && tempEmp.getImageUrl() == null){
                tempEmp.setImageUrl(tempVal.toString());
            }
        } else if (qName.equalsIgnoreCase("FormattedPrice")) {
            fprice = tempVal.toString();
        } else if (qName.equalsIgnoreCase("CurrencyCode")) {
            currency = tempVal.toString();
        }
        if(fprice != null && currency != null){
            String price = fprice.replace("EUR","")+" " + currency;
            tempEmp.setCurrentPrice(price);
            fprice = null;
            currency = null;
        }
    }
}