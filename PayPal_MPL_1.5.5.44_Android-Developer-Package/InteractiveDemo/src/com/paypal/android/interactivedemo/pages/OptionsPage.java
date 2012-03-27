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
import com.paypal.android.interactivedemo.components.LabelledEditText;
import com.paypal.android.interactivedemo.components.LabelledSpinner;
import com.paypal.android.interactivedemo.components.NavBar;

public class OptionsPage extends QAPage implements OnClickListener, OnItemSelectedListener, TextWatcher {
	
	NavBar optionsNavBar;

	LabelledSpinner editCurrency;
	LabelledSpinner editLanguage;
	LabelledSpinner editButtonType;
	LabelledSpinner editButtonText;
	LabelledEditText editCancelUrl;
	LabelledEditText editReturnUrl;
	LabelledEditText editIpnUrl;
	LabelledEditText editMemo;

	public OptionsPage(Context context) {
		super(context);
		loadPage(context);
	}

	public OptionsPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		
		optionsNavBar = new NavBar(context, this);
		optionsNavBar.titleText.setText("Options");
		optionsNavBar.leftButton.setText("Back");
		optionsNavBar.rightButton.setText("N/A");
		optionsNavBar.rightButton.setVisibility(INVISIBLE);
		addView(optionsNavBar);
		
		editCurrency = new LabelledSpinner(context, this);
		editCurrency.label.setText("Currency:");
		editCurrency.picker.setPrompt("Select Currency");
		for(int i=0; i<InteractiveDemo.currencyCodes.length; i++)
			editCurrency.adapter.add(InteractiveDemo.currencyCodes[i]);
		editCurrency.picker.setSelection(qa.options.currency);
		addView(editCurrency);
		
		editLanguage = new LabelledSpinner(context, this);
		editLanguage.label.setText("Language:");
		editLanguage.picker.setPrompt("Select Language");
		for(int i=0; i<InteractiveDemo.languageCodes.length; i++)
			editLanguage.adapter.add(InteractiveDemo.languageCodes[i]);
		editLanguage.picker.setSelection(qa.options.language);
		addView(editLanguage);
		
		editButtonType = new LabelledSpinner(context, this);
		editButtonType.label.setText("Button Style:");
		editButtonType.picker.setPrompt("Select Button Style");
		editButtonType.adapter.add("152 x 33");
		editButtonType.adapter.add("194 x 37");
		editButtonType.adapter.add("278 x 43");
		editButtonType.adapter.add("294 x 45");
		editButtonType.picker.setSelection(qa.options.buttonType);
		addView(editButtonType);
		
		editButtonText = new LabelledSpinner(context, this);
		editButtonText.label.setText("Button Text:");
		editButtonText.picker.setPrompt("Select Button Text");
		editButtonText.adapter.add("Pay");
		editButtonText.adapter.add("Donate");
		editButtonText.picker.setSelection(qa.options.buttonText);
		addView(editButtonText);
		
		editCancelUrl = new LabelledEditText(context, this, true, "Cancel URL:");
		//editCancelUrl.label.setText("Cancel URL:");
		editCancelUrl.field.setText(qa.options.cancelUrl);
		addView(editCancelUrl);
		
		editReturnUrl = new LabelledEditText(context, this, true, "Return URL:");
		//editReturnUrl.label.setText("Return URL:");
		editReturnUrl.field.setText(qa.options.returnUrl);
		addView(editReturnUrl);
		
		editIpnUrl = new LabelledEditText(context, this, false, "IPN URL:");
		//editIpnUrl.label.setText("IPN URL:");
		editIpnUrl.field.setText(qa.options.ipnUrl);
		addView(editIpnUrl);
		
		editMemo = new LabelledEditText(context, this, false, "Memo:");
		//editMemo.label.setText("Memo:");
		editMemo.field.setText(qa.options.memo);
		addView(editMemo);
		
		optionsNavBar.leftButton.requestFocus();
	}

	public void onClick(View v) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(v == optionsNavBar.leftButton) {
			if(qa.flowType == InteractiveDemo.FLOW_PREAPPROVAL)
				qa.changePage(InteractiveDemo.PAGE_PREAPPROVAL);
			else {
				if(qa.flowType == InteractiveDemo.FLOW_SIMPLE)
					qa.changePage(InteractiveDemo.PAGE_PAYMENT_SIMPLE);
				else
					qa.changePage(InteractiveDemo.PAGE_PAYMENT);
			}
		} else if(v == optionsNavBar.rightButton) {
			//Nothing
		}
	}

	public void onItemSelected(AdapterView<?> p, View v, int position, long id) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(p == editCurrency.picker) {
			qa.options.currency = editCurrency.picker.getSelectedItemPosition();
		} else if(p == editLanguage.picker) {
			qa.options.language = editLanguage.picker.getSelectedItemPosition();
		} else if(p == editButtonType.picker) {
			qa.options.buttonType = editButtonType.picker.getSelectedItemPosition();
		} else if(p == editButtonText.picker) {
			qa.options.buttonText = editButtonText.picker.getSelectedItemPosition();
		}
	}

	public void onNothingSelected(AdapterView<?> p) {
		//Nothing needed
	}

	public void afterTextChanged(Editable s) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(s == editCancelUrl.field.getText()) {
			qa.options.cancelUrl = editCancelUrl.field.getText().toString();
		} else if(s == editReturnUrl.field.getText()) {
			qa.options.returnUrl = editReturnUrl.field.getText().toString();
		} else if(s == editIpnUrl.field.getText()) {
			qa.options.ipnUrl = editIpnUrl.field.getText().toString();
		} else if(s == editMemo.field.getText()) {
			qa.options.memo = editMemo.field.getText().toString();
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		//Nothing needed
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//Nothing needed
	}

}
