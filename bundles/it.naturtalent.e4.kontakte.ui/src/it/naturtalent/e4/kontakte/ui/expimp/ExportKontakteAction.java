package it.naturtalent.e4.kontakte.ui.expimp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.kontakte.ui.Activator;
import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.e4.project.expimp.dialogs.DefaultExportDialog;
import it.naturtalent.e4.project.expimp.dialogs.ExportNtProjektDialog;

import javax.inject.Inject;
import javax.xml.bind.JAXB;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class ExportKontakteAction extends Action
{

	@Inject @Optional private IEclipseContext context;
	@Inject @Optional private IEventBroker eventBroker;
	@Inject @Optional private IKontakteDataFactory kontakDataFactory;	

	
	public static final String EXPORT_KONTAKT_PATH_SETTING = "exportkontaktepathsetting"; //$NON-NLS-N$
	protected String exportSettingPath = EXPORT_KONTAKT_PATH_SETTING ;
	
	protected String modelCollectionName = IKontakteDataFactory.KONTAKTEDATA_COLLECTION_NAME;
	
	private IDialogSettings dialogSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();

	
	
	@Override
	public void run()
	{
		// Modell erzeugen 
		KontakteDataModel kontaktModel = kontakDataFactory.createModel(modelCollectionName);
		kontaktModel.loadModel();
		List<ExpImportData>lexpimpdata = new ArrayList<ExpImportData>();
		List<KontakteData>lData = kontaktModel.getKontakteData();
		for(KontakteData kontakteData : lData)
		{
			ExpImportData expimpdata = new ExpImportData();
			expimpdata.setLabel(kontakteData.getAddress().getName());
			expimpdata.setData(kontakteData);
			lexpimpdata.add(expimpdata);
		}
		
		DefaultExportDialog dialog = new DefaultExportDialog(Display.getDefault().getActiveShell());
		dialog.create();
		dialog.setEventBroker(eventBroker);				
		dialog.setExportPath(dialogSettings.get(exportSettingPath));		
		dialog.init(lexpimpdata);
		if(dialog.open() == DefaultExportDialog.OK)
		{
			ExpImportData [] selectedData = dialog.getSelectedData();
			if(ArrayUtils.isEmpty(selectedData))
				return;

			File tempFile;
			File exportFile;			
			String exportPath = dialog.getExportPath();

			switch (dialog.getExportOption())
			{
				// XML Format
				case ExportNtProjektDialog.EXPORTOPTION_XMLFORMAT:
					
					exportFile = new File(FilenameUtils.removeExtension(exportPath)+".xml");
					if(exportFile.exists())
					{
						if (!MessageDialog.openQuestion(Display.getDefault()
								.getActiveShell(),
								Messages.ExportKontakteDialog_OverwriteLabel,
								Messages.ExportKontakteDialog_OverwriteMessage))
								return;				
					}

					ByteArrayOutputStream out = new ByteArrayOutputStream();
					FileOutputStream fos = null;
					
					JAXBKontakteModel model = new JAXBKontakteModel();
					model.setKontakte(new ArrayList<KontakteData>());
					List<KontakteData>kontakte = model.getKontakte(); 
					for(ExpImportData expImpData : selectedData)				
						kontakte.add((KontakteData) expImpData.getData());				
					JAXB.marshal(model, out);
					
					try
					{
						fos = new FileOutputStream(exportFile);
						fos.write(out.toByteArray());
						dialogSettings.put(EXPORT_KONTAKT_PATH_SETTING, exportFile.getPath());
						//dialogSettings.put(EXPORT_ARCHIV_OPTION_SETTING, ProjectExportDialog.EXPORTOPTION_XMLFORMAT);
						
					} catch (Exception e)
					{
						MultiStatus info = new MultiStatus(Activator.PLUGIN_ID, 1, Messages.bind(Messages.ExportKontakteDialog_ErrorExport, exportPath), null);					
						info.add(new Status(IStatus.INFO, Activator.PLUGIN_ID, 1, e.getMessage(), null));
						ErrorDialog.openError(Display.getDefault().getActiveShell(), Messages.ExportKontakteDialog_ExportErrorMessageLabel, null, info);
												
					}finally
					{
						try
						{
							fos.close();
						} catch (IOException e)
						{
						}
					}
					break;

			}
		}
		
		
		/*
		ExportKontakteDialog dialog = ContextInjectionFactory.make(ExportKontakteDialog.class, context);	
		dialog.create();
		dialog.init(null,null);
		dialog.open();
		super.run();
		*/
		
	}
	

}
