package com.matterhorn.megamodel.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.matterhorn.megamodel.domain.jaxb.SqlDateAdapter;

@Entity
public class Company {
	
	
	@Id
	private Long id;
	
	private String name;
	private String stockCode;
	private String isin;
	private String summary;
	private Date fiscalYearEnd;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@XmlJavaTypeAdapter(SqlDateAdapter.class)
	public Date getFiscalYearEnd() {
		return fiscalYearEnd;
	}

	public void setFiscalYearEnd(Date fiscalYearEnd) {
		this.fiscalYearEnd = fiscalYearEnd;
	}

}