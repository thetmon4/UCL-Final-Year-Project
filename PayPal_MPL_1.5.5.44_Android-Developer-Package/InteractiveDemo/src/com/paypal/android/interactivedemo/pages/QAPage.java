package com.paypal.android.interactivedemo.pages;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class QAPage extends ScrollView {
	
	LinearLayout content;

	public QAPage(Context context) {
		super(context);
		load(context);
	}

	public QAPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		load(context);
	}

	public QAPage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		load(context);
	}
	
	public void load(Context context) {
		content = new LinearLayout(context);
		content.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		content.setPadding(5, 5, 5, 5);
		content.setOrientation(LinearLayout.VERTICAL);
		
		//TODO: Any style things here
		
		super.addView(content);
	}
	
	public void addView(View child) {
		content.addView(child);
	}
	
	public Dialog getDateDialog(int id) {
		return null;
	}

}
