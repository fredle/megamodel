package com.matterhorn.megamodel.entities.enums;

import java.sql.Date;

import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.TimeSeriesItem;

public class DataItemKey {
	public DataItemKey(DataSet ds, DataItem dataItem) {
		this.datasetId = ds.getId();
		this.definitionId = dataItem.getDefinition().getIdentCode();
		if(dataItem instanceof TimeSeriesItem){
			this.type = 1;
			if(((TimeSeriesItem) dataItem).getTimeSeriesType()==null){
				throw new RuntimeException("TimeSeriesType cannot be null");
			}
			this.timeSeriesType = ((TimeSeriesItem) dataItem).getTimeSeriesType().ordinal();
			if( ((TimeSeriesItem) dataItem).getDate()==null){
				throw new RuntimeException("Date Value cannot be null");
			}
			this.date = ((TimeSeriesItem) dataItem).getDate();

		} else {
			this.type = 0;
			this.timeSeriesType = -1;
		}
		this.dataType = dataItem.getDataType();
	}

	private Long datasetId;
	private String definitionId;
	private Integer type;
	private DataItemType dataType;
	private Date date;
	private Integer timeSeriesType;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result
				+ ((datasetId == null) ? 0 : datasetId.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((definitionId == null) ? 0 : definitionId.hashCode());
		result = prime * result
				+ ((timeSeriesType == null) ? 0 : timeSeriesType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		DataItemKey other = (DataItemKey) obj;
		if (dataType != other.dataType)
			return false;
		if (datasetId == null) {
			if (other.datasetId != null)
				return false;
		} else if (!datasetId.equals(other.datasetId))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (definitionId == null) {
			if (other.definitionId != null)
				return false;
		} else if (!definitionId.equals(other.definitionId))
			return false;
		if (timeSeriesType == null) {
			if (other.timeSeriesType != null)
				return false;
		} else if (!timeSeriesType.equals(other.timeSeriesType))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}
