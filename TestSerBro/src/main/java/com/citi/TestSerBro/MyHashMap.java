package com.citi.TestSerBro;

import java.io.Serializable;

public class MyHashMap implements Serializable {
	
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; 
	

    private Node table[] = new Node[(int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY)];
    
    int TABLE_ELEMENTS = 1;
    int CURRENT_TABLE_SIZE = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    int SIZE=0;
   

    private class Node implements Serializable {
         private String key;
         private String value;
         private int hash;
         private Node next=null;

          Node(String key, String value,int hash) {
                this.key = key;
                this.value = value;
                this.hash=hash;
          }

          public String getValue() {
                return value;
          }

          public void setValue(String value) {
                this.value = value;
          }

          public String getKey() {
                return key;
          }
          
          public void setKey(String key) {
              this.key = key;
          }

			public int getHash() {
				return hash;
			}
	
			public void setHash(int hash) {
				this.hash = hash;
			}
			
			public Node getNext() {
				return next;
			}
	
			public void setNext(Node next) {
				this.next = next;
			}

			@Override
			public String toString() {
				return "Node [key=" + key + ", value=" + value + ", hash=" + hash + ", next=" + next + "]";
			}
          
    }

    public Node get(String key) {
    	
    	  int h;
          int hash =(key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
          
          Node temp=null;
          for(Node n:table)
          {
        	  if(n!=null && n.getHash()==hash){
        		  temp=findNode(n,key);break;
        	  }
          }
        	  return temp;
    }
    private Node findNode(Node n,String key)
    {
    	if(n!=null)
    	{
			  if(n.getKey().equals(key)){
				  return n;
			  }
			  else
			  {
				return findNode(n.getNext(),key);  
			  }
    	}
    		return null;
    }

    public boolean put(String key, String value) {
    	
    	int h;
    	 Node temp=null;
    	 
        int hash =(key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        
        for(Node n:table)
        {
        	if(n!=null && n.getHash()==hash){
        		while(!n.getKey().equals(key) && n.next!=null)
            	{
            		n=n.next;
            	}
        		
        		if(n.getKey().equals(key))
        		{
        			//update value if both key and hash are equals
        			n.setValue(value);
        			return false;
        		}
        		
            	Node newNode=new Node(key,value,hash);
            	SIZE++;
            	n.setNext(newNode);
            	return true;
      	  }
        }
        Node newNode=new Node(key,value,hash);
        SIZE++;
        
        if(TABLE_ELEMENTS<=CURRENT_TABLE_SIZE){
        	table[TABLE_ELEMENTS-1]=newNode;
        	TABLE_ELEMENTS++;
        	return true;
        }
        else
        {
        	System.out.println("increase size"+key);
        	
        	//increase table capacity
        	CURRENT_TABLE_SIZE=(int)(CURRENT_TABLE_SIZE +(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY));
        	Node[] tempTable=table;
        	System.out.println("test CURRENT_TABLE_SIZE"+CURRENT_TABLE_SIZE);
        	 table = new Node[CURRENT_TABLE_SIZE];
        	 System.arraycopy(tempTable, 0, table, 0, TABLE_ELEMENTS-1);
        	 table[TABLE_ELEMENTS-1]=newNode;
        	 TABLE_ELEMENTS++;        	
        	 return true;
        }
       
    }
    public String toString()
    {
    	String map=null;
    	for(Node n:table)
    		map+=n;
    	return map;
    	
    }
    public int size()
    {
    	return SIZE;
    }
    
    public int capacity()
    {
    	return CURRENT_TABLE_SIZE;
    }
    
}

