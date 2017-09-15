package it.naturtalent.e4.office.service.libo;

import it.naturtalent.e4.office.INtOffice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.ContentFilter;
import org.jdom2.filter.Filter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class XslDom
{
	public static final String NAMESPACE_XSL = "http://www.w3.org/1999/XSL/Transform";
	
	private Document doc;
	
	private Map<String,String> mapXSLValus = new HashMap<String, String>();
	{
		mapXSLValus.put(INtOffice.DOCUMENT_ABSENDER, "absRows");		
		mapXSLValus.put(INtOffice.DOCUMENT_ADRESSE, "adrRows");
		mapXSLValus.put(INtOffice.DOCUMENT_REFERENZ, "refRows");
		mapXSLValus.put(INtOffice.DOCUMENT_BETREFF, "betreffRows");
	}
	
	public XslDom(File xslFile)
	{		
		try
		{
			doc = new SAXBuilder().build(xslFile);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Document getDoc()
	{
		return doc;
	}

	public void setTextTag(String tagname, String [] tags)
	{
		if(doc != null)
		{
			tagname = mapXSLValus.get(tagname);
			Element elemParent = findXSLTag(doc, tagname);
			setTags(elemParent, tags);			
		}
	}
	
	public List<String> getTextTag(String tagname)
	{		
		if(doc != null)
		{
			List<String>texte = new ArrayList<String>();
			tagname = mapXSLValus.get(tagname);
			Element elemParent = findXSLTag(doc, tagname);
			
			List<Element>elems = elemParent.getChildren();
			for(Element elem : elems)
			{
				String s = elem.getText();
				System.out.println(elem.getName()+": "+s);
			}

		}
		
		return null;
	}
	
	public void setTextTable(String tagname, String [][] rowValues)
	{
		if(doc != null)
		{
			tagname = mapXSLValus.get(tagname);
			Element elemParent = findXSLTag(doc, tagname);
			
			List<Element>destElems = elemParent.getChildren();
			
			int destIdx = 0;
			for(String [] cell : rowValues)
			{
				for(int cellIdx = 0;cellIdx < cell.length;cellIdx++)
				{
					Element elemTag = destElems.get(destIdx++);
					if (elemTag != null)						
						elemTag.setText(cell[cellIdx]);
				}
			}
		}
	}

	
	private void setTags(Element elemParent, String [] tags)
	{
		List<Element>elemTags = elemParent.getChildren();
		for(Element elemTag : elemTags)
			elemTag.setText("");
		
		if(ArrayUtils.isNotEmpty(tags))
		{			
			int i = 1;
			for(String text : tags)
			{
				Element elemTag = elemTags.get(i++);
				if(elemTag == null)
					break;
				elemTag.setText(text);					
			}
		}		
	}

	private Element findXSLTag(Document xslDoc, String tag)
	{
		StringBuilder pathBuilder;
		
		pathBuilder = new StringBuilder("//"+tag);		
		Filter<Content> filter = new ContentFilter(ContentFilter.ELEMENT);
		
		XPathExpression<Content> xpath = XPathFactory.instance()
				.compile(pathBuilder.toString(),filter);
		
		return (Element) xpath.evaluateFirst(xslDoc);
	}

	
	private Element findXSLTagNamespace(Document xslDoc, String tag)
	{
		StringBuilder pathBuilder;
		
		pathBuilder = new StringBuilder("//"+"xsl:variable");
		pathBuilder.append("[@"+"name");
		pathBuilder.append("='"+tag+"']");
		
		Filter<Content> filter = new ContentFilter(ContentFilter.ELEMENT);
		
		XPathExpression<Content> xpath = XPathFactory.instance()
				.compile(pathBuilder.toString(),filter,null,Namespace
						.getNamespace("xsl",NAMESPACE_XSL));
		
		return (Element) xpath.evaluateFirst(xslDoc);
	}

}
