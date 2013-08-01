package com.matterhorn.megamodel.api;

import java.util.List;

import com.matterhorn.megamodel.api.transport.Pagination;
import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.FinancialItemDefinition;

public interface MegaModelDownloadService {

	long countDataItems(long dataSetId);
	
	DataSet downloadMainDataSet(long id);

	List<DataItem> downloadDataItems(long dataSetId, Pagination pager);

	long countFinancialItemDefinitionNotUsedInCurrentDataSet(long dataSetId);

	List<FinancialItemDefinition> downloadFinancialItemDefinitionsNotUsedInCurrentDataSet(long dataSetId, Pagination pager);

	
}
