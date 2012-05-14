package com.bryanmarty.greenbutton.database;


import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.bryanmarty.greenbutton.data.IntervalReading;
import com.bryanmarty.greenbutton.database.DatabaseManager;
import com.bryanmarty.greenbutton.database.TrackRequest;

import android.content.Context;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class TrackManager {
	
	private static boolean initialized = false;
	private static DatabaseManager dbManager;
	private static Context context_;
	private static ThreadPoolExecutor threadPool_;
	private static BlockingQueue<Runnable> queue_;
	
	public synchronized static boolean initialize(Context context) {
		if(initialized) {
			return initialized;
		}
		context_ = context;
		try {
			queue_ = new LinkedBlockingQueue<Runnable>();
			threadPool_ = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, queue_);
			dbManager = new DatabaseManager(context_);
			dbManager.createDataBase();
			dbManager.openDataBase();
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
			initialized = false;
		}
		return initialized;
	}
	
	public synchronized static void shutdown() {
		if(!initialized) {
			return;
		}
		threadPool_.shutdown();
		try {
			threadPool_.awaitTermination(10L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dbManager.close();
		initialized = false;
	}
	
	public static Future<Boolean> addReadings(final LinkedList<IntervalReading> readings) {
		TrackRequest<Boolean> request = new TrackRequest<Boolean>() {
			
			@Override
			public Boolean call() throws Exception {
				boolean success = false;
				SQLiteDatabase db = getDatabase();
				
				if(db == null) {
					throw new SQLiteException("Database was null");
				}
				
				InsertHelper ihelp = new InsertHelper(db,"gbdata");
				
				try {
					db.beginTransaction();
				
					for (IntervalReading reading : readings) {
						ihelp.prepareForInsert();
						ihelp.bind(1, reading.getStartTime().getTime());
						ihelp.bind(2, reading.getDuration());
						ihelp.bind(3,reading.getValue());
						ihelp.bind(4,reading.getCost());
						ihelp.execute();
					}
					db.setTransactionSuccessful();
					success = true;
				} finally {
					db.endTransaction();
				}
				return success;
			}
		};
		request.setDatabase(dbManager.getDatabase());
		return threadPool_.submit(request);
	}
	
}
