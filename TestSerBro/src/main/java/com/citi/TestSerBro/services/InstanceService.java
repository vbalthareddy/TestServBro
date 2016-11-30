package com.citi.TestSerBro.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.citi.TestSerBro.model.Instance;

@Service
public class InstanceService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int insert(Instance m){		 
		  String sql = "insert into Instance(instance,planId,userName,password,url) values(?,?,?,?,?);";
		  
		  return jdbcTemplate.update(sql, m.getInstance(),m.getPlanId(),m.getUserName(),m.getPassword(),m.getUrl());
	  }
	public int update(Instance m){
		
		  String sql = "update Instance set planId=?,userName=?,password=?,url=? where instance=?;";
		  return jdbcTemplate.update(sql, m.getPlanId(),m.getUserName(),m.getPassword(),m.getUrl(),m.getInstance());
	  }
	public Instance get(int instance){
		
		List<Instance> list= jdbcTemplate.query("SELECT * FROM Instance where instance="+instance, new RowMapper<Instance>(){
			
			public Instance mapRow(ResultSet rs, int arg1) throws SQLException {
		    	  Instance m = new Instance(rs.getInt("instance"),rs.getString("planId"), rs.getString("userName"), rs.getString("password"), rs.getString("url"));
		        return m;
		      }
		    });
		if(list!=null && list.size()>1)
			return list.get(0);
		else
			return null;
	  }
	public int delete(int instance){
		
		String sql = "delete from Instance  where instance=?;";
		  return jdbcTemplate.update(sql, instance);
	  }
	

}
