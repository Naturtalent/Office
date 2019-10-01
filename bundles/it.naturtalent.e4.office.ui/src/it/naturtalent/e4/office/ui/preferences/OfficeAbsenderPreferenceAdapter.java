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
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Sender;

/**
 * Adapter zur Verwaltung der AbsenderPraeferenzen 
 * 
 * Ein Absender ist definiert als eine Adresse erweitert mit einem zusaetzlichen Namen und einem OfficeContext.
 * 
 * Als Praeferenz werden die Absender definiert, deren Adresse üblicherweise im Briefkopf angegeben werden. Es koennen
 * verschiedene Absender definiert und unter einem Namen gespeichert werden. Der 'gecheckte' Absender wird aktuell beim
 * Erstellen eines Anschreibens benutzt.
 * 
 * Mit dem OfficeContext kann die Adresse einem bestimmten Bereich zugeordnet werden (Business, Privat, ...).
 * Der OfficeContext wird in den Eclips4 Context eigetragen und steuert wiederum den Filter des Renderers.
 * Im Modell sind alle Absender dem Container Sender zugeordnet.
 * 
 * 
 * @author dieter
 *
 */
public class OfficeAbsenderPreferenceAdapter extends AbstractPreferenceAdapter
{
	// UI der Absender-Praeferenzliste
	private OfficeAbsenderPreferenceComposite absenderComposite;
	
	//protected List<Absender>importedAbsenderList;
	
	
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
		absenderComposite.appliedPressed();		
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		// Composite beschriften
		referenceNode.setTitle(getLabel());
		
		// Absender UI
		absenderComposite = new OfficeAbsenderPreferenceComposite(referenceNode.getParentNode(), SWT.NONE);
		
		init(absenderComposite);
				
		return absenderComposite;
	}
	
	protected void init(Composite composite)
	{
		// einen Infotext hinzufuegen
		Label label = new Label(composite, SWT.NONE);
		label.setText("Absender definieren, einen Präferenzierten selektieren"); //$NON-NLS-N$;
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Hyperlink hyperlinkExport = new Hyperlink(composite, SWT.NONE);
		hyperlinkExport.setText("exportieren"); //$NON-NLS-N$
		hyperlinkExport.setToolTipText("alle Absender exportieren"); //$NON-NLS-N$
		hyperlinkExport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	Sender semder = (Sender) OfficeUtils.findObject(AddressPackage.eINSTANCE.getSender());
            	OfficeDefaultPreferenceUtils.exportPreference(semder);
            }
        });
		
		new Label(composite, SWT.NONE);
				
		Hyperlink hyperlinkImport = new Hyperlink(composite, SWT.NONE);
		hyperlinkImport.setText("importieren"); //$NON-NLS-N$
		hyperlinkImport.setToolTipText("Absender importieren"); //$NON-NLS-N$
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
            				postImport(importedAbsenderList);
            			}
            		}
            	}
            }
        });
	}
	
	// realisiert die zum Import anstehenden Absender
	protected void postImport(List<Absender>importedAbsenderList)
	{
		absenderComposite.importAbsender(importedAbsenderList);
	}


	/*
	 * Canceln der Eingabe
	 */
	@Override
	public void cancelPressed()
	{
		absenderComposite.doCancel();
	}
	
	

}
