package thet.mon.aye;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import thet.mon.aye.R.string;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal; import com.paypal.android.MEP.PayPalReceiverDetails;
import com.paypal.android.MEP.PayPalPayment;

public class DevilscanActivity extends ListActivity {

    private static final int ACTIVITY_CREATE=0;
    private BarcodeDBAdapter mDbHelper;
    private Cursor mNotesCursor;
    public JsonObject Jobj;
  
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDbHelper = new BarcodeDBAdapter(this);
        mDbHelper.open();
        fillData();
        
        final Button scanButton = (Button) findViewById(R.id.button);
		scanButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {  
  				Intent intent1 = new Intent("com.google.zxing.client.android.SCAN");
		        intent1.putExtra("SCAN_MODE", "1D_CODE_MODE");
		        startActivityForResult(intent1, 0);
		      	}
			
		});
    }
    
    @SuppressWarnings("finally")
	/*public String callWebService(String q){	
        URL url = new URL(q);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in =new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader inS = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(inS);
            String data =""; 
            String l;
            while((l=br.readLine())!=null) {
                data = data + l;
            }
            return data;
        } finally {
            urlConnection.disconnect();
        }
    }
    */
    private void fillData() {
        // Get all of the rows from the database and create the item list
        mNotesCursor = mDbHelper.fetchAllNotes();
        startManagingCursor(mNotesCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{BarcodeDBAdapter.KEY_BARCODE_TYPE};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes = 
            new SimpleCursorAdapter(this, R.layout.barcode_row, mNotesCursor, from, to);
        setListAdapter(notes);
    }
   
  
    public void onActivityResult(int requestCode, int resultCode, Intent intent1) {
        if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	            String contents = intent1.getStringExtra("SCAN_RESULT");
	            String format = intent1.getStringExtra("SCAN_RESULT_FORMAT");
	            
	           // callWebService(String q)
	           // String tescoDetails = getTescoDetails(contents);
	            //Intent JsonIntent= new Intent(this,JsonList.class);
	            //startActivity(JsonIntent);
	             String tescoDetails;
				try {
					tescoDetails = JsonObject.getTescoDetails(contents);
					 mDbHelper.createNote(tescoDetails, format);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          //  Save tesco details to database
	            
	          
	        } else if (resultCode == RESULT_CANCELED) {
	            // Handle cancel
	        
	        }
	    }
    }
   /*private void callWebService(String q) {
//    	Bundle b = new Bundle();
//    	b.putString("param1", q);
	  // Intent newIntent = new Intent(this, JsonList.class);
	   Intent newIntent= new Intent 
    	//newIntent.putExtra("param1", q);
    	startActivity(newIntent);
    	
	        	    
    }*/
}
