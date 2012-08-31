package com.bryanmarty.greenbutton;

import com.bryanmarty.greenbutton.database.TrackManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;


public class GreenButtonProjectActivity extends Activity {
	
	Vibrator vib_;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.home);
	    TrackManager.initialize(this);
	    vib_ = (Vibrator) this.getSystemService("vibrator");
	    Intent i = new Intent();
	    i.setClass(this, GreenButtonLoginActivity.class);
	    startActivity(i);
	}

	public void onClick_PIN(View v)
	{
	    vib_.vibrate(75);
		Intent intent = new Intent();
	    intent.setClass(this,GreenButtonPINActivity.class);
	    startActivity(intent);
	}
	
	public void onClick_Chart(View v) {
		vib_.vibrate(75);
		Intent intent = new Intent();
		intent.setClass(this, GreenButtonGraphActivity.class);
		startActivity(intent);
	}
	
	public void onClick_Budget(View v) {
		vib_.vibrate(75);
		Intent intent = new Intent();
		intent.setClass(this, GreenButtonBudgetActivity.class);
		startActivity(intent);
	}
	
	public void onClick_Web(View v) {
		vib_.vibrate(75);
		Uri uriURL = Uri.parse("http://greenbutton.case.edu");
		Intent intent = new Intent(Intent.ACTION_VIEW, uriURL);
		startActivity(intent);
		
	}
    @Override
	protected void onDestroy() {
		TrackManager.shutdown();
		super.onDestroy();
	}

	
	
}