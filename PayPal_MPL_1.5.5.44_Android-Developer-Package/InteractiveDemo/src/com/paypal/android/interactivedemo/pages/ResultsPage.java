package com.paypal.android.interactivedemo.pages;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paypal.android.interactivedemo.InteractiveDemo;
import com.paypal.android.interactivedemo.components.NavBar;

public class ResultsPage extends QAPage implements OnClickListener {
	
	NavBar resultsNavBar;
	TextView title;
	TextView info;
	TextView extra;
	Button finishButton;

	public ResultsPage(Context context) {
		super(context);
		loadPage(context);
	}

	public ResultsPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadPage(context);
	}
	
	public void loadPage(Context context) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		
		resultsNavBar = new NavBar(context, this);
		resultsNavBar.titleText.setText("Results");
		resultsNavBar.leftButton.setText("Restart");
		resultsNavBar.rightButton.setText("Finish");
		addView(resultsNavBar);
		
		LinearLayout container = new LinearLayout(context);
		container.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		container.setPadding(5, 10, 5, 10);
		container.setOrientation(LinearLayout.VERTICAL);
		container.setGravity(Gravity.CENTER);
		
		title = new TextView(context);
		title.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		title.setPadding(0, 5, 0, 5);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setTextSize(30.0f);
		title.setText(qa.resultTitle);
		container.addView(title);
		
		info = new TextView(context);
		info.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		info.setPadding(0, 5, 0, 5);
		info.setGravity(Gravity.CENTER_HORIZONTAL);
		info.setTextSize(20.0f);
		info.setText(qa.resultInfo);
		container.addView(info);

		extra = new TextView(context);
		extra.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		extra.setPadding(0, 5, 0, 15);
		extra.setGravity(Gravity.CENTER_HORIZONTAL);
		extra.setTextSize(12.0f);
		extra.setText(qa.resultExtra);
		container.addView(extra);
		
		finishButton = new Button(context);
		finishButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		finishButton.setText("Finish");
		finishButton.setOnClickListener(this);
		container.addView(finishButton);
		
		addView(container);
	}

	public void onClick(View v) {
		InteractiveDemo qa = InteractiveDemo.getInstance();
		if(v == resultsNavBar.leftButton) {
			qa.resultTitle = "";
			qa.resultInfo = "";
			qa.resultExtra = "";
			qa.changePage(InteractiveDemo.PAGE_INTRO);
		} else if(v == resultsNavBar.rightButton || v == finishButton) {
			qa.finish();
		}
	}
}
