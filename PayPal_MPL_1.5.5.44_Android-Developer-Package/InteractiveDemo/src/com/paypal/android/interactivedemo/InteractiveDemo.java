package com.paypal.android.interactivedemo;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalAdvancedPayment;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalPayment;
import com.paypal.android.MEP.PayPalPreapproval;
import com.paypal.android.MEP.PayPalReceiverDetails;
import com.paypal.android.interactivedemo.pages.ButtonPage;
import com.paypal.android.interactivedemo.pages.EditItemPage;
import com.paypal.android.interactivedemo.pages.EditReceiverPage;
import com.paypal.android.interactivedemo.pages.IntroPage;
import com.paypal.android.interactivedemo.pages.ItemsPage;
import com.paypal.android.interactivedemo.pages.OptionsPage;
import com.paypal.android.interactivedemo.pages.PaymentPage;
import com.paypal.android.interactivedemo.pages.PaymentPageSimple;
import com.paypal.android.interactivedemo.pages.PreapprovalPage;
import com.paypal.android.interactivedemo.pages.QAPage;
import com.paypal.android.interactivedemo.pages.ReceiversPage;
import com.paypal.android.interactivedemo.pages.ResultsPage;
import com.paypal.android.interactivedemo.qa.QADetails;
import com.paypal.android.interactivedemo.qa.QAOptions;
import com.paypal.android.interactivedemo.qa.QAReceiver;

public class InteractiveDemo extends Activity {

	private static final int DESIRED_RESULT = 1;
	private static InteractiveDemo instance;
	private QAPage thePage;
	public static InteractiveDemo getInstance() {return instance;}	
	
	public static final String build = "10.12.09.8056";
	public static final String[] currencyCodes = {
		"AUD", "BRL", "CAD", "CZK", "DKK", "EUR", "HKD", "HUF", 
		"ILS", "JPY", "MYR", "MXN", "NOK", "NZD", "PHP", "PLN", 
		"GBP", "SGD", "SEK", "CHF", "TWD", "THB", "USD"
	};
	public static final String[] languageCodes = {
		"de_AT", "de_CH", "de_DE", "en_AT", "en_AU", "en_BE", 
		"en_CA", "en_CH", "en_DE", "en_ES", "en_FR", "en_GB", 
		"en_HK", "en_IN", "en_JP", "en_MX", "en_NL", "en_PL", 
		"en_SG", "en_TW", "en_US", "es_AR", "es_ES", "es_MX", 
		"fr_BE", "fr_CA", "fr_CH", "fr_FR", "it_IT", "ja_JP", 
		"nl_BE", "nl_NL", "pl_PL", "pt_BR", "zh_HK", "zh_TW"
	};
	public static final String[] paymentPeriods = {
		"None", "Daily", "Weekly", "Biweekly", "Semimonthly",
		"Monthly", "Annually"
	};
	public static final String[] paymentPeriodParameters = {
		"None", "DAILY", "WEEKLY", "BIWEEKLY", "SEMIMONTHLY",
		"MONTHLY", "ANNUALLY"
	};
	public static final String[] datesOfMonth = {
		"None", "1", "2", "3", "4", "5", "6", "7", "8", "9",
		"10", "11", "12", "13", "14", "15", "16", "17", "18",
		"19", "20", "21", "22", "23", "24", "25", "26", "27",
		"28", "29", "30", "31"
	};
	public static final String[] daysOfWeek = {
		"None", "Sunday", "Monday", "Tuesday", "Wednesday",
		"Thursday", "Friday", "Saturday"
	};
	public static final String[] dayOfWeekParameters = {
		"None", "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY",
		"THURSDAY", "FRIDAY", "SATURDAY"
	};
	
	//Page reference numbers to change layouts
	public static final int PAGE_INTRO = 0;
	public static final int PAGE_PAYMENT = 1;
	public static final int PAGE_PAYMENT_SIMPLE = 2;
	public static final int PAGE_OPTIONS = 3;
	public static final int PAGE_RECEIVERS = 4;
	public static final int PAGE_EDIT_RECEIVER = 5;
	public static final int PAGE_ITEMS = 6;
	public static final int PAGE_EDIT_ITEM = 7;
	public static final int PAGE_PREAPPROVAL = 8;
	public static final int PAGE_BUTTON = 9;
	public static final int PAGE_RESULTS = 10;
	public static final int PAGE_CREATE_PREAPPROVAL = 11;
	private int currentPage = PAGE_INTRO;
	
	//Flow reference numbers to change app flow
	public static final int FLOW_SIMPLE = 0;
	public static final int FLOW_ADVANCED = 1;
	public static final int FLOW_PREAPPROVAL = 2;
	public int flowType = -1;
	
	//Variables to fill out payment/preapproval with
	public String appID;
	public int server;
	public QADetails details;
	public QAOptions options;
	public ArrayList<QAReceiver> receivers;
	public int currentReceiver;
	public int currentItem;

	//Reference to the library and an object of each type
	public PayPal pp;
	public PayPalPayment simplePayment;
	public PayPalAdvancedPayment advancedPayment;
	public PayPalPreapproval preapproval;
	//Strings for the results page
	public String resultTitle;
	public String resultInfo;
	public String resultExtra;
	public boolean initializedLibrary;
	
	//Called when the app starts and defaults the variables
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        appID = "APP-80W284485P519543T";
        server = PayPal.ENV_SANDBOX;
        details = new QADetails();
        options = new QAOptions();
        receivers = new ArrayList<QAReceiver>();
        pp = null;
        initializedLibrary = false;
        changePage(currentPage);
    }
    
    //Handles back key input to change layouts
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
    	if(keyCode == android.view.KeyEvent.KEYCODE_BACK) {
    		switch(flowType) {
    		case FLOW_SIMPLE:
    			switch(currentPage) {
	    		case PAGE_INTRO:
	    			finish();
	    		case PAGE_PAYMENT:
	    		case PAGE_RESULTS:
	    			changePage(PAGE_INTRO);
	    			break;
	    		case PAGE_OPTIONS:
	    		case PAGE_EDIT_RECEIVER:
	    		case PAGE_BUTTON:
	    			changePage(PAGE_PAYMENT_SIMPLE);
	    			break;
	    		case PAGE_ITEMS:
	    			changePage(PAGE_PAYMENT_SIMPLE);
	    			break;
	    		case PAGE_EDIT_ITEM:
	    			changePage(PAGE_ITEMS);
	    			break;
    			}
    			break;
    		case FLOW_ADVANCED:
    			switch(currentPage) {
	    		case PAGE_INTRO:
	    			finish();
	    		case PAGE_PAYMENT:
	    		case PAGE_RESULTS:
	    			changePage(PAGE_INTRO);
	    			break;
	    		case PAGE_OPTIONS:
	    		case PAGE_RECEIVERS:
	    		case PAGE_BUTTON:
	    			changePage(PAGE_PAYMENT);
	    			break;
	    		case PAGE_EDIT_RECEIVER:
	    			changePage(PAGE_RECEIVERS);
	    			break;
	    		case PAGE_ITEMS:
	    			changePage(PAGE_EDIT_RECEIVER);
	    			break;
	    		case PAGE_EDIT_ITEM:
	    			changePage(PAGE_ITEMS);
	    			break;
    			}
    			break;
    		case FLOW_PREAPPROVAL:
    			switch(currentPage) {
	    		case PAGE_INTRO:
	    			finish();
	    		case PAGE_PREAPPROVAL:
	    		case PAGE_RESULTS:
	    			changePage(PAGE_INTRO);
	    			break;
	    		case PAGE_OPTIONS:
	    		case PAGE_BUTTON:
	    			changePage(PAGE_PREAPPROVAL);
	    			break;
    			}
    			break;
    		}
    		return true;
    	}
    	return false;
    }
    
    //Switches the current content view to a specified layout
    public void changePage(int page) {
    	
    	if(page == PAGE_INTRO) {
    		thePage = new IntroPage(this);
    		setContentView(thePage);
    	} else if(page == PAGE_PAYMENT) {
    		thePage = new PaymentPage(this);
    		setContentView(thePage);
    	} else if(page == PAGE_PAYMENT_SIMPLE) {
    		thePage = new PaymentPageSimple(this);
    		setContentView(thePage);
    	} else if(page == PAGE_OPTIONS) {
    		thePage = new OptionsPage(this);
    		setContentView(thePage);
    	} else if(page == PAGE_RECEIVERS) {
    		thePage = new ReceiversPage(this);
    		setContentView(thePage);
    	} else if(page == PAGE_EDIT_RECEIVER) {
    		thePage = new EditReceiverPage(this);
    		setContentView(thePage);
    	} else if(page == PAGE_ITEMS) {
    		thePage = new ItemsPage(this);
    		setContentView(thePage);
    	} else if(page == PAGE_EDIT_ITEM) {
    		thePage = new EditItemPage(this);
    		setContentView(thePage);
    	} else if(page == PAGE_PREAPPROVAL) {
    		thePage = new PreapprovalPage(this);
    		setContentView(thePage);
    	} else if(page == PAGE_BUTTON) {
    		thePage = new ButtonPage(this);
    		setContentView(thePage);
    	} else if(page == PAGE_RESULTS) {
    		thePage = new ResultsPage(this);
    		setContentView(thePage);
    	} 
    	
    	currentPage = page;
    }
    
    //Initializes the library and payment objects
    public void initLibrary() {
    	if(!verifyInfo())
    		return;
    	
    	//initWithAppID creates the PayPal instance and initializes things behind the scenes
    	pp = PayPal.getInstance();
   		if(!initializedLibrary) {
   	    	if(pp == null) {
    			pp = PayPal.initWithAppID(this, appID, server);
    		}
			initializedLibrary = true;
   		}
   		
   		//Sets up the payment/preapproval object depending on current flow
    	switch(flowType) {
    	case FLOW_SIMPLE:
    		setupSimplePayment();
    		break;
    	case FLOW_ADVANCED:
    		setupAdvancedPayment();
    		break;
    	case FLOW_PREAPPROVAL:
    		setupPreapproval();
    		break;
    	}
    }
    
    //A simple function that verifies required information is passed (but does no error checking)
    public boolean verifyInfo() {
		if(appID == null || appID.length() <= 0)
			return false;
    	if(flowType == FLOW_PREAPPROVAL) {
    		if(details.preapprovalKey == null || details.preapprovalKey.length() <= 0)
    			return false;
    		if(details.merchantName == null || details.merchantName.length() <= 0)
    			return false;
    	} else {
    		if(receivers.size() <= 0)
    			return false;
	    	for(int i=0; i<receivers.size(); i++) {
	    		if(receivers.get(i).recipient == null || receivers.get(i).recipient.length() <= 0)
	    			return false;
	    		if(receivers.get(i).subtotal == null || receivers.get(i).subtotal.length() <= 0)
	    			return false;
	    	}
    	}
    	if(options.cancelUrl == null || options.cancelUrl.length() <= 0)
    		return false;
    	if(options.returnUrl == null || options.returnUrl.length() <= 0)
    		return false;
    	//Otherwise,
    	return true;
    }
    
    //This function sets up a PayPalPayment that does not use multiple receivers
    private void setupSimplePayment() {
    	simplePayment = new PayPalPayment();
    	//Currency is required, ipn and memo are optional
    	simplePayment.setCurrencyType(currencyCodes[options.currency]);
    	simplePayment.setIpnUrl(options.ipnUrl);
    	simplePayment.setMemo(options.memo);
    	
    	//Recipient and subtotal are required, payment type and subtype are optional
    	simplePayment.setRecipient(receivers.get(0).recipient);
		BigDecimal subtotal = new BigDecimal(receivers.get(0).subtotal);
		simplePayment.setSubtotal(subtotal);
		simplePayment.setPaymentType(receivers.get(0).paymentType);
		simplePayment.setPaymentSubtype(receivers.get(0).paymentSubtype);
    	
		PayPalInvoiceData invoice = new PayPalInvoiceData();
		//Tax and shipping are now part of invoice data (and both optional)
    	if(receivers.get(0).invoice.tax.length() > 0) {
    		BigDecimal tax = new BigDecimal(receivers.get(0).invoice.tax);
    		invoice.setTax(tax);
    	}
    	if(receivers.get(0).invoice.shipping.length() > 0) {
    		BigDecimal shipping = new BigDecimal(receivers.get(0).invoice.shipping);
    		invoice.setShipping(shipping);
    	}
    	
    	//Loop through the list of items in the invoice data and fill parameters
    	for(int j=0; j<receivers.get(0).invoice.items.size(); j++) {
    		PayPalInvoiceItem item = new PayPalInvoiceItem();
    		if(receivers.get(0).invoice.items.get(j).name.length() > 0) {
    			item.setName(receivers.get(0).invoice.items.get(j).name);
    		}
    		if(receivers.get(0).invoice.items.get(j).id.length() > 0) {
    			item.setID(receivers.get(0).invoice.items.get(j).id);
    		}
    		if(receivers.get(0).invoice.items.get(j).quantity.length() > 0) {
    			int quantity = Integer.parseInt(receivers.get(0).invoice.items.get(j).quantity);
    			item.setQuantity(quantity);
    		}
    		if(receivers.get(0).invoice.items.get(j).unitPrice.length() > 0) {
    			BigDecimal unitPrice = new BigDecimal(receivers.get(0).invoice.items.get(j).unitPrice);
    			item.setUnitPrice(unitPrice);
    		}
    		if(receivers.get(0).invoice.items.get(j).price.length() > 0) {
    			BigDecimal totalPrice = new BigDecimal(receivers.get(0).invoice.items.get(j).price);
    			item.setTotalPrice(totalPrice);
    		}
    		invoice.getInvoiceItems().add(item);
    	}
    	simplePayment.setInvoiceData(invoice);
    	
    	//Few more optional components
    	if(receivers.get(0).merchantName.length() > 0)
    		simplePayment.setMerchantName(receivers.get(0).merchantName);    		
    	if(receivers.get(0).description.length() > 0)
    		simplePayment.setDescription(receivers.get(0).description);
    	if(receivers.get(0).customID.length() > 0)
    		simplePayment.setCustomID(receivers.get(0).customID);
    	
    	//Set library-specific options for simple payment
        pp.setLanguage(languageCodes[options.language]);
        pp.setCancelUrl(options.cancelUrl);
        pp.setReturnUrl(options.returnUrl);
        pp.setFeesPayer(details.feePayer);
        pp.setShippingEnabled(details.shippingEnabled);
        pp.setDynamicAmountCalculationEnabled(details.dynamicCalculation);
    }
    
	//This function sets up a PayPalAdvancedPayment that supports multiple receivers
    private void setupAdvancedPayment() {
    	advancedPayment = new PayPalAdvancedPayment();
    	//Currency is required, ipn and memo are optional
    	advancedPayment.setCurrencyType(currencyCodes[options.currency]);
    	advancedPayment.setIpnUrl(options.ipnUrl);
    	advancedPayment.setMemo(options.memo);
    	advancedPayment.setMerchantName(details.merchantName);
    	
    	//Loop through the list of receivers and add to the payment
    	for(int i=0; i<receivers.size(); i++) {
    		PayPalReceiverDetails receiver = new PayPalReceiverDetails();
    		receiver.setRecipient(receivers.get(i).recipient);

        	//Recipient and subtotal are required, payment type and subtype are optional
    		BigDecimal subtotal = new BigDecimal(receivers.get(i).subtotal);
    		receiver.setSubtotal(subtotal);
    		receiver.setIsPrimary(receivers.get(i).isPrimary);
       		receiver.setPaymentType(receivers.get(i).paymentType);
    		receiver.setPaymentSubtype(receivers.get(i).paymentSubtype);
    		
    		PayPalInvoiceData invoice = new PayPalInvoiceData();
    		//Tax and shipping are now part of invoice data (and both optional)
    		if(receivers.get(i).invoice.tax.length() > 0) {
    			BigDecimal tax = new BigDecimal(receivers.get(i).invoice.tax);
    			invoice.setTax(tax);
    		}
    		if(receivers.get(i).invoice.shipping.length() > 0) {
    			BigDecimal shipping = new BigDecimal(receivers.get(i).invoice.shipping);
    			invoice.setShipping(shipping);
    		}
    		
    		//Loop through the list of items in the invoice data and fill parameters
    		for(int j=0; j<receivers.get(i).invoice.items.size(); j++) {
    			PayPalInvoiceItem item = new PayPalInvoiceItem();
    			if(receivers.get(i).invoice.items.get(j).name.length() > 0) {
    				item.setName(receivers.get(i).invoice.items.get(j).name);
    			}
    			if(receivers.get(i).invoice.items.get(j).id.length() > 0) {
    				item.setID(receivers.get(i).invoice.items.get(j).id);
    			}
    			if(receivers.get(i).invoice.items.get(j).quantity.length() > 0) {
    				int quantity = Integer.parseInt(receivers.get(i).invoice.items.get(j).quantity);
    				item.setQuantity(quantity);
    			}
    			if(receivers.get(i).invoice.items.get(j).unitPrice.length() > 0) {
    				BigDecimal unitPrice = new BigDecimal(receivers.get(i).invoice.items.get(j).unitPrice);
    				item.setUnitPrice(unitPrice);
    			}
    			if(receivers.get(i).invoice.items.get(j).price.length() > 0) {
    				BigDecimal totalPrice = new BigDecimal(receivers.get(i).invoice.items.get(j).price);
    				item.setTotalPrice(totalPrice);
    			}
    			invoice.getInvoiceItems().add(item);
    		}
    		receiver.setInvoiceData(invoice);
    		
    		//Few more optional components
    		if(receivers.get(i).merchantName.length() > 0)
    			receiver.setMerchantName(receivers.get(i).merchantName);    		
    		if(receivers.get(i).description.length() > 0)
    			receiver.setDescription(receivers.get(i).description);
    		if(receivers.get(i).customID.length() > 0)
    			receiver.setCustomID(receivers.get(i).customID);
    		
    		advancedPayment.getReceivers().add(receiver);
    	}
    	
    	//Set library-specific options for advanced payment
       	pp.setLanguage(languageCodes[options.language]);
       	pp.setCancelUrl(options.cancelUrl);
       	pp.setReturnUrl(options.returnUrl);
       	pp.setFeesPayer(details.feePayer);
       	pp.setShippingEnabled(details.shippingEnabled);
       	pp.setDynamicAmountCalculationEnabled(details.dynamicCalculation);
    }
    
	//This function sets up a PayPalPreapproval
    private void setupPreapproval() {
    	preapproval = new PayPalPreapproval();
    	//Currency and merchant is required, ipn and memo are optional
    	preapproval.setCurrencyType(currencyCodes[options.currency]);
    	preapproval.setIpnUrl(options.ipnUrl);
    	preapproval.setMemo(options.memo);
    	preapproval.setMerchantName(details.merchantName);
    	
    	//Set library-specific options for preapproval
		pp.setPreapprovalKey(details.preapprovalKey);
       	pp.setLanguage(languageCodes[options.language]);
       	pp.setCancelUrl(options.cancelUrl);
       	pp.setReturnUrl(options.returnUrl);
    }
    
    //Starts the library via getting the intent and calling startActivity
    public void startLibrary() {
    	switch(flowType) {
    	case FLOW_SIMPLE:
    		startActivityForResult(pp.checkout(simplePayment, this, new Adjuster(), new ResultDelegate()), DESIRED_RESULT);
    		break;
    	case FLOW_ADVANCED:
    		startActivityForResult(pp.checkout(advancedPayment, this, new Adjuster(), new ResultDelegate()), DESIRED_RESULT);
    		break;
    	case FLOW_PREAPPROVAL:
    		startActivityForResult(pp.preapprove(preapproval, this, new ResultDelegate()), DESIRED_RESULT);
    		break;
    	}
    }
    
    //Called when the library exits due to completion, cancellation, or error
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != DESIRED_RESULT) {
			return;
		}
		
		//All of our result handling should now take place in the result delegate
		
		changePage(PAGE_RESULTS);
    }
    
    protected Dialog onCreateDialog(int id) {
    	return thePage.getDateDialog(id);
    }
}