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
import it.naturtalent.office.model.address.FootNote;
import it.naturtalent.office.model.address.FootNotes;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Referenzen;
import it.naturtalent.office.model.address.Sender;


/**
 * Adapter zur Anpassung der Office-FootNote Praeferenz
 * 
 * @author dieter
 *
 */
public class OfficeFootNotePreferenceAdapter extends AbstractPreferenceAdapter
{
	// UI der FootNote-Praeferenzliste
	protected OfficeFootNotePreferenceComposite  footNoteComposite;
	
	
	@Override
	public String getNodePath()
	{
		return "Office/Default"; //$NON-NLS-N$
	}
	
	@Override
	public String getLabel()
	{
		return "Fußnoten"; //$NON-NLS-N$
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
		footNoteComposite.appliedPressed();		
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode preferenceNode)
	{
		// Composite beschriften
		preferenceNode.setTitle(getLabel());
		
		// Checkliste zur Anzeige der FootNotenamen
		footNoteComposite = new OfficeFootNotePreferenceComposite (preferenceNode.getParentNode(), SWT.NONE);
		
		init(footNoteComposite);
		
		return footNoteComposite;
	}
	
	protected void init(Composite composite)
	{
		// einen Infotext hinzufuegen
		Label label = new Label(composite, SWT.NONE);
		label.setText("Fußnoten definieren, einen präferenzierten selektieren"); //$NON-NLS-N$;
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Hyperlink hyperlinkExport = new Hyperlink(composite, SWT.NONE);
		hyperlinkExport.setText("exportieren");
		hyperlinkExport.setToolTipText("alle Fußnoten exportieren");
		hyperlinkExport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	FootNotes footnotes = (FootNotes) OfficeUtils.findObject(AddressPackage.eINSTANCE.getFootNotes());
            	OfficeDefaultPreferenceUtils.exportPreference(footnotes);
            }
        });
		
		new Label(composite, SWT.NONE);
				
		Hyperlink hyperlinkImport = new Hyperlink(composite, SWT.NONE);
		hyperlinkImport.setText("importieren");
		hyperlinkImport.setToolTipText("Fußnoten importieren");
		hyperlinkImport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	// mir Importdialog die Quelldatei oeffnen
            	List<EObject>eObjects = OfficeDefaultPreferenceUtils.importPreference();
            	if((eObjects != null) && (!eObjects.isEmpty()))
            	{
            		// beinhaltet die Datei Footnotes
            		if(eObjects.get(0) instanceof FootNotes)
            		{     
            			FootNotes footNotes = (FootNotes) eObjects.get(0); 
            			EList<FootNote>allFootnotes = footNotes.getFootNotes();            			
            			
            			// importierte FootNotes in einer Liste sammeln
            			if(allFootnotes != null)
            			{
            				List<FootNote>importedFooNoteList = new ArrayList<FootNote>();            				
            				for(FootNote importFootNote : allFootnotes)            				
            					importedFooNoteList.add(importFootNote);
            				
            				// hier kann noch eine Context-Filterfunktion eingeschaltet werden
            				postImport(importedFooNoteList);
            			}
            		}
            	}
            }
        });
	
	}
	
	// abschliessendes contextbezogenes Einlesen der Fussnoten
	protected void postImport(List<FootNote>importedFooNoteList)
	{
		footNoteComposite.importFootNotes(importedFooNoteList);
	}

	/*
	 * Canceln der Eingabe
	 */
	@Override
	public void cancelPressed()
	{
		footNoteComposite.doCancel();
	}
	
	

}
