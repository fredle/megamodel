package com.matterhorn.megamodel.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.matterhorn.megamodel.domain.jaxb.SqlDateAdapter;
import com.matterhorn.megamodel.domain.jaxb.TimestampAdapter;
import com.matterhorn.megamodel.entities.enums.DataSetType;
import com.matterhorn.megamodel.entities.enums.TimeSeriesType;

@Entity
public class DataSet {
	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Currency currency;
	
	private Long units;
	
	private Boolean reportConsolidated;
	
	
	private Company company;

	private Timestamp lastUpdate;
	
	private String updateStaff;
	
	private Date dateOfLastHistoricalData;
	
	private DataSetType type;

	@ManyToOne(fetch=FetchType.EAGER)
	private ModelDefinition modelDefinition;
	
	private TimeSeriesType preferredInterimType;
		
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public Long getUnits() {
		return units;
	}
	public void setUnits(Long units) {
		this.units = units;
	}

	
//	public List<DataItem> getDataItems() {
//		return dataItems;
//	}
//	public void setDataItems(List<DataItem> dataItems) {
//		this.dataItems = dataItems;
//	}
//
//	public List<DataObject> getDataObjects() {
//		return dataObjects;
//	}
//	public void setDataObjects(List<DataObject> dataObjects) {
//		this.dataObjects = dataObjects;
//	}

	public Boolean getReportConsolidated() {
		return reportConsolidated;
	}
	public void setReportConsolidated(Boolean reportConsolidated) {
		this.reportConsolidated = reportConsolidated;
	}

	
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}


	@XmlJavaTypeAdapter(TimestampAdapter.class)
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public DataSetType getType() {
		return type;
	}
	
	public void setType(DataSetType type) {
		this.type = type;
	}

	
	public String getUpdateStaff() {
		return updateStaff;
	}
	public void setUpdateStaff(String updateStaff) {
		this.updateStaff = updateStaff;
	}

	
	@XmlJavaTypeAdapter(SqlDateAdapter.class)
	public Date getDateOfLastHistoricalData() {
		return dateOfLastHistoricalData;
	}
	public void setDateOfLastHistoricalData(Date dateOfLastHistoricalData) {
		this.dateOfLastHistoricalData = dateOfLastHistoricalData;
	}
	
	public ModelDefinition getModelDefinition() {
		return modelDefinition;
	}
	
	public void setModelDefinition(
			ModelDefinition modelDefinition) {
		this.modelDefinition = modelDefinition;
	}

	public TimeSeriesType getPreferredInterimType() {
		return preferredInterimType;
	}
	
	public void setPreferredInterimType(TimeSeriesType preferredInterimType) {
		this.preferredInterimType = preferredInterimType;
	}

	
	@Override
	public String toString() {
		return (getCompany()!= null?getCompany():"No company") + " : " + (type!=null?type:"No type")
						+ " : " + (getCurrency()!=null?getCurrency():"No currency"); 
	}
}
