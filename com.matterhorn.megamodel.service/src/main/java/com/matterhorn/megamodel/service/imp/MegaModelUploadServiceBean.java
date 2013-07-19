package com.matterhorn.megamodel.service.imp;
import static com.matterhorn.megamodel.service.api.MergeUtil.merge;
import static javax.persistence.Persistence.createEntityManagerFactory;
import static org.apache.felix.scr.annotations.ReferenceCardinality.MANDATORY_UNARY;
import static org.apache.felix.scr.annotations.ReferencePolicy.DYNAMIC;

import java.util.Collection;
import java.util.Collections;
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

import com.matterhorn.megamodel.domain.CapexObject;
import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataObject;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.DebtObject;
import com.matterhorn.megamodel.domain.DebtRevolver;
import com.matterhorn.megamodel.domain.EquityObject;
import com.matterhorn.megamodel.domain.ObjectTimeSeries;
import com.matterhorn.megamodel.domain.ObjectTimeSeriesItem;
import com.matterhorn.megamodel.domain.PersistenceConstants;
import com.matterhorn.megamodel.domain.TimeSeriesItem;
import com.matterhorn.megamodel.domain.transport.UploadCargo;
import com.matterhorn.megamodel.domain.transport.UploadResp;
import com.matterhorn.megamodel.entities.enums.DataItemKey;
import com.matterhorn.megamodel.entities.enums.DataItemType;
import com.matterhorn.megamodel.entities.enums.DataObjectKey;
import com.matterhorn.megamodel.service.api.MegaModelDao;
import com.matterhorn.megamodel.service.api.MegaModelUploadService;


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
		
		if (cargo.publishOutput) {
			List<DataItem> data = cargo.dataItems.getDataItemArrayAsList();
			if(data.isEmpty()) {
				throw new IllegalStateException("Cannot publish DataItems, as DataItem list is empty");
			}
		}
		Collection<DataObject> objectsToRemove = Collections.emptyList();

		processObjectsToRemove(objectsToRemove);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		boolean downloadRefreshRequired = persistCargoDataItems(cargo, mds);
		downloadRefreshRequired |= persistCargoDataObjects(cargo, mds);

		LOG.info("commiting");
		tx.commit();
		LOG.info("... upload() completed, download for refresh " + ((downloadRefreshRequired) ? "IS" : "NOT") + " required");
		return new UploadResp(downloadRefreshRequired);

	}


	private boolean persistCargoDataObjects(UploadCargo cargo, DataSet mds)
	{
		boolean newlyPersisted = false;
		DataObject[] dataObjects = cargo.dataObjects.getDataObjectArray();
		if(dataObjects == null) {
			return newlyPersisted;
		}
		Map<Long, DataObject> dbDataObjects = loadExisting(dataObjects);

		
		Map<DataObjectKey, DataObject> dbDataObjectsByUniqueKey = new HashMap<DataObjectKey, DataObject>();
		
		List<DataObject> dsDataObjects = megaModelDao.getDataObjects(mds);

		for(DataObject dataObject : dsDataObjects){
			dbDataObjectsByUniqueKey.put(new DataObjectKey(mds, dataObject.getDefinition()), dataObject);
		}
		
		
		
		
		for(DataObject dob : dataObjects) {
			LOG.info("dob.id: " + dob.getId());
			Class<? extends DataObject> clazz = dob.getClass();
			if(dob.getId() != null) {
				DataObject existing = dbDataObjects.get(dob.getId());
				existing.setFieldComments(dob.getFieldComments());
				existing.setName(dob.getName());
				
				if(DebtObject.class.isAssignableFrom(clazz)) {
					merge((DebtObject) dob, (DebtObject) existing, em);
				} else if(CapexObject.class.isAssignableFrom(clazz)) {
					merge((CapexObject) dob, (CapexObject) existing, em);
				} else if(EquityObject.class.isAssignableFrom(clazz)) {
					merge((EquityObject) dob, (EquityObject) existing);
				} else if(DebtRevolver.class.isAssignableFrom(clazz)) {
					merge((DebtRevolver) dob, (DebtRevolver) existing);
				} else {
					String msg  = "A developer has added a new 'type' somewhere (subclass of " + DataObject.class.getCanonicalName()
						+ ") and hasn't checked a myriad of places for the potental ramificiations. Please report this (stupid) fatal error immediately";
					LOG.info(msg);
					throw new RuntimeException(new IllegalStateException(msg));
				}
				existing = em.merge(existing);
				
			} else if(dbDataObjectsByUniqueKey.get(new DataObjectKey(mds,dob.getDefinition()))!=null) {
				//try to avoid constraint errors...
				DataObject existing = dbDataObjectsByUniqueKey.get(new DataObjectKey(mds,dob.getDefinition()));
				existing.setFieldComments(dob.getFieldComments());
				existing.setName(dob.getName());
				
				if(DebtObject.class.isAssignableFrom(clazz)) {
					merge((DebtObject) dob, (DebtObject) existing, em);
				} else if(CapexObject.class.isAssignableFrom(clazz)) {
					merge((CapexObject) dob, (CapexObject) existing, em);
				} else if(EquityObject.class.isAssignableFrom(clazz)) {
					merge((EquityObject) dob, (EquityObject) existing);
				} else if(DebtRevolver.class.isAssignableFrom(clazz)) {
					merge((DebtRevolver) dob, (DebtRevolver) existing);
				} else {
					String msg  = "A developer has added a new 'type' somewhere (subclass of " + DataObject.class.getCanonicalName() + ")";
					LOG.info(msg);
					throw new RuntimeException(new IllegalStateException(msg));
				}
				existing = em.merge(existing);
				LOG.info(existing.toString());
			} else {
				dob.setDefinition(em.merge(dob.getDefinition()));
				dob.setDataSet(mds);
				
				if(DebtObject.class.isAssignableFrom(clazz)) {
					DebtObject deb = (DebtObject) dob;
//					associateObjectTimeSeriesItems(deb.getAmortization());
				} else if(CapexObject.class.isAssignableFrom(clazz)) {
					CapexObject cap = (CapexObject) dob;
//					associateObjectTimeSeriesItems(cap.getCapexAddition());
//					associateObjectTimeSeriesItems(cap.getConstructionInProgress());
				}				
				em.persist(dob);
				newlyPersisted = true;
				
			}
			
			
			if(dob.getId() == null) {
			} else {
			}
		}
		return newlyPersisted;
	}

	
//	private void associateObjectTimeSeriesItems(ObjectTimeSeries series)
//	{
//		if(series != null) {
//			for(ObjectTimeSeriesItem item : series.getItems()) {
//				item.setTimeSeries(series);
//			}
//		}
//	}


	private Map<Long, DataObject> loadExisting(DataObject[] dataObjects)
	{
		Set<Long> ids = new HashSet<Long>(dataObjects.length);
		for(DataObject dataObject : dataObjects) {
			if(dataObject.getId() != null) {
				ids.add(dataObject.getId());
			}
		}
		Map<Long, DataObject> dbDataObjects = Collections.emptyMap();
		if(!ids.isEmpty()) {
			String query = "SELECT o.id, o FROM " + DataObject.class.getSimpleName() + " o WHERE o.id IN :ids";
			@SuppressWarnings("unchecked")
			List<Object[]> rows = em.createQuery(query).setParameter("ids", ids).getResultList();
			dbDataObjects = new HashMap<Long, DataObject>(rows.size());
			for(Object[] row : rows) {
				dbDataObjects.put((Long)row[0], (DataObject)row[1]);
			}
		}
		return dbDataObjects;
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
			if(di.getDefinition().getIdentCode().equals("IS_00053")){
				LOG.info(di.toString());
			}
			if(di.getDataType() == DataItemType.Output) {
				continue;
			}
			//LOG.info("di.id: " + di.getId());
			di.setDefinition(em.merge(di.getDefinition()));
			di.setDataSet(mds);
			if(di.getId() != null) {
				DataItem old = ffs.get(di.getId());
				merge(di, old);
				em.merge(old);
			} else if(oldDataItems.get(new DataItemKey(mds,di))!=null){
				//check to avoid constraint violations.
				DataItem old = oldDataItems.get(new DataItemKey(mds,di));
				LOG.info("matched old dataitem: " + old.getId());
				merge(di, old);
				em.merge(old);				
			} else {
				em.persist(di);
				newlyPersisted = true;
			}
		}
		return newlyPersisted;
	}

	private void processObjectsToRemove(Collection<DataObject> objectsToRemove)
	{
		for (DataObject dataObj : objectsToRemove) {
			// Since this could be an object of the segment
			DataSet objDs = dataObj.getDataSet();
			
			if (dataObj instanceof DebtObject) {
				DebtObject debtObj = (DebtObject) dataObj;
				remove(debtObj.getAmortization());
			}
			if (dataObj instanceof CapexObject) {
				CapexObject capex = (CapexObject) dataObj;
				remove(capex.getCapexAddition());
				remove(capex.getConstructionInProgress());
			}
			em.remove(dataObj);
			LOG.info("Removed entity: " + dataObj);
		}
	}
	
	
	private void remove(Object object)
	{
		if(object != null) {
			em.remove(object);
		}
	}


}
