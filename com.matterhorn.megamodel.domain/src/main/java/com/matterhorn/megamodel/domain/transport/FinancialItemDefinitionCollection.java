package com.matterhorn.megamodel.domain.transport;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.matterhorn.megamodel.domain.FinancialItemDefinition;

/**
 * @author caspar
 */
@XmlRootElement(name = "FinancialItemDefinitionCollection")
public class FinancialItemDefinitionCollection {

	private FinancialItemDefinition[] definitions;

	public FinancialItemDefinition[] getDefinitions()
	{
		return definitions;
	}

	public void setDefinitions(FinancialItemDefinition[] definitions)
	{
		this.definitions = definitions;
	}
	
	
	public void setDefinitions(Collection<FinancialItemDefinition> definitions)
	{
		this.definitions = new FinancialItemDefinition[definitions.size()]; 
		definitions.toArray(this.definitions);
	}
	
	
}
