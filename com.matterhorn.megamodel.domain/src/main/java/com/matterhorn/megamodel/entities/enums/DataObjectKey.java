package com.matterhorn.megamodel.entities.enums;

import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.FinancialItemDefinition;



public class DataObjectKey {

	public DataObjectKey(DataSet mds, FinancialItemDefinition definition) {
		this.dataSetId = mds.getId();
		this.definition = definition.getIdentCode();
	}
	private Long dataSetId;
	private String definition;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataSetId == null) ? 0 : dataSetId.hashCode());
		result = prime * result
				+ ((definition == null) ? 0 : definition.hashCode());
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
		DataObjectKey other = (DataObjectKey) obj;
		if (dataSetId == null) {
			if (other.dataSetId != null)
				return false;
		} else if (!dataSetId.equals(other.dataSetId))
			return false;
		if (definition == null) {
			if (other.definition != null)
				return false;
		} else if (!definition.equals(other.definition))
			return false;
		return true;
	}
	
	
}
