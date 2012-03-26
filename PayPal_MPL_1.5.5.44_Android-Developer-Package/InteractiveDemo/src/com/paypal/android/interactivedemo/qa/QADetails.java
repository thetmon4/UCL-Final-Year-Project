package com.paypal.android.interactivedemo.qa;

import com.paypal.android.MEP.PayPal;

public class QADetails {
	public String preapprovalKey;
	public String merchantName;
	public int paymentType;
	public int paymentSubtype;
	public int feePayer;
	public boolean shippingEnabled;
	public boolean dynamicCalculation;
	public String maxNumPayments;
	public String maxTotalAmtAllPayments;
	public String maxAmtPerPayment;
	public String maxNumPaymentPerPeriod;
	public String senderEmail;
	public int paymentPeriod;
	public int dateOfMonth;
	public int dayOfWeek;
	public boolean pinRequired;
	
	public QADetails() {
		preapprovalKey = "";
		merchantName = "";
		paymentType = PayPal.PAYMENT_TYPE_NONE;
		paymentSubtype = PayPal.PAYMENT_SUBTYPE_NONE;
		feePayer = PayPal.FEEPAYER_EACHRECEIVER;
		shippingEnabled = true;
		dynamicCalculation = false;
	}
}