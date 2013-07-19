package com.matterhorn.megamodel.domain.transport;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.matterhorn.megamodel.domain.DataSet;

@XmlRootElement(name = "MainDataSetTO")
public class MainDataSetTO {

	public long id;
	public String dataSetType;
	public String primaryModelDefinitionCode;
	public String currency;
	public String lastUpdateStaff;
	
	public long units;
	public boolean reportConsolidated;
	public Date lastHistoricalData;
	public Date lastUpdate;
	public String preferredInterimType;
	


	public static MainDataSetTO marshal(DataSet mds)
	{
		if(mds == null) {
			return null;
		}
		MainDataSetTO to = new MainDataSetTO();
		to.dataSetType = (mds.getType() == null) ? null : mds.getType().name();
		to.currency = (mds.getCurrency() == null) ? null : mds.getCurrency().getId();
		to.lastUpdateStaff = (mds.getUpdateStaff() == null) ? null : mds.getUpdateStaff();
		to.id = mds.getId();
		to.primaryModelDefinitionCode = (mds.getModelDefinition().getCode() == null) ? null : mds.getModelDefinition().getCode();
		to.units = (mds.getUnits() == null) ? 1L : mds.getUnits();
		to.reportConsolidated = (mds.getReportConsolidated() == null) ? true : mds.getReportConsolidated();
		to.lastHistoricalData = mds.getDateOfLastHistoricalData();
		to.preferredInterimType = (mds.getPreferredInterimType() == null) ? null : mds.getPreferredInterimType().toString();
		to.lastUpdate = mds.getLastUpdate();
		
		return to;
	}
}
