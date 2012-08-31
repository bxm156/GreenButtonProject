package com.bryanmarty.greenbutton;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;

public class GreenButtonSettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}



}
