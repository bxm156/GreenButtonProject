package com.bryanmarty.greenbutton;

import com.bryanmarty.greenbutton.tendril.oauth.TendrilConnectOAuth;
import com.bryanmarty.greenbutton.tendril.oauth.TendrilConnectOAuthResponse;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class GreenButtonLoginActivity extends Activity {

	private TextView username_;
	private TextView password_;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username_ = (TextView) findViewById(R.id.login_username);
        password_ = (TextView) findViewById(R.id.login_password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_green_button_login, menu);
        return true;
    }
    
    public void onClickLogin(View v) {
    	Thread thread = new Thread(new Runnable() {
	    	
			@Override
			public void run() {
				TendrilConnectOAuthResponse response = TendrilConnectOAuth.authenticate(GreenButtonLoginActivity.this, username_.getText().toString(), password_.getText().toString());
		    	String toast = "Null";
		    	if(response != null) {
		    		toast = response.access_token;
		    	}
		    	Log.i("Access Token",toast);
			}
	    		
    	});
    	thread.start();
    	
    }

    
}
