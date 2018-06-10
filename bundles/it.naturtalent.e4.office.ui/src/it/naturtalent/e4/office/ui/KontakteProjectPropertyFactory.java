package it.naturtalent.e4.office.ui;

import it.naturtalent.e4.project.INtProjectProperty;
import it.naturtalent.e4.project.INtProjectPropertyFactory;

public class KontakteProjectPropertyFactory implements INtProjectPropertyFactory
{

	public final static String KONTAKTEPROJECTPROPERTYLABEL = "KontakteProjectProperties";
		
	
	@Override
	public INtProjectProperty createNtProjektData()
	{		
		return new KontakteProjectProperty();
	}

	@Override
	public String getLabel()
	{		
		return KONTAKTEPROJECTPROPERTYLABEL;
	}

	@Override
	public String getParentContainerName()
	{		
		return OfficeUtils.OFFICEPROJECTNAME;
	}

}
