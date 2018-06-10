package it.naturtalent.e4.office.ui.wizards;

import java.io.File;
import java.lang.ref.Reference;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
import it.naturtalent.e4.project.expimp.dialogs.ExportDestinationComposite;
import it.naturtalent.libreoffice.odf.ODFOfficeDocumentHandler;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Empfaenger;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Senders;

/**
 * Mit diesem Wizard fragt der DefaultWriteAdapter die erforderlichen Daten ab.
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
	
	private IEclipseContext context;
	private Empfaenger empfaenger;
	private Absender absender;
	
	// das  betroffene ODF-Dokument
	private File odfDocumentFile;
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
		ODFReceiverWizardPage receiverWizardPage = ContextInjectionFactory.make(ODFReceiverWizardPage.class, context);
		ODFSenderWizardPage senderWizardPage = ContextInjectionFactory.make(ODFSenderWizardPage.class, context);
		
		addPage(receiverWizardPage);
		addPage(senderWizardPage);
	}

	@Override
	public boolean performFinish()
	{
		if((empfaenger != null) || (absender != null))
		{					
			try
			{
				TextDocument odfDocument = TextDocument.loadDocument(odfDocumentFile);

				if (empfaenger != null)
				{
					Adresse adr = empfaenger.getAdresse();
					writeReceiverData(odfDocument);
	;			}

				if (absender != null)
				{
					Adresse adr = absender.getAdresse();
					System.out.println("Absender: " + adr.getName());
				}
				
				odfDocument.save(odfDocumentFile);

			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return true;
	}
	
	/**
	 * Absender selektiert
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
	 * Empfaenger selektiert
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
	
	private void writeReceiverData(TextDocument odfDocument)
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
				ODFDocumentUtils.writeTableText(table, 0, 0, adr.getName());
			}
		}
	}
	
}
