package com.bryanmarty.greenbutton;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;


public class GreenButtonGraphActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		LinearLayout layout = (LinearLayout) findViewById(R.id.graphLayout);
		Intent i = this.getIntent();
		
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
				new GraphViewData(1, 2.0d),
				new GraphViewData(2, 1.5d),
				new GraphViewData(3, 2.5d),
				new GraphViewData(4, 1.0d)
		});

		GraphView graphView = new LineGraphView(this, "GraphViewDemo");
		graphView.addSeries(exampleSeries); // data
		layout.addView(graphView);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	
	
}
