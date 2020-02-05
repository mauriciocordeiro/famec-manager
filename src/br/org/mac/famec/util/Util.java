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

        	token = new StringTokenizer(d, "-");
        	int ano = Integer.parseInt(token.nextToken());
        	int mes = Integer.parseInt(token.nextToken());
        	int dia = Integer.parseInt(token.nextToken());

	        GregorianCalendar date = new GregorianCalendar(ano, mes-1, dia, hora, min, sec);
	        
	        return date;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	public static String format(GregorianCalendar current, String mask) {
		SimpleDateFormat formatter = new SimpleDateFormat(mask);
		Date date = current.getTime();
		return formatter.format(date);
	}
	
	public static String leadingZero(int num, int length) {
		String format = String.format("%%0%dd", length);
		String result = String.format(format, num);
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(format(new GregorianCalendar(), "dd 'de' MMMM 'de' yyyy"));
	}

}
