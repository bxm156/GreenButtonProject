package com.bryanmarty.greenbutton.tasks;

public interface GBDataDownloaderListener {
	public void onPreExecute();
	public void onProgressUpdate(Integer... progress);
	public void onPostExecute(Integer result);
}
