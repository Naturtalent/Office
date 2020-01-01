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
import it.naturtalent.office.model.address.Signature;
import it.naturtalent.office.model.address.Signatures;


/**
 * Adapter zur Anpassung der Office-Signatures Praeferenz
 * 
 * @author dieter
 *
 */
public class OfficeSigaturePreferenceAdapter extends AbstractPreferenceAdapter
{
	// UI der Signature-Praeferenzliste
	protected OfficeSignaturePreferenceComposite signatureComposite;

	//protected List<Referenz>importedReferenzenList;
	
	
	@Override
	public String getNodePath()
	{
		return "Office/Default"; //$NON-NLS-N$
	}
	
	@Override
	public String getLabel()
	{
		return "Signaturen"; //$NON-NLS-N$
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
		signatureComposite.appliedPressed();		
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		// Composite beschriften
		referenceNode.setTitle(getLabel());
		
		// Checkliste zur Anzeige der Signaturenamen
		signatureComposite = new OfficeSignaturePreferenceComposite(referenceNode.getParentNode(), SWT.NONE);
		
		init(signatureComposite);
		
		return signatureComposite;
	}
	
	protected void init(Composite composite)
	{
		// einen Infotext hinzufuegen
		Label label = new Label(composite, SWT.NONE);
		label.setText("Signatureen definieren, einen präferenzierten selektieren"); //$NON-NLS-N$;
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Hyperlink hyperlinkExport = new Hyperlink(composite, SWT.NONE);
		hyperlinkExport.setText("exportieren");
		hyperlinkExport.setToolTipText("alle Signatureen exportieren");
		hyperlinkExport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	Signatures signatures = (Signatures) OfficeUtils.findObject(AddressPackage.eINSTANCE.getSignatures());
            	OfficeDefaultPreferenceUtils.exportPreference(signatures);
            }
        });
		
		new Label(composite, SWT.NONE);
				
		Hyperlink hyperlinkImport = new Hyperlink(composite, SWT.NONE);
		hyperlinkImport.setText("importieren");
		hyperlinkImport.setToolTipText("Signaturen importieren");
		hyperlinkImport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	List<EObject>eObjects = OfficeDefaultPreferenceUtils.importPreference();
            	if((eObjects != null) && (!eObjects.isEmpty()))
            	{
            		if(eObjects.get(0) instanceof Signatures)
            		{     
            			Signatures signatures = (Signatures) eObjects.get(0); 
            			EList<Signature>allSignatures = signatures.getSignatures();            			
            			
            			// importierte Signaturen in einer Liste sammeln
            			if(allSignatures != null)
            			{
            				List<Signature>importedSignatureList = new ArrayList<Signature>();            				
            				for(Signature importSignature : allSignatures)            				
            					importedSignatureList.add(importSignature);
            				postImport(importedSignatureList);          				
            			}
            		}
            	}
            }
        });
	}
	
	protected void postImport(List<Signature>importedSignatureList)
	{
		signatureComposite.importSignatures(importedSignatureList);
	}

	/*
	 * Canceln der Eingabe
	 */
	@Override
	public void cancelPressed()
	{
		signatureComposite.doCancel();
	}
	
	

}
