package com.matterhorn.megamodel.service.karafcommand;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.matterhorn.megamodel.api.MegaModelDao;
import com.matterhorn.megamodel.domain.TimeSeriesItem;
import com.matterhorn.megamodel.domain.enums.DataItemType;


/**
 * 
 * TODO flag for column size, and/or auto-tune with console text width
 * 
 * @author caspar
 */
@Command(scope = "megamodel", name = "getDataItems", description="Search available bloomberg fields")
public class GetDataItems extends OsgiCommandSupport {

	
    @Argument(index = 0, name = "dataSetId", description = "DataSetId", required = true, multiValued = true)
    private String[] queries;

    private PrintStream out;
    
    
    public GetDataItems()
	{
    	this(System.out);
	}

    
    public GetDataItems(PrintStream out)
	{
    	this.out = out;
	}
    
    
	@Override
	protected Object doExecute()
	{
		Long dsId = Long.parseLong(queries[0]);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2008);
		Date from = cal.getTime();
		cal.set(Calendar.YEAR, 2018);
		Date to = cal.getTime();
		
		MegaModelDao dao = getService();
		Set<String> identCodes = new HashSet<String>();
		identCodes.add("BS_A_00001");
		identCodes.add("BS_A_00002");
		identCodes.add("BS_A_00003");
		identCodes.add("BS_A_00004");
		identCodes.add("BS_A_00005");
		identCodes.add("BS_A_00006");
		identCodes.add("BS_A_00007");
		identCodes.add("IS_A_00001");
		identCodes.add("IS_A_00002");
		identCodes.add("IS_A_00003");
		identCodes.add("IS_A_00004");
		identCodes.add("IS_A_00005");
		identCodes.add("IS_A_00006");
		identCodes.add("IS_A_00007");
		List<TimeSeriesItem> tsis = dao.getDataItems(dsId, identCodes, DataItemType.Output, from, to);
		StringBuilder output = new StringBuilder();
		
		
		for(TimeSeriesItem tsi : tsis) {
			out.append(tsi.toString());
			out.append(System.getProperty("line.separator"));
		}
		
		out.append(tsis.size() + " TimeSeriesItems");
		out.append(System.getProperty("line.separator"));
		out.print(output.toString());

		
		return null;
	}


	private MegaModelDao getService()
	{
		final BundleContext bc = getBundleContext();
		ServiceReference<MegaModelDao> reference = bc.getServiceReference(MegaModelDao.class);
		return bc.getService(reference);
	}
}
