package com.bryanmarty.greenbutton.database;

import java.util.concurrent.Callable;

import android.database.sqlite.SQLiteDatabase;

public abstract class TrackRequest<E> implements Callable<E> {
	private SQLiteDatabase database;

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}
}
