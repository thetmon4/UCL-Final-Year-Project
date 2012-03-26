package com.paypal.android.interactivedemo.components;

import android.content.Context;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LabelledEditText extends LinearLayout {
	public TextView label;
	public EditText field;

	public LabelledEditText(Context context, TextWatcher listener, boolean required, String labelText) {
		super(context);

		setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		label = new TextView(context);
		label.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.65f));
		label.setGravity(Gravity.LEFT);
		label.setText(labelText);
		label.setLines(1);
		addView(label);
		field = new EditText(context);
		field.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.35f));
//		field.setText("");
		labelText = labelText.replaceAll(":", "");
		field.setHint(labelText + " " + (required ? "(Required)" : "(Optional)"));
		field.setSingleLine(true);
		field.addTextChangedListener(listener);
		addView(field);
	}

}
