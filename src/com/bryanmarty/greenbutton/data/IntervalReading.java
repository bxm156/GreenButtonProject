package com.bryanmarty.greenbutton.data;

import java.util.Date;

public class IntervalReading {
	
	private int duration; //Seconds
	private Date startTime;
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	private int value;
	
	
}
