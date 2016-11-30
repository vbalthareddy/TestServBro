package com.citi.TestSerBro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

import com.citi.TestSerBro.model.Instance;
import com.citi.TestSerBro.model.MyMap;
import com.citi.TestSerBro.services.InstanceService;
import com.citi.TestSerBro.services.MyMapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired
	MyMapService mapService;
	
	@Autowired
	InstanceService instanceService;
	
	@RequestMapping(value = "/put/{instance}/{key}/{value}",method = RequestMethod.GET)
	  @ResponseBody
	  public String put(@PathVariable("instance") String instance,
			  @PathVariable("key") String key,
			  @PathVariable("value") String value) throws Exception {
		
		if(instanceService.get(Integer.parseInt(instance))==null)
			return "Instance is not created";
		
	       MyMap data=mapService.getMap(Integer.parseInt(instance));
	       
	       if(data==null)
	       {
	    	   MyHashMap map=new MyHashMap();
	    	   map.put(key, value);
	           MyMap m=new MyMap(Integer.parseInt(instance),mapService.serialize(map));
	           if(mapService.insertMap(m) >0)
	        	   return "created hashmap and inserted record successfully ";
	           else
	        	   return "something went wrong while creating hashmap";
	       }
	       else
	       {
	    	   MyHashMap map=mapService.deSerialize(data.getData());	    			   
	    	   map.put(key, value);	           
	    	   if(mapService.updateMap(new MyMap(Integer.parseInt(instance),mapService.serialize(map)))  >0)
	    		   return "updated hashmap successfully";
	    	   else
	    		   return "something went wrong while updating hashmap";
	       }
	  }
	@RequestMapping(value = "/get/{instance}/{key}",method = RequestMethod.GET)
	 @ResponseBody
	public String get(@PathVariable("instance") String instance,
			  @PathVariable("key") String key)throws Exception
	{
		if(instanceService.get(Integer.parseInt(instance))==null)
			return "Instance is not created";
		
		MyMap data=mapService.getMap(Integer.parseInt(instance));
		 if(data==null)
			 return "No Map is available for this instance";
		 else
		 {
			 MyHashMap map=mapService.deSerialize(data.getData());
			 return "value: "+map.get(key)+" for key "+key;
		 }
	}
	@RequestMapping(value = "/getText",method = RequestMethod.GET)
	  @ResponseBody
	  public String getText() {
	       return "HELLO.............";   
	  }

	
}
