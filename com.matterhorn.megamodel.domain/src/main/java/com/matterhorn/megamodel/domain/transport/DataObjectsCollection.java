package com.matterhorn.megamodel.domain.transport;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.matterhorn.megamodel.domain.CapexObject;
import com.matterhorn.megamodel.domain.DataObject;
import com.matterhorn.megamodel.domain.DebtObject;
import com.matterhorn.megamodel.domain.DebtRevolver;
import com.matterhorn.megamodel.domain.EquityObject;


@XmlRootElement(name = "DataObjectsCollection")
public class DataObjectsCollection {

	private DataObject[] dataObjectArray;


	@XmlElements({ @XmlElement(name = "doc_CapexObject", type = CapexObject.class),
			@XmlElement(name = "doc_DebtObject", type = DebtObject.class),
			@XmlElement(name = "doc_DebtRevolver", type = DebtRevolver.class),
			@XmlElement(name = "doc_EquityObject", type = EquityObject.class) })
	public DataObject[] getDataObjectArray()
	{
		return dataObjectArray;
	}


	public void setDataObjectArray(DataObject[] dataObjects)
	{
		this.dataObjectArray = dataObjects;
	}


	@SuppressWarnings({"cast", "unchecked"})
	public List<DataObject> getDataObjectArrayAsList()
	{
		return (List<DataObject>) Arrays.asList(dataObjectArray);
	}


	public void setDataObjectArrayFromList(List<DataObject> dataObjects)
	{
		this.dataObjectArray = dataObjects.toArray(new DataObject[dataObjects.size()]);
	}

}
