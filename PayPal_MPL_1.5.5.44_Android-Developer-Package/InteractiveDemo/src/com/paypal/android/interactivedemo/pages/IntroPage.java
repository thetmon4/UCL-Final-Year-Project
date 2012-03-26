package com.paypal.android.interactivedemo.pages;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.paypal.android.MEP.PayPal;
import com.paypal.android.interactivedemo.InteractiveDemo;
import com.paypal.android.interactivedemo.components.LabelledEditText;
import com.paypal.android.interactivedemo.components.LabelledSpinner;
import com.paypal.android.interactivedemo.components.NavBar;
import com.paypal.android.interactivedemo.qa.QAReceiver;

public class IntroPage extends QAPage implements OnClickListener, OnItemSelectedListener, TextWatcher {
	
	NavBar introNavBar;
	LabelledEditText editAppID;
	LabelledSpinner editServer;
	Button goSimple;
	Button goAdvanced;
	Button goPreapproval;
	Button goExit;
	TextView versionInfo;
	
	private static final int quadButtonHeight = 100;

	public IntroPage(Context context) {
		super(context);
		loadPage(context);
	}

	public IntroPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		
		introNavBar = new NavBar(context, this);
		introNavBar.titleText.setText("Interactive Demo");
		introNavBar.leftButton.setVisibility(INVISIBLE);
		introNavBar.rightButton.setVisibility(INVISIBLE);
		introNavBar.titleText.setSingleLine(false);
		
		addView(introNavBar);
		
		editAppID = new LabelledEditText(context, this, true, "App ID:");
		//editAppID.label.setText("App ID:");
		editAppID.field.setText(qa.appID);
		editAppID.field.setEnabled(!qa.initializedLibrary);
		addView(editAppID);
		
		editServer = new LabelledSpinner(context, this);
		editServer.label.setText("Server:");
		editServer.picker.setPrompt("Select Server");
		editServer.adapter.add("Sandbox");
		editServer.adapter.add("Live");
		editServer.adapter.add("None");
		editServer.picker.setSelection(qa.server);
		editServer.picker.setEnabled(!qa.initializedLibrary);
		addView(editServer);
		
		LinearLayout quadButtons = new LinearLayout(context);
		quadButtons.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		quadButtons.setOrientation(LinearLayout.VERTICAL);
		quadButtons.setGravity(Gravity.CENTER);
		quadButtons.setPadding(5, 10, 5, 10);
		LinearLayout quadTopHalf = new LinearLayout(context);
		quadTopHalf.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		quadTopHalf.setOrientation(LinearLayout.HORIZONTAL);
		quadTopHalf.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout quadBottomHalf = new LinearLayout(context);
		quadBottomHalf.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		quadBottomHalf.setOrientation(LinearLayout.HORIZONTAL);
		quadBottomHalf.setGravity(Gravity.CENTER_HORIZONTAL);
		
		LinearLayout simpleLayout = new LinearLayout(context);
		simpleLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, quadButtonHeight, 0.5f));
		simpleLayout.setPadding(2, 2, 2, 2);
		simpleLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		goSimple = new Button(context);
		goSimple.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		goSimple.setText("Simple");
		goSimple.setTextSize(20.0f);
		goSimple.setOnClickListener(this);
		simpleLayout.addView(goSimple);
		
		LinearLayout advancedLayout = new LinearLayout(context);
		advancedLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, quadButtonHeight, 0.5f));
		advancedLayout.setPadding(2, 2, 2, 2);
		advancedLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		goAdvanced = new Button(context);
		goAdvanced.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		goAdvanced.setText("Advanced");
		goAdvanced.setTextSize(20.0f);
		goAdvanced.setOnClickListener(this);
		advancedLayout.addView(goAdvanced);
		
		LinearLayout preapprovalLayout = new LinearLayout(context);
		preapprovalLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, quadButtonHeight, 0.5f));
		preapprovalLayout.setPadding(2, 2, 2, 2);
		preapprovalLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		goPreapproval = new Button(context);
		goPreapproval.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		goPreapproval.setText("Preapproval");
		goPreapproval.setTextSize(20.0f);
		goPreapproval.setOnClickListener(this);
		preapprovalLayout.addView(goPreapproval);
		
		LinearLayout exitLayout = new LinearLayout(context);
		exitLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, quadButtonHeight, 0.5f));
		exitLayout.setPadding(2, 2, 2, 2);
		exitLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		goExit = new Button(context);
		goExit.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		goExit.setText("Exit");
		goExit.setTextSize(20.0f);
		goExit.setOnClickListener(this);
		exitLayout.addView(goExit);
		
		quadTopHalf.addView(simpleLayout);
		quadTopHalf.addView(advancedLayout);
		quadBottomHalf.addView(preapprovalLayout);
		quadBottomHalf.addView(exitLayout);
		quadButtons.addView(quadTopHalf);
		quadButtons.addView(quadBottomHalf);
		addView(quadButtons);
		
		versionInfo = new TextView(context);
		versionInfo.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		versionInfo.setPadding(0, 0, 0, 5);
		versionInfo.setGravity(Gravity.CENTER_HORIZONTAL);
		versionInfo.setTextSize(12.0f);
		versionInfo.setText("Interactive Demo Build " + InteractiveDemo.build + "\nMPL Library Build " + PayPal.getBuild());
		addView(versionInfo);
		
		introNavBar.leftButton.requestFocus();
	}
	
	public void startSimplePaymentFlow() {
		
	}
	
	public void startAdvancedPaymentFlow() {
		
	}
	
	public void startPreapprovalFlow() {
		
	}

	public void onClick(View v) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(v == goSimple) {
			qa.advancedPayment = null;
			qa.preapproval = null;
			if (qa.flowType != InteractiveDemo.FLOW_SIMPLE) {
				qa.receivers.clear();
				qa.receivers.add(new QAReceiver());
			}
			qa.currentReceiver = 0;
			qa.flowType = InteractiveDemo.FLOW_SIMPLE;
			qa.changePage(InteractiveDemo.PAGE_PAYMENT_SIMPLE);
		} else if(v == goAdvanced) {
			qa.simplePayment = null;
			qa.preapproval = null;
			if (qa.flowType != InteractiveDemo.FLOW_ADVANCED)
				qa.receivers.clear();			
			qa.flowType = InteractiveDemo.FLOW_ADVANCED;
			qa.changePage(InteractiveDemo.PAGE_PAYMENT);
		} else if(v == goPreapproval) {
			qa.simplePayment = null;
			qa.advancedPayment = null;
			qa.flowType = InteractiveDemo.FLOW_PREAPPROVAL;
			qa.changePage(InteractiveDemo.PAGE_PREAPPROVAL);
		} else if(v == goExit) {
			qa.finish();
		}
	}
	
	public void onItemSelected(AdapterView<?> p, View v, int position, long id) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(p == editServer.picker) {
			qa.server = editServer.picker.getSelectedItemPosition();
			
			//Automatically change to an appropriate APP ID for each server
			switch(qa.server) {
			case PayPal.ENV_SANDBOX:
				qa.appID = "APP-80W284485P519543T";
				break;
			case PayPal.ENV_LIVE:
				qa.appID = "";
				break;
			case PayPal.ENV_NONE:
				qa.appID = "No App ID for Demo";
				break;
			}
			editAppID.field.setText(qa.appID);
		}
	}

	public void onNothingSelected(AdapterView<?> p) {
		//Nothing needed
	}

	public void afterTextChanged(Editable s) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(s == editAppID.field.getText()) {
			qa.appID = editAppID.field.getText().toString();
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		//Nothing needed
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//Nothing needed
	}
}
