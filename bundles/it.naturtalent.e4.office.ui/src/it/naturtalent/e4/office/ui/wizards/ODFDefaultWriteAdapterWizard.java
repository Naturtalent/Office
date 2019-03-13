package it.naturtalent.e4.office.ui.wizards;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.resources.IFile;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.odftoolkit.simple.TextDocument;

import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.OfficeUtils;

/**
 * Mit diesem DefaultWizard fragt der DefaultWriteAdapter die erforderlichen Daten ab und schreibt sie in das Dokument.
 * 
 * Adapter fuer das Handling mit dem TextDokument
 * @see it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter
 * 
 * Dient als Grundlage fuer spezifische Erweiterungen. 
 * Mit 'addPages()' koennen die gewuenschten Pages eingefuegt werden
 * 
 * @author dieter
 *
 */
public class ODFDefaultWriteAdapterWizard extends Wizard
{
	
	//public final static String RECEIVER_PAGE_NAME = "ODF_RECEIVER";
	//public final static String SENDER_PAGE_NAME = "ODF_SENDER";
	
	// erforderlich fuer das Erstellen der Pages via ContextInjectionFactory
	protected IEclipseContext context;
	
	// OfficeKontext indem der Wizard arbeitet
	public static final String DEFAULT_OFFICECONTEXT = "officecontext";
	protected String officeContext = DEFAULT_OFFICECONTEXT;

	// Modus-Flag zeigt an, ob der Wizard im New- (create) oder im OpenModus laeuft 		
	public final static boolean WIZARDCREATEMODE = false;
	public final static boolean WIZARDOPENMODE = true;
	protected boolean wizardModus = WIZARDCREATEMODE;
	
	// Name mit dem Modus-Flag in den Eclipse4-Context kolportiert wird
	public final static String CONTEXTWIZARDMODE = "ContextWizardMode"; //$NON-NLS-N$
		
	// Write-Dokument als File
	protected File odfDocumentFile;
	
	// Writefile als ODF-Textdokument (Grundlage fuer den Zugriff mit dem Toolkit)
	protected TextDocument odfDocument;
	
	// Label des aus dem Dokument gelesenen Absenders
	static final String LOADED_EMPFAENGER = "Empfänge aus der Datei";
	static final String LOADED_ABSENDER = "Absender aus der Datei";
	
	/**
	 * Konstruktion
	 */
	public ODFDefaultWriteAdapterWizard()
	{
		super();
		
		// den DefaultOffice-Context in den Eclipse4-Context einbringen (steuert die Filterung im Rederer)
		setOfficeContext(DEFAULT_OFFICECONTEXT);
	}

	@PostConstruct
	private void postConstuct(IEclipseContext context)
	{
		this.context = context;
		
		// Modus-Flag wird vom Handler (NewTextHandle / OpenTextHandler) ueber den Ecpipse4-Context uebergeben
		if(context.containsKey(CONTEXTWIZARDMODE));
		{
			wizardModus = (Boolean) context.get(CONTEXTWIZARDMODE);
			
			// nach dem Sichern des WizardModus-Flags wird dieser aus dem IEclipse4-Context entfernt			
			context.remove(CONTEXTWIZARDMODE);
		}
	}
	
	@PreDestroy
	private void preDestroy()
	{
		E4Workbench.getServiceContext().remove(DEFAULT_OFFICECONTEXT);
	}
	
	/*
	 * Im Eclipse4 Context wird ein weiterer benutzerdefinierter Context eingebracht.
	 * Dieser Context hat den Namen 'OfficeUtils.OFFICE_CONTEXT' und den Wert 'officeContexct'
	 */
	public void setOfficeContext(String officeContext)
	{
		this.officeContext = officeContext;	
		E4Workbench.getServiceContext().set(DEFAULT_OFFICECONTEXT, officeContext);
	}
	
	public String getOfficeContext()
	{
		return officeContext;
	}

	@Override
	public void addPages()
	{
		// WizardPages (ODFReceiverWizardPage,ODFSenderWizardPage) erzeugen
		ODFEmpfaengerWizardPage receiverWizardPage = ContextInjectionFactory.make(ODFEmpfaengerWizardPage.class, context);
		ODFAbsenderWizardPage absenderWizardPage = ContextInjectionFactory.make(ODFAbsenderWizardPage.class, context);
		
		// WizardPages hinzufuegen
		addPage(receiverWizardPage);
		addPage(absenderWizardPage);
		addPage(ContextInjectionFactory.make(ODFSignatureWizardPage.class, context));
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
					try
					{
						writeWizardPage.readFromDocument(odfDocument);
					} catch (Exception e)
					{
						// von der WizardPage kann nicht gelesen werden (existiert moeglicherweise nicht)
					}
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
	
	/**
	 * Mit dieser Funktion ruft der Wizard alle seine Seiten auf und executiert die jeweiligen 
	 * 'writeToDocument(odfDocument)' Funktionen der Pages. 
	 * 
	 * Es werden nur die Pages betrachtet, die das Interface 'IWriteWizardPage' implementieren.
	 */
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

	/**
	 * Die Funktion ruft alle assoziierten Pages auf und executiert die jeweiligen 'cancelPage(odfDocument)' 
	 * Funktionen der Pages. Es werden nur die Pages betrachtet, die das Interface 'IWriteWizardPage' implementieren.
	 */

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
						
						BusyIndicator.showWhile(Display.getDefault(), new Runnable()
						{
							@Override
							public void run()
							{
								// projectExportDialog.createDialogArea(Composite parent) via Busyindicator aufrufen
								readDocumentData();
							}
						});
						
						

				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}	
	
	/**
	 * den Modus-Flag abfragen
	 * @return
	 */
	public boolean isWizardModus()
	{
		return wizardModus;
	}

	
}
