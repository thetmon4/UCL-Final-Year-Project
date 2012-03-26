package com.paypal.android.interactivedemo.pages;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.paypal.android.interactivedemo.InteractiveDemo;
import com.paypal.android.interactivedemo.components.LabelledButton;
import com.paypal.android.interactivedemo.components.LabelledEditText;
import com.paypal.android.interactivedemo.components.NavBar;

public class PreapprovalPage extends QAPage implements OnClickListener, TextWatcher {
	
	NavBar paNavBar;
	LabelledEditText editPreapprovalKey;
	LabelledEditText editMerchantName;
	LabelledButton editOptions;

	public PreapprovalPage(Context context) {
		super(context);
		loadPage(context);
	}

	public PreapprovalPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		
		paNavBar = new NavBar(context, this);
		paNavBar.titleText.setText("Preapproval");
		paNavBar.leftButton.setText("Back");
		paNavBar.rightButton.setText("Next");
		addView(paNavBar);
		
		editPreapprovalKey = new LabelledEditText(context, this, true, "Preapproval Key:");
		//editPreapprovalKey.label.setText("Preapproval Key:");
		editPreapprovalKey.field.setText(qa.details.preapprovalKey);
		addView(editPreapprovalKey);
		
		editMerchantName = new LabelledEditText(context, this, true, "Merchant Name:");
		//editMerchantName.label.setText("Merchant Name:");
		editMerchantName.field.setText(qa.details.merchantName);
		addView(editMerchantName);

		editOptions = new LabelledButton(context, this);
		editOptions.label.setText("Options");
		editOptions.button.setText("Edit Options");
		addView(editOptions);
		
		paNavBar.leftButton.requestFocus();
	}

	public void onClick(View v) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(v == paNavBar.leftButton) {
			qa.changePage(InteractiveDemo.PAGE_INTRO);
		} else if(v == paNavBar.rightButton) {
			if(qa.verifyInfo())
				qa.changePage(InteractiveDemo.PAGE_BUTTON);
		} else if(v == editOptions.button) {
			qa.changePage(InteractiveDemo.PAGE_OPTIONS);
		}
	}

	public void afterTextChanged(Editable s) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(s == editPreapprovalKey.field.getText()) {
			qa.details.preapprovalKey = editPreapprovalKey.field.getText().toString();
		} else if(s == editMerchantName.field.getText()) {
			qa.details.merchantName = editMerchantName.field.getText().toString();
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		//Nothing needed
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//Nothing needed
	}

}
