package com.ideal.recognition.ideal;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ideal.recognition.ideal.amazon.AmazonInvoke;
import com.ideal.recognition.ideal.amazon.AmazonParser;
import com.ideal.recognition.ideal.ebay.EbayInvoke;
import com.ideal.recognition.ideal.ebay.EbayParser;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Scanner extends AppCompatActivity implements SearchView.OnQueryTextListener, OnClickListener, WebService.OnTaskDoneListener{

    private Button scanBtn;
    private ArrayList<Listing> all_listings;
    private ListView resultListView;
    private SearchView searchView;
    private TextView message;
    private static int slot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slot = new Random().nextInt(10);

        message = (TextView)findViewById(R.id.messagebox);
        resultListView = (ListView) findViewById(R.id.result_list_view);
        scanBtn = (Button)findViewById(R.id.scan_button);
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
        scanBtn.setOnClickListener(this);
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 1
                Listing selectedListing = all_listings.get(position);

                // 2
                Uri uri = Uri.parse(selectedListing.getListingUrl()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        });
    }

    public void onClick(View v){
        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null && scanningResult.getContents() != null) {
            doSearch(scanningResult.getContents());
        }
    }

    private void doSearch(String keyword){
        all_listings = new ArrayList<>();
        message.setText("");

        keyword = keyword.replaceAll(" ","+");
        Log.i("KEY", keyword);

        AmazonInvoke amazon = new AmazonInvoke();
        String amazonUrl = amazon.getAmazonUrl(keyword);

        new WebService(amazonUrl,this).execute();
        Log.e("[AMAZON-URL]", amazonUrl);

        Log.d("[SLOT]",String.valueOf(slot));
        EbayInvoke ebay = new EbayInvoke();
        String ebayUrl = ebay.getEbayUrl(keyword);

        new WebService(ebayUrl,this).execute();
        Log.e("[EBAY-URL]", ebayUrl);

}

    @Override
    public void onTaskDone(String responseData) throws UnsupportedEncodingException {
        Log.e("[Response]",responseData);
        if(responseData.startsWith("<")){

            List<Listing> listings = AmazonParser.parse(new ByteArrayInputStream(responseData.getBytes()));

            all_listings.addAll(listings);
            if(all_listings.size() > 0){
                Collections.sort(all_listings);
                Collections.reverse(all_listings);
                ListingArrayAdapter adapter = new ListingArrayAdapter(this, all_listings);
                resultListView.setAdapter(adapter);
                message.setText("");
            }
            else {
                ListingArrayAdapter adapter = new ListingArrayAdapter(this, all_listings);
                resultListView.setAdapter(adapter);
                message.setText("No results");
            }
        }
        else if (responseData.startsWith("{")) {
            ArrayList<Listing> listings = null;
            EbayParser parser = new EbayParser(this.getApplicationContext());
            try {
                listings = parser.parseListings(responseData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(listings != null && !listings.isEmpty()){
                for(Listing i : listings){
                    all_listings.add(i);
                    i.setSource("Ebay");
                }
            }
            if(all_listings.size() > 0){
                Collections.sort(all_listings);
                Collections.reverse(all_listings);
                ListingArrayAdapter adapter = new ListingArrayAdapter(this, all_listings);
                resultListView.setAdapter(adapter);
                message.setText("");
            }
            else {
                ListingArrayAdapter adapter = new ListingArrayAdapter(this, all_listings);
                resultListView.setAdapter(adapter);
                message.setText("No results");
            }
            Log.e("[EBAY]",String.valueOf(all_listings.size()));
        }
    }

    @Override
    public void onError() {
        Log.e("[Error]","Error retrieving results");
        if(all_listings.size() == 0) {
            ListingArrayAdapter adapter = new ListingArrayAdapter(this, all_listings);
            resultListView.setAdapter(adapter);
            message.setText("No results");
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        doSearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
