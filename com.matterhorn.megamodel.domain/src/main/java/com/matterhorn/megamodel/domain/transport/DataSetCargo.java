package com.matterhorn.megamodel.domain.transport;

import javax.xml.bind.annotation.XmlRootElement;

import com.matterhorn.megamodel.domain.DataSet;

/**
 * @author caspar
 */
@XmlRootElement(name = "DataSetCargo")
public class DataSetCargo {

	public DataSet mainDataSet;
	
}
