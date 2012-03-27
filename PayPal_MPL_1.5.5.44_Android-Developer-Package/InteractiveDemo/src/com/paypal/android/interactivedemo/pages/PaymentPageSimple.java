package com.paypal.android.interactivedemo.pages;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.paypal.android.MEP.PayPal;
import com.paypal.android.interactivedemo.InteractiveDemo;
import com.paypal.android.interactivedemo.components.LabelledButton;
import com.paypal.android.interactivedemo.components.LabelledEditText;
import com.paypal.android.interactivedemo.components.LabelledSpinner;
import com.paypal.android.interactivedemo.components.NavBar;

public class PaymentPageSimple extends QAPage implements OnClickListener, OnItemSelectedListener, TextWatcher {
	
	NavBar mainNavBar;
	LabelledButton editOptions;
	LabelledButton editReceivers;
	LabelledSpinner editFeePayer;
	LabelledSpinner editShippingEnabled;
	LabelledSpinner editDynamicCalculation;
	
	LabelledEditText editRecipient;
	LabelledEditText editSubtotal;
	LabelledEditText editName;
	LabelledEditText editDescription;
	LabelledEditText editCustomID;
	LabelledButton editInvoice;
	LabelledSpinner editPaymentType;
	LabelledSpinner editPaymentSubtype;
	
	public PaymentPageSimple(Context context) {
		super(context);
		loadPage(context);
	}

	public PaymentPageSimple(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		
		mainNavBar = new NavBar(context, this);
		mainNavBar.titleText.setText("Simple");
		mainNavBar.leftButton.setText("Back");
		mainNavBar.rightButton.setText("Next");
		addView(mainNavBar);
		
		editRecipient = new LabelledEditText(context, this, true, "Recipient:");
		//editRecipient.label.setText("Recipient:");
		editRecipient.field.setText(qa.receivers.get(qa.currentReceiver).recipient);
		addView(editRecipient);
		
		editSubtotal = new LabelledEditText(context, this, true, "Subtotal:");
		//editSubtotal.label.setText("Subtotal:");
		editSubtotal.field.setText(qa.receivers.get(qa.currentReceiver).subtotal);
		addView(editSubtotal);
		
		editName = new LabelledEditText(context, this, false, "Name:");
		//editName.label.setText("Name:");
		editName.field.setText(qa.receivers.get(qa.currentReceiver).merchantName);
		addView(editName);
		
		editDescription = new LabelledEditText(context, this, false, "Description:");
		//editDescription.label.setText("Description:");
		editDescription.field.setText(qa.receivers.get(qa.currentReceiver).description);
		addView(editDescription);
		
		editCustomID = new LabelledEditText(context, this, false, "Custom ID:");
		//editCustomID.label.setText("Custom ID:");
		editCustomID.field.setText(qa.receivers.get(qa.currentReceiver).customID);
		addView(editCustomID);
		
		editInvoice = new LabelledButton(context, this);
		editInvoice.label.setText("Invoice:");
		editInvoice.button.setText("Edit Invoice (" + qa.receivers.get(qa.currentReceiver).invoice.items.size() + " items)");
		addView(editInvoice);
		
		editPaymentType = new LabelledSpinner(context, this);
		editPaymentType.label.setText("Payment Type:");
		editPaymentType.picker.setPrompt("Select Payment Type");
		editPaymentType.adapter.add("Goods");
		editPaymentType.adapter.add("Service");
		editPaymentType.adapter.add("Personal");
		editPaymentType.adapter.add("None");
		editPaymentType.picker.setSelection(qa.receivers.get(qa.currentReceiver).paymentType);
		addView(editPaymentType);
		
		editPaymentSubtype = new LabelledSpinner(context, this);
		editPaymentSubtype.label.setText("Payment Subtype:");
		editPaymentSubtype.picker.setPrompt("Select Payment Subtype");
		editPaymentSubtype.adapter.add("Affiliate Payments");
		editPaymentSubtype.adapter.add("B2B");
		editPaymentSubtype.adapter.add("Payroll");
		editPaymentSubtype.adapter.add("Rebates");
		editPaymentSubtype.adapter.add("Refunds");
		editPaymentSubtype.adapter.add("Reimbursements");
		editPaymentSubtype.adapter.add("Donations");
		editPaymentSubtype.adapter.add("Utilities");
		editPaymentSubtype.adapter.add("Tuition");
		editPaymentSubtype.adapter.add("Government");
		editPaymentSubtype.adapter.add("Insurance");
		editPaymentSubtype.adapter.add("Remittances");
		editPaymentSubtype.adapter.add("Rent");
		editPaymentSubtype.adapter.add("Mortgage");
		editPaymentSubtype.adapter.add("Medical");
		editPaymentSubtype.adapter.add("Child Care");
		editPaymentSubtype.adapter.add("Event Planning");
		editPaymentSubtype.adapter.add("General Contractors");
		editPaymentSubtype.adapter.add("Entertainment");
		editPaymentSubtype.adapter.add("Tourism");
		editPaymentSubtype.adapter.add("Invoice");
		editPaymentSubtype.adapter.add("Transfer");
		editPaymentSubtype.adapter.add("None");
		editPaymentSubtype.picker.setSelection(qa.receivers.get(qa.currentReceiver).paymentSubtype);
		if(qa.details.paymentType != PayPal.PAYMENT_TYPE_SERVICE)
			editPaymentSubtype.setVisibility(GONE);
		addView(editPaymentSubtype);
		
		editFeePayer = new LabelledSpinner(context, this);
		editFeePayer.label.setText("Fee Paid By:");
		editFeePayer.picker.setPrompt("Select Fee Payer");
		editFeePayer.adapter.add("Sender");
		editFeePayer.adapter.add("Primary Reciever");
		editFeePayer.adapter.add("Each Reciever");
		editFeePayer.adapter.add("Secondary Only");
		editFeePayer.picker.setSelection(qa.details.feePayer);
		addView(editFeePayer);
		
		editShippingEnabled = new LabelledSpinner(context, this);
		editShippingEnabled.label.setText("Shipping:");
		editShippingEnabled.picker.setPrompt("Set Shipping");
		editShippingEnabled.adapter.add("Disabled");
		editShippingEnabled.adapter.add("Enabled");
		editShippingEnabled.picker.setSelection(qa.details.shippingEnabled ? 1 : 0);
		addView(editShippingEnabled);
		
		editDynamicCalculation = new LabelledSpinner(context, this);
		editDynamicCalculation.label.setText("Dynamic Calculation:");
		editDynamicCalculation.picker.setPrompt("Set Dynamic Calculation");
		editDynamicCalculation.adapter.add("Off");
		editDynamicCalculation.adapter.add("On");
		editDynamicCalculation.picker.setSelection(qa.details.dynamicCalculation ? 1 : 0);
		addView(editDynamicCalculation);
		
		// need to find out if the options part should move here too
		/*editCurrency = new LabelledSpinner(context, this);
		editCurrency.label.setText("Currency:");
		editCurrency.picker.setPrompt("Select Currency");
		for(int i=0; i<MPL_Demo.currencyCodes.length; i++)
			editCurrency.adapter.add(MPL_Demo.currencyCodes[i]);
		editCurrency.picker.setSelection(qa.options.currency);
		addView(editCurrency);*/
		editOptions = new LabelledButton(context, this);
		editOptions.label.setText("Options:");
		editOptions.button.setText("Edit Options");
		addView(editOptions);
		
		mainNavBar.leftButton.requestFocus();
	}

	public void onClick(View v) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(v == mainNavBar.leftButton) {
			qa.changePage(InteractiveDemo.PAGE_INTRO);
		} else if(v == mainNavBar.rightButton) {
			if(qa.verifyInfo())
				qa.changePage(InteractiveDemo.PAGE_BUTTON);
		} else if(v == editInvoice.button) {
			qa.changePage(InteractiveDemo.PAGE_ITEMS);
		} else if(v == editOptions.button) {
			qa.changePage(InteractiveDemo.PAGE_OPTIONS);
		}
	}

	public void onItemSelected(AdapterView<?> p, View v, int position, long id) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(p == editPaymentType.picker) {
			qa.receivers.get(qa.currentReceiver).paymentType = editPaymentType.picker.getSelectedItemPosition();
			if(editPaymentSubtype != null) {
				if(qa.receivers.get(qa.currentReceiver).paymentType != PayPal.PAYMENT_TYPE_SERVICE) {
					editPaymentSubtype.picker.setSelection(PayPal.PAYMENT_SUBTYPE_NONE);
					editPaymentSubtype.setVisibility(GONE);
				} else {
					editPaymentSubtype.setVisibility(VISIBLE);
				}
			}
		} else if(p == editPaymentSubtype.picker) {
			qa.receivers.get(qa.currentReceiver).paymentSubtype = editPaymentSubtype.picker.getSelectedItemPosition();
		} else if(p == editFeePayer.picker) {
			qa.details.feePayer = editFeePayer.picker.getSelectedItemPosition();
		} else if(p == editShippingEnabled.picker) {
			qa.details.shippingEnabled = (editShippingEnabled.picker.getSelectedItemPosition() == 1);
		} else if(p == editDynamicCalculation.picker) {
			qa.details.dynamicCalculation = (editDynamicCalculation.picker.getSelectedItemPosition() == 1);
		}
	}

	public void onNothingSelected(AdapterView<?> p) {
		//Nothing needed
	}

	public void afterTextChanged(Editable s) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(s == editRecipient.field.getText()) {
			qa.receivers.get(qa.currentReceiver).recipient = editRecipient.field.getText().toString();
		} else if(s == editSubtotal.field.getText()) {
			qa.receivers.get(qa.currentReceiver).subtotal = editSubtotal.field.getText().toString();
		} else if(s == editName.field.getText()) {
			qa.receivers.get(qa.currentReceiver).merchantName = editName.field.getText().toString();
		} else if(s == editDescription.field.getText()) {
			qa.receivers.get(qa.currentReceiver).description = editDescription.field.getText().toString();
		} else if(s == editCustomID.field.getText()) {
			qa.receivers.get(qa.currentReceiver).customID = editCustomID.field.getText().toString();
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		//Nothing needed
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//Nothing needed
	}
}
