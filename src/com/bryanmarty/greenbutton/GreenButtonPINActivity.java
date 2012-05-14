package com.bryanmarty.greenbutton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;

public class GreenButtonPINActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.home);
	}

	public void onClick_PIN(View v)
	{
		Intent intent = new Intent();
	     intent.setClass(this,GreenButtonProjectActivity.class);
	     startActivity(intent);
	}
}
