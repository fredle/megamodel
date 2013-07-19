package com.matterhorn.megamodel.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.matterhorn.megamodel.domain.jaxb.SqlDateAdapter;
import com.matterhorn.megamodel.entities.enums.DataItemType;

@Entity
public class ObjectTimeSeriesItem {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date date;

	private DataItemType dataType;
	private String comments;

	private Double dblValue;
	private Date dateValue;
	private String strValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@XmlJavaTypeAdapter(SqlDateAdapter.class)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public DataItemType getDataType() {
		return dataType;
	}
	public void setDataType(DataItemType dataType) {
		this.dataType = dataType;
	}

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	public Double getDblValue() {
		return dblValue;
	}
	public void setDblValue(Double dblValue) {
		this.dblValue = dblValue;
	}
	
	@XmlJavaTypeAdapter(SqlDateAdapter.class)
	public Date getDateValue() {
		return dateValue;
	}
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	public String getStrValue() {
		return strValue;
	}
	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	
}
