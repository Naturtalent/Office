package it.naturtalent.e4.office.odf.shapes;

import it.naturtalent.e4.office.odf.ODFDrawDocumentHandler;

import java.util.List;

import org.odftoolkit.odfdom.pkg.OdfElement;

public interface IDrawOdfElementModel
{
	public List<OdfElement>getOdfElements(String layer);
	public List<OdfElement>getOdfElements(String drawPage, String layer);
	public void openDrawDocument(String sourceDrawDocumentPath);
	public void closeDrawDocument();
	public ODFDrawDocumentHandler getOdfDrawDocumentHandler();
}
