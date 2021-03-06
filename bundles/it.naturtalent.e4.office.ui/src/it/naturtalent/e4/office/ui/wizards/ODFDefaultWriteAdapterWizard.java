package it.naturtalent.e4.office.ui.wizards;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.odftoolkit.simple.TextDocument;

import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.e4.project.IResourceNavigator;
import it.naturtalent.e4.project.ui.navigator.ResourceNavigator;
import it.naturtalent.e4.project.ui.utils.RefreshResource;

/**
 * Dieser Wizard wird vom Adapter 'ODFDefaultWriteAdapter' bereitgestellt und ermoeglicht die erforderlichen Abfragen
 * mit dem von ihm wiederum erzeugten und hinzugefuegten WizardPages.
 * 
 * Ins Spiel gebracht wird der Wizard von dem Adapter 'ODFDefaultWriteAdapter' und ermoeglicht das Handling 
 * mit dem ODF-API und der Anwendung Libreoffice.  
 * @see it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter
 * 
 * Mit einem speziellen Context 'officeContext' koennen Objekte aus dem EMF-Modell (Referenzen, Signaturen, Fussnoten etc.) 
 * identifiziert werden (nicht verwechseln mit E4Context).  
 * 
 * Duiese Klasse dient als Grundlage fuer weitere spezifische Erweiterungen (z.B. Businesstexte) 
 * Mit 'addPages()' koennen die gewuenschten Pages eingefuegt werden
 * 
 * @author dieter
 *
 */
public class ODFDefaultWriteAdapterWizard extends Wizard
{	
	// in diesem Context arbeitet der Wizard, die WizardPages und die EMF-Renderer	
	// EMF-Objekte werden mit diesem Context identifizier 
	protected String officeContext;
	
	// Office-Praeferenzknoten
	protected IEclipsePreferences instancePreferenceNode;
	
	// erforderlich fuer das Erstellen der Pages via ContextInjectionFactory
	protected IEclipseContext context;
	
	private ESelectionService selectionService;

	// Modus-Flag zeigt an, ob der Wizard im New- (create) oder im OpenModus laeuft 
	// Flag wird vom jeweiligen Handler (OpenTextHandler/NewTextHandler) im E4Context abgelegt
	public final static boolean WIZARDCREATEMODE = false;
	public final static boolean WIZARDOPENMODE = true;
	protected boolean wizardModus = WIZARDCREATEMODE;
	
	// unter diesem Namen wird das Modus-Flag im E4Context hinterlegt
	public final static String E4CONTEXT_WIZARDMODE = "officewizardmode"; //$NON-NLS-N$
	
	// Write-Dokument als File
	protected File odfDocumentFile;
	
	// Writefile als ODF-Textdokument (Grundlage fuer den Zugriff mit dem Toolkit)
	protected TextDocument odfDocument;
	
	/**
	 * Konstruktion
	 */
	public ODFDefaultWriteAdapterWizard()
	{
		super();
		
		officeContext = OfficeDefaultPreferenceUtils.DEFAULT_OFFICE_CONTEXT;
		
		instancePreferenceNode = InstanceScope.INSTANCE
				.getNode(OfficeDefaultPreferenceUtils.ROOT_DEFAULTOFFICE_PREFERENCES_NODE);
		
		// den DefaultOffice-Context in den Eclipse4-Context einbringen (steuert die Filterung im Rederer)
		//setOfficeContext(DEFAULT_OFFICECONTEXT);
	}

	@PostConstruct
	private void postConstuct(IEclipseContext context, ESelectionService selectionService)
	{
		this.context = context;
		this.selectionService = selectionService;
		
		// der Wizard hinterlegt im E4Context den OfficeContext fuer die zugehoerigen Pages		
		E4Workbench.getServiceContext().set(OfficeUtils.E4CONTEXTKEY_OFFICECONTEXT, officeContext);
		
		// der Wizard hinterlegt im E4Context den Praeferenzknoten fuer die zugehoerigen Pages		
		E4Workbench.getServiceContext().set(OfficeUtils.E4CONTEXTKEY_OFFICEPRAEFERENZNODE, instancePreferenceNode);

		
		// Modus-Flag vom E4Context abfragen (wird vom Handler (NewTextHandle / OpenTextHandler) dort hinterlegtn
		if(context.containsKey(E4CONTEXT_WIZARDMODE));
		{
			wizardModus = (Boolean) context.get(E4CONTEXT_WIZARDMODE);
			
			// nach dem Sichern des WizardModus-Flags wird dieser aus dem IEclipse4-Context entfernt			
			context.remove(E4CONTEXT_WIZARDMODE);
		}
	}
	
	@PreDestroy
	private void preDestroy()
	{
		// E4Context wieder freigeben 
		E4Workbench.getServiceContext().remove(OfficeUtils.E4CONTEXTKEY_OFFICECONTEXT);
		
		E4Workbench.getServiceContext().remove(E4CONTEXT_WIZARDMODE);
		
		//performUnDo();
	}
	
	@Override
	public void addPages()
	{		
		// WizardPages (ODFReceiverWizardPage,ODFSenderWizardPage) erzeugen
		ODFBetreffWizardPage betreffWizardPage = ContextInjectionFactory.make(ODFBetreffWizardPage.class, context);
		ODFEmpfaengerWizardPage receiverWizardPage = ContextInjectionFactory.make(ODFEmpfaengerWizardPage.class, context);		
		ODFAbsenderWizardPage absenderWizardPage = ContextInjectionFactory.make(ODFAbsenderWizardPage.class, context);
		ODFReferenzWizardPage referenzWizardPage = ContextInjectionFactory.make(ODFReferenzWizardPage.class, context);
		ODFFootNoteWizardPage footNoteWizardPage = ContextInjectionFactory.make(ODFFootNoteWizardPage.class, context);
		ODFSignatureWizardPage signatureWizardPage = ContextInjectionFactory.make(ODFSignatureWizardPage.class, context);
		
		
		// WizardPages hinzufuegen
		addPage(betreffWizardPage);
		addPage(receiverWizardPage);
		addPage(absenderWizardPage);
		addPage(referenzWizardPage);		
		addPage(footNoteWizardPage);
		addPage(signatureWizardPage);
		
		
	}
	
	// im WIZARDOPENMODE lesen die WizardPages 'ihre' Daten aus dem goeffnenden Dokument		
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
						e.printStackTrace();
						// von der WizardPage kann nicht gelesen werden (existiert moeglicherweise nicht)
					}
				}
			}
		}
	}

	@Override
	public boolean performFinish()
	{
		// alle implizierten Pages schreiben Iíhren part in das Dokument
		doPerformFinish();
		return true;
	}
	
	/**
	 * Mit dieser Funktion ruft der Wizard alle seine Seiten auf und exekutiert die jeweiligen 
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
			odfDocument.close();
				
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
		performUnDo();
		return super.performCancel();
	}
	
	/**
	 * UnDo - Funktion in allen WizardPages aufrufen.
	 */
	private void performUnDo()
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
					writeWizardPage.unDo(odfDocument);
				}
			}
			
			// im Create-Mode wird die beeits erzeugte Datei wieder zurueckgenommen
			if(wizardModus == WIZARDCREATEMODE)		
			{
				// IConteiner der zuloeschende Resource ermitteln 
				IResource newCreatedResouce = (IResource) selectionService.getSelection(ResourceNavigator.RESOURCE_NAVIGATOR_ID);
				IResource parentResouce = newCreatedResouce.getParent();
				
				// Datei loeschen	
				FileUtils.forceDelete(odfDocumentFile);

				// Container refreshen
				RefreshResource refreshResource = new RefreshResource();
				refreshResource.refresh(Display.getDefault().getActiveShell(), parentResouce);					
				
				// Container im Navigator selektieren
				MApplication currentApplication = E4Workbench.getServiceContext().get(IWorkbench.class).getApplication();
				IEventBroker eventBroker = currentApplication.getContext().get(IEventBroker.class);				
				eventBroker.post(IResourceNavigator.NAVIGATOR_EVENT_SELECT_REQUEST, parentResouce);
			}		
			
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	
	}
	
	/**
	 * Der OpenTextHandler/NewTextHandler meldet die im ResourceNavigator selektierte Datei (LibreOffice Text-Datei)
	 * (@see it.naturtalent.e4.office.ui.handlers.OpenTextHandler)
	 * 
	 * Nur im OpenModus 'WIZARDOPENMODE' wird der Inhalt der Datei eingelesen (ODFTookit)
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
