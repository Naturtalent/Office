package it.naturtalent.e4.office.ui;

import java.io.File;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.IWizard;

import it.naturtalent.office.model.address.Receivers;

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
	// In den Metadaten des ODF-Dokuments wird unter diesem Namen
	// der verwendete Adapter (Adapterfactory) gespeichert
	public static final String ODFADAPTERFACTORY = "ODFAdapter";
	
	// Name der Tabelle im ODF-Dokument in die die Absenderdaten (im Kopf des Dokuments) geschrieben werden 
	public static String ODF_WRITESENDER = "Absendertabelle";

	// Name der Tabelle im ODF-Dokument in die die Adressdaten geschrieben werden
	public static String ODF_WRITEADRESSE = "Adresstabelle";

	// Eventname einer ODF-Dateidefinition (File des ODF-Dokuments)
	// wird ein ODF-WriteDokument angelegt erfolgt dieses Event mit 'File' als Datenobjekt 
	public final static String ODFWRITE_FILEDEFINITIONEVENT = "odfwritedefinition";
	
	// Senderdaten eintragen
	public void setSender(EObject sender);

	// Empfaengerdaten eintragen
	public void setReceiver(EObject receiver);
	
	// ein Wizard zur Eingabe der Daten
	public IWizard createWizard(IEclipseContext context);

	// ODF-File im Zielverzeichnis erzeugen und zurueckgeben
	public File createODF(File destDir);
	
	// ODF-File oeffnen
	public void openODF(File odfFile);
	
	// ODF-File schliessen
	public void closeODF();
}
