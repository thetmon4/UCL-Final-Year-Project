package com.paypal.android.interactivedemo.qa;

import java.util.Locale;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.interactivedemo.InteractiveDemo;

public class QAOptions {
	public int currency;
	public int language;
	public int buttonType;
	public int buttonText;
	public String cancelUrl;
	public String returnUrl;
	public String ipnUrl;
	public String memo;
	
	public QAOptions() {
		currency = getDefaultCurrency();
		language = getDefaultLanguage();
		buttonType = PayPal.BUTTON_194x37;
		buttonText = CheckoutButton.TEXT_PAY;
		cancelUrl = "https://www.paypal.com";
		returnUrl = "https://www.paypal.com";
		ipnUrl = "";
		memo = "";
	}
	
	private int getDefaultCurrency() {
//		String curr = TODO: pick out a currency?
//		for(int i=0; i<MPLQA.currencyCodes.length; i++) {
//			if(MPLQA.currencyCodes[i].equals(curr)) {
//				return i;
//			}
//		}
		return 22; //USD
	}
	
	private int getDefaultLanguage() {
		String lang = Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
		for(int i=0; i<InteractiveDemo.languageCodes.length; i++) {
			if(InteractiveDemo.languageCodes[i].equals(lang)) {
				return i;
			}
		}
		return 20; //en_US;
	}
}