package com.paypal.android.interactivedemo.pages;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.interactivedemo.InteractiveDemo;
import com.paypal.android.interactivedemo.components.NavBar;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ButtonPage extends QAPage implements OnClickListener {
	
	protected static final int INITIALIZE_SUCCESS = 0;
	protected static final int INITIALIZE_FAILURE = 1;
	
	NavBar buttonNavBar;
	CheckoutButton checkout;
	TextView details;
	boolean launchedLibrary;
	Context _context;
	LinearLayout buttonContainer;
	
	// This handler will allow us to properly update the UI. You cannot touch Views from a non-UI thread.
	Handler hRefresh = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
		    	case INITIALIZE_SUCCESS:
		    		setupButton();
		            break;
		    	case INITIALIZE_FAILURE:
		    		showFailure();
		    		break;
			}
		}
	};

	public ButtonPage(Context context) {
		super(context);
		_context = context;
		loadPage(context);
	}

	public ButtonPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		_context = context;
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		// Initialize the library. We'll do it in a separate thread because it requires communication with the server
		// which may take some time depending on the connection strength/speed.
		Thread libraryInitializationThread = new Thread() {
			public void run() {
				InteractiveDemo.getInstance().initLibrary();
				
				// The library is initialized so let's create our CheckoutButton and update the UI.
				if (PayPal.getInstance() != null && PayPal.getInstance().isLibraryInitialized()) {
					hRefresh.sendEmptyMessage(INITIALIZE_SUCCESS);
				}
				else {
					hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
				}
			}
		};
		libraryInitializationThread.start();
		
		buttonNavBar = new NavBar(context, this);
		buttonNavBar.titleText.setText("Launch PayPal");
		buttonNavBar.leftButton.setText("Back");
		buttonNavBar.rightButton.setText("N/A");
		buttonNavBar.rightButton.setVisibility(INVISIBLE);
		buttonNavBar.leftButton.setEnabled(false);
		addView(buttonNavBar);
		
		buttonContainer = new LinearLayout(context);
		buttonContainer.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		buttonContainer.setGravity(Gravity.CENTER);
		addView(buttonContainer);
		
		details = new TextView(context);
		details.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		details.setTextSize(16.0f);
		details.setText("Initializing Library...");
		addView(details);
		
		buttonNavBar.leftButton.requestFocus();
	}

	public void onClick(View v) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(v == buttonNavBar.leftButton) {
			if(qa.flowType == InteractiveDemo.FLOW_PREAPPROVAL)
				qa.changePage(InteractiveDemo.PAGE_PREAPPROVAL);
			else {
				if(qa.flowType == InteractiveDemo.FLOW_SIMPLE)
					qa.changePage(InteractiveDemo.PAGE_PAYMENT_SIMPLE);
				else
					qa.changePage(InteractiveDemo.PAGE_PAYMENT);
			}
		} else if(v == checkout) {
			if(!launchedLibrary) {
				launchedLibrary = true;
				qa.startLibrary();
			}
		}
	}
	
	/**
	 * Create our CheckoutButton and update the UI.
	 */
	public void setupButton() {
		InteractiveDemo qa = InteractiveDemo.getInstance();

		//Get the checkout button from the library
		checkout = qa.pp.getCheckoutButton(qa, qa.options.buttonType, qa.options.buttonText);
		checkout.setOnClickListener(this);
		buttonContainer.addView(checkout);
		
		details.setText("");
		buttonNavBar.leftButton.setEnabled(true);
	}
	
	/**
	 * Show a failure message because initialization failed.
	 */
	public void showFailure() {
		details.setText("Could not initialize the PayPal library.");
		buttonNavBar.leftButton.setEnabled(true);
	}
}
