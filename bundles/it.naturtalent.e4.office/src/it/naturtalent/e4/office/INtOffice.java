package it.naturtalent.e4.office;

import java.io.File;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;

import org.jdom2.Document;




public interface INtOffice
{
	
	// Komponenten der OpenDocument Dateien
	static public final String OPENDOCUMENT_CONTENT = "content.xml";
	static public final String OPENDOCUMENT_STYLES = "styles.xml";
	
	// Dokumentelemente
	public static final String DOCUMENT_ABSENDER = "docabsender";
	public static final String DOCUMENT_ADRESSE = "docadresse";
	public static final String DOCUMENT_PRAESENTATION = "docpraesentation";
	public static final String DOCUMENT_REFERENZ = "docreference";
	public static final String DOCUMENT_BETREFF = "docbetreff";
	public static final String DOCUMENT_HEADER = "docheader";
	public static final String DOCUMENT_FOOTER = "docfooter";
	public static final String DOCUMENT_SIGNATURE = "docsignature";
	
	
	// Name des Systemproperty auf das externe OfficeProgramm
	public static final String EXTERN_OFFICE_PROPERTY = "UNO_PATH";
	
	// OfficeContext 
	//public static final String NTOFFICE_CONTEXT = "xntw";
	
	/**
	 * Ein Officedokument oeffnen und so aufbereiten, dass die Transformations
	 * funktionen angewendet werden koennen.
	 * 
	 * @param docSource
	 * @return
	 */
	public void openDocument(File fileDocument);

	
	/**
	 * Der Inhalt 'content.xml' des geoffneten Dokuments wird zurueckgegeben
	 * 
	 * @return
	 */
	public Document getOpenDocument();
	
	/**
	 * Der Inhalt einer Komponente z.B. 'styles.xml' des geoffneten Dokuments 
	 * wird zurueckgegeben
	 * 
	 * @return
	 */
	public Document getOpenDocument(String component);

		
	/**
	 * Stylesheet hinzufuegen, mit dem die Transfomation (@see 'transform()') durchgefuehrt wird.
	 * 
	 * @param fileXSL
	 */
	public void setStyleSheet(File fileXSL);
	
	/**
	 * Transformation des Dokuments mit dem internen Stylesheet '@see setStyleSheet()'.
	 * Die Transformation wirkt auf das geoffnete Dokument.
	 * 	
	 */
	public void transform();

	/**
	 * Das Stylesheet 'xslFile' transformiert die Daten des geoffneten
	 * (entpackten) Dokuments ohne dieses zu veraendern. Das Ergebnis wird in einem separaten
	 * Reslult zurueckgegeben.
	 * 
	 * Dient dem Auslesen der Dokumentdaten (Absender, Adresse, ...) 
	 * 
	 * @param xslFile
	 * @return Ergebnis der Transformation 
	 */
	public File transform(File xslFile);
	
	/**
	 * Das Dokument wird gepackt und vor weiteren Transformationen geschuetzt.
	 * Zurueckgegeben wird das durch Transformationen geaenderte Office-Dokument.
	 * 
	 * @return
	 */
	public File closeDocument();
	
	
	/**
	 * Ueberprueft, ob 'fileDocument' eine gueltiges OpenDocument ist.
	 * 
	 * @param fileDocument
	 * @return
	 */
	public boolean isOfficeDocument(File fileDocument);
	
	
	public void showDocument();
	
	public void showDocument(File fileDocument);
	
	/**
	 * @param tableName
	 * @return
	 */
	
	
	public List<String>readTextTags(String textTag);
	
	public Source closeXSL();
	public void setXSLVariable(String name, String [] values);
	public void setXSLTable(String name, String [][] values);
	public String [] getXSLArray(String arrayName);
}
