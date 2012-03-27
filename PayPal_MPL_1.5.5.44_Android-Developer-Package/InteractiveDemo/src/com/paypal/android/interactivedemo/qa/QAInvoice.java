package com.paypal.android.interactivedemo.qa;

import java.util.ArrayList;

public class QAInvoice {
	public ArrayList<QAItem> items;
	public String tax;
	public String shipping;
	
	public QAInvoice() {
		items = new ArrayList<QAItem>();
		tax = "";
		shipping = "";
	}
}
