package com.bryanmarty.greenbutton;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.bryanmarty.greenbutton.database.TrackManager;
import com.bryanmarty.greenbutton.tasks.GBDataDownloader;
import com.bryanmarty.greenbutton.tasks.GBDataDownloaderListener;
import com.bryanmarty.greenbutton.tasks.GBDataParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class GreenButtonPINActivity extends Activity implements GBDataDownloaderListener {

	private EditText txtGBData;
	private Button btnGetGBData;
	private ProgressBar pbGBDownloadProgress;
	
	/** Called when the activity is first created. */	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin);
        TrackManager.initialize(this.getApplicationContext());
        
        //txtGBData = (EditText)this.findViewById(R.id.txtGBDataResponse);
        btnGetGBData = (Button)this.findViewById(R.id.btnGetGBData);
        pbGBDownloadProgress = (ProgressBar)this.findViewById(R.id.gbDownloadProgress);
    }
    
	public void getGBData(View view) {
    	GBDataDownloader gbDataDownloader = new GBDataDownloader(this.getApplicationContext(),this);
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
	public void onPostExecute(Integer result) {
		Log.i("Inserted Records: ",String.valueOf(result));
    	btnGetGBData.setEnabled(true);
    	pbGBDownloadProgress.setVisibility(View.GONE);
    	onBackPressed();
    	
    	//TODO: This will be removed, but when testing with a real example file, setting the contents of the 
    	// text box to all the data causes an out of memory error in Android
    	//txtGBData.setText(result);
	}
	
}
