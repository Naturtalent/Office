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
import org.eclipse.jface.wizard.Wizard;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;

import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.ODFDocumentUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Empfaenger;
import it.naturtalent.office.model.address.Referenz;

/**
 * Mit diesem DefaultWizard fragt der DefaultWriteAdapter die erforderlichen Daten ab und schreibt sie in das Dokument.
 * @see it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter
 * 
 * @author dieter
 *
 */
public class ODFDefaultWriteAdapterWizard extends Wizard
{
	// Selectinevents im MasterTree (@see ODFReceiverRenderer())
	public final static String SENDER_MASTERSELECTION_EVENT = "sendermasterselectinevent";
	public final static String RECEIVER_MASTERSELECTION_EVENT = "receivermasterselectinevent";
	
	public final static String RECEIVER_PAGE_NAME = "ODF_RECEIVER";
	public final static String SENDER_PAGE_NAME = "ODF_SENDER";
	
	protected IEclipseContext context;
	private Empfaenger empfaenger;
	private Absender absender;
	
	private ODFSenderWizardPage senderWizardPage;
	
	// das  betroffene ODF-Dokument
	private File odfDocumentFile;
	
	protected TextDocument odfDocument;
	//private TextDocument odfDocument;
	
	//private ODFReceiverWizardPage receiverWizardPage;
	//private ODFSenderWizardPage senderWizardPage;
	
	@PostConstruct
	private void postConstuct(IEclipseContext context)
	{
		this.context = context;
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

	@Override
	public boolean performFinish()
	{
		doPerformFinish();
		return true;
	}
	
	protected void doPerformFinish()
	{
		// die selektierten Adressen / Absender in das Dokument schreiben
		if((empfaenger != null) || (absender != null))
		{ 
			try
			{
				// ODFDokument oeffnen 
				odfDocument = TextDocument.loadDocument(odfDocumentFile);

				if (empfaenger != null)
				{
					// Empfaegerdaten in das Dokument schreiben
					Adresse adr = empfaenger.getAdresse();
					writeReceiverData(odfDocument);
	;			}

				if (absender != null)
				{
					// Absenderdaten im DialogSetting speichern
					senderWizardPage.storeSenderData();
					
					// Absenderdaten in das Dokument schreiben
					Adresse adr = absender.getAdresse();
					writeTransmitterData(odfDocument);
				}
				
				// ODFDokument speichern 
				odfDocument.save(odfDocumentFile);

			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	
	}
	
	/**
	 * Ein Absender wurde selektiert
	 * @param selObject
	 */
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

	/**
	 * Ein Empfaenger wurde selektiert
	 * @param selObject
	 */
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
	
	@Inject
	@Optional
	public void handleWriteFileDefinitionEvent(
			@UIEventTopic(IODFWriteAdapter.ODFWRITE_FILEDEFINITIONEVENT) Object writeFile)
	{
		if (writeFile instanceof IFile)
		{
			IFile iFile = (IFile) writeFile;				
			odfDocumentFile = iFile.getLocation().toFile();			
		}
	}
	
	/*
	 * Die Empfaengerdaten werden in das Dokument geschrieben
	 */
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
	
	/*
	 * Die Senderdaten werden in das Dokument geschrieben
	 */
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

	
}
