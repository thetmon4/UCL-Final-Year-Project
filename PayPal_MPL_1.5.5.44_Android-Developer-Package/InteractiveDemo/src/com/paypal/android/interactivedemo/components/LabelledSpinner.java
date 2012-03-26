package com.paypal.android.interactivedemo.components;

import android.content.Context;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class LabelledSpinner extends LinearLayout {
	public TextView label;
	public Spinner picker;
	public ArrayAdapter<CharSequence> adapter;

	public LabelledSpinner(Context context, OnItemSelectedListener listener) {
		super(context);

		setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		label = new TextView(context);
		label.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.75f));
		label.setGravity(Gravity.LEFT);
//		label.setText("");
		addView(label);
		picker = new Spinner(context);
		picker.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.25f));
//		picker.setPrompt("");
        adapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//      adapter.add("");
		picker.setOnItemSelectedListener(listener);
        picker.setAdapter(adapter);
		addView(picker);
	}

}
