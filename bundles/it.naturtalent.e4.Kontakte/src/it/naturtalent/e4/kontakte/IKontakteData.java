package it.naturtalent.e4.kontakte;



import it.naturtalent.e4.project.address.AddressData;

import java.util.List;

public interface IKontakteData
{
	public java.lang.String getId();
	public void setId(String id);
	public java.lang.Integer getType();
	public void setType(java.lang.Integer type);
	public AddressData getAddress();
	public void setAddress(AddressData address);
	public List<String> getPhones();
	public void setPhones(List<String> phones);
	public List<String> getMobiles();
	public void setMobiles(List<String> mobiles);
	public List<String> getEmails();
	public void setEmails(List<String> emails);
	public List<String> getUrls();
	public void setUrls(List<String> urls);
	public List<BankData> getBanks();
	public void setBanks(List<BankData> banks);
	public String getNotice();
	public void setNotice(String notice);
}
