 
package it.naturtalent.e4.office.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;

public class DeleteDoubleEntries
{
	private static Kontakte kontakte;
	
	private List<Kontakt>doubleEmptyOrtKontakts = new ArrayList<Kontakt>(); 
	
	@Execute
	public void execute()
	{
		List<Kontakt>removeKontakts = new ArrayList<Kontakt>();
		List<Kontakt>emptyOrtKontakts = new ArrayList<Kontakt>();
		
		ECPProject ecpProject = OfficeUtils.getOfficeProject();
		
		kontakte = OfficeUtils.getKontakte();
		EList<Kontakt>allKontakts = kontakte.getKontakte();
		System.out.println("Size (org): "+allKontakts.size());
		for(Kontakt kontakt : allKontakts)
		{
			if(kontakt.getAdresse() != null)
			{
				String ort = kontakt.getAdresse().getOrt();	
				if(StringUtils.isNotEmpty(ort))
				{
					if(!StringUtils.isNumeric(StringUtils.substring(ort, 0,1)))
						removeKontakts.add(kontakt);
				}
				else
				{
					if(!isDoubleEmptyOrt(emptyOrtKontakts, kontakt))
						emptyOrtKontakts.add(kontakt);	
					else
						doubleEmptyOrtKontakts.add(kontakt);
				}
			}
		}
		
		allKontakts.removeAll(removeKontakts);		
		System.out.println("Size (after): "+allKontakts.size());
		
		for(Kontakt kontakt : doubleEmptyOrtKontakts)		
			System.out.println(kontakt.getName());
					
		allKontakts.removeAll(doubleEmptyOrtKontakts);		
		
		MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Konsolidierung", "Beendet! - speichern nicht vergessen");
		
		//allKontakts = kontakte.getKontakte();
		//System.out.println("Size (after Delete emptyOrte): "+allKontakts.size());

	}
	
	private boolean isDoubleEmptyOrt(List<Kontakt>emptyOrtKontakts, Kontakt emptyOrtKontakt)
	{
		for(Kontakt kontakt : emptyOrtKontakts)
		{
			if(StringUtils.equals(kontakt.getName(),emptyOrtKontakt.getName()))
				return true;
		}
		
		return false;
	}
		
}