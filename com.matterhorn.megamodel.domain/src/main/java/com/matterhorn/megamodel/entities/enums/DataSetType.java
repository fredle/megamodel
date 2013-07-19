package com.matterhorn.megamodel.entities.enums;

public enum DataSetType {
	Matterhorn(true,false),
	IBES(false, true),
	WorldScope(false, false),
	Bloomberg(false, true),
	Reuters(false, false);	
	
	private boolean matterhorn;
	private boolean consensus;
	
	private DataSetType (boolean matterhorn, boolean consensus) {
		this.matterhorn = matterhorn;
		this.consensus = consensus;
	}
	
	public int getNumerical() {
		switch (this) {
			case Matterhorn:
				return 0;
			case WorldScope:
				return 1;			
			case IBES:
				return 2;
			case Bloomberg:
				return 3;
			default:
				return 0;
		}
	}
	
	public boolean isMatterhorn() {
		return matterhorn;
	}

	public boolean isConcencus() {
		return consensus;
	}

}
