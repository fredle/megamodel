package com.matterhorn.megamodel.service.api;

import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.TimeSeriesItem;

public final class MergeUtil {
	
	private MergeUtil() {}
	
	
	public static void merge(DataItem di, DataItem old)
	{
		old.setBolValue(di.getBolValue());
		old.setDateValue(di.getDateValue());
		old.setDblValue(di.getDblValue());
		old.setStrValue(di.getStrValue());
		old.setComments(di.getComments());
		if(old instanceof TimeSeriesItem) {
			TimeSeriesItem oldTi = (TimeSeriesItem) old;
			oldTi.setDate(((TimeSeriesItem)di).getDate());
		}
	}
	

}
