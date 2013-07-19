package com.matterhorn.megamodel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.matterhorn.megamodel.entities.enums.FinancialDataType;

@Entity
public class FinancialItemDefinition {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String identCode;
	private Boolean useMultiplier;
	private Boolean percentage;
	private Boolean currencySpecific;
	private Boolean active;


	private String name;
	private String shortName;
	private String description;

	private FinancialDataType dataType;
	@ManyToOne
	private FinancialItemDefinition parentDefinition;

	private String bbFieldCode;
	private String bbFieldCodeBankingModel;
	private String bestCode;
	private String priceMultipleHeading;
	private String yieldHeading;
	
	
	@Override
	public String toString() {
		if(getIdentCode()!=null){
			return "{IdentCode:" + getIdentCode() + "}";
		}
		return super.toString();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getUseMultiplier() {
		return useMultiplier;
	}
	public void setUseMultiplier(Boolean useMultiplier) {
		this.useMultiplier = useMultiplier;
	}
	public Boolean getPercentage() {
		return percentage;
	}
	public void setPercentage(Boolean percentage) {
		this.percentage = percentage;
	}
	public Boolean getCurrencySpecific() {
		return currencySpecific;
	}
	public void setCurrencySpecific(Boolean currencySpecific) {
		this.currencySpecific = currencySpecific;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public FinancialDataType getDataType() {
		return dataType;
	}
	public void setDataType(FinancialDataType dataType) {
		this.dataType = dataType;
	}
	public FinancialItemDefinition getParentDefinition() {
		return parentDefinition;
	}
	public void setParentDefinition(FinancialItemDefinition parentDefinition) {
		this.parentDefinition = parentDefinition;
	}
	public String getIdentCode() {
		return identCode;
	}
	public void setIdentCode(String identCode) {
		this.identCode = identCode;
	}
	public String getBbFieldCode() {
		return bbFieldCode;
	}
	public void setBbFieldCode(String bbFieldCode) {
		this.bbFieldCode = bbFieldCode;
	}
	public String getBbFieldCodeBankingModel() {
		return bbFieldCodeBankingModel;
	}
	public void setBbFieldCodeBankingModel(String bbFieldCodeBankingModel) {
		this.bbFieldCodeBankingModel = bbFieldCodeBankingModel;
	}
	public String getBestCode() {
		return bestCode;
	}
	public void setBestCode(String bestCode) {
		this.bestCode = bestCode;
	}
	public String getPriceMultipleHeading() {
		return priceMultipleHeading;
	}
	public void setPriceMultipleHeading(String priceMultipleHeading) {
		this.priceMultipleHeading = priceMultipleHeading;
	}
	public String getYieldHeading() {
		return yieldHeading;
	}
	public void setYieldHeading(String yieldHeading) {
		this.yieldHeading = yieldHeading;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}


}
