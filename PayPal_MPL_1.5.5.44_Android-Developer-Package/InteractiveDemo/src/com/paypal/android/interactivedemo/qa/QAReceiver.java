package com.paypal.android.interactivedemo.qa;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paypal.android.MEP.PayPal;
import com.paypal.android.interactivedemo.InteractiveDemo;

public class QAReceiver {
	public String recipient;
	public String subtotal;
	public boolean isPrimary;
	public int paymentType;
	public int paymentSubtype;
	public QAInvoice invoice;
	public String description;
	public String customID;
	public String merchantName;
	
	public Button deleteButton;
	
	public QAReceiver() {
		recipient = "";
		subtotal = "";
		isPrimary = false;
		paymentType = PayPal.PAYMENT_TYPE_NONE;
		paymentSubtype = PayPal.PAYMENT_SUBTYPE_NONE;
		invoice = new QAInvoice();
		description = "";
		customID = "";
		merchantName = "";
	}
	
	public LinearLayout getLayout(Context context, OnClickListener listener) {
		LinearLayout content = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 3, 0, 3);
		content.setLayoutParams(params);
		content.setOrientation(LinearLayout.VERTICAL);
		
		//Background setting here
		GradientDrawable unfocused = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] {0xff333333, 0xff222222});
		unfocused.setCornerRadius(3.0f);
		unfocused.setStroke(2, isPrimary ? 0xffcccc33 : 0xffcccccc);
		GradientDrawable focused = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] {0xff666666, 0xff444444});
		focused.setCornerRadius(3.0f);
		focused.setStroke(2, isPrimary ? 0xffcccc33 : 0xffcccccc);
		
		StateListDrawable background = new StateListDrawable();
		background.addState(new int[] {-android.R.attr.state_focused}, unfocused);
		background.addState(new int[] {android.R.attr.state_focused}, focused);
		content.setBackgroundDrawable(background);
		
		//Top layout
		LinearLayout top = new LinearLayout(context);
		top.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		top.setPadding(3, 0, 3, 0);
		top.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView name = new TextView(context);
		name.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.25f));
		name.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		if(merchantName.length() > 0) {
			name.setText(merchantName);
		} else if(recipient.length() > 0) {
			name.setText(recipient);
		} else {
			name.setText("New Recipient");
		}
		name.setTextSize(30.0f);
		name.setSingleLine(true);
		top.addView(name);
		
		deleteButton = new Button(context);
		deleteButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.75f));
		deleteButton.setText("Delete");
		deleteButton.setOnClickListener(listener);
		top.addView(deleteButton);
		
		//Bottom layout
		LinearLayout bottom = new LinearLayout(context);
		bottom.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		bottom.setPadding(3, 0, 3, 0);
		bottom.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView total = new TextView(context);
		total.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.5f));
		total.setGravity(Gravity.LEFT);
		String code = InteractiveDemo.currencyCodes[InteractiveDemo.getInstance().options.currency];
		if(subtotal.length() > 0) {
			total.setText(code + " " + subtotal);
		} else {
			total.setText(code + " 0.00");
		}
		total.setTextSize(20.0f);
		bottom.addView(total);
		
		TextView items = new TextView(context);
		items.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.5f));
		items.setGravity(Gravity.LEFT);
		items.setText("Items: " + invoice.items.size());
		items.setTextSize(20.0f);
		bottom.addView(items);
		
		content.addView(top);
		content.addView(bottom);
		content.setOnClickListener(listener);
		content.setFocusable(true);
		return content;
	}
}
