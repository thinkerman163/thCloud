package com.glodon.water.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DataUtil {
	
	
    public static Double getRoundingData( Double value,int newScale) {
    	
        return getRoundingData( value, newScale,BigDecimal.ROUND_HALF_UP);
   }
   public static Double getRoundingData( Double value,int newScale, int roundingMode) {
  	 BigDecimal bg;
  	 if(value!=null)
  	 {
       bg = new BigDecimal(value);
       value = bg.setScale(newScale,roundingMode).doubleValue();
  	 }
       return value;
  }  	
	
	public static void main(String[] args) throws ParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse("3088-05-01");
	    System.out.println(sdf.format(parse));
    }
	
}