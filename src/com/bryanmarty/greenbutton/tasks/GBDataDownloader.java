package com.bryanmarty.greenbutton.tasks;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.Future;

import com.bryanmarty.greenbutton.DebugSettings;
import com.bryanmarty.greenbutton.GreenButtonProjectActivity;
import com.bryanmarty.greenbutton.data.IntervalReading;
import com.bryanmarty.greenbutton.database.TrackManager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GBDataDownloader extends AsyncTask<String, Integer, Integer> {
	private final GBDataDownloaderListener listener_;
	private Context context_;

	public GBDataDownloader(Context context, GBDataDownloaderListener listener) {
		context_ = context;
		listener_ = listener;
	}

	protected Integer doInBackground(String... pin) {
		URL url;
		HttpURLConnection urlConnection = null;
		String gbData = "";
		StringBuffer gbBuffer = null;

		try {
			url = new URL(DebugSettings.SERVER + "get/" + pin[0]);
			urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader gbReader = new BufferedReader(new InputStreamReader(in));
			File tmp = File.createTempFile("gbdata",".xml",context_.getCacheDir());
			BufferedWriter out = new BufferedWriter(new FileWriter(tmp));
			
			gbBuffer = new StringBuffer();
			String ln = System.getProperty("line.separator");
			while ((gbData = gbReader.readLine()) != null) {
				out.write(gbData + ln);
			}
			out.close();
			Log.i("status","complete");
			GBDataParser gbParser = new GBDataParser(tmp);
			gbParser.parseGBData();
			while(!gbParser.isDoneParsing()) { }
			tmp.delete();
			LinkedList<IntervalReading> list = gbParser.getIntervalReadingList();
			Future<Boolean> fResult = TrackManager.addReadings(list);
			try {
				Boolean result = fResult.get();
				if(result) {
					return list.size();
				} else {
					return -1;
				}
			} catch (Exception e) {
				
			}
		
		} catch (IOException ioe) {
			// we might consider having a special failure response instead of
			// catching the exception
			// Toast.makeText(getApplicationContext(),
			// "Invalid PIN, please enter again.", Toast.LENGTH_SHORT).show();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			// view.setEnabled(true);
			// pb.setVisibility(View.GONE);
		}

		return -1;
		// TODO: more intelligent error message

	}
	protected void onProgressUpdate(Integer... progress) {
		listener_.onProgressUpdate(progress);
    }

    protected void onPostExecute(Integer result) 
    {
    	listener_.onPostExecute(result);
    }
    protected void onPreExecute() {
    	listener_.onPreExecute();
    }
}
