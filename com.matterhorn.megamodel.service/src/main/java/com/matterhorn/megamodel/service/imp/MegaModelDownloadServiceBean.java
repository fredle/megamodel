package com.matterhorn.megamodel.service.imp;

import static javax.persistence.Persistence.createEntityManagerFactory;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.matterhorn.megamodel.api.MegaModelDownloadService;
import com.matterhorn.megamodel.api.transport.Pagination;
import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.FinancialItemDefinition;
import com.matterhorn.megamodel.domain.PersistenceConstants;
import com.matterhorn.megamodel.domain.enums.DataItemType;


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
		//return count(DataItem.class, dataSetId); 
		
		String query = "SELECT COUNT(d) FROM DataItem d WHERE d.dataSet.id = :id AND d.dataType != :type";
		Number retVal = (Number) em.createQuery(query).setParameter("id", dataSetId).setParameter("type", DataItemType.Output).getSingleResult();
		LOG.info("dataSet.id: " + dataSetId + " has a total of " + retVal + " instances of DataItems ");
		
		return retVal.longValue();
	}


	@Override
	public List<DataItem> downloadDataItems(long dataSetId, Pagination pagination)
	{
		
		Query query = em.createQuery("SELECT d FROM DataItem d WHERE d.dataSet.id = :id " + 
						" AND d.dataType != :type ORDER BY d.id").setParameter("type", DataItemType.Output);
		
		query.setParameter("id", dataSetId);
		QueryPagination.paginate(query, pagination);
		@SuppressWarnings("unchecked")
		List<DataItem> retVal = query.getResultList();
		LOG.info("Downloading [retrieved: " + retVal.size() + ", page.start:" + pagination.start 
					+ ", page.size:" + pagination.size + "]  for dataSet.id: " + dataSetId);
		return retVal;
	
	}
	
	@Override
	public long countFinancialItemDefinitionNotUsedInCurrentDataSet(long dataSetId)
	{
		//Long count = (Long) getEntityManager().createNativeQuery("SELECT count(*) FROM financialitemdefinition def WHERE def.id NOT IN (SELECT definition_id FROM dataitem WHERE dataset_id = "+dataSetId+") ").getSingleResult();
		Long count = (Long) getEntityManager().createNativeQuery("SELECT count(*) FROM financialitemdefinition def").getSingleResult();
		
		return count;
	}

	@Override
	public List<FinancialItemDefinition> downloadFinancialItemDefinitionsNotUsedInCurrentDataSet(long dataSetId, Pagination pagination)
	{
//		List<Long> ids = getEntityManager().createQuery("SELECT di.definition.id FROM DataItem di WHERE di.dataSet.id = :dsid").setParameter("dsid", dataSetId).getResultList();
//		Query query = em.createQuery("SELECT def FROM FinancialItemDefinition def WHERE def.id NOT IN :defs");
//		query.setParameter("defs",ids);
		
		Query query = em.createQuery("SELECT def FROM FinancialItemDefinition def");
		
		QueryPagination.paginate(query, pagination);
		@SuppressWarnings("unchecked")
		List<FinancialItemDefinition> dataItems = query.getResultList();
		return dataItems;
	}
	
}
