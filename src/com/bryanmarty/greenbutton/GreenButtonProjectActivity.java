package com.bryanmarty.greenbutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.bryanmarty.greenbutton.database.TrackManager;
import com.bryanmarty.greenbutton.tasks.GBDataDownloader;
import com.bryanmarty.greenbutton.tasks.GBDataDownloaderListener;
import com.bryanmarty.greenbutton.tasks.GBDataParser;

public class GreenButtonProjectActivity extends Activity implements GBDataDownloaderListener {
	
	private EditText txtGBData;
	private Button btnGetGBData;
	private ProgressBar pbGBDownloadProgress;
	
	/** Called when the activity is first created. */	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TrackManager.initialize(this);
        
        txtGBData = (EditText)this.findViewById(R.id.txtGBDataResponse);
        btnGetGBData = (Button)this.findViewById(R.id.btnGetGBData);
        pbGBDownloadProgress = (ProgressBar)this.findViewById(R.id.gbDownloadProgress);
        
        GBDataParser gbParser = new GBDataParser("");
    }
    
    @Override
	protected void onDestroy() {
		TrackManager.shutdown();
		super.onDestroy();
	}



	public void getGBData(View view) {
    	GBDataDownloader gbDataDownloader = new GBDataDownloader(this);
    	EditText txtPin = (EditText)this.findViewById(R.id.txtPIN_Entry);
    	String pin = txtPin.getText().toString();
    	if(pin.contentEquals("") || pin.length() != 7) {
    		Toast.makeText(getApplicationContext(), "Invalid PIN, please enter again.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	gbDataDownloader.execute(pin);
    	
    }

	@Override
	public void onPreExecute() {
		btnGetGBData.setEnabled(false);
		pbGBDownloadProgress.setVisibility(View.VISIBLE);
		
	}

	@Override
	public void onProgressUpdate(Integer... progress) {

	}

	@Override
	public void onPostExecute(String result) {
    	btnGetGBData.setEnabled(true);
    	pbGBDownloadProgress.setVisibility(View.GONE);
    	
    	//TODO: This will be removed, but when testing with a real example file, setting the contents of the 
    	// text box to all the data causes an out of memory error in Android
    	txtGBData.setText(result);
	}
}