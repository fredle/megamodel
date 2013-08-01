package com.matterhorn.megamodel.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum EquityTransactionType {				// In DB
	OrdinaryShares("Ordinary Shares"),			// 0
	RightsIssue("Rights Issue"),				// 1
	BonusIssue("Bonus Issue"),	 				// 2
	ConvertibleNotes("Convertible Notes"),		// 3
	ConvertiblePreferred("Convertible Preferred"),// 4
	Warrants("Warrants");						// 5
	
	private String description;
	private EquityTransactionType (String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return description;
	}
	
	
	private static Map<String, EquityTransactionType> fromStr;
	static {
		fromStr = new HashMap<String, EquityTransactionType>();
		for (EquityTransactionType type : EquityTransactionType.values()) {
			fromStr.put(type.toString().toUpperCase(), type);
		}
	}

	public static EquityTransactionType fromString(String s) {
		if (s == null)
			return null;
		return fromStr.get(s.toUpperCase());
	}

}
