package com.matterhorn.megamodel.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.matterhorn.megamodel.entities.enums.TimeSeriesType;

@Entity
public class ObjectTimeSeries {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private TimeSeriesType timeSeriesType;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ObjectTimeSeriesItem> items;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public TimeSeriesType getTimeSeriesType() {
		return timeSeriesType;
	}
	public void setTimeSeriesType(TimeSeriesType timeSeriesType) {
		this.timeSeriesType = timeSeriesType;
	}

	public List<ObjectTimeSeriesItem> getItems() {
		return items;
	}
	public void setItems(List<ObjectTimeSeriesItem> items) {
		this.items = items;
	}
	
}
