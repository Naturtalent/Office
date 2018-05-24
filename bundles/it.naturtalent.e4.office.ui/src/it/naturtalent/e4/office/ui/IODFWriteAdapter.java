package it.naturtalent.e4.office.ui;

import java.io.File;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.IWizard;

/**
 * Mit diesem Adapter werden unterschiedliche Anschreibenformate eingebunden.
 * Die Vorlagen koennen unterschiedliche Layouts haben. Der Zugriff auf die Daten
 * erfolgt im ODF (OpenDocumentFormat).
 * 
 * 
 * @author dieter
 *
 */
public interface IODFWriteAdapter
{

	public static final String ODFADAPTERFACTORY = "ODFAdapter";
	
	
	// Senderdaten eintragen
	public void setSender(EObject sender);

	// Empfaengerdaten eintragen
	public void setReceiver(EObject receiver);
	
	// ein Wizard zur Eingabe der Daten
	public IWizard createWizard();

	// ODF-File im Zielverzeichnis erzeugen und zurueckgeben
	public File createODF(File destDir);
	
	// ODF-File oeffnen
	public void openODF(File odfFile);
	
	// ODF-File schliessen
	public void closeODF();
}
