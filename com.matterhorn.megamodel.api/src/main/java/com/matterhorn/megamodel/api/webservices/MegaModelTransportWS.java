package com.matterhorn.megamodel.api.webservices;

import static javax.jws.WebParam.Mode.IN;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.matterhorn.megamodel.api.transport.CompanyTO;
import com.matterhorn.megamodel.api.transport.DataItemsCollection;
import com.matterhorn.megamodel.api.transport.FinancialItemDefinitionCollection;
import com.matterhorn.megamodel.api.transport.Pagination;
import com.matterhorn.megamodel.api.transport.UploadCargo;
import com.matterhorn.megamodel.api.transport.UploadResp;
import com.matterhorn.megamodel.domain.DataSet;

@WebService
public interface MegaModelTransportWS {

	@WebMethod
	long countDataItems(@WebParam(name = "dataSetIdForItems", mode = IN) long dataSetId);
	
	
	@WebMethod
	DataSet downloadMainDataSet(@WebParam(name = "mainDataSetId", mode = IN) long id);
	
	
	@WebMethod
	DataItemsCollection downloadDataItems(
			@WebParam(name = "dataSetIdForItems", mode = IN) long dataSetId,
			@WebParam(name = "paginationForItems", mode = IN) Pagination Pager);


	@WebMethod
	UploadResp upload(@WebParam(name = "cargo", mode = IN) UploadCargo cargo);

	
	@WebMethod
	long countFinancialItemDefinitionNotUsedInCurrentDataSet(
			@WebParam(name = "mainDataSetId", mode = IN) long dataSetId);

	
	@WebMethod
	FinancialItemDefinitionCollection downloadFinancialItemDefinitionsNotUsedInCurrentDataSet(
			@WebParam(name = "mainDataSetId", mode = IN) long dataSetId,
			@WebParam(name = "pagination", mode = IN) Pagination idPager);

	
	@WebMethod
	boolean LoginPing();
	
	@WebMethod
	public CompanyTO[] searchCompanies(
			@WebParam(name="term", mode=IN) String term, 
			@WebParam(name="limit", mode=IN) int limit);

}
