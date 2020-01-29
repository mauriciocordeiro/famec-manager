package br.org.mac.famec.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.json.JSONObject;

import sol.dao.ResultSetMap;

public class Util {
	
	public static String rsmToJSON(ResultSetMap rsm) {
		try {
			if(rsm == null)
				rsm = new ResultSetMap();
			
			JSONObject json = new JSONObject();
			json.put("pointer", rsm.getPointer());
			json.put("lines", rsm.getLines());
			String jsonStr = json.toString();
			return jsonStr;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static GregorianCalendar angularDateToCalendar(String data) {
		try {
	        if(data==null || data.trim().equals(""))
	        	return null;
	        StringTokenizer token = new StringTokenizer(data, "T");
	        String d = token.nextToken(), h = "";
	        int hora =0, min = 0, sec = 0, millisec=0;
//	        if(token.hasMoreTokens())	{
//	        	h	=	token.nextToken();
//				StringTokenizer token2	= new StringTokenizer(h, ":");
//				hora =	Integer.parseInt(token2.nextToken().trim());
//				min  =	Integer.parseInt(token2.nextToken().trim());
//				String token3 = token2.hasMoreTokens() ? token2.nextToken() : null;
//				sec  =	token3==null ? 0 : Integer.parseInt(token3.trim());
////				token3 = token2.hasMoreTokens() ? token2.nextToken() : null;
////				millisec  =	token3==null ? 0 : Integer.parseInt(token3.trim());
//			}
        	token = new StringTokenizer(d, "-");
        	int ano = Integer.parseInt(token.nextToken());
        	int mes = Integer.parseInt(token.nextToken());
        	int dia = Integer.parseInt(token.nextToken());

	        GregorianCalendar date = new GregorianCalendar(ano, mes-1, dia, hora, min, sec);
	        //date.set(Calendar.MILLISECOND, millisec);
	        return date;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	public static String format(GregorianCalendar date, String mask) {
		SimpleDateFormat formatter = new SimpleDateFormat(mask);
		Date d = date.getTime();
		return formatter.format(d);
	}
	
	public static String leadingZero(int num, int length) {
		String format = String.format("%%0%dd", length);
		String result = String.format(format, num);
		return result;
	}

}
