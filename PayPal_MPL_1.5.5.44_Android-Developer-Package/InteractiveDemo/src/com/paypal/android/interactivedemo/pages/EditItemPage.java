package com.paypal.android.interactivedemo.pages;

import com.paypal.android.interactivedemo.InteractiveDemo;
import com.paypal.android.interactivedemo.components.LabelledEditText;
import com.paypal.android.interactivedemo.components.NavBar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class EditItemPage extends QAPage implements OnClickListener, TextWatcher {
	
	NavBar itemNavBar;
	LabelledEditText editItemName;
	LabelledEditText editCustomID;
	LabelledEditText editQuantity;
	LabelledEditText editUnitPrice;
	LabelledEditText editTotalPrice;
	Button doneButton;

	public EditItemPage(Context context) {
		super(context);
		loadPage(context);
	}

	public EditItemPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		
		itemNavBar = new NavBar(context, this);
		itemNavBar.titleText.setText("Edit Item");
		itemNavBar.leftButton.setText("Back");
		itemNavBar.rightButton.setText("N/A");
		itemNavBar.rightButton.setVisibility(INVISIBLE);
		addView(itemNavBar);
		
		editItemName = new LabelledEditText(context, this, false, "Item Name:");
		//editItemName.label.setText("Item Name:");
		editItemName.field.setText(qa.receivers.get(qa.currentReceiver).invoice.items.get(qa.currentItem).name);
		addView(editItemName);
		
		editCustomID = new LabelledEditText(context, this, false, "Custom ID:");
		//editCustomID.label.setText("Custom ID:");
		editCustomID.field.setText(qa.receivers.get(qa.currentReceiver).invoice.items.get(qa.currentItem).id);
		addView(editCustomID);
		
		editQuantity = new LabelledEditText(context, this, false, "Quantity:");
		//editQuantity.label.setText("Quantity:");
		editQuantity.field.setText(qa.receivers.get(qa.currentReceiver).invoice.items.get(qa.currentItem).quantity);
		addView(editQuantity);
		
		editUnitPrice = new LabelledEditText(context, this, false, "Unit Price:");
		//editUnitPrice.label.setText("Unit Price:");
		editUnitPrice.field.setText(qa.receivers.get(qa.currentReceiver).invoice.items.get(qa.currentItem).unitPrice);
		addView(editUnitPrice);
		
		editTotalPrice = new LabelledEditText(context, this, false, "Total Price:");
		//editTotalPrice.label.setText("Total Price:");
		editTotalPrice.field.setText(qa.receivers.get(qa.currentReceiver).invoice.items.get(qa.currentItem).price);
		addView(editTotalPrice);
		
		doneButton = new Button(context);
		doneButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		doneButton.setText("Done");
		doneButton.setOnClickListener(this);
		addView(doneButton);
		
		itemNavBar.leftButton.requestFocus();
	}

	public void onClick(View v) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(v == itemNavBar.leftButton || v == doneButton) {
			qa.currentItem = -1;
			qa.changePage(InteractiveDemo.PAGE_ITEMS);
		} else if(v == itemNavBar.rightButton) {
			//Nothing
		}
	}

	public void afterTextChanged(Editable s) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(s == editItemName.field.getText()) {
			qa.receivers.get(qa.currentReceiver).invoice.items.get(qa.currentItem).name = editItemName.field.getText().toString();
		} else if(s == editCustomID.field.getText()) {
			qa.receivers.get(qa.currentReceiver).invoice.items.get(qa.currentItem).id = editCustomID.field.getText().toString();
		} else if(s == editQuantity.field.getText()) {
			qa.receivers.get(qa.currentReceiver).invoice.items.get(qa.currentItem).quantity = editQuantity.field.getText().toString();
		} else if(s == editUnitPrice.field.getText()) {
			qa.receivers.get(qa.currentReceiver).invoice.items.get(qa.currentItem).unitPrice = editUnitPrice.field.getText().toString();
		} else if(s == editTotalPrice.field.getText()) {
			qa.receivers.get(qa.currentReceiver).invoice.items.get(qa.currentItem).price = editTotalPrice.field.getText().toString();
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		//Nothing needed
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//Nothing needed
	}

}
