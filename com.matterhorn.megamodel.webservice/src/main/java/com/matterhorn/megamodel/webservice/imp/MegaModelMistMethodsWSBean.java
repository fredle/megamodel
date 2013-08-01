package com.matterhorn.megamodel.webservice.imp;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jws.WebService;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.matterhorn.megamodel.api.MegaModelDao;
import com.matterhorn.megamodel.api.MegaModelDownloadService;
import com.matterhorn.megamodel.api.MegaModelUploadService;
import com.matterhorn.megamodel.api.webservices.MegaModelMistMethodsWS;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.TimeSeriesItem;
import com.matterhorn.megamodel.domain.enums.DataItemType;
import com.matterhorn.megamodel.domain.enums.DataSetType;

@WebService(targetNamespace = "http://megamodel.matterhorninvestment.com/megamodel", 
portName="MegaModelPort",
serviceName="MegaModelService", 
endpointInterface="com.matterhorn.megamodel.api.webservices.MegaModelMistMethodsWS")
public class MegaModelMistMethodsWSBean implements MegaModelMistMethodsWS {

	private static final Logger LOG  = Logger.getLogger(MegaModelTransportWSBean.class.getCanonicalName());
	
	private MegaModelDownloadService downloadService;
	private MegaModelUploadService uploadService;
	private MegaModelDao megaModelDao;


	public MegaModelMistMethodsWSBean() {
		BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		megaModelDao = bundleContext.getService(bundleContext.getServiceReference(MegaModelDao.class));
		downloadService = bundleContext.getService(bundleContext.getServiceReference(MegaModelDownloadService.class));
		uploadService = bundleContext.getService(bundleContext.getServiceReference(MegaModelUploadService.class));
	}
	@Override
	public DataSet getPublishedDataSet(Long companyId) {
		// TODO Auto-generated method stub
		return megaModelDao.getPublishedDataSet(companyId);
	}
	
	@Override
	public List<TimeSeriesItem> getDataItems(Long dataSetId, Set<String> identCodes, DataItemType type, Date from, Date to){
		return megaModelDao.getDataItems(dataSetId, identCodes, type,from,to);
	}
	
	@Override
	public long returnOne() {
		return 1;
	}
	
	
	
}
