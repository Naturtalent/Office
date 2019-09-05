package it.naturtalent.e4.office.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.Hyperlink;

import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.preferences.AbstractPreferenceAdapter;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Sender;


public class OfficeAbsenderPreferenceAdapter extends AbstractPreferenceAdapter
{
	// UI der Absender-Praeferenzliste
	private OfficeAbsenderPreferenceComposite preferenceComposite;
	
	
	@Override
	public String getNodePath()
	{
		return "Office/Default"; //$NON-NLS-N$
	}
	
	@Override
	public String getLabel()
	{
		return "Absender"; //$NON-NLS-N$
	}

	@Override
	public void restoreDefaultPressed()
	{
		// TODO Auto-generated method stub
		
	}

	/*
	 * Festschreiben der Eingabe
	 */
	@Override
	public void appliedPressed()
	{
		preferenceComposite.appliedPressed();		
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		// Composite beschriften
		referenceNode.setTitle(getLabel());
		
		// Absender 
		preferenceComposite = new OfficeAbsenderPreferenceComposite(referenceNode.getParentNode(), SWT.NONE);
		
		// einen Infotext hinzufuegen
		Label label = new Label(preferenceComposite, SWT.NONE);
		label.setText("Absender definieren, einen präferenzierten selektieren"); //$NON-NLS-N$;
		
		new Label(preferenceComposite, SWT.NONE);
		new Label(preferenceComposite, SWT.NONE);
		new Label(preferenceComposite, SWT.NONE);
		
		Hyperlink hyperlinkExport = new Hyperlink(preferenceComposite, SWT.NONE);
		hyperlinkExport.setText("exportieren");
		hyperlinkExport.setToolTipText("alle Absender exportieren");
		hyperlinkExport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	Sender semder = (Sender) OfficeUtils.findObject(AddressPackage.eINSTANCE.getSender());
            	OfficeDefaultPreferenceUtils.exportPreference(semder);
            }
        });
		
		new Label(preferenceComposite, SWT.NONE);
				
		Hyperlink hyperlinkImport = new Hyperlink(preferenceComposite, SWT.NONE);
		hyperlinkImport.setText("importieren");
		hyperlinkImport.setToolTipText("Absender importieren");
		hyperlinkImport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	List<EObject>eObjects = OfficeDefaultPreferenceUtils.importPreference();
            	if((eObjects != null) && (!eObjects.isEmpty()))
            	{
            		if(eObjects.get(0) instanceof Sender)
            		{            			
            			Sender importedSender = (Sender) eObjects.get(0); 
            			EList<Absender>importedAbsender = importedSender.getSenders();
            			
            			// importierte Absender in einer Liste sammeln
            			if(importedAbsender != null)
            			{
            				List<Absender>importedAbsenderList = new ArrayList<Absender>();            				
            				for(Absender importAbsender : importedAbsender)            				
            					importedAbsenderList.add(importAbsender);
            				
            				preferenceComposite.importAbsender(importedAbsenderList);
            			}
            		}
            	}
            }
        });
		
		
		
		
		return preferenceComposite;
	}

	/*
	 * Canceln der Eingabe
	 */
	@Override
	public void cancelPressed()
	{
		preferenceComposite.doCancel();
	}
	
	

}
