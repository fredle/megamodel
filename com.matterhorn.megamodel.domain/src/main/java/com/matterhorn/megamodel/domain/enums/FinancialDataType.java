package com.matterhorn.megamodel.domain.enums;



public enum FinancialDataType {
	
	TypeDouble("Number"),
	TypeDate("Date"),
	TypeString("Text"),
	TypeBoolean("Boolean");
	
	
	private String description;
//	private Class<? extends DataObject> declaredType = null;
	
	
	private FinancialDataType (String description) {
		this.description = description;
	}
	
	
//	private FinancialDataType (String description, Class<? extends DataObject> declaredType) {		
//		this.description = description;
//		this.declaredType = declaredType;
//	}


	public static FinancialDataType fromString(String s) {
		for (FinancialDataType fdt : values()) {
			if (fdt.description.equals(s)) {
				return fdt;
			}
		}
		return null;
	}
	

	@Override
	public String toString() {
		return description;
	}
	
	
	/**
	 * Has to be overridden for it to act as an object
	 * @return
	 */
//	public boolean isObjectCompatible(Class<? extends DataObject> type) {
//		return (declaredType != null && type != null && declaredType.isAssignableFrom(type));
//	}
	
	
	public boolean isObject () {
		return false;
	}
}
