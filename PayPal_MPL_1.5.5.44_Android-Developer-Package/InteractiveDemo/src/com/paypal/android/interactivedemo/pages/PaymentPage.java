package com.paypal.android.interactivedemo.pages;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.paypal.android.interactivedemo.InteractiveDemo;
import com.paypal.android.interactivedemo.components.LabelledButton;
import com.paypal.android.interactivedemo.components.LabelledEditText;
import com.paypal.android.interactivedemo.components.LabelledSpinner;
import com.paypal.android.interactivedemo.components.NavBar;

public class PaymentPage extends QAPage implements OnClickListener, OnItemSelectedListener, TextWatcher {
	
	NavBar mainNavBar;
	LabelledEditText editName;
	LabelledButton editOptions;
	LabelledButton editReceivers;
	LabelledSpinner editFeePayer;
	LabelledSpinner editShippingEnabled;
	LabelledSpinner editDynamicCalculation;
	
	public PaymentPage(Context context) {
		super(context);
		loadPage(context);
	}

	public PaymentPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		
		mainNavBar = new NavBar(context, this);
		if(qa.flowType == InteractiveDemo.FLOW_SIMPLE)
			mainNavBar.titleText.setText("Simple");
		else if(qa.flowType == InteractiveDemo.FLOW_ADVANCED)
			mainNavBar.titleText.setText("Advanced");
		mainNavBar.leftButton.setText("Back");
		mainNavBar.rightButton.setText("Next");
		addView(mainNavBar);
		
		editName = new LabelledEditText(context, this, false, "Merchant Name:");
		//editName.label.setText("Name:");
		editName.field.setText(qa.details.merchantName);
		addView(editName);
		
		editReceivers = new LabelledButton(context, this);
		if(qa.flowType == InteractiveDemo.FLOW_SIMPLE) {
			editReceivers.label.setText("Payment:");
			editReceivers.button.setText("Edit Payment");
		} else if(qa.flowType == InteractiveDemo.FLOW_ADVANCED) {
			editReceivers.label.setText("Receivers:");
			editReceivers.button.setText("Edit Receivers (" + qa.receivers.size() + ")");
		}
		addView(editReceivers);
		
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
		} else if(v == editOptions.button) {
			qa.changePage(InteractiveDemo.PAGE_OPTIONS);
		} else if(v == editReceivers.button) {
			if(qa.flowType == InteractiveDemo.FLOW_SIMPLE) {
				qa.changePage(InteractiveDemo.PAGE_EDIT_RECEIVER);
			} else if(qa.flowType == InteractiveDemo.FLOW_ADVANCED) {
				qa.changePage(InteractiveDemo.PAGE_RECEIVERS);
			}
		}
	}

	public void onItemSelected(AdapterView<?> p, View v, int position, long id) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(p == editFeePayer.picker) {
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
		if(s == editName.field.getText()) {
			qa.details.merchantName = editName.field.getText().toString();
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		//Nothing needed
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//Nothing needed
	}
}
