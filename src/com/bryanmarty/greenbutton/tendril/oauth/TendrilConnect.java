package com.bryanmarty.greenbutton.tendril.oauth;

import android.content.Context;

public class TendrilConnect {

	private static TendrilConnect instance_;
	private static Context context_;
	private static TendrilConnectOAuthResponse authorization_;
	private boolean valid = false;
	
	
	public static TendrilConnect getInstance(Context context) {
		if(instance_ == null) {
			instance_ = new TendrilConnect(context);
		}
		return instance_;
	}
	
	public TendrilConnect(Context context) {
		context_ = context;
	}
	
	public boolean login(String username, String password) {
		TendrilConnectOAuthResponse response = TendrilConnectOAuth.authenticate(context_, username, password);
		
		//Login failed
		if(response == null) {
			return false;
		}
		
		authorization_ = response;
		valid = true;
		return true;
	}
	
	public static boolean isValid() {
		return isValid();
	}

}
