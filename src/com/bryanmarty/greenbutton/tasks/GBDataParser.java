package com.bryanmarty.greenbutton.tasks;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Date;
import java.util.LinkedList;
import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import com.bryanmarty.greenbutton.data.IntervalReading;

public class GBDataParser extends DefaultHandler {

	private LinkedList<IntervalReading> _intervalReadingList;
	private IntervalReading _intervalReading;
	
	private File _gbData;
	private SAXParser _sp;
	
	private boolean _isIntervalReading;
	private boolean _isCost;
	private boolean _isTimePeriod;
	private boolean _isDuration;
	private boolean _isStart;
	private boolean _isValue;
	
	private boolean _isDoneParsing;
	
	public GBDataParser(File tmp)
	{
		_gbData = tmp;	
		_intervalReadingList = new LinkedList<IntervalReading>();
		_isDoneParsing = false;
	}
	
	public void parseGBData()
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try 
		{
			_sp = spf.newSAXParser();
			_sp.parse(new InputSource(new FileReader(_gbData)), this);
		
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
		
	public boolean isDoneParsing()
	{
		return _isDoneParsing;
	}
	
	public LinkedList<IntervalReading> getIntervalReadingList()
	{
		if (_isDoneParsing)
			return _intervalReadingList;
		else
			return null;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
    {
		if (qName.equalsIgnoreCase("IntervalReading"))
		{
			_isIntervalReading = true;
			_intervalReading = new IntervalReading();
		}
		
		else if (qName.equalsIgnoreCase("cost") && _isIntervalReading)
		{
			_isCost = true;
		}
		else if (qName.equalsIgnoreCase("duration") && _isIntervalReading)
		{
			_isDuration = true;
		}
		else if (qName.equalsIgnoreCase("start") && _isIntervalReading)
		{
			_isStart = true;
		}
		else if (qName.equalsIgnoreCase("value") && _isIntervalReading)
		{
			_isValue = true;
		}
    }
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		if(qName.equalsIgnoreCase("IntervalReading"))
		{
			_intervalReadingList.add(_intervalReading);
			_isIntervalReading = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException 
	{
		 if(_isCost)
		 {
			 // TODO: set cost once attribute is in IntervalReading obj
			 _isCost = false;
		 }
		 else if (_isDuration)
		 {
			 // in seconds
			 _intervalReading.setDuration(Integer.parseInt(new String(ch, start, length)));			 
			 //System.out.println("Duration: " + new String(ch, start, length));
			 _isDuration = false;
		 }
		 else if (_isStart)
		 {
			 // unix time stamp - must multiply by 1000L because Date obj
			 // ctor expects ms not s.
			 long timeStamp = 1000L * Long.parseLong(new String(ch, start, length));
			 Date d = new Date(timeStamp);
			 _intervalReading.setStartTime(d);
			 
			 //System.out.println("Start Time: " + new String(ch, start, length));
			 _isStart = false;
		 }
		 else if (_isValue)
		 {
			 _intervalReading.setValue(Integer.parseInt(new String(ch, start, length)));
			 //System.out.println("Value: " + new String(ch, start, length));
			 _isValue = false;
		 }
	}	

	public void endDocument()
	{
		_isDoneParsing = true;
	}

}
