package it.naturtalent.e4.office.ui.wizards;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;

import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.ODFDocumentUtils;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Empfaenger;
import it.naturtalent.office.model.address.Referenz;

/**
 * Mit diesem DefaultWizard fragt der DefaultWriteAdapter die erforderlichen Daten ab und schreibt sie in das Dokument.
 * @see it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter
 * Dient als Grundlage fuer spezifische Erweiterungen.
 * 
 * @author dieter
 *
 */
public class ODFDefaultWriteAdapterWizard extends Wizard
{
	// Selectionevents im MasterTree (@see ODFReceiverRenderer())
	//public final static String SENDER_MASTERSELECTION_EVENT = "sendermasterselectinevent";
	public final static String RECEIVER_MASTERSELECTION_EVENT = "receivermasterselectinevent";
	
	public final static String RECEIVER_PAGE_NAME = "ODF_RECEIVER";
	public final static String SENDER_PAGE_NAME = "ODF_SENDER";
	
	
	protected IEclipseContext context;
	private Empfaenger empfaenger;
	private Absender absender;
	
	private ODFSenderWizardPage senderWizardPage;
	
	// Flag zeigt an, ob der Wizard im New- (create) oder im OpenModus laeuft 	
	public final static String CONTEXTWIZARDMODE = "ContextWizardMode"; //$NON-NLS-N$
	public final static boolean WIZARDCREATEMODE = false;
	public final static boolean WIZARDOPENMODE = true;
	protected boolean wizardModus = WIZARDCREATEMODE;	
		
	// ODF-Dokument als File
	protected File odfDocumentFile;
	
	protected TextDocument odfDocument;
	//private TextDocument odfDocument;
	
	//private ODFReceiverWizardPage receiverWizardPage;
	//private ODFSenderWizardPage senderWizardPage;
	
	@PostConstruct
	private void postConstuct(IEclipseContext context)
	{
		this.context = context;
		
		// Modusflag wird vom Handler (NewTextHandle / OpenTextHandler) ueber den Context uebergeben
		if(context.containsKey(CONTEXTWIZARDMODE));
		{
			// Flag wird aus dem 'context' entfernt
			wizardModus = (Boolean) context.get(CONTEXTWIZARDMODE);
			context.remove(CONTEXTWIZARDMODE);
		}
	}
	
	@Override
	public void addPages()
	{
		// WizardPages (ODFReceiverWizardPage,ODFSenderWizardPage) erzeugen
		ODFReceiverWizardPage receiverWizardPage = ContextInjectionFactory.make(ODFReceiverWizardPage.class, context);
		senderWizardPage = ContextInjectionFactory.make(ODFSenderWizardPage.class, context);
		
		// WizardPages hinzufuegen
		addPage(receiverWizardPage);
		addPage(senderWizardPage);
	}
	
	// die WizardPages lesen 'ihre' Daten von der zuoeffnenden Datei		
	protected void readDocumentData()
	{		
		if (odfDocument != null)
		{
			IWizardPage[] allPages = getPages();
			for (IWizardPage page : allPages)
			{
				if (page instanceof IWriteWizardPage)
				{
					IWriteWizardPage writeWizardPage = (IWriteWizardPage) page;
					writeWizardPage.readFromDocument(odfDocument);
				}
			}
		}
	}

	@Override
	public boolean performFinish()
	{
		doPerformFinish();
		
		// speicher die EMF-Modelle im OfficeProject
		if(OfficeUtils.getOfficeProject().hasDirtyContents())
			ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
		
		return true;
	}
	
	protected void doPerformFinish()
	{
		try
		{
			odfDocument = TextDocument.loadDocument(odfDocumentFile);
			
			// IWriteWizardPage filtern und 'performOK() ausfuehren		
			IWizardPage [] allPages = getPages();
			for(IWizardPage page : allPages)
			{
				if (page instanceof IWriteWizardPage)
				{
					IWriteWizardPage writeWizardPage = (IWriteWizardPage) page;				
					writeWizardPage.writeToDocument(odfDocument);
				}
			}
			
			// ODFDokument speichern 
			odfDocument.save(odfDocumentFile);
				
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public boolean performCancel()
	{
		try
		{
			odfDocument = TextDocument.loadDocument(odfDocumentFile);
			
			// ggf Undo-Funkrionen in den Pages ausfuehren
			IWizardPage [] allPages = getPages();
			for(IWizardPage page : allPages)
			{
				if (page instanceof IWriteWizardPage)
				{
					IWriteWizardPage writeWizardPage = (IWriteWizardPage) page;				
					writeWizardPage.cancelPage(odfDocument);
				}
			}
			
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return super.performCancel();
	}
	
	
	
	/**
	 * Ein Absender wurde selektiert
	 * @param selObject
	 */
	/*
	@Inject
	@Optional
	public void handleSenderSelectionEvent(@UIEventTopic(SENDER_MASTERSELECTION_EVENT) Object selObject)
	{
		if (selObject instanceof Absender)	
		{
			absender = (Absender) selObject;
			return;
		}
		else 
			if (selObject instanceof Adresse)
			{
				Adresse adr = (Adresse) selObject;
				absender = (Absender) adr.eContainer();
				return;
			}
			else
				if (selObject instanceof Referenz)
				{
					Referenz ref = (Referenz) selObject;
					absender = (Absender) ref.eContainer();
					return;
				}
		
		absender = null;
	}
	*/

	/**
	 * Ein Empfaenger wurde selektiert
	 * @param selObject
	 */
	/*
	@Inject
	@Optional
	public void handleReceiverSelectionEvent(@UIEventTopic(RECEIVER_MASTERSELECTION_EVENT) Object selObject)
	{
		if (selObject instanceof Empfaenger)
		{
			empfaenger = (Empfaenger) selObject;
			return;
		}
		else
			if (selObject instanceof Adresse)
			{
				Adresse adr = (Adresse) selObject;
				empfaenger = (Empfaenger) adr.eContainer();
				return;
			}
		
		empfaenger = null;
	}
	*/
	
	/**
	 * Der OpenTextHandler/NewTextHandler meldet die im ResourceNavigator selektierte Datei (LibreOffice Text-Datei)
	 * (@see it.naturtalent.e4.office.ui.handlers.OpenTextHandler)
	 * 
	 * Nur im OpenModus wird der Inhalt der Datei eingelesen (ODFTookit)
	 * 
	 * @param writeFile
	 */
	@Inject
	@Optional
	public void handleWriteFileDefinitionEvent(
			@UIEventTopic(IODFWriteAdapter.ODFWRITE_FILEDEFINITIONEVENT) Object writeFile)
	{
		if (odfDocumentFile == null) // verhindert die Ausführung bei Mehrfachaufrufen
		{
			if (writeFile instanceof IFile)
			{
				IFile iFile = (IFile) writeFile;

				odfDocumentFile = iFile.getLocation().toFile();
				try
				{					
					odfDocument = TextDocument.loadDocument(odfDocumentFile);

					// im OpenModus wird der Dateiinhalt eingelesen
					if (wizardModus == WIZARDOPENMODE)
						readDocumentData();

				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}	
	
	/*
	 * das eingentliche Einlesen der Daten, wird von Erweiterungen spezifiziert
	 */
	protected void readDocumentContent()
	{
	}
	
	/*
	 * Die Empfaengerdaten werden in das Dokument geschrieben
	 */
	/*
	protected void writeReceiverData(TextDocument odfDocument)
	{
		if((empfaenger != null) || (odfDocument != null))
		{
			// Adresstabelle lesen
			Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITEADRESSE);
			if (table != null)
			{
				// Tabelle loeschen
				CellRange cellRange = ODFDocumentUtils.markTable(table); 
				ODFDocumentUtils.clearCellRange(cellRange);
				
				Adresse adr = empfaenger.getAdresse();
				int row = 0;
				
				String adrText = adr.getName();
				if(StringUtils.isNotEmpty(adrText))
					ODFDocumentUtils.writeTableText(table, row++, 0, adrText);
				
				adrText = adr.getName2();
				if(StringUtils.isNotEmpty(adrText))
					ODFDocumentUtils.writeTableText(table, row++, 0, adrText);
				
				adrText = adr.getName3();
				if(StringUtils.isNotEmpty(adrText))
					ODFDocumentUtils.writeTableText(table, row++, 0, adrText);

				adrText = adr.getStrasse();
				if(StringUtils.isNotEmpty(adrText))
					ODFDocumentUtils.writeTableText(table, row++, 0, adrText);

				adrText = adr.getOrt();
				if(StringUtils.isNotEmpty(adrText))
					ODFDocumentUtils.writeTableText(table, row++, 0, adrText);
			}
		}
	}
	*/
	
	/*
	 * Die Senderdaten werden in das Dokument geschrieben
	 */
	/*
	protected void writeTransmitterData(TextDocument odfDocument)
	{
		if((absender != null) || (odfDocument != null))
		{
			// Absender lesen
			Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITESENDER);
			if (table != null)
			{
				// Tabelle loeschen
				CellRange cellRange = ODFDocumentUtils.markTable(table); 
				ODFDocumentUtils.clearCellRange(cellRange);
				
				Adresse adresse = absender.getAdresse();
				StringBuilder builder = new StringBuilder();
				builder.append(adresse.getName());
				builder.append(adresse.getName2());
				ODFDocumentUtils.writeTableText(table, 0, 0, builder.toString());

				builder = new StringBuilder();
				builder.append(adresse.getStrasse());
				builder.append(adresse.getOrt());
				ODFDocumentUtils.writeTableText(table, 1, 0, builder.toString());

			}
		}
	}
	*/

	public boolean isWizardModus()
	{
		return wizardModus;
	}

	
}
