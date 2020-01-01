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
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Referenzen;


/**
 * Adapter zur Anpassung der Office-Referenzen Praeferenz
 * 
 * @author dieter
 *
 */
public class OfficeReferenzPreferenceAdapter extends AbstractPreferenceAdapter
{
	// UI der Referenz-Praeferenzliste
	protected OfficeReferenzPreferenceComposite referenceComposite;
	
	//protected List<Referenz>importedReferenzenList;
	
	
	@Override
	public String getNodePath()
	{
		return "Office/Default"; //$NON-NLS-N$
	}
	
	@Override
	public String getLabel()
	{
		return "Referenzen"; //$NON-NLS-N$
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
		referenceComposite.appliedPressed();		
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		// Composite beschriften
		referenceNode.setTitle(getLabel());
		
		// Checkliste zur Anzeige der Referenznamen
		referenceComposite = new OfficeReferenzPreferenceComposite(referenceNode.getParentNode(), SWT.NONE);
		
		init(referenceComposite);
		
		return referenceComposite;
	}
	
	/*
	 * Erweitert die Composite (Hyperlinks 'export' / 'import' 
	 * 
	 */
	protected void init(Composite composite)
	{
		// einen Infotext hinzufuegen
		Label label = new Label(composite, SWT.NONE);
		label.setText("Referenzen definieren, einen präferenzierten selektieren"); //$NON-NLS-N$;
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Hyperlink hyperlinkExport = new Hyperlink(composite, SWT.NONE);
		hyperlinkExport.setText("exportieren");
		hyperlinkExport.setToolTipText("alle Referenzen exportieren");
		hyperlinkExport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	Referenzen referenzen = (Referenzen) OfficeUtils.findObject(AddressPackage.eINSTANCE.getReferenzen());
            	OfficeDefaultPreferenceUtils.exportPreference(referenzen);
            }
        });
		
		new Label(composite, SWT.NONE);
				
		Hyperlink hyperlinkImport = new Hyperlink(composite, SWT.NONE);
		hyperlinkImport.setText("importieren");
		hyperlinkImport.setToolTipText("Referenzen importieren");
		hyperlinkImport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	List<EObject>eObjects = OfficeDefaultPreferenceUtils.importPreference();
            	if((eObjects != null) && (!eObjects.isEmpty()))
            	{
            		if(eObjects.get(0) instanceof Referenzen)
            		{     
            			Referenzen referenzen = (Referenzen) eObjects.get(0); 
            			EList<Referenz>allReferenzen = referenzen.getReferenzen();            			
            			
            			// importierte Referenzen in einer Liste sammeln
            			if(allReferenzen != null)
            			{
            				List<Referenz>importedReferenzenList = new ArrayList<Referenz>();            				
            				for(Referenz importReferenz : allReferenzen)            				
            					importedReferenzenList.add(importReferenz);
            				postImport(importedReferenzenList);          				
            			}
            		}
            	}
            }
        });
	}
	
	/*
	 * ueberschreibbare Funktion zur Spezialisierung von OfficeContext und Praeferenzknoten 
	 */
	protected void postImport(List<Referenz>importedReferenzenList)
	{
		referenceComposite.importReferenzen(importedReferenzenList);
	}

	/*
	 * Canceln der Eingabe
	 */
	@Override
	public void cancelPressed()
	{
		referenceComposite.doCancel();
	}
	
	

}
