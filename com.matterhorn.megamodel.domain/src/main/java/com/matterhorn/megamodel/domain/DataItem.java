package com.matterhorn.megamodel.domain;

import static javax.persistence.DiscriminatorType.INTEGER;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.matterhorn.megamodel.domain.enums.DataItemType;
import com.matterhorn.megamodel.domain.jaxb.SqlDateAdapter;

@DiscriminatorColumn(name = "type", discriminatorType = INTEGER)
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorValue("0")
@Entity
public abstract class DataItem {
	@Id
	@GeneratedValue(strategy = AUTO)
	private Long id;
	
	@ManyToOne(fetch = EAGER)
	private DataSet dataSet;

	@ManyToOne
	private FinancialItemDefinition definition;
	
	private DataItemType dataType;
	private String comments;

	private Double dblValue;
	private Date dateValue;
	private String strValue;
	private Boolean bolValue;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}

	public FinancialItemDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(FinancialItemDefinition definition) {
		this.definition = definition;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public DataItemType getDataType() {
		return dataType;
	}

	public void setDataType(DataItemType dataType) {
		this.dataType = dataType;
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

	public Boolean getBolValue() {
		return bolValue;
	}

	public void setBolValue(Boolean bolValue) {
		this.bolValue = bolValue;
	}

	@Override
	public String toString() {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return "{DataItem:(date:" + sdf.format(getDateValue()) + ";dataType:"+ getDataType() + ";ds:" + getDataSet().getId() + ";type:0)}";
		} catch (NullPointerException e){
			return super.toString();
		}
	}
}
