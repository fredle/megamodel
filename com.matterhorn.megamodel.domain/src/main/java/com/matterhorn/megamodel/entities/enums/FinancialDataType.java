package com.matterhorn.megamodel.entities.enums;

import com.matterhorn.megamodel.domain.CapexObject;
import com.matterhorn.megamodel.domain.DataObject;
import com.matterhorn.megamodel.domain.DebtObject;
import com.matterhorn.megamodel.domain.DebtRevolver;
import com.matterhorn.megamodel.domain.EquityObject;


public enum FinancialDataType {
	
	TypeDouble("Number"),
	TypeDate("Date"),	
	TypeString("Text"),
	TypeBoolean("Boolean"),
	TypeDebtObject("Debt Object", DebtObject.class),
	TypeDebtRevolver("Debt Revolver", DebtRevolver.class),
	TypeEquity("Equity Object", EquityObject.class),
	TypeCapex("Capex Object", CapexObject.class);
	
	
	private String description;
	private Class<? extends DataObject> declaredType = null;
	
	
	private FinancialDataType (String description) {
		this.description = description;
	}
	
	
	private FinancialDataType (String description, Class<? extends DataObject> declaredType) {		
		this.description = description;
		this.declaredType = declaredType;
	}


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
	public boolean isObjectCompatible(Class<? extends DataObject> type) {
		return (declaredType != null && type != null && declaredType.isAssignableFrom(type));
	}
	
	
	public boolean isObject () {
		return (declaredType != null);
	}
}
