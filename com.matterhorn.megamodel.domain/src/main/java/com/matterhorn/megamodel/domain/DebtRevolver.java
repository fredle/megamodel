package com.matterhorn.megamodel.domain;

import javax.persistence.Entity;

@Entity
public class DebtRevolver extends DataObject {
	private Boolean revolver;
	private Double minimumCash;
	private Double interestRate;
		
	public Boolean getRevolver() {
		return revolver;
	}
	public void setRevolver(Boolean revolver) {
		this.revolver = revolver;
	}
	public Double getMinimumCash() {
		return minimumCash;
	}
	public void setMinimumCash(Double minimumCash) {
		this.minimumCash = minimumCash;
	}
	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	
}
