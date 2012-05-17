package com.bryanmarty.greenbutton.database;

import java.io.File;
import java.io.IOException;
import com.bryanmarty.greenbutton.DebugSettings;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "greenbutton.db";
	private static final String DATABASE_PATH = "/data/data/com.bryanmarty.greenbutton/databases/";
	private static final String databasePath = DATABASE_PATH + DATABASE_NAME;
	
	
	@SuppressWarnings("unused")
	private Context context_;
	private SQLiteDatabase db_;

	public DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		context_ = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();
		
		if (DebugSettings.DEBUG && DebugSettings.ALWAYSNEWDATABASE) {
			File f = new File(databasePath);
			f.delete();
			dbExist = f.exists();
		}
		
		if (!dbExist) {
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			SQLiteDatabase db_Read; 

		
			db_Read = getWritableDatabase();
		
			if(db_Read != null) {
				db_Read.close();
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			checkDB = SQLiteDatabase.openDatabase(databasePath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			String sql = "CREATE TABLE gbdata (start integer UNIQUE ON CONFLICT REPLACE, duration integer NOT NULL, value blob NOT NULL, cost integer NULL);";
			db.execSQL(sql);
		} catch (SQLException e) {
			Log.e("SQLite","Failed to create database");
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public synchronized SQLiteDatabase openDataBase() throws SQLException {
		try {
			// Open the database
			String myPath = DATABASE_PATH + DATABASE_NAME;
			db_ = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
			db_.setLockingEnabled(true);

		} catch (Exception e) {
			e.printStackTrace();
			db_ = null;
		}
		return db_;
	}
	
	public SQLiteDatabase getDatabase() {
		return db_;
	}

	@Override
	public synchronized void close() {
		try {
			if (db_ != null) {
				db_.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.close();
		}
	}
}
