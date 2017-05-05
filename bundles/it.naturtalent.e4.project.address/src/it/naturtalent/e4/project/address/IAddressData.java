package it.naturtalent.e4.project.address;

public interface IAddressData
{
	public String getAnrede();
	public void setAnrede(String anrede);
	public String getName();
	public void setName(String name);
	public String getNamezusatz1();
	public void setNamezusatz1(String namezusatz1);
	public String getNamezusatz2();
	public void setNamezusatz2(String namezusatz2);
	public String getStrasse();
	public void setStrasse(String strasse);
	public String getPlz();
	public void setPlz(String plz);
	public String getOrt();
	public void setOrt(String ort);
	public String getType();
	public void setType(String type);	
	public Object clone();
}
