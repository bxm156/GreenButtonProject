package com.bryanmarty.greenbutton;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.net.*;
import java.io.*;

public class GreenButtonProjectActivity extends Activity {
	
	private EditText txtGBData;
	private Button btnGetGBData;
	private ProgressBar pbGBDownloadProgress;
	
	/** Called when the activity is first created. */	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        txtGBData = (EditText)this.findViewById(R.id.txtGBDataResponse);
        btnGetGBData = (Button)this.findViewById(R.id.btnGetGBData);
        pbGBDownloadProgress = (ProgressBar)this.findViewById(R.id.gbDownloadProgress);
    }
    
    public void getGBData(View view) throws IOException
    {
    	GBDataDownloader gbDataDownloader = new GBDataDownloader();
    	EditText txtPin = (EditText)this.findViewById(R.id.txtPIN_Entry);
    	String pin = txtPin.getText().toString();
    	if(pin.equals("") || pin.length() != 7)
    	{
    		Toast.makeText(getApplicationContext(), "Invalid PIN, please enter again.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	gbDataDownloader.execute(pin);
    	
    }
    
    private class GBDataDownloader extends AsyncTask<String, Integer, String> 
    {    
    	protected void onPreExecute() 
    	{
    		btnGetGBData.setEnabled(false);
    		pbGBDownloadProgress.setVisibility(View.VISIBLE);
    		
    	}
    	
    	protected String doInBackground(String... pin) 
    	{
    		URL url;
    		HttpURLConnection urlConnection = null; 
    		String gbData = "";
    		StringBuffer gbBuffer = null;
    		
    		try 
        	{
    			url = new URL("http://greenbutton.case.edu/get/" + pin[0]);
    			urlConnection = (HttpURLConnection) url.openConnection();
            	
        		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        		BufferedReader gbReader = new BufferedReader(new InputStreamReader(in));
        	     gbBuffer = new StringBuffer();
        	     while((gbData = gbReader.readLine()) != null)
        	     {
        	    	 gbBuffer.append(gbData + System.getProperty("line.separator"));
        	     }
        	}
        	catch (IOException ioe) {
        		// we might consider having a special failure response instead of catching the exception
        		//Toast.makeText(getApplicationContext(), "Invalid PIN, please enter again.", Toast.LENGTH_SHORT).show();
        	}
        	finally
        	{
        		if(urlConnection != null)
        			urlConnection.disconnect();
        		 //view.setEnabled(true);
        	     //pb.setVisibility(View.GONE);
        	}
    		
    		return gbBuffer != null ? gbBuffer.toString() : "Error downloading Green Button from server\n"; // TODO: more intelligent error message
    	    
        }

        protected void onProgressUpdate(Integer... progress) {
        	
        }

        protected void onPostExecute(String result) 
        {
        	btnGetGBData.setEnabled(true);
        	pbGBDownloadProgress.setVisibility(View.GONE);
        	txtGBData.setText(result);
        }
    }
}