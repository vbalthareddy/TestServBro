package com.citi.TestSerBro;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.citi.TestSerBro.model.Instance;
import com.citi.TestSerBro.model.Plans;
import com.citi.TestSerBro.model.ServPlans;
import com.citi.TestSerBro.model.Services;
import com.citi.TestSerBro.services.InstanceService;
import com.citi.TestSerBro.services.PlansService;
import com.citi.TestSerBro.services.ServicesService;

@RestController
public class TestBrokerController {
	
	@Autowired
	PlansService plansServices;
	
	@Autowired
	ServicesService servicesService;
	
	@Autowired
	InstanceService instanceService;
	
	 @RequestMapping(value = "/v2/catalog",method = RequestMethod.GET,produces="application/json")
	  @ResponseBody
	  public List<ServPlans> getPlans() {
		 List<Services> service=servicesService.getServices();
		 //List<Plans> allPlans=plans.getAllPlans();
		 List<ServPlans> servplans=null;
		 for(Services serv:service)
		 {
			 List<Plans> allPlans=plansServices.getAllPlans(serv.getId());
			 if(servplans==null)
				 servplans= new  ArrayList<ServPlans>();
			 ServPlans servplan=new ServPlans();
			 servplan.setServ(serv);
			 servplan.setPlans(allPlans);
			 servplans.add(servplan);
		 }
		 return servplans;
		 
	 }
	 @RequestMapping(value = "/v2/catalogOld",method = RequestMethod.GET,produces="application/json")
	  @ResponseBody
	  public String getCatalog() {
		 JSONParser parser = new JSONParser();
		 
	        try {
	 
	            Object obj = parser.parse(new FileReader("service-definition.json"));
	 
	            JSONObject jsonObject = (JSONObject) obj;          
	            return jsonObject.toJSONString();	 
	            
	  }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        	return null;
	        }
	   }
	 
	 
	 @RequestMapping(value = "/v2/service_instances/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<String> create(@PathVariable("id") String id, @RequestBody Services service) {

		
		 Services existing=servicesService.get(Integer.parseInt(id));
	        if (existing!=null) {
	            if (existing.equals(service)) {
	                return new ResponseEntity<>("{}", HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>("{}", HttpStatus.CONFLICT);
	            }
	        } else {
	        	servicesService.insert(service);
	            return new ResponseEntity<>("{}", HttpStatus.CREATED);
	        }
	    }
	 
	 
	 @RequestMapping(value = "/v2/service_instances/{serviceId}/service_bindings/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<Object> createBinding(@PathVariable("serviceId") String serviceId,
	                                                @PathVariable("id") String instanceId,
	                                                @RequestBody Instance instance) {
		 
		 Services existingServ=servicesService.get(Integer.parseInt(serviceId));
		 
	        if (existingServ==null) {
	            return new ResponseEntity<Object>("{\"description\":\"Service instance " + serviceId + " does not exist!\"", HttpStatus.BAD_REQUEST);
	        }
	        
			 Instance existing=instanceService.get(Integer.parseInt(instanceId));

	        if (existing!=null){
	            if (existing.equals(instance)) {
	                return new ResponseEntity<>("{}", HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>("{}", HttpStatus.CONFLICT);
	            }
	        }
	        else
	        {
	        	instance.setPassword("1234");
	        	instance.setPassword("abcd");
	        	instanceService.insert(instance);
	            return new ResponseEntity<Object>(wrapCredentials(instance), HttpStatus.CREATED);
	        }
	 }
	 
	 @RequestMapping(value = "/v2/service_instances/{service_id}/service_bindings/{plan_id}", method = RequestMethod.DELETE)
	    public ResponseEntity<String> deleteBinding(@PathVariable("service_id") String serviceId,@PathVariable("plan_id") String planId) {
		 
		 int delete =plansServices.deletePlan(planId,serviceId);
		 
		 if (delete>0) {	            
			 return new ResponseEntity<>("{}", HttpStatus.OK);
	        } else {
	        	return new ResponseEntity<>("{}", HttpStatus.GONE);
	        }
	    }
	 @RequestMapping(value = "/v2/service_instances/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<String> delete(@PathVariable("id") String id
	                                        ) {

		 if (servicesService.delete(Integer.parseInt(id))>0) {	            
			 return new ResponseEntity<>("{}", HttpStatus.OK);
	        } else {
	        	return new ResponseEntity<>("{}", HttpStatus.GONE);
	        }
	    }
	 private Map<String, Object> wrapCredentials(Instance instance) {
	        Map<String, Object> wrapper = new HashMap<>();
	        wrapper.put("Instance", instance);
	        return wrapper;
	    }
/*
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
	 @RequestMapping(value = "/v2/service_instances/{id}", method = RequestMethod.DELETE)
	    public HttpStatus delete(@PathVariable("id") String id) {
	        int delete = plansServices.deletePlan(id);

	        if (delete>0) {	            
	            return  HttpStatus.OK;
	        } else {
	            return HttpStatus.GONE;
	        }
	    }
	 
	
	 @RequestMapping(value = "/v2/service_instances", method = RequestMethod.POST,produces="application/json",consumes="application/json")
	    public HttpStatus create(@RequestBody Plans plan) {
	        
		 int delete = plansServices.insertPlan(plan);

	        if (delete>0) {	            
	            return  HttpStatus.OK;
	        } else {
	            return HttpStatus.GONE;
	        }
	    }
	 @RequestMapping(value = "/v2/service_instances", method = RequestMethod.PUT,produces="application/json",consumes="application/json")
	    public HttpStatus update(@RequestBody Plans plan) {
	        
		 int delete = plansServices.updatePlan(plan);

	        if (delete>0) {	            
	            return  HttpStatus.OK;
	        } else {
	            return HttpStatus.GONE;
	        }
	    }*/
}
