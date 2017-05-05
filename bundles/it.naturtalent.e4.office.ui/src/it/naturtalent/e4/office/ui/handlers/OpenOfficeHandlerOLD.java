package it.naturtalent.e4.office.ui.handlers;

import java.awt.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.OpenDocumentUtils;
import it.naturtalent.e4.office.ui.Activator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.JDOMFactory;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.XSLTransformer;
import org.osgi.framework.Bundle;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class OpenOfficeHandlerOLD
{
	
	@Inject
	@Optional INtOffice ntOffice;
	
	@Execute
	public void execute(MApplication application)
	{
		/*
		MWindow window = MBasicFactory.INSTANCE.createWindow();
		MPart part = MBasicFactory.INSTANCE.createPart();
		window.getChildren().add(part);
		application.getChildren().add(window);
		IEclipseContext context = part.getContext();
		ContextInjectionFactory.make(OfficeView.class, context);
		*/

		if(ntOffice != null)
		{
			// Dokument oeffnen
			File fileDoc = OpenDocumentUtils.getOfficeResource(IOfficeService.NTOFFICE_CONTEXT, "anschreiben.odt");
			ntOffice.openDocument(fileDoc);
			
			// transformieren
			//Source xslSource = OfficeUtils.getOfficeSource(INtOffice.NTOFFICE_WRITE, "xntw2oo.xsl");
			//ntOffice.transform(xslSource);
			
			URL xslURL = Activator.getPluginPath("/templates/xntw2oo.xsl");
			
			Source xslSource = new StreamSource(FileUtils.toFile(xslURL));
			//ntOffice.transform(xslSource);

			
			//System.out.println(xslSource.getSystemId());
			
			
			
			/*
			
			// Dokument oeffnen
			String documentPath = "/home/dieter/Repositories4/Naturtalent4/it.naturtalent.e4.office.ui/Stellungnahme/Stellungnahme.odt";
			//String documentPath = "D:\\Daten\\Repositories4\\Naturtalent4\\it.naturtalent.e4.office.ui\\Stellungnahme\\Stellungnahme.odt";
			ntOffice.openDocument(documentPath);
			
			// transformieren
			URL xslURL = Activator.getPluginPath("/filters/nt2ooo.xsl");
			String xslPath = xslURL.getPath();
			ntOffice.transform(xslPath);
			
			// schliessen
			ntOffice.closeDocument();
			
			*/
			
			ntOffice.showDocument();
			
		}
		
		
		try
		{			
			URL xslURL = Activator.getPluginPath("/filters/nt2ooo.xsl");
			String xslPath = xslURL.getPath();
			URL xmlURL = Activator.getPluginPath("/Stellungnahme/content.xml");
			String xmlPath = xmlURL.getPath();		
			
		/*
			Document transformResult = transformJDOM(xslPath, xmlPath);			
			if (transformResult != null)
			{
				URL outURL = Activator
						.getPluginPath("/Stellungnahme/output.xml");
				FileWriter outFile = new FileWriter(outURL.getPath());
				new XMLOutputter().output(transformResult, outFile);
				System.out.println("Transform ok");
			}			
			else
			{
				System.out.println("Transform misslungen");
			}
		*/
			
			
			 


		/*
			File fileResult = transform(xslPath, xmlPath);			
			URL outURL = Activator.getPluginPath("/Stellungnahme/output.xml");		
			FileUtils.copyFile(fileResult, FileUtils.toFile(outURL));
			System.out.println(FileUtils.readFileToString(fileResult));
			*/
		
			

		} catch (Exception e)
		{
			
			e.printStackTrace();
		}
	}

	@CanExecute
	public boolean canExecute()
	{
		// TODO Your code goes here
		return true;
	}


	
	
	private Document transformJDOM(String xslPath, String xmlPath)
	{
		File xslFile,xnlFile;
		
		if(StringUtils.isNotEmpty(xslPath) && StringUtils.isNotEmpty(xmlPath))
		{
			try
			{
				Document xslDoc = new SAXBuilder().build(new File(xslPath));
				
				Document xmlDoc = new SAXBuilder().build(new File(xmlPath));
				
				
				String s = System.getProperty("javax.xml.transform.TransformerFactory");
				
				XSLTransformer transformer = new XSLTransformer(new File(xslPath));

				
				
				
				
				return transformer.transform(xmlDoc);
				
				//return new XSLTransformer(new File(xslPath)).transform(xmlDoc);
				
				
				
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return null;
	}
	
	private File transform(String xslPath, String xmlPath)
	{		
		try
		{			
			Source xslt = new StreamSource(new File(xslPath));
			Source xmlContent = new StreamSource(new File(xmlPath));

			File tmpFile = File.createTempFile("xmlout", ".xml");
			tmpFile.deleteOnExit();

			Transformer transformer = TransformerFactory.newInstance().newTransformer(xslt);

			
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			
			// einruecken
			transformer.setParameter(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			transformer.transform(xmlContent, new StreamResult(tmpFile));
			
			
			return tmpFile;
			
		}catch (TransformerConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	private void exportFilter()
	{		
		try
		{
			URL xslURL = Activator.getPluginPath("/filters/ooo2nt.xsl");
			File xslFile = new File(xslURL.toURI().getPath());			
			Source xslt = new StreamSource(xslFile);
			
			URL xmlURL = Activator.getPluginPath("/Stellungnahme/content.xml");
			File xmlFile = new File(xmlURL.toURI().getPath());			
			Source xmlContent = new StreamSource(xmlFile);
			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(xslt);
			
			File tmpFile = File.createTempFile("xmlout", ".xml");
			tmpFile.deleteOnExit();
			transformer.transform(xmlContent, new StreamResult(tmpFile));
			
			System.out.println(xslt.getSystemId());
			
			
		} catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}