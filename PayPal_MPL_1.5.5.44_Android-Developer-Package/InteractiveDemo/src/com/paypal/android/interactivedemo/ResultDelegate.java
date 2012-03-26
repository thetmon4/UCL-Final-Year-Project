package com.paypal.android.interactivedemo;

import java.io.Serializable;

import com.paypal.android.MEP.PayPalResultDelegate;

public class ResultDelegate implements PayPalResultDelegate, Serializable {

	private static final long serialVersionUID = 9002L;

	public void onPaymentSucceeded(String transactionID, String paymentStatus) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		qa.resultTitle = "SUCCESS";
		qa.resultInfo = "You have successfully completed your transaction.";
		qa.resultExtra = "Transaction ID: " + transactionID;
	}

	public void onPaymentFailed(String paymentStatus, String correlationID, String transactionID, String errorID, String errorMessage) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		qa.resultTitle = "FAILURE";
		qa.resultInfo = errorMessage;
		qa.resultExtra = "Error ID: " + errorID + "\nCorrelation ID: " + correlationID + "\nTransaction ID: " + transactionID;
	}

	public void onPaymentCanceled(String paymentStatus) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		qa.resultTitle = "CANCELED";
		qa.resultInfo = "The transaction has been cancelled.";
		qa.resultExtra = "";
	}
}