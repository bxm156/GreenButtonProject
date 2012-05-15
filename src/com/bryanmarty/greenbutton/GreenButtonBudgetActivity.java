package com.bryanmarty.greenbutton;

import java.net.ContentHandler;

import android.app.Activity;
import android.os.Bundle;

public class GreenButtonBudgetActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.budget);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
}
