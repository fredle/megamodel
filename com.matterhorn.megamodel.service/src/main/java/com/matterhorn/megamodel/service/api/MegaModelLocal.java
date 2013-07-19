package com.matterhorn.megamodel.service.api;

import java.util.Collection;
import java.util.List;

import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataObject;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.TimeSeriesItem;
import com.matterhorn.megamodel.domain.transport.TickableHistoricalPeriod;
import com.matterhorn.megamodel.entities.enums.TimeSeriesType;


public interface MegaModelLocal {
	public DataSet uploadDataSet(Long creator, DataSet ds, boolean publishOutput, Integer fromYear,
			Integer toYear, List<DataItem> itemsToPublish, Collection<DataObject> objectsToRemove, String[] updateLinks,
			String[] updateEntities);

	public String copyTimeSeriesItemsFromBloomberg(java.sql.Date date, DataSet dataSet);

	public void deleteDataSet(long dsId);

	public void deleteDataSet(DataSet ds);

	public int copyRecentData(Long creator, DataSet toDataSet, DataSet fromDataSet,
			List<TickableHistoricalPeriod> tickablePeriods) throws DatasetDownloadException;

	public DataSet copyDataSet(Long creator, DataSet newDataSet, DataSet fromDataSet);

	public DataSet createNewDataSet(Long creator, DataSet newDataSet);

	public String createMatterhornTimeSeriesItems(List<TimeSeriesItem> tsis, DataSet dataSetDTO, java.sql.Date date);

	public ListMap2D<DataSet, TimeSeriesType, TickableHistoricalPeriod> getTickablePeriodsAndDataSet(DataSet toDataset);

}
