package it.naturtalent.e4.office.ui;

import it.naturtalent.e4.office.OfficeConstants;
import it.naturtalent.e4.office.ui.actions.NewLetterAction;
import it.naturtalent.e4.project.AbstractNewActionAdapter;

public class NewLetterAdapter extends AbstractNewActionAdapter
{

	{ 
		newActionClass = NewLetterAction.class;
	}
	
	@Override
	public String getLabel()
	{		
		return Activator.properties.getProperty(OfficeConstants.PROPERTY_OFFICENEWLETTER);
	}

	@Override
	public String getCategory()
	{		
		return "Office"; //$NON-NLS-N$ //$NON-NLS-1$
	}

	@Override
	public String getMessage()
	{		
		return Messages.NewLetterAdapter_Message;
	}

}
