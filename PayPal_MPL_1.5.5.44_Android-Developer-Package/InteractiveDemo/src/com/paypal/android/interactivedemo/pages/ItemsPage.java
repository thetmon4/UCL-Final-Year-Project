package com.paypal.android.interactivedemo.pages;

import com.paypal.android.interactivedemo.InteractiveDemo;
import com.paypal.android.interactivedemo.components.LabelledEditText;
import com.paypal.android.interactivedemo.components.NavBar;
import com.paypal.android.interactivedemo.qa.QAItem;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemsPage extends QAPage implements OnClickListener, TextWatcher {
	
	public static final int MAX_ITEMS = 20;
	
	NavBar invoiceNavBar;
	LabelledEditText editTax;
	LabelledEditText editShipping;
	TextView noItems;
	LinearLayout listItems;
	Button addItem;

	public ItemsPage(Context context) {
		super(context);
		loadPage(context);
	}

	public ItemsPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		
		invoiceNavBar = new NavBar(context, this);
		invoiceNavBar.titleText.setText("Invoice Items");
		invoiceNavBar.leftButton.setText("Back");
		invoiceNavBar.rightButton.setText("Add");
		addView(invoiceNavBar);
		
		editTax = new LabelledEditText(context, this, false, "Tax:");
		//editTax.label.setText("Tax:");
		editTax.field.setText(qa.receivers.get(qa.currentReceiver).invoice.tax);
		addView(editTax);
		
		editShipping = new LabelledEditText(context, this, false, "Shipping:");
		//editShipping.label.setText("Shipping:");
		editShipping.field.setText(qa.receivers.get(qa.currentReceiver).invoice.shipping);
		addView(editShipping);
		
		noItems = new TextView(context);
		noItems.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		noItems.setGravity(Gravity.CENTER);
		noItems.setText("No items present.");
		noItems.setTextSize(24.0f);
		
		listItems = new LinearLayout(context);
		listItems.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		listItems.setPadding(3, 3, 3, 3);
		listItems.setOrientation(LinearLayout.VERTICAL);
		
		if(qa.receivers.size() == 0) {
			listItems.addView(noItems);
		} else {
			for(int i=0; i<qa.receivers.get(qa.currentReceiver).invoice.items.size(); i++) {
				LinearLayout receiver = qa.receivers.get(qa.currentReceiver).invoice.items.get(i).getLayout(context, this);
				listItems.addView(receiver);
			}
		}
		addView(listItems);
		
		addItem = new Button(context);
		addItem.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		addItem.setText("Add Item");
		addItem.setOnClickListener(this);
		addView(addItem);
		
		invoiceNavBar.leftButton.requestFocus();
	}
	
	public void addNewItem() {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(qa.receivers.get(qa.currentReceiver).invoice.items.size() < MAX_ITEMS) {
			qa.receivers.get(qa.currentReceiver).invoice.items.add(new QAItem());
			qa.currentItem = qa.receivers.get(qa.currentReceiver).invoice.items.size()-1;
			qa.changePage(InteractiveDemo.PAGE_EDIT_ITEM);
		}
	}
	
	public void editItem(int index) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		qa.currentItem = index;
		qa.changePage(InteractiveDemo.PAGE_EDIT_ITEM);
	}
	
	public void removeItem(int index) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		qa.receivers.get(qa.currentReceiver).invoice.items.remove(index);
		listItems.removeViewAt(index);
		if(qa.receivers.get(qa.currentReceiver).invoice.items.size() == 0)
			listItems.addView(noItems);
	}

	public void onClick(View v) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(v == invoiceNavBar.leftButton) {
			if(qa.flowType == InteractiveDemo.FLOW_SIMPLE)
				qa.changePage(InteractiveDemo.PAGE_PAYMENT_SIMPLE);
			else
				qa.changePage(InteractiveDemo.PAGE_EDIT_RECEIVER);
		} else if(v == invoiceNavBar.rightButton || v == addItem) {
			addNewItem();
		} else {
			for(int i=0; i<qa.receivers.get(qa.currentReceiver).invoice.items.size(); i++) {
				if(v == listItems.getChildAt(i)) {
					editItem(i);
				} else if(v == qa.receivers.get(qa.currentReceiver).invoice.items.get(i).deleteButton) {
					removeItem(i);
					return;
				}
			}
		}
	}

	public void afterTextChanged(Editable s) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(s == editTax.field.getText()) {
			qa.receivers.get(qa.currentReceiver).invoice.tax = editTax.field.getText().toString();
		} else if(s == editShipping.field.getText()) {
			qa.receivers.get(qa.currentReceiver).invoice.shipping = editShipping.field.getText().toString();
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		//Nothing needed
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//Nothing needed
	}

}
