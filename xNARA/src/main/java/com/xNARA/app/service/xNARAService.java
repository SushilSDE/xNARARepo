package com.xNARA.app.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface xNARAService 
{
	public JSONArray callEndpoint(String url);

	public JSONObject getCombinedJson(JSONArray json1, JSONArray json2, long customer_id);
}
