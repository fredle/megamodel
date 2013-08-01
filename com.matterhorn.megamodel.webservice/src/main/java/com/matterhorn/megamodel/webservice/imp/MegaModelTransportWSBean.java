package com.matterhorn.megamodel.webservice.imp;

import static com.matterhorn.megamodel.webservice.util.WsContextLogTracer.traceWsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.matterhorn.megamodel.api.MegaModelDao;
import com.matterhorn.megamodel.api.MegaModelDownloadService;
import com.matterhorn.megamodel.api.MegaModelUploadService;
import com.matterhorn.megamodel.api.transport.CompanyTO;
import com.matterhorn.megamodel.api.transport.DataItemsCollection;
import com.matterhorn.megamodel.api.transport.FinancialItemDefinitionCollection;
import com.matterhorn.megamodel.api.transport.MainDataSetTO;
import com.matterhorn.megamodel.api.transport.Pagination;
import com.matterhorn.megamodel.api.transport.UploadCargo;
import com.matterhorn.megamodel.api.transport.UploadResp;
import com.matterhorn.megamodel.api.webservices.MegaModelTransportWS;
import com.matterhorn.megamodel.domain.Company;
import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.FinancialItemDefinition;

@WebService(targetNamespace = "http://megamodel.matterhorninvestment.com/megamodel", 
	portName="MegaModelPort",
	serviceName="MegaModelService", 
	endpointInterface="com.matterhorn.megamodel.api.webservices.MegaModelTransportWS")
public class MegaModelTransportWSBean implements MegaModelTransportWS {

	private static final Logger LOG  = Logger.getLogger(MegaModelTransportWSBean.class.getCanonicalName());
	
	private MegaModelDownloadService downloadService;
	private MegaModelUploadService uploadService;
	private MegaModelDao megaModelDao;

	public MegaModelTransportWSBean() {
		BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		megaModelDao = bundleContext.getService(bundleContext.getServiceReference(MegaModelDao.class));
		downloadService = bundleContext.getService(bundleContext.getServiceReference(MegaModelDownloadService.class));
		uploadService = bundleContext.getService(bundleContext.getServiceReference(MegaModelUploadService.class));
	}
	

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
		DataItemsCollection items = new DataItemsCollection();
		items.setDataItemArrayFromList(data);
		return items;
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
