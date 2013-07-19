package com.matterhorn.megamodel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.matterhorn.megamodel.domain.jaxb.FieldCommentAdapter;

@Inheritance(strategy=InheritanceType.JOINED)
@Entity
public abstract class DataObject {

	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	
	@ManyToOne
	private DataSet dataSet;
	
	private String[][] fieldComments;


	@ManyToOne
	private FinancialItemDefinition definition;	

	public FinancialItemDefinition getDefinition() {
		return definition;
	}


	public void setDefinition(FinancialItemDefinition definition) {
		this.definition = definition;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public DataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}

	@XmlJavaTypeAdapter(FieldCommentAdapter.class)
	public String[][] getFieldComments() {
		return fieldComments;
	}

	public void setFieldComments(String[][] fieldComments) {
		this.fieldComments = fieldComments;
	}

}
