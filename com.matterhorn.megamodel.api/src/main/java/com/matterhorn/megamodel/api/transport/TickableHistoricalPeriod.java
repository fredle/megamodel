package com.matterhorn.megamodel.api.transport;

import java.sql.Date;

import com.matterhorn.megamodel.domain.enums.TimeSeriesType;

public class TickableHistoricalPeriod {

	private TimeSeriesType periodType;
	
	private Date periodDate;
	
	private Boolean hasMatterhornData;
	
	private Boolean hasBloombegData;

	private Boolean ticked;
	

	public TimeSeriesType getPeriodType() {
		return periodType;
	}

	public void setPeriodType(TimeSeriesType periodType) {
		this.periodType = periodType;
	}

	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}

	public Boolean getHasMatterhornData() {
		return hasMatterhornData;
	}

	public void setHasMatterhornData(Boolean hasMatterhornData) {
		this.hasMatterhornData = hasMatterhornData;
	}

	public Boolean getHasBloombegData() {
		return hasBloombegData;
	}

	public void setHasBloombegData(Boolean hasBloombegData) {
		this.hasBloombegData = hasBloombegData;
	}
	
	public Boolean getTicked() {
		return ticked;
	}

	public void setTicked(Boolean ticked) {
		this.ticked = ticked;
	}
	
	
}