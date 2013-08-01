package com.matterhorn.megamodel.api.transport;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UploadCargo")
public class UploadCargo {

	public long dataSetId;
	public List<Long> deleteDataItemIds;
	public DataItemsCollection dataItems;
	
	public boolean publishOutput;
	public Integer fromYear;
	public Integer toYear;

	public String username;
}
