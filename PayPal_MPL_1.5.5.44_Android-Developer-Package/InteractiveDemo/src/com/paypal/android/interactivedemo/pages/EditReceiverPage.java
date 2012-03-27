package com.paypal.android.interactivedemo.pages;

import com.paypal.android.MEP.PayPal;
import com.paypal.android.interactivedemo.InteractiveDemo;
import com.paypal.android.interactivedemo.components.LabelledButton;
import com.paypal.android.interactivedemo.components.LabelledEditText;
import com.paypal.android.interactivedemo.components.LabelledSpinner;
import com.paypal.android.interactivedemo.components.NavBar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class EditReceiverPage extends QAPage implements OnClickListener, OnItemSelectedListener, TextWatcher {

	NavBar receiverNavBar;
	LabelledEditText editRecipient;
	LabelledEditText editSubtotal;
	LabelledEditText editName;
	LabelledEditText editDescription;
	LabelledEditText editCustomID;
	LabelledButton editInvoice;
	LabelledSpinner editPaymentType;
	LabelledSpinner editPaymentSubtype;
	LabelledSpinner editPrimary;
	
	public EditReceiverPage(Context context) {
		super(context);
		loadPage(context);
	}

	public EditReceiverPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		
		receiverNavBar = new NavBar(context, this);
		if(qa.flowType == InteractiveDemo.FLOW_SIMPLE)
			receiverNavBar.titleText.setText("Edit Payment");
		else if(qa.flowType == InteractiveDemo.FLOW_ADVANCED)
			receiverNavBar.titleText.setText("Edit Receiver");
		receiverNavBar.leftButton.setText("Back");
		receiverNavBar.rightButton.setText("N/A");
		receiverNavBar.rightButton.setVisibility(INVISIBLE);
		addView(receiverNavBar);
		
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
		
		//No primary field on simple payments
		if(qa.flowType == InteractiveDemo.FLOW_ADVANCED) {
			editPrimary = new LabelledSpinner(context, this);
			editPrimary.label.setText("Primary Reciever:");
			editPrimary.picker.setPrompt("Set Is Primary Reciever");
			editPrimary.adapter.add("No");
			editPrimary.adapter.add("Yes");
			editPrimary.picker.setSelection(qa.receivers.get(qa.currentReceiver).isPrimary ? 1 : 0);
			addView(editPrimary);
		}
		
		receiverNavBar.leftButton.requestFocus();
	}

	public void onClick(View v) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(v == receiverNavBar.leftButton) {
			if(qa.flowType == InteractiveDemo.FLOW_SIMPLE) {
				qa.changePage(InteractiveDemo.PAGE_PAYMENT);
			} else if(qa.flowType == InteractiveDemo.FLOW_ADVANCED) {
				qa.currentReceiver = -1;
				qa.changePage(InteractiveDemo.PAGE_RECEIVERS);
			}
		} else if(v == receiverNavBar.rightButton) {
			//Nothing
		} else if(v == editInvoice.button) {
			qa.changePage(InteractiveDemo.PAGE_ITEMS);
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
		} else if(p == editPrimary.picker) {
			qa.receivers.get(qa.currentReceiver).isPrimary = (editPrimary.picker.getSelectedItemPosition() == 1);
			
			//Can only have one primary
			if(qa.flowType == InteractiveDemo.FLOW_ADVANCED) {
				if(qa.receivers.get(qa.currentReceiver).isPrimary) {
					for(int i=0; i<qa.receivers.size(); i++) {
						if(i != qa.currentReceiver) {
							qa.receivers.get(i).isPrimary = false;
						}
					}
				}
			}
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
