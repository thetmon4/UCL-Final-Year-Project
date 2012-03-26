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

import com.paypal.android.interactivedemo.InteractiveDemo;

public class QAItem {
	public String name;
	public String id;
	public String price;
	public String unitPrice;
	public String quantity;
	public Button deleteButton;
	
	public QAItem() {
		name = "";
		id = "";
		price = "";
		unitPrice = "";
		quantity = "";
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
		unfocused.setStroke(2, 0xffcccccc);
		GradientDrawable focused = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] {0xff666666, 0xff444444});
		focused.setCornerRadius(3.0f);
		focused.setStroke(2, 0xffcccccc);
		
		StateListDrawable background = new StateListDrawable();
		background.addState(new int[] {-android.R.attr.state_focused}, unfocused);
		background.addState(new int[] {android.R.attr.state_focused}, focused);
		content.setBackgroundDrawable(background);
		
		//Top layout
		LinearLayout top = new LinearLayout(context);
		top.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		top.setPadding(3, 0, 3, 0);
		top.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView itemName = new TextView(context);
		itemName.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.25f));
		if(name.length() > 0) {
			itemName.setText(name);
		} else {
			itemName.setText("New Item");
		}
		itemName.setTextSize(30.0f);
		itemName.setSingleLine(true);
		top.addView(itemName);
		
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
		
		TextView itemPrice = new TextView(context);
		itemPrice.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		itemPrice.setGravity(Gravity.LEFT);
		String code = InteractiveDemo.currencyCodes[InteractiveDemo.getInstance().options.currency];
		if(price.length() > 0) {
			itemPrice.setText(code + " " + price);
		} else {
			itemPrice.setText(code + " 0.00");
		}
		itemPrice.setTextSize(20.0f);
		bottom.addView(itemPrice);
		
		content.addView(top);
		content.addView(bottom);
		content.setOnClickListener(listener);
		content.setFocusable(true);
		return content;
	}
}
