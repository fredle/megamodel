package com.matterhorn.megamodel.domain;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.matterhorn.megamodel.domain.enums.TimeSeriesType;
import com.matterhorn.megamodel.domain.jaxb.SqlDateAdapter;

@DiscriminatorValue("1")
@Entity
public class TimeSeriesItem extends DataItem {
	
	private Date date;
	private TimeSeriesType timeSeriesType;
	
	@XmlJavaTypeAdapter(SqlDateAdapter.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TimeSeriesType getTimeSeriesType() {
		return timeSeriesType;
	}

	public void setTimeSeriesType(TimeSeriesType timeSeriesType) {
		this.timeSeriesType = timeSeriesType;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String val = "{";
		if(getDate()!=null){
			val += "DataItem:(date:" + sdf.format(getDate()) + ";";
		}
		if(getDataType()!=null){
			val +=  "dataType:"+ getDataType() + ";";
		}
		if(getDefinition()!=null){
			val +=  "definition:"+ getDefinition() + ";";
		}
		if(getDataSet()!=null&&getDataSet().getId()!=null){
			val +=  "ds:" + getDataSet().getId() + ";";
		}
		if(getDataType()!=null){
			val +=  "TimeSeriesType:"+ getTimeSeriesType() +";";
		}
		if(getDblValue()!=null){
			val +=  "DblValue:"+ getDblValue() +";";
		}
		if(getStrValue()!=null){
			val +=  "StrValue:"+ getStrValue() +";";
		}
		if(getDateValue()!=null){
			val +=  "DateValue:"+  sdf.format(getDateValue()) +";";
		}
		
		
		return val + "}";
	}

}
