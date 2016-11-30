package com.citi.TestSerBro.model;

public class Services {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bindable == null) ? 0 : bindable.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + maxdbpernode;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Services other = (Services) obj;
		if (bindable == null) {
			if (other.bindable != null)
				return false;
		} else if (!bindable.equals(other.bindable))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maxdbpernode != other.maxdbpernode)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	private String name;
	private String id;
	private String description;
	private int maxdbpernode;
	private String bindable;
	
	public Services(String name, String id, String description, int maxdbpernode, String bindable) {
		super();
		this.name = name;
		this.id = id;
		this.description = description;
		this.maxdbpernode = maxdbpernode;
		this.bindable = bindable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMaxdbpernode() {
		return maxdbpernode;
	}
	public void setMaxdbpernode(int maxdbpernode) {
		this.maxdbpernode = maxdbpernode;
	}
	public String getBindable() {
		return bindable;
	}
	public void setBindable(String bindable) {
		this.bindable = bindable;
	}
	
	 
}
