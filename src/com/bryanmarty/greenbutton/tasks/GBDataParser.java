package com.bryanmarty.greenbutton.tasks;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.bryanmarty.greenbutton.data.IntervalReading;

public class GBDataParser extends DefaultHandler {

	LinkedList<IntervalReading> _intervalReadingList;
	IntervalReading _intervalReading;
	
	String _gbData;
	SAXParser _sp;
	
	boolean _isIntervalReading;
	boolean _isCost;
	boolean _isTimePeriod;
	boolean _isDuration;
	boolean _isStart;
	boolean _isValue;
	
	public GBDataParser(String gbData)
	{
		_gbData = gbData;	
		_intervalReadingList = new LinkedList<IntervalReading>();
	}
	
	public List<IntervalReading> parseGBData()
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try 
		{
			_sp = spf.newSAXParser();
			_sp.parse(new InputSource(new StringReader(_gbData)), this);
		
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
		return _intervalReadingList;
	}
	
	public void startElement(String uri, String localName, String qName, 
            Attributes attributes) throws SAXException 
    {
		if (qName.equalsIgnoreCase("IntervalReading"))
		{
			_isIntervalReading = true;
			_intervalReading = new IntervalReading();
		}
		
		else if (qName.equalsIgnoreCase("cost"))
		{
			_isCost = true;
		}
		else if (qName.equalsIgnoreCase("duration"))
		{
			_isDuration = true;
		}
		else if (qName.equalsIgnoreCase("start"))
		{
			_isStart = true;
		}
		else if (qName.equalsIgnoreCase("value"))
		{
			_isValue = true;
		}
    }
	
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		if(qName.equalsIgnoreCase("IntervalReading"))
		{
			_intervalReadingList.add(_intervalReading);
			_isIntervalReading = false;
		}
	}
	
	public void characters(char ch[], int start, int length) throws SAXException 
	{
		 if(_isCost)
		 {
			 // TODO: set cost once attribute is in IntervalReading obj
		 }
		 else if (_isDuration)
		 {
			 // in seconds
			 _intervalReading.setDuration(Integer.parseInt(new String(ch, start, length)));			 
		 }
		 else if (_isStart)
		 {
			 // unix time stamp - must multiply by 1000L because Date obj
			 // ctor expects ms not s. 
			 _intervalReading.setStartTime(new Date(Integer.parseInt(new String(ch, start, length)) * 1000L)); 
		 }
		 else if (_isValue)
		 {
			 _intervalReading.setValue(Integer.parseInt(new String(ch, start, length)));
		 }
	}
	
}
