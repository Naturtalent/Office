package it.naturtalent.e4.office.ui.wizards;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.wizard.IWizardPage;
import org.odftoolkit.simple.TextDocument;

/**
 * Dieses Inferface erweitert IWizardPage um weitere Funktionen im Zusammenhang mit 
 * dem OfficeWrite handling.  
 * 
 * @author dieter
 *
 */

public interface IWriteWizardPage 
{
	// diese Funktion schreibt Daten in das Textdokument
	public void writeToDocument (TextDocument odfDocument);
	
	// diese Funktion liest Daten vom Textdokument
	public void readFromDocument (TextDocument odfDocument);
	
	// Cancel ermoeglicht den Pages UnDo-Funktionen durchzufuehren 
	public void cancelPage (TextDocument odfDocument);
	
	// rueckname aller temporaer eingefuegten EMF-Elemende 
	public void unDo (TextDocument odfDocument);
	
	

}
