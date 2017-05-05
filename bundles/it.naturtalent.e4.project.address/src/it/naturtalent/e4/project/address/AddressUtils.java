package it.naturtalent.e4.project.address;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class AddressUtils
{
	public static String createClipboardText(AddressData address)
	{
		String value = null;
		List<String>properties = new ArrayList<String>();
				
		for(int i = 0;i < 7;i++)
		{
			switch (i)
				{
					case 0: 
												 
						value = address.getType();
						if(StringUtils.equals(value, AddressData.TYPE_PRIVATE))
						{
							properties.add(StringUtils.isNotEmpty(value) ? value : "");
							value = address.getAnrede();							
						}
						break;
						
					case 1: value = address.getName();break;
					case 2:	value = address.getNamezusatz1(); break;
					case 3: value = address.getNamezusatz2(); break;
					case 4: value = address.getStrasse(); break;
					case 5: value = address.getPlz(); break;
					case 6: value = address.getOrt(); break;
					default: value = ""; break;
				}
			
			properties.add(StringUtils.isNotEmpty(value) ? value : " ");			
		}
		
		String [] lines = properties.toArray(new String[properties.size()]);
		return StringUtils.join(lines,";");
	}
	
	public static AddressData addressDataFromClipboard(String clipboardText)
	{
		if (StringUtils.startsWith(clipboardText, AddressData.TYPE_PUBLIC)
				|| StringUtils.startsWith(clipboardText, AddressData.TYPE_PRIVATE))
		{			
			String [] lines = StringUtils.split(clipboardText, ";");			
			if((lines.length >= 6) && (lines.length < 9))
			{	
				AddressData address = new AddressData();
				String type = lines[0];
				for (int i = 0; i < lines.length; i++)
				{
					if (StringUtils.equals(type, AddressData.TYPE_PRIVATE))
					{
						switch (i)
						{
							case 0:address.setType(type);break;
							case 1: address.setAnrede(lines[i]); break;
							case 2: address.setName(lines[i]); break;
							case 3: address.setNamezusatz1(lines[i]); break;
							case 4: address.setNamezusatz2(lines[i]); break;
							case 5: address.setStrasse(lines[i]); break;
							case 6: address.setPlz(lines[i]); break;
							case 7: address.setOrt(lines[i]); break;						
							default: break;
						}
					}
					else
					{
						switch (i)
						{
							case 0:address.setType(type);break;							
							case 1: address.setName(lines[i]); break;
							case 2: address.setNamezusatz1(lines[i]); break;
							case 3: address.setNamezusatz2(lines[i]); break;
							case 4: address.setStrasse(lines[i]); break;
							case 5: address.setPlz(lines[i]); break;
							case 6: address.setOrt(lines[i]); break;						
							default: break;
						}
					}
				}
				return address;
			}
		}
		
		return null;
	}
	
	public static AddressData getAddressData(String [] rows)
	{
		int i = -1;
		AddressData address = new AddressData();
		
		// mit letzter Zeile beginnen (erwartet PLZ u Ort)
		for(i = rows.length - 1;i >= 0;i--)
		{
			if(StringUtils.isNotEmpty(rows[i]))
			{
				if(parseAddressPLZ(address, rows[i]))
					break;
			}					
		}
		
		if(--i >= 0) address.setStrasse(rows[i]);			

		if(--i >= 0) address.setNamezusatz2(rows[i]);			

		if(--i >= 0) address.setNamezusatz1(rows[i]);			

		if(--i >= 0) address.setAnrede(rows[i]);	
				
		rotateAddressName(address);
		return address;
	}
	
	private static void rotateAddressName(AddressData address)
	{
		if(StringUtils.isEmpty(address.getName()))
		{
			if(StringUtils.isNotEmpty(address.getNamezusatz1()))
			{
				address.setName(address.getNamezusatz1());
				address.setNamezusatz1(address.getNamezusatz2());
				address.setNamezusatz2("");
				return;
			}
			if(StringUtils.isNotEmpty(address.getNamezusatz2()))
			{
				address.setName(address.getNamezusatz2());
				address.setNamezusatz1("");
				address.setNamezusatz2("");
				return;
			}
		}
	}
	
	private static boolean parseAddressPLZ(AddressData address, String plzOrt)
	{
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(plzOrt);
		if(m.find())
		{			
			address.setPlz(StringUtils.substring(plzOrt, 0,
					StringUtils.indexOf(plzOrt, " ")));

			address.setOrt(StringUtils.substring(plzOrt, address.getPlz()
					.length()).trim());
			
			return true;
		}
		
		return (false);
	}

	public static String[] getAddressText(AddressData officeAddressData)
	{
		String[] result = new String[0];
		String stgValue;

		if (officeAddressData != null)
		{
			if (officeAddressData.getType().equals(AddressData.TYPE_PRIVATE))
			{
				// Anrede
				result = createAddressTextArray(officeAddressData.getAnrede(),
						result);

				// Vorname und Nachname
				stgValue = "";
				if (StringUtils.isNotEmpty(officeAddressData.getNamezusatz1()))
					stgValue = officeAddressData.getNamezusatz1();
				if (StringUtils.isNotEmpty(officeAddressData.getName()))
					stgValue = stgValue + " " + officeAddressData.getName();
				result = createAddressTextArray(stgValue, result);
			}
			else
			{
				// Name
				result = createAddressTextArray(officeAddressData.getName(), result);

				// Zusatz
				result = createAddressTextArray(officeAddressData.getNamezusatz1(),
						result);
				result = createAddressTextArray(officeAddressData.getNamezusatz2(),
						result);
			}

			// Strasse
			result = createAddressTextArray(officeAddressData.getStrasse(), result);

			// PLZ und Ort
			stgValue = "";
			if (StringUtils.isNotEmpty(officeAddressData.getPlz()))
				stgValue = officeAddressData.getPlz();
			if (StringUtils.isNotEmpty(officeAddressData.getOrt()))
				stgValue = stgValue + " " + officeAddressData.getOrt();
			result = createAddressTextArray(stgValue, result);
		}

		return result;
	}
	
	private static String [] createAddressTextArray(String item, String [] textArray)
	{
		if(StringUtils.isNotEmpty(item))
			textArray = ArrayUtils.add(textArray, item);		
		return textArray;
	}

}
