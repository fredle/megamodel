package com.matterhorn.megamodel.api.webservices;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.TimeSeriesItem;
import com.matterhorn.megamodel.domain.enums.DataItemType;

@WebService
public interface MegaModelMistMethodsWS {

		@WebMethod
		long returnOne();

		@WebMethod
		DataSet getPublishedDataSet(Long id);

		@WebMethod
		List<TimeSeriesItem> getDataItems(Long dataSetId, Set<String> identCodes, DataItemType type ,Date from, Date to);

}
