package com.bryanmarty.greenbutton.data;

import java.util.Date;

public class IntervalReading {
	
	private int duration; //Seconds
	private Date startTime;
	private int value;
	private int cost;
	
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
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	//	example cost: 	"842283" = $8.42, rounds up I believe
	public int getCost() {
		return cost;
	}

}
