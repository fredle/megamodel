package com.matterhorn.megamodel.webservice.imp;

import static com.matterhorn.megamodel.webservice.api.WsContextLogTracer.traceWsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.matterhorn.megamodel.domain.Company;
import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataObject;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.FinancialItemDefinition;
import com.matterhorn.megamodel.domain.transport.CompanyTO;
import com.matterhorn.megamodel.domain.transport.DataItemsCollection;
import com.matterhorn.megamodel.domain.transport.DataObjectsCollection;
import com.matterhorn.megamodel.domain.transport.FinancialItemDefinitionCollection;
import com.matterhorn.megamodel.domain.transport.MainDataSetTO;
import com.matterhorn.megamodel.domain.transport.Pagination;
import com.matterhorn.megamodel.domain.transport.UploadCargo;
import com.matterhorn.megamodel.domain.transport.UploadResp;
import com.matterhorn.megamodel.service.api.MegaModelDao;
import com.matterhorn.megamodel.service.api.MegaModelDownloadService;
import com.matterhorn.megamodel.service.api.MegaModelUploadService;
import com.matterhorn.megamodel.webservice.api.MegaModelTransportWS;

@WebService(targetNamespace = "http://megamodel.matterhorninvestment.com/megamodel", 
	portName="MegaModelPort",
	serviceName="MegaModelService", 
	endpointInterface="com.matterhorn.megamodel.webservice.api.MegaModelTransportWS")
public class MegaModelTransportWSBean implements MegaModelTransportWS {

	public MegaModelTransportWSBean() {
		megaModelDao = getBundleContext().getService(getBundleContext().getServiceReference(MegaModelDao.class));
		downloadService = getBundleContext().getService(getBundleContext().getServiceReference(MegaModelDownloadService.class));
		uploadService = getBundleContext().getService(getBundleContext().getServiceReference(MegaModelUploadService.class));
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		LOG.info("xxxxxx" + bundle.getLocation());
	}
	
	private static final Logger LOG  = Logger.getLogger(MegaModelTransportWSBean.class.getCanonicalName());
	
	private MegaModelDownloadService downloadService;

	private MegaModelUploadService uploadService;
	
	private MegaModelDao megaModelDao;

	@Resource 
	WebServiceContext wsContext;

	@Override
	public DataSet downloadMainDataSet(long id)
	{
		traceWsContext(wsContext, LOG);
		DataSet mds = downloadService.downloadMainDataSet(id);
		return mds;
	}
	

	@Override
	public long countDataItems(long dataSetId)
	{
		traceWsContext(wsContext, LOG);
		return downloadService.countDataItems(dataSetId);
	}


	@Override
	public DataItemsCollection downloadDataItems(long dataSetId, Pagination pagination)
	{
		traceWsContext(wsContext, LOG);
		List<DataItem> data = downloadService.downloadDataItems(dataSetId, pagination);
		//LazyInitializationHelper.initializeDataItems(data); 
		DataItemsCollection items = new DataItemsCollection();
		items.setDataItemArrayFromList(data);
		return items;
	}


	@Override
	public long countDataObjects(long dataSetId)
	{
		traceWsContext(wsContext, LOG);
		return downloadService.countDataObjects(dataSetId);
	}

	
	@Override
	public DataObjectsCollection downloadDataObjects(long dataSetId, Pagination pagination)
	{
		traceWsContext(wsContext, LOG);
		List<DataObject> data = downloadService.downloadDataObjects(dataSetId, pagination);
		//LazyInitializationHelper.initializeDataObjects(data);
		DataObjectsCollection objects = new DataObjectsCollection();
		objects.setDataObjectArray(data.toArray(new DataObject[data.size()]));
		return objects;
	}


	@Override
	public UploadResp upload(UploadCargo cargo)
	{
		traceWsContext(wsContext, LOG);
		return uploadService.upload(cargo);
	}


	@Override
	public long countFinancialItemDefinitionNotUsedInCurrentDataSet(long dataSetId)
	{
		traceWsContext(wsContext, LOG);
		if(downloadService==null){
			LOG.info("null downloadService");
		}

		return downloadService.countFinancialItemDefinitionNotUsedInCurrentDataSet(dataSetId);
	}


	private BundleContext getBundleContext() {
		return FrameworkUtil.getBundle(this.getClass()).getBundleContext();
	}





	@Override
	public FinancialItemDefinitionCollection downloadFinancialItemDefinitionsNotUsedInCurrentDataSet(long dataSetId, Pagination pager)
	{
		traceWsContext(wsContext, LOG);
		FinancialItemDefinitionCollection wsWrapped = new FinancialItemDefinitionCollection();
		List<FinancialItemDefinition> fids = downloadService.downloadFinancialItemDefinitionsNotUsedInCurrentDataSet(dataSetId, pager);
		wsWrapped.setDefinitions(fids);
		return wsWrapped;
	}
	
	
	@Override
	public boolean LoginPing()
	{
		traceWsContext(wsContext, LOG);
		return true;
	}
	
	
	@Override
	public CompanyTO[] searchCompanies(String term, int limit)
	{
				
		List<Company> companies = loadMatchingCompanies(term, limit);
		List<CompanyTO> ctos = new ArrayList<CompanyTO>();
		
		for(int i = 0; i < companies.size(); i++) {
			Company company = companies.get(i);
			CompanyTO to = CompanyTO.marshal(company);
			ctos.add(to);
			List<DataSet> mainDataSets = loadDataSetsForCompany(limit, to.id);
			to.mainDataSets = new MainDataSetTO[mainDataSets.size()];
			for(int j = 0; j < to.mainDataSets.length; j++) {
				to.mainDataSets[j] = MainDataSetTO.marshal(mainDataSets.get(j));
			}
		}
		LOG.info("Returning " + ctos.size() + " companies");
		return ctos.toArray(new CompanyTO[ctos.size()]);
	}

	private List<DataSet> loadDataSetsForCompany(int limit, Long companyId)
	{
		traceWsContext(wsContext, LOG);
		return megaModelDao.loadDataSetsForCompany(limit, companyId);
	}


	private List<Company> loadMatchingCompanies(String term, int limit)
	{
		traceWsContext(wsContext, LOG);
		return megaModelDao.loadMatchingCompanies(term, limit);
	}
	
}
