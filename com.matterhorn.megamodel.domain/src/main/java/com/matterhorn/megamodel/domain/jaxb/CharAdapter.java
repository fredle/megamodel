package com.matterhorn.megamodel.domain.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class CharAdapter extends XmlAdapter<String, Character> {

	@Override
	public String marshal(Character c) throws Exception
	{
		return String.valueOf(c);
	}

	@Override
	public Character unmarshal(String s) throws Exception
	{
		if(s == null) {
			return null;
		}
		if(s.length() != 1) {
			throw new IllegalArgumentException(
					"Provided string \"" + s + "\" has invalid length of " + s.length() + " should be 1");
		}
		return s.charAt(0);
	}

}
