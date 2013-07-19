package com.matterhorn.megamodel.domain.transport;

import javax.xml.bind.annotation.XmlRootElement;

import com.matterhorn.megamodel.domain.Company;

@XmlRootElement(name = "CompanyTO")
public class CompanyTO {

	public long id;
	public String bbCode;
	public String isin;
	public String name;
	public String summary;

	public MainDataSetTO[] mainDataSets; 


	public static CompanyTO marshal(Company company)
	{
		if(company == null) {
			return null;
		}
		CompanyTO companyTO = new CompanyTO();
		companyTO.id = company.getId();
		companyTO.bbCode = company.getStockCode();
		companyTO.isin = company.getIsin();
		companyTO.name = company.getName();
		companyTO.summary = company.getSummary();
		
		return companyTO;
	}
}
