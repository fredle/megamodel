package com.matterhorn.megamodel.service.api;

import java.util.Collection;
import java.util.List;

import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataObject;
import com.matterhorn.megamodel.domain.DataSet;


public interface MegaModelRemote {
	public DataSet uploadDataSet(Long creator, DataSet ds,
			boolean publishOutput, Integer fromYear, Integer toYear, List<DataItem> itemsToPublish, Collection<DataObject> objectsToRemove, String[] updateLinks, String[] updateEntities);

}
