package com.ideal.recognition.ideal.amazon;

import android.util.Log;

import com.ideal.recognition.ideal.Listing;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by recognition on 17/05/17.
 */

public class AmazonParser {
    public static List<Listing> parse(InputStream is) {
        List<Listing> listings = null;
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            // create a SAXXMLHandler
            AmazonHandler saxHandler = new AmazonHandler();
            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // the process starts
            xmlReader.parse(new InputSource(is));
            // get the `Employee list`
            listings = saxHandler.getListings();

        } catch (Exception ex) {
            Log.d("XML", "SAXXMLParser: parse() failed");
        }

        // return Employee list
        return listings;
    }
}