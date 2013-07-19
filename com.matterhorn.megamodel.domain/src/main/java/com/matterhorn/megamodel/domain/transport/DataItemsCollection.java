package com.matterhorn.megamodel.domain.transport;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.SingleDataItem;
import com.matterhorn.megamodel.domain.TimeSeriesItem;


@XmlRootElement(name = "DataItemsCollection")
public class DataItemsCollection {

	private DataItem[] dataItemArray;


	@XmlElements({ @XmlElement(name = "dic_SingleDataItem", type = SingleDataItem.class),
			@XmlElement(name = "dic_TimeSeriesItem", type = TimeSeriesItem.class) })
	public DataItem[] getDataItemArray()
	{
		return dataItemArray;
	}


	public void setDataItemArray(DataItem[] dataItems)
	{
		this.dataItemArray = dataItems;
	}


	@SuppressWarnings({ "unchecked" })
	public List<DataItem> getDataItemArrayAsList()
	{
		return (List<DataItem>) ((dataItemArray == null || dataItemArray.length == 0) ? Collections.emptyList() : Arrays.asList(dataItemArray));
	}


	public void setDataItemArrayFromList(List<DataItem> dataItems)
	{
		dataItemArray = dataItems.toArray(new DataItem[dataItems.size()]);
	}
}
