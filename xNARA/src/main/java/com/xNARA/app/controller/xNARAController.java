package com.xNARA.app.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xNARA.app.model.RequestPayload;
import com.xNARA.app.service.xNARAService;

@RestController
@RequestMapping("/")
public class xNARAController 
{
	@Autowired
	xNARAService service;
	
	@Value("${url.path1}")
	String url1;
	@Value("${url.path2}")
	String url2; 
	
	@PostMapping("accept/")
	public ResponseEntity<Object> combinedApi(@RequestBody RequestPayload request)
	{
		long customer_id = request.getCustomerId();
		JSONArray json1 = service.callEndpoint(url1);
		JSONArray json2 = service.callEndpoint(url2);
		JSONObject result = service.getCombinedJson(json1,json2,customer_id);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
}
