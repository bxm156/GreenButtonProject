package com.bryanmarty.greenbutton;

import com.bryanmarty.greenbutton.tendril.oauth.TendrilConnect;
import com.bryanmarty.greenbutton.tendril.oauth.TendrilConnectOAuth;
import com.bryanmarty.greenbutton.tendril.oauth.TendrilConnectOAuthResponse;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
    	
    	//Start a new thread to login, all network access must not be on UI thread 
    	Thread thread = new Thread(new Runnable() {
	    	
			@Override
			public void run() {
		    	TendrilConnect connect = TendrilConnect.getInstance(getApplicationContext());
		    	String username = username_.getText().toString();
		    	String password = password_.getText().toString();
		    	boolean result = connect.login(username, password);
				
			}
	    		
    	});
    	thread.start();
    	
    }

    
}
