package com.bryanmarty.greenbutton;

import java.net.ContentHandler;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
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
		String[] monthName = {"January", "February",
				"March", "April", "May", "June", "July",
				"August", "September", "October", "November",
				"December"};
		TextView txtDaysRemaining = (TextView)this.findViewById(R.id.txtDaysRemaining);
		TextView txtCurrentMonth = (TextView)this.findViewById(R.id.txtCurrentMonth);
		
		Calendar c = Calendar.getInstance();
		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dayInMonth = c.get(Calendar.DAY_OF_MONTH);
		int daysLeft = daysInMonth - dayInMonth;
		txtDaysRemaining.setText("" + daysLeft);
		txtCurrentMonth.setText(monthName[c.get(Calendar.MONTH)]);
	}
	
	protected void setUpUsage()
	{
		LinkedList<IntervalReading> result = new LinkedList<IntervalReading>();
		LinkedList<IntervalReading> result2 = new LinkedList<IntervalReading>();
		Date dateMostRecentEntry = Calendar.getInstance().getTime();
		Date dateStartThisMonth;
		Date dateStartLastMonth;
		Date dateEndLastMonth;
		
		Future<Date> futureDateMostRecentEntry = TrackManager.getLastDate();
		try {
				dateMostRecentEntry = futureDateMostRecentEntry.get();
		} catch (Exception e){
			e.printStackTrace();
		}
			
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(dateMostRecentEntry.getTime());
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		dateStartThisMonth = c.getTime();
		
		c.roll(Calendar.MONTH, -1);
		dateStartLastMonth = c.getTime();
		int numDayslastMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DAY_OF_MONTH, numDayslastMonth);
		dateEndLastMonth = c.getTime();
		
		Future<LinkedList<IntervalReading>> future = TrackManager.getReadingsBetween(dateStartLastMonth, dateEndLastMonth);
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
				
		Future<LinkedList<IntervalReading>> future2 = TrackManager.getReadingsBetween(dateStartThisMonth, dateMostRecentEntry);
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
