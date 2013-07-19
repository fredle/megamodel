package com.matterhorn.megamodel.entities.enums;



public enum CapexDepreciationMethod {	

	StraightLine(1, "Straight Line Depreciation"),
	SumOfYears(2, "Sum of Years Depreciation"),	
	ConstantPercentage(3, "Constant % Percentage Depreciation");
	
	private String strDesc;
	private int id;
	

	public int getId() {
		return id;
	}


	private CapexDepreciationMethod(int id, String desc) {
		this.id = id;
		this.strDesc = desc;
	}


	@Override
	public String toString() {
		return strDesc;
	}
	

	public static CapexDepreciationMethod getById(Number id) {
		int searchId = id.intValue();
		for (CapexDepreciationMethod cdm : values()) {
			if (cdm.id == searchId) {
				return cdm;
			}
		}
		return null;
	}

}
