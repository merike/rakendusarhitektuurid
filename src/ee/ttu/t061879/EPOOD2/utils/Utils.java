package ee.ttu.t061879.EPOOD2.utils;

import java.util.Calendar;

public class Utils {
	public static String isoDate(String date) throws Exception{
		try{
			Calendar c = Calendar.getInstance();
			c.setLenient(false);
			c.set(Integer.parseInt(date.substring(6, 10)),
					Integer.parseInt(date.substring(3, 5)), 
					Integer.parseInt(date.substring(0, 2)));
			return date.substring(6, 10) + "-" + date.substring(3, 5) + "-" + date.substring(0, 2);
		}
		catch (Exception e) {
			throw new Exception();
		}
	}
	
	public static String localDate(String isoDate){
		return isoDate.substring(8, 10) + "." + isoDate.substring(5, 7) + "." + isoDate.substring(0, 4);
	}
	
	public static double number(String s) throws NumberFormatException{
		return Double.parseDouble(s.replace(" ", "").replace(",", "."));
	}
	
	public static String number(double d){
		return String.format("%1$2.2f", (double)d);
	}
	
	public static String formError(String error){
		return "<span class='form_error'>" + error + "</span>";
	}
}
