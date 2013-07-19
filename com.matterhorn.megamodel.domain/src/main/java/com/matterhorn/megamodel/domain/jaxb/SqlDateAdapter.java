package com.matterhorn.megamodel.domain.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author caspar
 */
public class SqlDateAdapter extends XmlAdapter<java.util.Date, java.sql.Date> {

	@Override
	public java.sql.Date unmarshal(java.util.Date date) throws Exception
	{
		return (date == null) ? null : new java.sql.Date(date.getTime());
	}


	@Override
	public java.util.Date marshal(java.sql.Date date) throws Exception
	{
		return (date == null) ? null : new java.util.Date(date.getTime());
	}

}