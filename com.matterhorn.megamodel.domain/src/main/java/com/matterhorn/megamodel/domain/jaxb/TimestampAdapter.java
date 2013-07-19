package com.matterhorn.megamodel.domain.jaxb;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 * Mist contains loads of references to {@link java.sql.Timestamp} which isn't JAXB friendly,
 * as it doesn't have a zero arg constructor.
 * Rather than refactor all occurrences we can simply use an {@link javax.xml.bind.annotation.adapters.XmlAdapter}
 * 
 * @author caspar
 */
@XmlRootElement
@XmlType
public class TimestampAdapter extends XmlAdapter<Date, Timestamp> {

	@Override
	public Date marshal(Timestamp timestamp) throws Exception {
		return (timestamp == null) ? null : new Date(timestamp.getTime());
	}

	@Override
	public Timestamp unmarshal(Date date) throws Exception {
		return (date == null) ? null : new Timestamp(date.getTime());
	}
}
