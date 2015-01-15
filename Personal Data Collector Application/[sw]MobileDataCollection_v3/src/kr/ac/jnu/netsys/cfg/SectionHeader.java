package kr.ac.jnu.netsys.cfg;

public class SectionHeader {
	private String name;
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	public SectionHeader(String name)
	{
		this.name = name;
	}

}
