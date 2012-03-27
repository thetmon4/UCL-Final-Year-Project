package thet.mon.aye;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal; import com.paypal.android.MEP.PayPalReceiverDetails;
import com.paypal.android.MEP.PayPalPayment;


public class DevilscanActivity extends ListActivity {
    /** Called when the activity is first created. */
    
    private static final int ACTIVITY_CREATE=0;
    private BarcodeDBAdapter mDbHelper;
    private Cursor mNotesCursor;
	
   //private ZxingScan zxscan;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDbHelper = new BarcodeDBAdapter(this);
        mDbHelper.open();
        fillData();
    // getListView();
       
        final Button scanButton = (Button) findViewById(R.id.button);
		scanButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {  
                       
			//	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				//intent.putExtra("com.google.zxing.client.android.SCAN",
					//	"1D_CODE_MODE");
				
				// startActivityForResult(intent, 0)
               // call ZXingScan on click !
				//Intent zxscan= new Intent(DevilscanActivity.this,ZxingScan.class);
				
				
				//startActivity(zxscan);
				
			    //finish();
				
				Intent intent1 = new Intent("com.google.zxing.client.android.SCAN");
		        intent1.putExtra("SCAN_MODE", "1D_CODE_MODE");
		        startActivityForResult(intent1, 0);
		        
			}
			
		});
		
//		CheckoutButton launchPayPalButton = ppObj.getCheckoutButton(this, PayPal.BUTTON_278x43, CheckoutButton.TEXT_PAY);
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//		params.bottomMargin = 10;
//		launchPayPalButton.setLayoutParams(params);
////		launchPayPalButton.setOnClickListener(this);
//		((RelativeLayout)findViewById(R.id.RelativeLayout01)).addView(launchPayPalButton);
		

    }
    
   
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
//    static public PayPal initWithAppID(Context context, String appID, int server)
//    {    
//   
//    	PayPal pp = PayPal.getInstance();
//    	if (pp == null) {
//    	    try {
////    	        pp = PayPal.initWithAppID(getApplicationContext(),"", PayPal.ENV_NONE);
//    	    } catch (IllegalStateException e) {
//    	        throw new RuntimeException(e);
//    	    }
//    	    pp.setShippingEnabled(false);
//    	}
//
//    
//    }
    
  /* protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        l=getListView();
        Cursor c = mNotesCursor;
        c.moveToPosition(position);
    	
     //  Intent i = new Intent(this, ZxingScan.class);
    	Intent i = new Intent("com.google.zxing.client.android.SCAN");
        //intent1.putExtra("SCAN_MODE", "1D_CODE_MODE");
        startActivityForResult(i, 0);
        i.putExtra(BarcodeDBAdapter.KEY_ROWID, id);
        i.putExtra(BarcodeDBAdapter.KEY_BARCODE_TYPE, c.getString(
                c.getColumnIndexOrThrow(BarcodeDBAdapter.KEY_BARCODE_TYPE)));
        i.putExtra(BarcodeDBAdapter.KEY_PRICE, c.getString(
                c.getColumnIndexOrThrow(BarcodeDBAdapter.KEY_PRICE)));
        startActivityForResult(i, ACTIVITY_CREATE);
    }*/
    
  
   
    public void onActivityResult(int requestCode, int resultCode, Intent intent1) {
		//intent1 = new Intent("com.google.zxing.client.android.SCAN");
       // intent1.putExtra("SCAN_MODE", "QR_CODE_MODE");
       // startActivityForResult(intent1, 0);
        if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	            String contents = intent1.getStringExtra("SCAN_RESULT");
	            String format = intent1.getStringExtra("SCAN_RESULT_FORMAT");
	            mDbHelper.createNote(contents, format);
	             
	        } else if (resultCode == RESULT_CANCELED) {
	            // Handle cancel
	        }
	    }
        /*
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle extras = intent.getExtras();
        switch(requestCode) {
            case ACTIVITY_CREATE:
                String title = extras.getString(BarcodeDBAdapter.KEY_BARCODE_TYPE);
                String body = extras.getString(BarcodeDBAdapter.KEY_PRICE);
                mDbHelper.createNote(title, body);
                fillData();
                break;
         
               
        }*/
    }
}
