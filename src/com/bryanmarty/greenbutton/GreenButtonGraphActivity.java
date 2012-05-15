package com.bryanmarty.greenbutton;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.Future;

import com.bryanmarty.greenbutton.data.DataPoint;
import com.bryanmarty.greenbutton.data.IntervalReading;
import com.bryanmarty.greenbutton.database.TrackManager;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;


public class GreenButtonGraphActivity extends Activity {
	
	LinkedList<IntervalReading> cached_ = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}
	
	

	@Override
	protected void onStart() {
		prepareData();
		showGraph(prepareDaily());
		super.onStart();
	}

	protected void prepareData() {
		LinkedList<IntervalReading> result = new LinkedList<IntervalReading>();
		
		//Pull all readings from the beginning of the month
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		Future<LinkedList<IntervalReading>> future = TrackManager.getReadingsSince(cal.getTime());
		try {
			result = future.get();
			cached_ = result;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected TreeMap<Integer,DataPoint> prepareDaily() {
		TreeMap<Integer,DataPoint> map = new TreeMap<Integer,DataPoint>();
		for(IntervalReading r : cached_) {
			Date startDate = r.getStartTime();
			int day = startDate.getDate();
			DataPoint d = map.get(day);
			if(d != null) {
				d.cost += r.getCost();
				d.value += r.getValue();
			} else {
				d = new DataPoint();
				d.x = day;
				d.cost = r.getCost();
				d.value = r.getValue();
				map.put(day, d);
			}
		}
		return map;
	}
	
	protected void showGraph(TreeMap<Integer,DataPoint> data) {
		
		if(data.size() <= 0) {
			return;
		}
		
		GraphViewData[] valuePoints = new GraphViewData[data.size()];
		GraphViewData[] costPoints = new GraphViewData[data.size()];
		
		TreeSet<Integer> keys = new TreeSet<Integer>(data.keySet());
		int x = 0;
		for(Integer key : keys) {
			DataPoint dp = data.get(key);
			GraphViewData pCost = new GraphViewData(dp.x,dp.cost);
			GraphViewData pValue = new GraphViewData(dp.x,dp.value);
			valuePoints[x] = pValue;
			costPoints[x] = pCost;
			x++;
		}
		
		// init example series data
		GraphViewSeries valueSeries = new GraphViewSeries(valuePoints);
		GraphViewSeries costSeries = new GraphViewSeries(costPoints);
		
		GraphView graphView = new LineGraphView(this, "GraphViewDemo");
		graphView.addSeries(costSeries);
		graphView.addSeries(valueSeries);
		LinearLayout layout = (LinearLayout) findViewById(R.id.graphLayout);
		layout.addView(graphView);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	
	
}
