package com.bryanmarty.greenbutton;

import com.bryanmarty.greenbutton.database.TrackManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class GreenButtonProjectActivity extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.home);
	    TrackManager.initialize(this);
	}

	public void onClick_PIN(View v)
	{
		Intent intent = new Intent();
	     intent.setClass(this,GreenButtonPINActivity.class);
	     startActivity(intent);
	}
	
	public void onClick_Chart(View v) {
		Intent intent = new Intent();
		intent.setClass(this, GreenButtonGraphActivity.class);
		startActivity(intent);
	}
	
	
}