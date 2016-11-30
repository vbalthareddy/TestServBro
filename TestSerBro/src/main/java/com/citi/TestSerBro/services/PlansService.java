package com.citi.TestSerBro.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.citi.TestSerBro.model.Plans;

@Service
public class PlansService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public int insertPlan(Plans p){
		
		  String sql = "insert into plans(Name,id,service,description,volumeSize,cost) values(?,?,?,?,?,?);";
		  return jdbcTemplate.update(sql, p.getName(),p.getId(),p.getService(),p.getDescription(),p.getVolumeSize(),p.getCost());
	  }
	
	public int deletePlan(String id){
		
		  String sql = "delete from plans where id=?";
		  return jdbcTemplate.update(sql, id);
	  }
	
	public int deletePlan(String plan,String service){
		
		  String sql = "delete from plans where id=? and service=?";
		  return jdbcTemplate.update(sql, plan,service);
	  }
	public int updatePlan(Plans p){
		
		 String sql = "update plans set Name=?,description=?,service=?,volumeSize=?,cost=? where id=?";
		  return jdbcTemplate.update(sql, p.getName(),p.getDescription(),p.getService(),p.getVolumeSize(),p.getCost(),p.getId());
	  }

	public List<Plans> getAllPlans(String service){
	    return jdbcTemplate.query("SELECT * FROM plans where service='"+service+"'", new RowMapper<Plans>(){

	      public Plans mapRow(ResultSet rs, int arg1) throws SQLException {
	    	  Plans p = new Plans();
	        p.setName(rs.getString("name"));
	        p.setId(rs.getString("id"));
	        p.setService(rs.getString("service"));
	        p.setDescription(rs.getString("description"));
	        p.setVolumeSize(rs.getInt("volumeSize"));
	        p.setCost(rs.getInt("cost"));
	        return p;
	      }
	    });
	  }
}

