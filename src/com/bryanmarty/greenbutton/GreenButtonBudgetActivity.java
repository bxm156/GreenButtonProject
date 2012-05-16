package com.bryanmarty.greenbutton;

import java.net.ContentHandler;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.Future;

import com.bryanmarty.greenbutton.data.IntervalReading;
import com.bryanmarty.greenbutton.database.TrackManager;

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
	    setUpUsage();
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
	
	protected void setUpUsage()
	{
		LinkedList<IntervalReading> result = new LinkedList<IntervalReading>();
		LinkedList<IntervalReading> result2 = new LinkedList<IntervalReading>();
		
		
		Calendar c = Calendar.getInstance();
		c.set(2011, Calendar.SEPTEMBER, 1, 0, 0);
		/*c.roll(Calendar.MONTH, -1); // last month
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);*/		
		
		Date startLastMonth = c.getTime();
		c.roll(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date endLastMonth = c.getTime();
		
		Future<LinkedList<IntervalReading>> future = TrackManager.getReadingsBetween(startLastMonth, endLastMonth);
		try {
			result = future.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int usagePrevious = 0;
		for(IntervalReading r : result)
		{
			usagePrevious += r.getValue();
		}
		
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.DAY_OF_MONTH, 1);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		Date now = c2.getTime();
		
		Future<LinkedList<IntervalReading>> future2 = TrackManager.getReadingsSince(now);
		try {
			result2 = future2.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int usageCurrent = 0;
		for(IntervalReading r : result2 )
		{
			usageCurrent += r.getValue();
		}
		
		TextView txtUsageCurrent = (TextView)this.findViewById(R.id.txtUsageCurrent);
		txtUsageCurrent.setText("" + (int)((double)usageCurrent / 1000.0));
		
		TextView txtUsagePrevious = (TextView)this.findViewById(R.id.txtUsagePrevious);
		txtUsagePrevious.setText("" + (int)((double)usagePrevious / 1000.0));
				
	}
	
	protected void setUpUsagePrevious()
	{
		
	}
}
