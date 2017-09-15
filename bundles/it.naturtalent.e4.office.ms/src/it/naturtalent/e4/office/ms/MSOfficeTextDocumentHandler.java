package it.naturtalent.e4.office.ms;

import it.naturtalent.e4.office.IOfficeDocumentHandler;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageProperties;
import org.apache.poi.openxml4j.util.Nullable;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class MSOfficeTextDocumentHandler implements IOfficeDocumentHandler
{
	
	protected File fileDoc;
	
	private XWPFDocument document;
	
	private String documentProperty;
	private String authorProperty = "Nt Office"; //$NON-NLS-N$
	
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public void openOfficeDocument(File fileDoc)
	{
		document = null;
		documentProperty = null;

		String ext = FilenameUtils.getExtension(fileDoc.getPath());
		if (StringUtils.equals(ext, MS_OFFICETEXTDOCUMENT_EXTENSION))
		{

			try
			{
				this.fileDoc = fileDoc;
				FileInputStream inputStream = new FileInputStream(fileDoc);
				document = new XWPFDocument(OPCPackage.open(inputStream));
						
				if(document != null)
					readDocumentProperty();
				
			} catch (Exception e)
			{
				log.error(e);
			}
		}
	}
		
	private void readDocumentProperty()
	{
		try
		{
			OPCPackage opcPackage = document.getPackage();
			PackageProperties pp = opcPackage.getPackageProperties();
			Nullable<String> foo = pp.getContentTypeProperty();		
			if(foo.hasValue())		
				documentProperty = foo.getValue();
		} catch (Exception e)
		{
			log.error(e);
		}
		
	}
	
	private void writeDocumentProperty()
	{
		try
		{
			OPCPackage opcPackage = document.getPackage();
			PackageProperties pp = opcPackage.getPackageProperties();
			
			if(documentProperty != null)
				pp.setContentTypeProperty(documentProperty);
			
			if(authorProperty != null)
				pp.setCreatorProperty(authorProperty);
			
		} catch (Exception e)
		{
			log.error(e);
		}
	}
	

	@Override
	public void saveOfficeDocument()
	{
		FileOutputStream fos;
		try
		{
			writeDocumentProperty();
			fos = new FileOutputStream(fileDoc);
			document.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e)
		{			
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Office Fehler", e.getMessage());
		}
	}

	@Override
	public void showOfficeDocument()
	{		
		Job j = new Job("Show Document") //$NON-NLS-1$
		{
			@Override
			protected IStatus run(IProgressMonitor monitor)
			{
				try
				{
					//closeOfficeDocument();
					Desktop.getDesktop().open(fileDoc);
				} catch (IOException e)
				{
					log.error(e);
				}
				
				return Status.OK_STATUS;
			}
		};

		j.schedule();
	}

	@Override
	public void closeOfficeDocument()
	{
		
	}

	@Override
	public Object getDocument()
	{		
		return document;
	}

	@Override
	public String getProperty()
	{		
		return documentProperty;
	}

	@Override
	public void setProperty(String property)
	{
		this.documentProperty = property;		
	}

}
