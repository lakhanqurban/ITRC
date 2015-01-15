package kr.ac.jnu.netsys.cfg;

import java.util.ArrayList;

public class CBaseObj {
	private String name_display;
	private String name_class;
	
	//configuration
	private ArrayList<String> config_name;
	private ArrayList<String> config_value;

	// schedule
	private long duration;
	private long interval;
	private boolean opportunistic;
	private boolean strict;
	
	private boolean bhasDuration;
	private boolean isActive;
	
	private int type;
	private int minInterval;
	
	private int category;
	
	public CBaseObj(int category, String name_display, String name_class, boolean hasDuration, int minInterval, boolean opportunistic, boolean strict)
	{
		this.category = category;
		this.name_class = name_class;
		this.name_display = name_display;
		this.bhasDuration = hasDuration;
		this.minInterval = minInterval;
		
		this.duration = 0;
		this.interval = 0;
		this.config_name = new ArrayList<String>();
		this.config_value = new ArrayList<String>();
	}
	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}
	public boolean getActive()
	{
		return isActive;
	}
	public void addConfig(String name, String value)
	{
		this.config_name.add(name);
		this.config_value.add(value);
	}
	public int getConfig_size()
	{
		return this.config_name.size();
	}
	public String getConfig_name(int index)
	{
		return this.config_name.get(index);
	}
	public String getConfig_value(int index)
	{
		return this.config_value.get(index);
	}
	public void setSchedule_from_string(String interval, String duration)
	{
		if(!interval.isEmpty())
			this.interval = Long.parseLong(interval);
		if(!duration.isEmpty())
			this.duration = Long.parseLong(duration);
	}
	public long getInterval()
	{
		return this.interval;
	}
	public long getDuration()
	{
		return this.duration;
	}
	
	public boolean getOpportunistic()
	{
		return this.opportunistic;
	}
	public boolean getStrict()
	{
		return this.strict;
	}
	public String getClass_name()
	{
		return this.name_class;
	}
	public String getDisplay_name()
	{
		return this.name_display;
	}
	public boolean hasDuration()
	{
		return bhasDuration;
	}
	public void setHasDuration(boolean hasDuration)
	{
		this.bhasDuration = hasDuration;
	}
	public void setInterval(String value)
	{
		if(!value.isEmpty())
			this.interval = Long.parseLong(value);
	}
	public void setDuration(String value)
	{
		if(!value.isEmpty())
			this.duration =Long.parseLong(value);
	}
	public int getMinInterval()
	{
		return this.minInterval;
	}
}
