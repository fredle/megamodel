package com.matterhorn.megamodel.service.api;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import com.matterhorn.megamodel.domain.CapexObject;
import com.matterhorn.megamodel.domain.DataItem;
import com.matterhorn.megamodel.domain.DebtObject;
import com.matterhorn.megamodel.domain.DebtRevolver;
import com.matterhorn.megamodel.domain.EquityObject;
import com.matterhorn.megamodel.domain.ObjectTimeSeries;
import com.matterhorn.megamodel.domain.ObjectTimeSeriesItem;
import com.matterhorn.megamodel.domain.TimeSeriesItem;

/**
 * @author caspar
 */
public final class MergeUtil {
	
	private MergeUtil() {}
	
	
	public static void merge(DataItem di, DataItem old)
	{
		old.setBolValue(di.getBolValue());
		old.setDateValue(di.getDateValue());
		old.setDblValue(di.getDblValue());
		old.setStrValue(di.getStrValue());
		old.setComments(di.getComments());
		if(old instanceof TimeSeriesItem) {
			TimeSeriesItem oldTi = (TimeSeriesItem) old;
			oldTi.setDate(((TimeSeriesItem)di).getDate());
		}
	}
	

	public static void merge(DebtRevolver dob, DebtRevolver existing)
	{
		existing.setInterestRate(dob.getInterestRate());
		existing.setMinimumCash(dob.getMinimumCash());
		existing.setRevolver(dob.getRevolver());
	}


	public static void merge(EquityObject dob, EquityObject existing)
	{
		existing.setConvert(dob.getConvert());
		existing.setConvertibleAfterYear(dob.getConvertibleAfterYear());
		existing.setCupon(dob.getCupon());
		existing.setEquityType(dob.getEquityType());
		existing.setIssueDate(dob.getIssueDate());
		existing.setMaturity(dob.getMaturity());
		existing.setParValue(dob.getParValue());
		existing.setPrincipal(dob.getPrincipal());
		existing.setRetainedEarnings(dob.getRetainedEarnings());
		existing.setSharePrice(dob.getSharePrice());
		existing.setStrike(dob.getStrike());
		existing.setTransactionType(dob.getTransactionType());
	}


	public static void merge(DebtObject deb, DebtObject existing, EntityManager em)
	{
		existing.setDateOfIssue(deb.getDateOfIssue());
//		existing.setDebtType(deb.getDebtType());
		existing.setFloating(deb.getFloating());
		existing.setInterestRate(deb.getInterestRate());
		existing.setPercentageInCurrency(deb.getPercentageInCurrency());
		existing.setPrincipal(deb.getPrincipal());
		existing.setSinkingFundPercent(deb.getSinkingFundPercent());
		existing.setSinkingFundPercent(deb.getSinkingFundYears());
		existing.setTimeToMaturity(deb.getTimeToMaturity());
		existing.setCurrency(deb.getCurrency());
		
		mergeObjectTimeSeries(deb.getAmortization(), existing.getAmortization(), em);
		em.merge(existing);
	}
	
	
	public static void merge(CapexObject cob, CapexObject existing, EntityManager em)
	{
		if(existing.getConstructionInProgress()!=null)
			mergeObjectTimeSeries(cob.getConstructionInProgress(), existing.getConstructionInProgress(), em);
		if(existing.getCapexAddition()!=null)
			mergeObjectTimeSeries(cob.getCapexAddition(), existing.getCapexAddition(), em);
		existing.setDepreciationMethod(cob.getDepreciationMethod());
		existing.setDepreciationType(cob.getDepreciationType());
		existing.setFieldComments(cob.getFieldComments());
		existing.setName(cob.getName());
		existing.setResidualValue(cob.getResidualValue());
		existing.setYearsRemaining(cob.getYearsRemaining());
		em.merge(existing);
	}

	
	public static void mergeObjectTimeSeries(ObjectTimeSeries series, ObjectTimeSeries existing, EntityManager em)
	{
		List<ObjectTimeSeriesItem> items = series.getItems();
		for(Iterator<ObjectTimeSeriesItem> it = items.iterator(); it.hasNext();) {
			ObjectTimeSeriesItem item = it.next();
			if(item.getId() == null) {
//				item.setTimeSeries(existing);
				existing.getItems().add(item);
				em.persist(item);
			} else {
				List<ObjectTimeSeriesItem> existingItems = existing.getItems();
				for(int j = 0; j < existingItems.size(); j++) {
					ObjectTimeSeriesItem existingItem = existingItems.get(j);
					if(item.getId().equals(existingItem.getId())) {
						existingItem.setComments(item.getComments());
						existingItem.setDataType(item.getDataType());
						existingItem.setDate(item.getDate());
						existingItem.setDateValue(item.getDateValue());
						existingItem.setDblValue(item.getDblValue());
						existingItem.setStrValue(item.getStrValue());
						em.merge(existingItem);
						existingItems.remove(j);
						break;
					}
				}
			}
			em.merge(existing);
		}
	}


}
