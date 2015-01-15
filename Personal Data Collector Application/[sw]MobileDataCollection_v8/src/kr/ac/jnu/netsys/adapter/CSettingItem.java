package kr.ac.jnu.netsys.adapter;

public class CSettingItem
{
	private String name;
	private String value;
	
	public CSettingItem(String name, String value)
	{
		this.name = name;
		this.value = value;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	public String getValue()
	{
		return this.value;
	}
	
	
};

