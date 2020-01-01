package it.naturtalent.e4.office.ui;

import java.io.File;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.IWizard;

import it.naturtalent.office.model.address.Receivers;

/**
 * Interface eines Adapters mit dem auf Calc-Vorlagen zugegriffen wird.
 * Die Vorlagen sind vordefinierte CalcTabellen 
 * 
 * 
 * @author dieter
 *
 */
public interface IODFCalcAdapter
{
	// in diesem Verzeichnis befindet sich standardmaessig die Original-Vorlagen
	public static final String PLUGIN_TEMPLATES_DIR = "templates"; //$NON-NLS-1$
	
	// Subdirectory der Vorlagen relativ zu Workspace/OFFICEDATADIR 
	public static final String ODFTEXT_TEMPLATE_DIRECTORY = "templates"; //$NON-NLS-1$

	
	// ein Wizard zur Eingabe der Daten und speichern im Dokument
	public IWizard createWizard(IEclipseContext context);

	// ODF-File im Zielverzeichnis erzeugen und zurueckgeben
	public File createODF(File destDir);
	
	// ODF-File oeffnen
	public void openODF(String odfPath);
	
	// ODF-File schliessen
	public void closeODF();
}
