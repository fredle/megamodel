package com.matterhorn.megamodel.domain.transport;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UploadCargo")
public class UploadCargo {

	public long dataSetId;
	public List<Long> deleteDataItemIds;
	public List<Long> deleteDataObjectIds;
	public DataItemsCollection dataItems;
	public DataObjectsCollection dataObjects;
	
	public boolean publishOutput;
	public Integer fromYear;
	public Integer toYear;

	public String username;
}
