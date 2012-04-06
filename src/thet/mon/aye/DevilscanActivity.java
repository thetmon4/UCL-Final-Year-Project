package thet.mon.aye;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal; import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalReceiverDetails;
import com.paypal.android.MEP.PayPalPayment;

public class DevilscanActivity extends ListActivity {

    private static final int ACTIVITY_CREATE=0;
    private BarcodeDBAdapter mDbHelper;
    private Cursor mNotesCursor;
    public JsonObject Jobj;
    PayPal ppObj; 
    
    @Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDbHelper = new BarcodeDBAdapter(this);
        mDbHelper.open();
        fillData();
        String total= Double.toString(getTotal());
        final TextView showtotal= (TextView)findViewById(R.id.text3);
        showtotal.setText("Total is"+total);
        final Button scanButton = (Button) findViewById(R.id.button);
		scanButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {  
  				Intent intent1 = new Intent("com.google.zxing.client.android.SCAN");
		        intent1.putExtra("SCAN_MODE", "1D_CODE_MODE");
		        startActivityForResult(intent1, 0);
		      	}
			
		});
		

        LinearLayout mainLayout= (LinearLayout) findViewById(R.id.mainLayout);
        
        ppObj = PayPal.initWithAppID(this.getBaseContext(), "APP-80W284485P519543T", PayPal.ENV_SANDBOX);
        CheckoutButton launchPayPalButton = ppObj.getCheckoutButton(this, PayPal.BUTTON_278x43, PayPal.PAYMENT_TYPE_PERSONAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.bottomMargin = 10;
        launchPayPalButton.setLayoutParams(params);
        //launchPayPalButton.setOnClickListener(this); 
        launchPayPalButton.setOnClickListener(new Button.OnClickListener() {
    	    public void onClick(View v) { 
    	    	
    	    	if(checkInternet())
    	    	{    	    	
	    	    	// Create a basic PayPalPayment.
	        		PayPalPayment payment = getPayment(getTotal());
	        							
					// Use checkout to create our Intent.
					Intent checkoutIntent = PayPal.getInstance().checkout(payment, v.getContext());
					// Use the android's startActivityForResult() and pass in our Intent.
					// This will start the library.
					startActivityForResult(checkoutIntent, 1);
	
    	    	} else 
    	    	{
    	    		Toast toast = Toast.makeText(v.getContext(), "You need to be connected to Internet.",Toast.LENGTH_SHORT);
    	    		toast.show();		
    	    	}
    	        
    	    }
    	    
    		});
                 
        
        mainLayout.addView(launchPayPalButton);
    }

    
    private double getTotal(){
   
       double total= mDbHelper.getTotal();
               
		return total;}
   
 private PayPalPayment getPayment(double d){
    	PayPalPayment payment = new PayPalPayment();
    	
    		payment.setSubtotal(BigDecimal.valueOf(10));
    		payment.setCurrencyType("GBP");
      		payment.setRecipient("thetmon4@gmail.com");
    		payment.setMerchantName("CoolPay Company");
    		
    		PayPal pp = PayPal.getInstance();
    		if(pp==null)
    			pp = PayPal.initWithAppID(this, "APP-80W284485P519543T", PayPal.ENV_SANDBOX);
    		
    		Intent paypalIntent = pp.checkout(payment, this);
    		this.startActivityForResult(paypalIntent, 1);
    		
    
    	return payment;
    }
    

    
    private boolean checkInternet()
    {
    	ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
    	  if (i == null)
    	    return false;
    	  if (!i.isConnected())
    	    return false;
    	  if (!i.isAvailable())
    	    return false;
    	  return true;
    }

    private void fillData() {
        // Get all of the rows from the database and create the item list
        mNotesCursor = mDbHelper.fetchAllNotes(); 
        startManagingCursor(mNotesCursor);
        
        // Create an array to specify the fields we want to display in the list (only TITLE)
        //String[] from = new String[]{BarcodeDBAdapter.KEY_PRICE};
        String[] from= new String[]{BarcodeDBAdapter.KEY_NAME,BarcodeDBAdapter.KEY_PRICE};
        
        
       
       
        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1, R.id.text2};
        

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes = 
        new SimpleCursorAdapter(this, R.layout.barcode_row, mNotesCursor, from,to);
     
        setListAdapter(notes);
    }
   
  
    public void onActivityResult(int requestCode, int resultCode, Intent intent1) {
        if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	            String contents = intent1.getStringExtra("SCAN_RESULT");
	            String format = intent1.getStringExtra("SCAN_RESULT_FORMAT");
	           
	             String tescoProductName;
	             double tescoPrice;
				try {
					tescoProductName = JsonObject.getTescoProductName(contents);
					tescoPrice= JsonObject.getTescoProductValue(contents);
					 mDbHelper.createNote(format,tescoProductName,tescoPrice);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          //  Save tesco details to database
	            
	          
	        } else if (resultCode == RESULT_CANCELED) {
	            // Handle cancel
	        
	        }
	        else if(requestCode==1)//request from PayPal
	        {
	        	//to do paypal response
	        	getPayment(resultCode);
	        }
	    }
    }
  
}
