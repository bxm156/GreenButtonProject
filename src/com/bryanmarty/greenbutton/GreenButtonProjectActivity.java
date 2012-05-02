package com.bryanmarty.greenbutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.net.*;
import java.io.*;

public class GreenButtonProjectActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    }
    
    public void getGBData(View view) throws IOException
    {
    	// TODO: probably should  download on separate thread & show progress spinner somewhere
    	EditText txtPin = (EditText)this.findViewById(R.id.txtPIN_Entry);
    	String pin = txtPin.getText().toString();
    	if(pin.equals("") || pin.length() != 7)
    	{
    		Toast.makeText(getApplicationContext(), "Invalid PIN, please enter again.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	URL url = new URL("http://greenbutton.case.edu/get/" + pin);
    	HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    	try 
    	{
    		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
    		BufferedReader gbReader = new BufferedReader(new InputStreamReader(in));
    	     String gbData = "";
    	     StringBuffer gbBuffer = new StringBuffer();
    	     while((gbData = gbReader.readLine()) != null)
    	     {
    	    	 gbBuffer.append(gbData + System.getProperty("line.separator"));
    	     }
    	     EditText txtGBData = (EditText)this.findViewById(R.id.txtGBDataResponse);
    	     txtGBData.setText(gbBuffer);
    	}
    	catch (IOException ioe) {
    		// we might consider having a special failure response instead of catching the exception
    		Toast.makeText(getApplicationContext(), "Invalid PIN, please enter again.", Toast.LENGTH_SHORT).show();
    	}
    	finally 
    	{
    	     urlConnection.disconnect();
    	}
    }
}