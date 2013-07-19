package com.matterhorn.megamodel.service.api;

import java.util.List;

import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataObject;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.FinancialItemDefinition;
import com.matterhorn.megamodel.domain.transport.Pagination;

public interface MegaModelDownloadService {

	long countDataItems(long dataSetId);
	
	DataSet downloadMainDataSet(long id);

	List<DataItem> downloadDataItems(long dataSetId, Pagination pager);

	long countDataObjects(long dataSetId);
	
	List<DataObject> downloadDataObjects(long dataSetId, Pagination pager);

	long countFinancialItemDefinitionNotUsedInCurrentDataSet(long dataSetId);

	List<FinancialItemDefinition> downloadFinancialItemDefinitionsNotUsedInCurrentDataSet(long dataSetId, Pagination pager);

	
}
