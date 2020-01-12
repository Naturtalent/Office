package it.naturtalent.e4.office.ui.dialogs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.e4.project.ui.Messages;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Kontakt;

public class CSVKontaktImportOperation implements IRunnableWithProgress
{

	// Syntax der Importdatei
    private static final String COMMA_DELIMITER = ";";
    private static final char COLUMN_DELIMITER = '"';
    
    // Positionen der Daten in einer Zeile
    private static final int NACHNAME_INDEX = 3;
    private static final int VORNAME_INDEX = 4;
    private static final int NAMEZUSATZ_INDEX = 6;
    private static final int STRASSE_INDEX = 12;
    private static final int PLZ_INDEX = 13;
    private static final int ORT_INDEX = 14;
    private static final int LAND_INDEX = 15;

    // Pfad zur CSV-Datei
	private String importPath;
	
	// die eingelesenen Importdatenstruktur
	private List<ExpImportData>lexpimpdata = new ArrayList<ExpImportData>();
	
	private BufferedReader br = null;
	
	// alle Kontaktzeilen werden in einer Liste gesammelt
	private List<String []> csvLines = new ArrayList<String[]>();

	
	public CSVKontaktImportOperation(String importPath)
	{
		super();
		this.importPath = importPath;
	}



	/**
	 * 
	 */
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
	{		
		try
		{
			BufferedReader br =  new BufferedReader(new FileReader(importPath));
			
			String line = "";			
			 //Read to skip the header
	        br.readLine();
	        
	        monitor.beginTask("Kontaktdaten werden eingelesen",IProgressMonitor.UNKNOWN);
	    
	        // Kontakte, Zeile fuer Zeile, aus der Datei lesen
			while ((line = br.readLine()) != null)
			{				
				String[] kontaktDetails = StringUtils.split(line, COMMA_DELIMITER);
				if(ArrayUtils.isNotEmpty(kontaktDetails))
					csvLines.add(kontaktDetails);				
			}
		} catch (Exception e)
		{			
			throw new InvocationTargetException(e);						
		}
		
		// Zeilen werden geparst und in EMF-Kontakte ueberfuehrt
		for(String [] csvLine : csvLines)
		{
			Kontakt kontakt = parseKontakt(csvLine);
			if(kontakt != null)
			{
				ExpImportData expimpdata = new ExpImportData();
				expimpdata.setLabel(kontakt.getName());
				expimpdata.setData(kontakt);
				lexpimpdata.add(expimpdata);											
			}			
		}
		
		monitor.done();
	}
	
	// aus einer einzelnen CSV-Zeile Kontakt und Adresse auslesen
	private Kontakt parseKontakt(String [] csvLine)
	{		
		String nachname = StringUtils.remove(csvLine[NACHNAME_INDEX], COLUMN_DELIMITER);
		if(StringUtils.isNotEmpty(nachname))
		{
			// EMF Kontakt erzeugen
			EClass kontaktClass = AddressPackage.eINSTANCE.getKontakt();
			Kontakt kontakt = (Kontakt) EcoreUtil.create(kontaktClass);
			EClass adressClass = AddressPackage.eINSTANCE.getAdresse();
			Adresse adresse = (Adresse) EcoreUtil.create(adressClass);
			kontakt.setAdresse(adresse);
								
			// Kontaktdaten uebernehemen (Nachname und Vorname)
			String kontaktname = nachname;
			String vorname = StringUtils.remove(csvLine[VORNAME_INDEX], COLUMN_DELIMITER);
			if(StringUtils.isNotEmpty(vorname))
				kontaktname = kontaktname + "," + vorname;
			kontakt.setName(kontaktname);
			
			String land = StringUtils.remove(csvLine[LAND_INDEX ], COLUMN_DELIMITER);
			if(StringUtils.isNotEmpty(land))
				kontakt.setKommunikation("<country>"+land+"</country>");
			
			// Adresse auslesen und zu Kontaktdaten hinzufuegen
			adresse.setName(vorname + " " + nachname);			
			adresse.setName2(StringUtils.remove(csvLine[NAMEZUSATZ_INDEX], COLUMN_DELIMITER));			
			adresse.setStrasse(StringUtils.remove(csvLine[STRASSE_INDEX], COLUMN_DELIMITER));
			
			// Plz zusaetzlich im eigenen Datenfeld 
			String plz = StringUtils.remove(csvLine[PLZ_INDEX], COLUMN_DELIMITER);
			adresse.setPlz(plz);
			
			// Plz und Ort gemeinsam in ein Datenfeld			
			String ort = StringUtils.remove(csvLine[ORT_INDEX], COLUMN_DELIMITER);			
			adresse.setOrt(plz+" "+ort);
			
			return kontakt;
		}	
		
		
		return null;
	}



	public List<ExpImportData> getLexpimpdata()
	{
		return lexpimpdata;
	}

	

}
