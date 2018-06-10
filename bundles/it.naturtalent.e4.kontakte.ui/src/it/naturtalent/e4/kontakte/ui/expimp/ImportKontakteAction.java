package it.naturtalent.e4.kontakte.ui.expimp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.kontakte.ui.Activator;
import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.address.AddressData;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.e4.project.expimp.dialogs.DefaultImportDialog;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;

import javax.inject.Inject;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class ImportKontakteAction extends Action
{

	@Inject @Optional private IEclipseContext context;	
	@Inject @Optional protected IEventBroker eventBroker;
	@Inject @Optional private IKontakteDataFactory kontakDataFactory;	
	
	// ein Importverzeichnis wurde selektiert
	private EventHandler eventHandler = new EventHandler()
	{
		@Override
		public void handleEvent(Event event)
		{
			// Daten der ausgewaehlten Datei lesen und dem Dialog uebergeben 
			importPath = (String) event.getProperty(IEventBroker.DATA);
			List<ExpImportData>expimpdata = readImportSource();
			importDialog.setModelData(expimpdata);
		}
	};

	
	private DefaultImportDialog importDialog;
	
	public static final String IMPORTPATH_SETTING = "importkontaktepathsetting"; //$NON-NLS-N$
	protected String importSettingPath = IMPORTPATH_SETTING;
	
	protected String modelCollectionName = IKontakteDataFactory.KONTAKTEDATA_COLLECTION_NAME;
	
	private String importPath;
	

	@Override
	public void run()
	{
		eventBroker.subscribe(DefaultImportDialog.IMPORTDESTINATION_EVENT, eventHandler);
		importDialog = ContextInjectionFactory.make(DefaultImportDialog.class, context);		
		importDialog.create();
		
		// Importpfad- und daten aus dem Setting vorbelegen
		IDialogSettings dialogSettings = importDialog.getDialogSettings();
		importPath = dialogSettings.get(importSettingPath);
		importDialog.setImportPath(importPath);
		List<ExpImportData>lexpimpdata = readImportSource();
		importDialog.setModelData(lexpimpdata);

		if(importDialog.open() == DefaultImportDialog.OK)
		{
			ExpImportData [] expImportData = importDialog.getSelectedData();
			if(ArrayUtils.isNotEmpty(expImportData))
			{
				Kontakte kontakte = OfficeUtils.getKontakte();
				for(ExpImportData impData : expImportData)
				{
					KontakteData kontakteData = (KontakteData) impData.getData();	
					
					AddressData adrData = kontakteData.getAddress();
					
					EClass kontaktClass = AddressPackage.eINSTANCE.getKontakt();
					Kontakt kontakt = (Kontakt) EcoreUtil.create(kontaktClass);					
					kontakt.setName(adrData.getName());

					// Adresse
					EClass adressClass = AddressPackage.eINSTANCE.getAdresse();
					Adresse adresse = (Adresse) EcoreUtil.create(adressClass);
					adresse.setName(adrData.getName());
					adresse.setName2(adrData.getNamezusatz1());
					adresse.setName3(adrData.getNamezusatz2());
					adresse.setOrt(adrData.getOrt());
					adresse.setStrasse(adrData.getStrasse());
					adresse.setPlz(adrData.getPlz());					
					kontakt.setAdresse(adresse);
					
					// Telekommunikation
					StringBuilder builder = new StringBuilder();
					List<String>phones = kontakteData.getPhones();
					if(phones != null)
					{
						for (String phone : phones)
							builder.append(phone + "\n");
					}
											
					List<String>mails = kontakteData.getEmails();
					if(mails != null)					
					for(String mail : mails)
						builder.append(mails+"\n");
					
					kontakt.setKommunikation(builder.toString()); 
					
					
					EList<Kontakt>kontaktList = kontakte.getKontakte();
					kontaktList.add(kontakt);
					
					//System.out.println(adrData.getName());
					
				}
				
				ECPProject officeProject = OfficeUtils.getOfficeProject();
				officeProject.saveContents();
				
				
				//doImport(expImportData, importDialog.isOverwritePermission());
				dialogSettings.put(importSettingPath, importPath);
			}

		}
		
		eventBroker.unsubscribe(eventHandler);
		super.run();
	}
	
	private void doImport(final ExpImportData[] expImportData, final boolean overwritePermission)
	{
		if (ArrayUtils.isNotEmpty(expImportData))
		{
			final List<String>importedContacts = new ArrayList<String>();
			final KontakteDataModel kontaktModel = kontakDataFactory.createModel();
			kontaktModel.setCollectionName(modelCollectionName);
			kontaktModel.loadModel();
			
			Runnable longJob = new Runnable()
			{
				@Override
				public void run()
				{
					for(ExpImportData impData : expImportData)
					{
						KontakteData kontakteData = (KontakteData) impData.getData();						
						KontakteData existData = kontaktModel.getData(kontakteData.getId());
						if(existData == null)
						{
							kontaktModel.addData(kontakteData);
							importedContacts.add(kontakteData.getId());
						}
						else
						{
							if(overwritePermission)
							{
								kontaktModel.addData(kontakteData);
								importedContacts.add(kontakteData.getId());
							}
						}
					}
					
					kontaktModel.saveModel();
					if((eventBroker != null) && (!importedContacts.isEmpty()))
						eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_IMPORTED_KONTAKTE, importedContacts);
				}
			};

			BusyIndicator.showWhile(Display.getDefault(), longJob);
		}
	}

	private List<ExpImportData> readImportSource()
	{
		List<ExpImportData>lexpimpdata = null;
		
		if(StringUtils.isEmpty(importPath))
			return null;
		
		File sourceFile = new File(importPath);
		if(sourceFile.exists())
		{
			FileInputStream fis = null;
			try
			{
				fis = new FileInputStream(sourceFile);
				JAXBKontakteModel jaxbModel = (JAXBKontakteModel) JAXB.unmarshal(fis, JAXBKontakteModel.class);
				if(jaxbModel != null)
				{
					lexpimpdata = new ArrayList<ExpImportData>();
					for(KontakteData kontakteData :jaxbModel.getKontakte())
					{
						ExpImportData expimpdata = new ExpImportData();
						expimpdata.setLabel(kontakteData.getAddress().getName());
						expimpdata.setData(kontakteData);	
						lexpimpdata.add(expimpdata);
					}					
				}									
				
			} catch (Exception e)
			{								
				MultiStatus info = new MultiStatus(Activator.PLUGIN_ID, 1, Messages.bind(Messages.ImportKontakteDialog_ErrorImport, importPath), null);
				info.add(new Status(IStatus.INFO, Activator.PLUGIN_ID, 1, e.getMessage(), null));
				ErrorDialog.openError(Display.getDefault().getActiveShell(), Messages.ImportKontakteDialog_ErrorImport, null, info);
				
			}finally
			{
				try
				{
					fis.close();
				} catch (Exception e)
				{
				}
			}			
		}
		return lexpimpdata;
	}

	

}
