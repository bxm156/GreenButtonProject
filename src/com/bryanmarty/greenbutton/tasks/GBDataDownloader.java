package com.bryanmarty.greenbutton.tasks;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class GBDataDownloader extends AsyncTask<String, Integer, String> {
	private final GBDataDownloaderListener listener_;

	public GBDataDownloader(GBDataDownloaderListener listener) {
		listener_ = listener;
	}

	protected String doInBackground(String... pin) {
		URL url;
		HttpURLConnection urlConnection = null;
		String gbData = "";
		StringBuffer gbBuffer = null;

		try {
			url = new URL("http://greenbutton.case.edu/get/" + pin[0]);
			urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader gbReader = new BufferedReader(new InputStreamReader(in));
			gbBuffer = new StringBuffer();
			while ((gbData = gbReader.readLine()) != null) {
				gbBuffer.append(gbData + System.getProperty("line.separator"));
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

		return gbBuffer != null ? gbBuffer.toString() : "Error downloading Green Button from server\n";
		// TODO: more intelligent error message

	}
	protected void onProgressUpdate(Integer... progress) {
		listener_.onProgressUpdate(progress);
    }

    protected void onPostExecute(String result) 
    {
    	listener_.onPostExecute(result);
    }
    protected void onPreExecute() {
    	listener_.onPreExecute();
    }
}
