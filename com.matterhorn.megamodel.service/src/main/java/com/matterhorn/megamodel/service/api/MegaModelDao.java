package com.matterhorn.megamodel.service.api;

import java.util.List;

import com.matterhorn.megamodel.domain.Company;
import com.matterhorn.megamodel.domain.Currency;
import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataObject;
import com.matterhorn.megamodel.domain.DataSet;

public interface MegaModelDao {

	List<DataSet> loadDataSetsForCompany(int limit, Long companyId);

	List<Company> loadMatchingCompanies(String term, int limit);

	List<Currency> getCurrencies();
	
	List<DataItem> getDataItems(DataSet dataSet);
	
	List<DataObject> getDataObjects(DataSet dataSet);
	
}
