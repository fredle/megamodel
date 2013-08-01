package com.matterhorn.megamodel.service.imp;
import static com.matterhorn.megamodel.service.api.MergeUtil.merge;
import static javax.persistence.Persistence.createEntityManagerFactory;
import static org.apache.felix.scr.annotations.ReferenceCardinality.MANDATORY_UNARY;
import static org.apache.felix.scr.annotations.ReferencePolicy.DYNAMIC;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import com.matterhorn.megamodel.api.MegaModelDao;
import com.matterhorn.megamodel.api.MegaModelUploadService;
import com.matterhorn.megamodel.api.transport.UploadCargo;
import com.matterhorn.megamodel.api.transport.UploadResp;
import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.PersistenceConstants;
import com.matterhorn.megamodel.domain.SingleDataItem;
import com.matterhorn.megamodel.domain.TimeSeriesItem;
import com.matterhorn.megamodel.domain.enums.DataItemKey;
import com.matterhorn.megamodel.domain.enums.DataSetType;


@Service
@Component(specVersion = "1.1", immediate = true, enabled = true)
public class MegaModelUploadServiceBean implements MegaModelUploadService {

	private static final Logger LOG = Logger.getLogger(MegaModelUploadServiceBean.class.getCanonicalName());

	@PersistenceContext(unitName=PersistenceConstants.PERSISTENCE_UNIT)
	private EntityManager em;
	
	@Reference(cardinality = MANDATORY_UNARY, policy = DYNAMIC)
	MegaModelDao megaModelDao;
	
	public MegaModelUploadServiceBean() {
		em = getEntityManager();
	}
	private EntityManager getEntityManager(){
		EntityManagerFactory factory = createEntityManagerFactory(PersistenceConstants.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		return em;
	}

	@Override
	public UploadResp upload(UploadCargo cargo)
	{
		
		
		LOG.info("upload() called ...");

		DataSet mds = em.find(DataSet.class, cargo.dataSetId);
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		boolean downloadRefreshRequired = persistCargoDataItems(cargo, mds);

		LOG.info("commiting");
		tx.commit();
		
		
		if (cargo.publishOutput) {
			createPublishedDataSet(mds, cargo.fromYear, cargo.toYear);
		}


		
		LOG.info("... upload() completed, download for refresh " + ((downloadRefreshRequired) ? "IS" : "NOT") + " required");
		return new UploadResp(downloadRefreshRequired);

	}


	private void createPublishedDataSet(DataSet mds, Integer fromYear,
			Integer toYear) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		DataSet pds = new DataSet();
		pds.setType(DataSetType.MatterhornPublished);
		pds.setCompany(mds.getCompany());
		pds.setCurrency(mds.getCurrency());
		pds.setDateOfLastHistoricalData(mds.getDateOfLastHistoricalData());
		pds.setLastUpdate(new Timestamp(new Date().getTime()));
		pds.setModelDefinition(mds.getModelDefinition());
		pds.setPreferredInterimType(mds.getPreferredInterimType());
		pds.setReportConsolidated(mds.getReportConsolidated());
		pds.setUnits(mds.getUnits());
		pds.setUpdateStaff(mds.getUpdateStaff());
		em.persist(pds);
		
		for(DataItem di : megaModelDao.getDataItems(mds)){
			if(di instanceof TimeSeriesItem){
				Calendar ca = Calendar.getInstance();
				if(((TimeSeriesItem) di).getDate()!=null){
					ca.setTime(((TimeSeriesItem) di).getDate());
					int year = ca.get(Calendar.YEAR);
					if(fromYear <= year && year <=toYear){
						em.persist(copyDataItem(di, pds));
					}
				} else {
					LOG.info("Timeseriesitem without date");
				}
			} else {
				em.persist(copyDataItem(di, pds));
			}

		}
		mds.getCompany().setPublishedDataSet(pds);
		tx.commit();
		
	}
	
	DataItem copyDataItem(DataItem oldDi,DataSet newDs){
		DataItem newDi;

		if(oldDi instanceof TimeSeriesItem){
			newDi = new TimeSeriesItem();
			((TimeSeriesItem)newDi).setDate(((TimeSeriesItem) oldDi).getDate());
			((TimeSeriesItem)newDi).setTimeSeriesType(((TimeSeriesItem) oldDi).getTimeSeriesType());
		} else {
			newDi = new SingleDataItem();
		}
		
		newDi.setDataSet(newDs);
		
		newDi.setBolValue(oldDi.getBolValue());
		newDi.setComments(oldDi.getComments());
		newDi.setDataType(oldDi.getDataType());
		newDi.setDateValue(oldDi.getDateValue());
		newDi.setDblValue(oldDi.getDblValue());
		newDi.setDefinition(oldDi.getDefinition());
		newDi.setStrValue(oldDi.getStrValue());
		
		return newDi;
	}
	
	private boolean persistCargoDataItems(UploadCargo cargo, DataSet mds)
	{
		boolean newlyPersisted = false;
		DataItem[] items = cargo.dataItems.getDataItemArray();
		if(items == null) {
			return newlyPersisted;
		}
		Set<Long> ids = new HashSet<Long>();
		for (DataItem di : items) {
			if(di.getId() != null) {
				ids.add(di.getId());
			}
		}
		Map<Long, DataItem> ffs = new HashMap<Long, DataItem>();
		if(!ids.isEmpty()) {
			Query q = em.createQuery("SELECT di.id, di FROM " + DataItem.class.getSimpleName() + " di WHERE di.id IN :ids");
			q.setParameter("ids", ids);
			@SuppressWarnings("unchecked")
			List<Object[]> rows = q.getResultList();
			for(Object[] row : rows) {
				ffs.put((Long)row[0], (DataItem)row[1]);
			}
		}
		
		
		Map<DataItemKey,DataItem> oldDataItems = new HashMap<DataItemKey, DataItem>();
		
		
		List<DataItem> dataItems = megaModelDao.getDataItems(mds);
		
		for (DataItem di : dataItems){
			if(di instanceof TimeSeriesItem && ((TimeSeriesItem) di).getTimeSeriesType()==null){
				LOG.info(di.getId() + " is null");
			} else {
				oldDataItems.put(new DataItemKey(mds,di), di);
			}
		}
		
		for(DataItem di : items) {

//			if(di.getDataType() == DataItemType.Output) {
//				continue;
//			}
			//LOG.info("di.id: " + di.getId());
			//di.setDefinition(em.merge(di.getDefinition()));
			di.setDataSet(mds);
			if(di.getId() != null) {
				DataItem old = ffs.get(di.getId());
				merge(di, old);
				em.merge(old);
			} else if(oldDataItems.get(new DataItemKey(mds,di))!=null){
				//check to avoid constraint violations.
				DataItem old = oldDataItems.get(new DataItemKey(mds,di));
				//LOG.info("matched old dataitem: " + old.getId());
				merge(di, old);
				em.merge(old);				
			} else {
				em.persist(di);
				newlyPersisted = true;
			}
		}
		return newlyPersisted;
	}

}
