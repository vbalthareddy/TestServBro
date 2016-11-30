package com.citi.TestSerBro.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.citi.TestSerBro.model.Instance;
import com.citi.TestSerBro.model.Plans;
import com.citi.TestSerBro.model.Services;

@Service
public class ServicesService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Services> getServices(){
	    return jdbcTemplate.query("SELECT * FROM Services", new RowMapper<Services>(){

	      public Services mapRow(ResultSet rs, int arg1) throws SQLException {
	    	  Services p = new Services(rs.getString("name"),rs.getString("id"), rs.getString("description"), rs.getInt("maxdbpernode"), rs.getString("bindable"));
	        return p;
	      }

	    });
	  }
	public int insert(Services m){		 
		  String sql = "insert into Services(name,id,description,maxdbpernode,bindable) values(?,?,?,?,?);";
		  
		  return jdbcTemplate.update(sql, m.getName(),m.getId(),m.getDescription(),m.getMaxdbpernode(),m.getBindable());
	  }
	public int update(Services m){
		
		  String sql = "update Instance set name=?,description=?,maxdbpernode=?,bindable=? where id=?;";
		  return jdbcTemplate.update(sql,m.getName(),m.getDescription(),m.getMaxdbpernode(),m.getBindable(),m.getId());
	  }
	public Services get(int id){
		
		List<Services> list= jdbcTemplate.query("SELECT * FROM Services where id="+id, new RowMapper<Services>(){
			
			public Services mapRow(ResultSet rs, int arg1) throws SQLException {
				
				Services m = new Services(rs.getString("name"),rs.getString("id"), rs.getString("description"), rs.getInt("maxdbpernode"), rs.getString("bindable"));
		        return m;
		      }
		    });
		if(list!=null && list.size()>1)
			return list.get(0);
		else
			return null;
	  }
	public int delete(int id){
		
		String sql = "delete from Services  where id=?;";
		  return jdbcTemplate.update(sql, id);
	  }
	
}
