package com.matterhorn.megamodel.webservice.api;

import static javax.jws.WebParam.Mode.IN;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.transport.CompanyTO;
import com.matterhorn.megamodel.domain.transport.DataItemsCollection;
import com.matterhorn.megamodel.domain.transport.DataObjectsCollection;
import com.matterhorn.megamodel.domain.transport.FinancialItemDefinitionCollection;
import com.matterhorn.megamodel.domain.transport.Pagination;
import com.matterhorn.megamodel.domain.transport.UploadCargo;
import com.matterhorn.megamodel.domain.transport.UploadResp;

//@SOAPBinding(style = RPC, parameterStyle = BARE, use = LITERAL)

@WebService
public interface MegaModelTransportWS {

//	String ENDPOINT_INTERFACE  =  "com.matterhorn.mist.ejb.sb.ws.MegaModelTransportWS";
	
	@WebMethod
	long countDataItems(@WebParam(name = "dataSetIdForItems", mode = IN) long dataSetId);
	
	
	@WebMethod
	DataSet downloadMainDataSet(@WebParam(name = "mainDataSetId", mode = IN) long id);
	
	
	@WebMethod
	DataItemsCollection downloadDataItems(
			@WebParam(name = "dataSetIdForItems", mode = IN) long dataSetId,
			@WebParam(name = "paginationForItems", mode = IN) Pagination Pager);


	@WebMethod
	long countDataObjects(@WebParam(name = "dataSetIdForObjects", mode = IN) long dataSetId);
	

	@WebMethod
	DataObjectsCollection downloadDataObjects(
			@WebParam(name = "dataSetIdForObjects", mode = IN) long dataSetId,
			@WebParam(name = "paginationForObjects", mode = IN) Pagination idPager);

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
