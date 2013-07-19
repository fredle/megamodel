package com.matterhorn.megamodel.domain.transport;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FieldComments")
public class FieldComments {

	public String[] fieldNames;
	public String[] comments;

	public FieldComments() {}
	
	public FieldComments(String[] fieldNames, String[] comments)
	{
		this.fieldNames = fieldNames;
		this.comments = comments;
	}
	
}
