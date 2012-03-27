package com.paypal.android.interactivedemo.components;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LabelledButton extends LinearLayout {
	public TextView label;
	public Button button;

	public LabelledButton(Context context, OnClickListener listener) {
		super(context);

		setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		label = new TextView(context);
		label.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.75f));
		label.setGravity(Gravity.LEFT);
//		label.setText("");
		addView(label);
		button = new Button(context);
		button.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.25f));
		button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		button.setTextSize(16.0f);
//		button.setText("");
		button.setOnClickListener(listener);
		addView(button);
	}

}
