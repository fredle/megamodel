package com.matterhorn.megamodel.api.transport;

import javax.xml.bind.annotation.XmlRootElement;

import com.matterhorn.megamodel.domain.DataSet;

@XmlRootElement(name = "DataSetCargo")
public class DataSetCargo {

	public DataSet mainDataSet;
	
}
