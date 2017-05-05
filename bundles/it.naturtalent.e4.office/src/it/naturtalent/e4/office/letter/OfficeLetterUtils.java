package it.naturtalent.e4.office.letter;

import it.naturtalent.e4.office.Activator;
import it.naturtalent.e4.office.OpenDocumentUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXB;

public class OfficeLetterUtils
{

	/**
	 * Die im OfficeContext 'officeContext' verfuegbaren Officeprofile laden.
	 * 
	 * @param officeContext
	 * @return
	 */
	public static OfficeLetterProfiles readProfiles(String officeContext)
	{
		File fileProfiles = new File(OpenDocumentUtils.getOfficeDir(officeContext),Activator.OFFICE_LETTERPROFILE_FILE);
		try
		{
			if (fileProfiles.exists())
			{
				FileInputStream fis = new FileInputStream(fileProfiles);
				return (OfficeLetterProfiles) JAXB.unmarshal(fis,
						OfficeLetterProfiles.class);
			}
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}	
	
	public static void writeProfiles(String officeContext, OfficeLetterProfiles officeProfiles)
	{
		FileOutputStream fos = null;
		File fileProfiles = new File(OpenDocumentUtils.getOfficeDir(officeContext),Activator.OFFICE_LETTERPROFILE_FILE);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JAXB.marshal(officeProfiles, out);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

		try
		{
			fos = new FileOutputStream(fileProfiles);
			fos.write(out.toByteArray());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fos.close();
			} catch (IOException e)
			{
			}
		}
	}
	
	public static void writeProfiles(File destFile, OfficeLetterProfiles officeProfiles)
	{
		FileOutputStream fos = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JAXB.marshal(officeProfiles, out);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

		try
		{
			fos = new FileOutputStream(destFile);
			fos.write(out.toByteArray());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fos.close();
			} catch (IOException e)
			{
			}
		}
	}
	

}
