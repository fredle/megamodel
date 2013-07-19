package com.matterhorn.megamodel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.matterhorn.megamodel.entities.enums.CapexDepreciationMethod;
import com.matterhorn.megamodel.entities.enums.CapexDepreciationType;

@Entity
public class CapexObject extends DataObject {
	
	private CapexDepreciationMethod depreciationMethod;
	private CapexDepreciationType depreciationType;
	private Double residualValue;
	private Double yearsRemaining;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private ObjectTimeSeries capexAddition;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private ObjectTimeSeries constructionInProgress;
	

	public CapexDepreciationMethod getDepreciationMethod() {
		return depreciationMethod;
	}
	public void setDepreciationMethod(CapexDepreciationMethod depreciationMethod) {
		this.depreciationMethod = depreciationMethod;
	}

	public CapexDepreciationType getDepreciationType() {
		return depreciationType;
	}
	public void setDepreciationType(CapexDepreciationType depreciationType) {
		this.depreciationType = depreciationType;
	}
	

	public Double getResidualValue() {
		return residualValue;
	}
	public void setResidualValue(Double residualValue) {
		this.residualValue = residualValue;
	}

	public Double getYearsRemaining() {
		return yearsRemaining;
	}
	public void setYearsRemaining(Double yearsRemaining) {
		this.yearsRemaining = yearsRemaining;
	}


	public ObjectTimeSeries getCapexAddition() {
		return capexAddition;
	}
	public void setCapexAddition(ObjectTimeSeries capexAddition) {
		this.capexAddition = capexAddition;
	}
	
	public ObjectTimeSeries getConstructionInProgress() {
		return constructionInProgress;
	}
	public void setConstructionInProgress(ObjectTimeSeries constructionInProgress) {
		this.constructionInProgress = constructionInProgress;
	}
	
	
}
