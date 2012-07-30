package org.hexq.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * @author hxq8176
 *
 */
public class DateUtils {
    
	private static final String PATTERN_DATE = "yyyy-MM-dd";
    private static final String PATTERN_TIME = "yyyy-MM-dd hh:mm:ss";
    
    private static final SimpleDateFormat sdfDate = new SimpleDateFormat(PATTERN_DATE);
    private static final SimpleDateFormat sdfTime = new SimpleDateFormat(PATTERN_TIME);
    private static SimpleDateFormat sdf = null;
    
    public static String formate(Date date, String pattern){
    	if(date == null){
    		return null;
    	}
    	String pat = pattern;
    	if(pattern == null || pattern.trim().equals("")){
    		pat = PATTERN_DATE;
    	}
    	
    	if(pat.equals(PATTERN_DATE)){
    		return sdfDate.format(date);
    	} else if(pat.equals(PATTERN_TIME)){
    		return sdfTime.format(date);
    	} else {
    		sdf = new SimpleDateFormat(pat);
    		return sdf.format(date);
    	}
    }
    
    public static String formatDate(Date date){
    	return formate(date, PATTERN_DATE);
    }
   
    public static String formatTime(Date date){
    	return formate(date, PATTERN_TIME);
    }
    
}
