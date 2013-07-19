package com.matterhorn.megamodel.domain;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.matterhorn.megamodel.domain.jaxb.SqlDateAdapter;


@Entity
public class DebtObject extends DataObject {



	private Date dateOfIssue;

	@ManyToOne
	private Currency currency;
	
	private Double percentageInCurrency;
	private Double principal;
	private Boolean floating;
	private Double interestRate;	
	private Double timeToMaturity;
	private Double sinkingFundYears;
	private Double sinkingFundPercent;



	@ManyToOne(cascade = CascadeType.ALL)
	private ObjectTimeSeries amortization;
	

	@XmlJavaTypeAdapter(SqlDateAdapter.class)
	public Date getDateOfIssue() {
		return dateOfIssue;
	}


	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public Double getPercentageInCurrency() {
		return percentageInCurrency;
	}
	public void setPercentageInCurrency(Double percentageInCurrency) {
		this.percentageInCurrency = percentageInCurrency;
	}

	public Double getPrincipal() {
		return principal;
	}


	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Boolean getFloating() {
		return floating;
	}


	public void setFloating(Boolean floating) {
		this.floating = floating;
	}

	public Double getInterestRate() {
		return interestRate;
	}


	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Double getTimeToMaturity() {
		return timeToMaturity;
	}


	public void setTimeToMaturity(Double timeToMaturity) {
		this.timeToMaturity = timeToMaturity;
	}

	public Double getSinkingFundYears() {
		return sinkingFundYears;
	}


	public void setSinkingFundYears(Double sinkingFundYears) {
		this.sinkingFundYears = sinkingFundYears;
	}

	public Double getSinkingFundPercent() {
		return sinkingFundPercent;
	}


	public void setSinkingFundPercent(Double sinkingFundPercent) {
		this.sinkingFundPercent = sinkingFundPercent;
	}

	public ObjectTimeSeries getAmortization() {
		return amortization;
	}
	public void setAmortization(ObjectTimeSeries amortization) {
		this.amortization = amortization;
	}

}
