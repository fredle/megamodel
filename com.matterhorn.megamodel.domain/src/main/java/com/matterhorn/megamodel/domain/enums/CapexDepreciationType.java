package com.matterhorn.megamodel.domain.enums;

public enum CapexDepreciationType {

	Split,
	Full;	
	
	public static CapexDepreciationType fromString(String s) {
		for (CapexDepreciationType fdt : values()) {
			if(fdt.name().equals(s)) {
				return fdt;
			}
		}
		return null;
	}
}
