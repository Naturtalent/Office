 
package it.naturtalent.e4.office.ui.handlers;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;

public class ReorgHandler
{
	@Execute
	public void execute()
	{
		Kontakte kontakte = OfficeUtils.getKontakte();
		
		EList<Kontakt>allKontacts = kontakte.getKontakte();
		
		for(Kontakt kontakt : allKontacts)
		{
			Adresse address = kontakt.getAdresse();
			String plz = address.getPlz();			
			if(StringUtils.isNotEmpty(plz))
			{				
				address.setOrt(plz+" "+address.getOrt());
				address.setPlz(null);
				continue;
			}

			/*
			if(StringUtils.equals(address.getName(), "Der Vorstand der Gemeinde"))
			{
				address.setName2(null);
				continue;
			}
			*/
			
			/*
			if(StringUtils.equals(kontakt.getName(), "Deutsche Telekom Technik GmbH"))
			{
				String reorgName = kontakt.getAdresse().getName2();
				kontakt.setName("Stadt "+reorgName);
				continue;
			}
			
			if(StringUtils.equals(kontakt.getName(), "Der Vorstand der Gemeinde"))
			{
				String reorgName = kontakt.getAdresse().getName2();
				kontakt.setName("Gemeinde "+reorgName);
				continue;
			}
			*/
			
			
		}
		
		ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
		
	}
		
}