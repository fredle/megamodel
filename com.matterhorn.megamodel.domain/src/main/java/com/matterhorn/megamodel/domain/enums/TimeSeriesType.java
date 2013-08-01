package com.matterhorn.megamodel.domain.enums;

public enum TimeSeriesType {
	Annual("ANN"),
	SemiAnnual,
	Quarterly("INT"),
	Monthly;
	
	
	private String reutersCode;

	private TimeSeriesType () {
		this.reutersCode = null;
	}

	private TimeSeriesType (String reutersCode) {
		this.reutersCode = reutersCode;
	}

	public String getReutersCode() {
		return reutersCode;
	}

	public static TimeSeriesType fromInt(int i) {
		TimeSeriesType[] tsTypes = values();
		if (i >= 0 && tsTypes.length > i) {
			return tsTypes[i];
		}
		return null;
	}
}
