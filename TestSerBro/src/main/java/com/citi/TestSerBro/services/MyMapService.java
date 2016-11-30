package com.citi.TestSerBro.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.citi.TestSerBro.MyHashMap;
import com.citi.TestSerBro.model.MyMap;
import com.citi.TestSerBro.model.Plans;

@Service
public class MyMapService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int insertMap(MyMap m){
		
		  String sql = "insert into mymap(instance,data) values(?,?);";
		  System.out.println(m.getData().length());
		  return jdbcTemplate.update(sql, m.getInstance(),m.getData());
	  }
	public int updateMap(MyMap m){
		
		  String sql = "update mymap set data=? where instance=?;";
		  return jdbcTemplate.update(sql, m.getData(),m.getInstance());
	  }
	public MyMap getMap(int instance){
		
		List<MyMap> list= jdbcTemplate.query("SELECT * FROM mymap where instance="+instance, new RowMapper<MyMap>(){
			
			public MyMap mapRow(ResultSet rs, int arg1) throws SQLException {
		    	  MyMap m = new MyMap(rs.getInt("instance"),rs.getString("data"));
		        return m;
		      }
		    });
		if(list!=null && list.size()>1)
			return list.get(0);
		else
			return null;
	  }
	
	public List<MyMap> getFullMap(){
			
			return jdbcTemplate.query("SELECT * FROM mymap", new RowMapper<MyMap>(){
	
				public MyMap mapRow(ResultSet rs, int arg1) throws SQLException {
			    	  MyMap m = new MyMap(rs.getInt("instance"),rs.getString("data"));
			        return m;
			      }
			    });
		  }
	public String serialize(MyHashMap map)throws Exception
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream( baos );
	    oos.writeObject( map );
	    oos.close();
	    return Base64.getEncoder().encodeToString(baos.toByteArray());
	}
	public  MyHashMap deSerialize(String data)throws Exception
	{
		byte [] byteMap = Base64.getDecoder().decode( data);
	    ObjectInputStream ois = new ObjectInputStream( 
	                                    new ByteArrayInputStream(  byteMap ) );
	    MyHashMap o  = (MyHashMap)ois.readObject();
	    ois.close();
	    return o;
	}
}
