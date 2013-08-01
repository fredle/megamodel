package com.matterhorn.megamodel.api;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.matterhorn.megamodel.domain.Company;
import com.matterhorn.megamodel.domain.Currency;
import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.TimeSeriesItem;
import com.matterhorn.megamodel.domain.enums.DataItemType;

public interface MegaModelDao {

	List<DataSet> loadDataSetsForCompany(int limit, Long companyId);

	List<Company> loadMatchingCompanies(String term, int limit);

	List<Currency> getCurrencies();
	
	List<DataItem> getDataItems(DataSet dataSet);

	DataSet getPublishedDataSet(Long companyId);

	List<TimeSeriesItem> getDataItems(Long dataSetId, Set<String> identCodes, DataItemType type, Date from, Date to);
	
//	List<DataObject> getDataObjects(DataSet dataSet);
	
}
