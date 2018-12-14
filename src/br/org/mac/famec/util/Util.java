package br.org.mac.famec.util;

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

}
