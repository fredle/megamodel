package com.matterhorn.megamodel.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Currency {
	
	
	@Id
	private String id;
	
	private String name;

	@Override
	public String toString() {
		return id;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getFxId() {
		return id;
	}

	public void setFxId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}