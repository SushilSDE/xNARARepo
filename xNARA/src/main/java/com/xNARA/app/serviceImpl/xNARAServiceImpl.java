package com.xNARA.app.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xNARA.app.service.xNARAService;

@Service
public class xNARAServiceImpl implements xNARAService 
{
	@Autowired
	RestTemplate restTemplate;
	
	Logger log = LoggerFactory.getLogger(xNARAServiceImpl.class);
	
	public JSONArray callEndpoint(String url) 
	{
		ResponseEntity<String> response;
		JSONParser parse;
		JSONArray obj;
		try 
		{
			response= restTemplate.getForEntity(url, String.class);
			String entityBody=response.getBody().toString();
			log.info("Str>>"+response.getBody().toString());
			parse = new JSONParser();
			obj = (JSONArray)parse.parse(entityBody);
			log.info("ArrayObj O/p>>"+obj);
			return obj;
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject getCombinedJson(JSONArray json1, JSONArray json2, long customer_id) 
	{
		int id=0;
		JSONObject obj = new JSONObject();
		JSONArray arr1 = new JSONArray();
		JSONArray arr2 = new JSONArray();
		JSONArray pack1 = new JSONArray();
		JSONArray pack2 = new JSONArray();
		try 
		{
		for(int t=0;t<json1.size();t++)
		{
			JSONObject var = (JSONObject)json1.get(t);//customer_id
			long custmId = (long) var.get("customer_id");
			if(custmId==customer_id) //Id is found
			{
			  id = Integer.parseInt((String)var.get("id"));
			  arr1= (JSONArray) var.get("pack_data");
			  log.info("pack1>>"+arr1+" Size>>"+arr1.size());
			  for(int ptr=0;ptr<arr1.size();ptr++)
			  {
				  log.info("pack1.get(ptr)>>"+arr1.get(ptr));
				  JSONObject pack1Obj = (JSONObject)arr1.get(ptr);
				  String ingred= (String)pack1Obj.get("ingredient");
				  String quan= String.valueOf(pack1Obj.get("quantity"));
				  String unit= String.valueOf(pack1Obj.get("unit"));
				  quan+=unit;//Merging  
				  ingred+=" "+quan;//making format according to “{ingredient} {quantity}{unit}”
				  pack1.add(ingred);
			  }	
			  log.info("pack1>>"+pack1);
			break;
			}
		}
		
		for(int t=0;t<json2.size();t++)
		{
			JSONObject var = (JSONObject)json2.get(t);//customer_id
			long custmId = (long) var.get("customer_id");
			if(custmId==customer_id) //Id is found
			{
			  id = Integer.parseInt((String)var.get("id"));
			  arr2= (JSONArray) var.get("pack_data");
			  for(int ptr=0;ptr<arr2.size();ptr++)
			  {
				  JSONObject pack2Obj = (JSONObject)arr2.get(ptr);
				  String ingred= (String)pack2Obj.get("ingredient");
				  String quan= String.valueOf(pack2Obj.get("quantity"));
				  String unit= String.valueOf(pack2Obj.get("unit"));
				  quan+=unit;//Merging  
				  ingred+=" "+quan;//making format according to “{ingredient} {quantity}{unit}”
				  pack2.add(ingred);
			  }	
			  log.info("pack2>>"+pack2);
			}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		obj.put("id", id);
		obj.put("customer_id", customer_id);
		obj.put("pack1", pack1);
		obj.put("pack2", pack2);
		log.info("obj>>"+obj);
		return obj;
	}
}
