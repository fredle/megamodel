package com.matterhorn.megamodel.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.matterhorn.megamodel.domain.jaxb.SqlDateAdapter;
import com.matterhorn.megamodel.entities.enums.EquityTransactionType;
import com.matterhorn.megamodel.entities.enums.EquityType;

@Entity
public class EquityObject extends DataObject {
	private EquityTransactionType transactionType;
	private Date issueDate;
	private Double principal;
	private Double sharePrice;
	private Double parValue;
	private Double cupon;
	private Double strike;
	private Double convertibleAfterYear;
	private Double maturity;
	private Boolean convert;
	private Boolean retainedEarnings;
	
	private EquityType equityType;
	
	public EquityTransactionType getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(EquityTransactionType transactionType) {
		this.transactionType = transactionType;
	}
		
	@XmlJavaTypeAdapter(SqlDateAdapter.class)
	public Date getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	
	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}


	public Double getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(Double sharePrice) {
		this.sharePrice = sharePrice;
	}


	public Double getParValue() {
		return parValue;
	}

	public void setParValue(Double parValue) {
		this.parValue = parValue;
	}


	public Double getCupon() {
		return cupon;
	}

	public void setCupon(Double cupon) {
		this.cupon = cupon;
	}


	public Double getStrike() {
		return strike;
	}

	public void setStrike(Double strike) {
		this.strike = strike;
	}


	public Double getConvertibleAfterYear() {
		return convertibleAfterYear;
	}

	public void setConvertibleAfterYear(Double convertibleAfterYear) {
		this.convertibleAfterYear = convertibleAfterYear;
	}


	public Double getMaturity() {
		return maturity;
	}

	public void setMaturity(Double maturity) {
		this.maturity = maturity;
	}


	public Boolean getConvert() {
		return convert;
	}

	public void setConvert(Boolean convert) {
		this.convert = convert;
	}

	public Boolean getRetainedEarnings() {
		return retainedEarnings;
	}

	public void setRetainedEarnings(Boolean retainedEarnings) {
		this.retainedEarnings = retainedEarnings;
	}

	public EquityType getEquityType() {
		return equityType;
	}

	public void setEquityType(EquityType equityType) {
		this.equityType = equityType;
	}
}
