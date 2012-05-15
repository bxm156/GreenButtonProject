package com.bryanmarty.greenbutton;

import java.net.ContentHandler;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GreenButtonBudgetActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.budget);
	    
	    setUpDate();
	}

	@Override
	protected void onStart() {		
		super.onStart();
		
	}
	
	protected void setUpDate()
	{
		TextView txtDaysRemaining = (TextView)this.findViewById(R.id.txtDaysRemaining);
		Calendar c = Calendar.getInstance();
		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dayInMonth = c.get(Calendar.DAY_OF_MONTH);
		int daysLeft = daysInMonth - dayInMonth;
		txtDaysRemaining.setText("" + daysLeft);
	}
	
	protected void setUpUsageCurrent()
	{
		
	}
	
	protected void setUpUsagePrevious()
	{
		
	}
}
