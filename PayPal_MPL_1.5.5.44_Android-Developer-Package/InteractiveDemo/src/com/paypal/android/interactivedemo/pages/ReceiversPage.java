package com.paypal.android.interactivedemo.pages;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paypal.android.interactivedemo.InteractiveDemo;
import com.paypal.android.interactivedemo.components.NavBar;
import com.paypal.android.interactivedemo.qa.QAReceiver;

public class ReceiversPage extends QAPage implements OnClickListener {
	
	public static final int MAX_RECEIVERS = 20;
	
	NavBar listNavBar;
	TextView noReceivers;
	LinearLayout listReceivers;
	Button addReceiver;

	public ReceiversPage(Context context) {
		super(context);
		loadPage(context);
	}

	public ReceiversPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		
		listNavBar = new NavBar(context, this);
		listNavBar.titleText.setText("Receivers");
		listNavBar.leftButton.setText("Back");
		listNavBar.rightButton.setText("Add");
		addView(listNavBar);
		
		noReceivers = new TextView(context);
		noReceivers.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		noReceivers.setGravity(Gravity.CENTER);
		noReceivers.setText("No receivers present.");
		noReceivers.setTextSize(24.0f);
		
		listReceivers = new LinearLayout(context);
		listReceivers.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		listReceivers.setPadding(3, 3, 3, 3);
		listReceivers.setOrientation(LinearLayout.VERTICAL);
		
		if(qa.receivers.size() == 0) {
			listReceivers.addView(noReceivers);
		} else {
			for(int i=0; i<qa.receivers.size(); i++) {
				LinearLayout receiver = qa.receivers.get(i).getLayout(context, this);
				listReceivers.addView(receiver);
			}
		}
		addView(listReceivers);
		
		addReceiver = new Button(context);
		addReceiver.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		addReceiver.setText("Add Receiver");
		addReceiver.setOnClickListener(this);
		addView(addReceiver);
		
		listNavBar.leftButton.requestFocus();
	}
	
	public void addNewReceiver() {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(qa.receivers.size() < MAX_RECEIVERS) {
			qa.receivers.add(new QAReceiver());
			qa.currentReceiver = qa.receivers.size()-1;
			qa.changePage(InteractiveDemo.PAGE_EDIT_RECEIVER);
		}
	}
	
	public void editReceiver(int index) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		qa.currentReceiver = index;
		qa.changePage(InteractiveDemo.PAGE_EDIT_RECEIVER);
	}
	
	public void removeReceiver(int index) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		qa.receivers.remove(index);
		listReceivers.removeViewAt(index);
		if(qa.receivers.size() == 0)
			listReceivers.addView(noReceivers);
	}

	public void onClick(View v) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(v == listNavBar.leftButton) {
			qa.changePage(InteractiveDemo.PAGE_PAYMENT);
		} else if(v == listNavBar.rightButton || v == addReceiver) {
			addNewReceiver();
		} else {
			for(int i=0; i<qa.receivers.size(); i++) {
				if(v == listReceivers.getChildAt(i)) {
					editReceiver(i);
				} else if(v == qa.receivers.get(i).deleteButton) {
					removeReceiver(i);
					return;
				}
			}
		}
	}
}
