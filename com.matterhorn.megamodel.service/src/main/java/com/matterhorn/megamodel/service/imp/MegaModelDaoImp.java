package com.matterhorn.megamodel.service.imp;

import static javax.persistence.Persistence.createEntityManagerFactory;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.matterhorn.megamodel.domain.Company;
import com.matterhorn.megamodel.domain.Currency;
import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DataObject;
import com.matterhorn.megamodel.domain.DataSet;
import com.matterhorn.megamodel.domain.PersistenceConstants;
import com.matterhorn.megamodel.entities.enums.DataSetType;
import com.matterhorn.megamodel.service.api.MegaModelDao;

@Service
@Component(specVersion = "1.1", immediate = true, enabled = true)
public class MegaModelDaoImp implements MegaModelDao {

	private static final Logger LOG  = Logger.getLogger(MegaModelDaoImp.class.getCanonicalName());
	
	EntityManager em;
	
	public MegaModelDaoImp() {
		em = getEntityManager();
	}
	private EntityManager getEntityManager(){
		EntityManagerFactory factory = createEntityManagerFactory(PersistenceConstants.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		return em;
	}

	@Override
	public List<DataSet> loadDataSetsForCompany(int limit, Long companyId)
	{
		Query query = em.createQuery("SELECT ds FROM DataSet ds WHERE ds.company.id = :companyId AND ds.type = :type");
		query.setParameter("type", DataSetType.Matterhorn);
		query.setParameter("companyId", companyId);
		query.setMaxResults(limit);
		
		@SuppressWarnings("unchecked")
		List<DataSet> mainDataSets = query.getResultList();
		LOG.info("Got " + mainDataSets.size() + " mainDataSets");
		return mainDataSets;
	}

	@Override
	public List<Company> loadMatchingCompanies(String term, int limit)
	{
		Query query = em.createQuery("SELECT c FROM Company c WHERE LOWER(c.name) LIKE LOWER('"+term+"%')");
		query.setMaxResults(limit);
		
		@SuppressWarnings("unchecked")
		List<Company> companies = query.getResultList();
		LOG.info("Got " + companies.size() + " companies");
		return companies;
	}

	@Override
	public List<Currency> getCurrencies() {
			Query query = em.createQuery("SELECT c FROM Currency c");
			@SuppressWarnings("unchecked")
			List<Currency> currencies = query.getResultList();
			LOG.info("Got " + currencies.size() + " currencies");
			return currencies;
	}
	
	
	@Override
	public List<DataItem> getDataItems(DataSet dataSet) {
		Query query = em.createQuery("SELECT d FROM DataItem d WHERE d.dataSet.id = :dsid");
		query.setParameter("dsid", dataSet.getId());
		
		@SuppressWarnings("unchecked")
		List<DataItem> dataItems = query.getResultList();
		return dataItems;
	}
	
	@Override
	public List<DataObject> getDataObjects(DataSet dataSet) {
		Query query = em.createQuery("SELECT d FROM DataObject d WHERE d.dataSet.id = :dsid");
		query.setParameter("dsid", dataSet.getId());
		
		@SuppressWarnings("unchecked")
		List<DataObject> dataObjects = query.getResultList();
		return dataObjects;
	}
	
}
