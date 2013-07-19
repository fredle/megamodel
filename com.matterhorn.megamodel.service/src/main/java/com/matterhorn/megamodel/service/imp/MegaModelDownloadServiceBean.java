package com.matterhorn.megamodel.service.imp;

import static javax.persistence.Persistence.createEntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataObject;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.FinancialItemDefinition;
import com.matterhorn.megamodel.domain.PersistenceConstants;
import com.matterhorn.megamodel.domain.transport.Pagination;
import com.matterhorn.megamodel.entities.enums.DataItemType;
import com.matterhorn.megamodel.service.api.MegaModelDownloadService;


@Service
@Component(specVersion = "1.1", immediate = true, enabled = true)
public class MegaModelDownloadServiceBean implements MegaModelDownloadService {

	public MegaModelDownloadServiceBean() {
		em = getEntityManager();
	}
	
	private static final Logger LOG  = Logger.getLogger(MegaModelDownloadServiceBean.class.getCanonicalName());
	private EntityManager getEntityManager(){
		EntityManagerFactory factory = createEntityManagerFactory(PersistenceConstants.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		return em;
	}

	@PersistenceContext(unitName=PersistenceConstants.PERSISTENCE_UNIT)
	private EntityManager em;


	@Override
	public DataSet downloadMainDataSet(long id)
	{
		return getEntityManager().find(DataSet.class, id);
	}
	
	@Override
	public long countDataItems(long dataSetId)
	{
		return count(DataItem.class, dataSetId); 
	}


	@Override
	public List<DataItem> downloadDataItems(long dataSetId, Pagination pagination)
	{
		return download(DataItem.class, dataSetId, pagination);
	}


	@Override
	public long countDataObjects(long dataSetId)
	{
		return count(DataObject.class, dataSetId);
	}


	@Override
	public List<DataObject> downloadDataObjects(long dataSetId, Pagination pagination)
	{
		return download(DataObject.class, dataSetId, pagination);
	}

	

	
	private long count(Class<?> clazz, long dataSetId)
	{
		String className = clazz.getSimpleName();
		LOG.info("getting clazz count for " + clazz.getCanonicalName());
		Number retVal;
		
		if( DataItem.class.isAssignableFrom(clazz)){
			String query = "SELECT COUNT(d) FROM " + className + " d WHERE d.dataSet.id = :id AND d.dataType != :type";
			retVal = (Number) em.createQuery(query).setParameter("id", dataSetId).setParameter("type", DataItemType.Output).getSingleResult();
		} else {
			String query = "SELECT COUNT(d) FROM " + className + " d WHERE d.dataSet.id = :id";
			retVal = (Number) em.createQuery(query).setParameter("id", dataSetId).getSingleResult();
		}
		LOG.info("dataSet.id: " + dataSetId + " has a total of " + retVal + " instances of type " + className);
		
		return retVal.longValue();
	}
	
	private <T> List<T> download(Class<T> clazz, long dataSetId, Pagination pagination)
	{
		String className = clazz.getSimpleName();
		
		Query query;
		if( DataItem.class.isAssignableFrom(clazz)){
			 query = em.createQuery("SELECT d FROM " + className + " d WHERE d.dataSet.id = :id " + 
						" AND d.dataType != :type ORDER BY d.id").setParameter("type", DataItemType.Output);
		} else {
			 query = em.createQuery("SELECT d FROM " + className + " d WHERE d.dataSet.id = :id " + 
						" ORDER BY d.id");
		}
		
		query.setParameter("id", dataSetId);
		QueryPagination.paginate(query, pagination);
		@SuppressWarnings("unchecked")
		List<T> retVal = query.getResultList();
		LOG.info("Downloading [retrieved: " + retVal.size() + ", page.start:" + pagination.start 
					+ ", page.size:" + pagination.size + "] of "+ className + " for dataSet.id: " + dataSetId);
		return retVal;
	}


	@Override
	public long countFinancialItemDefinitionNotUsedInCurrentDataSet(long dataSetId)
	{
//		List<Long> ids = getEntityManager().createQuery("SELECT di.id FROM DataItem di WHERE di.dataset.id = :dsid").setParameter("dsid", dataSetId).getResultList();
//		
//		LOG.info(ids.size() +" vals XXXXXX");
//		Query query = getEntityManager().createQuery("SELECT count(def.*) FROM FinancialItemDefinition def WHERE def.id NOT IN (:defs)");
//		query.setParameter("defs",ids);
//		
//		@SuppressWarnings("unchecked")
//		Number count = (Number) query.getSingleResult();
//		LOG.info(ids.size() +" count XXXXXX");
//		
//		return count.longValue();
		return 2646;
	}

	@Override
	public List<FinancialItemDefinition> downloadFinancialItemDefinitionsNotUsedInCurrentDataSet(long dataSetId, Pagination pagination)
	{
		Query query = em.createQuery("SELECT def FROM FinancialItemDefinition def");// WHERE def.id NOT IN (SELECT distinct(definition_id) FROM DataItem WHERE dataset_id = :dsid)");
		//query.setParameter("dsid", dataSetId);
		QueryPagination.paginate(query, pagination);
		@SuppressWarnings("unchecked")
		List<FinancialItemDefinition> dataItems = query.getResultList();
		return dataItems;
	}
	
}
