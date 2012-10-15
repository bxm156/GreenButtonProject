package com.bryanmarty.greenbutton;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class GreenButtonAboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
	    setContentView(R.layout.about);
	    
	    TextView link = (TextView)findViewById(R.id.textAboutLink);
	    link.setMovementMethod(LinkMovementMethod.getInstance());
	}

}
