package com.matterhorn.megamodel.domain.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FieldCommentAdapter extends XmlAdapter<FieldComments, String[][]> {

	@Override
	public String[][] unmarshal(FieldComments obj) throws Exception
	{
		return new String[][]{obj.fieldNames, obj.comments};
	}


	@Override
	public FieldComments marshal(String[][] array) throws Exception
	{
		return (array == null) ? null : new FieldComments(array[0], array[1]);
	}

}
