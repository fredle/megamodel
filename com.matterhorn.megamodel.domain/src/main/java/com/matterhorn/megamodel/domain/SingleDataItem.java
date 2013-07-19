package com.matterhorn.megamodel.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("2")
@Entity
public class SingleDataItem extends DataItem {
	
	@Override
	public String toString() {
		String val = "{";
		if(getDataType()!=null){
			val +=  "dataType:"+ getDataType() + ";";
		}
		if(getDataSet()!=null&&getDataSet().getId()!=null){
			val +=  "ds:" + getDataSet().getId() + ";";
		}
		if(getDblValue()!=null){
			val +=  "DblValue:"+ getDblValue() +";";
		}
		if(getStrValue()!=null){
			val +=  "StrValue:"+ getStrValue() +";";
		}
		if(getDateValue()!=null){
			val +=  "DateValue:"+ getDateValue() +";";
		}
		if(getDefinition()!=null){
			val +=  "Definition:"+ getDefinition() +";";
		}
		return val + "}";
	}
}
