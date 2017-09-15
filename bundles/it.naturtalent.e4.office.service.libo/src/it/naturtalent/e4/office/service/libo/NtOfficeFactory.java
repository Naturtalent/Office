package it.naturtalent.e4.office.service.libo;

import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.INtOfficeFactory;

public class NtOfficeFactory implements INtOfficeFactory
{

	@Override
	public INtOffice createNtOfficeInstance()
	{		
		return new NtOffice();
	}

}
