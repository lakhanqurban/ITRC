package kr.ac.jnu.netsys.cfg;

public class CDefinedProbe {
	private static final String prefix_class = "edu.mit.media.funf.probe.builtin.";
	public static String[] name_class = 
	{
		prefix_class + "ProcessStatisticsProbe",
		prefix_class + "ServicesProbe",
		prefix_class + "BatteryProbe",
		prefix_class + "TimeOffsetProbe",
		prefix_class + "TelephonyProbe",
		prefix_class + "HardwareInfoProbe",
		prefix_class + "AndroidInfoProbe",
		
			
	};
	public static String[] name_display = 
	{
		"Process Statistics",
		"Services",
		"Battery",
		"Time Offset",
		"Mobile Network Info",
		"HardwareInfo",
		"Android Info",
		
	};
	public static int[] category = 
	{
		1,1,1,1,1,1,1, // devices
		
	};
	public static boolean[] hasDuration = 
		{
		 false, false, false, false, false, false, false,
		  
		};
	
	public static boolean[] strict = 
		{
		true, true, true, true, true, true, true,
		};
	public static boolean[] opportunistic = 
		{
		 false, false, false, false, false, false, false,
		 
		 
		};
	public static int[] minInterval = 
		{
		1,1,1,1,86400,86400,86400,
		
		};
	//archive interval
	public static String archive_interval = "3600";  //default
	//archive Repository
	public static String archive_repository = "";
	
	//upload interval
	public static String upload_interval = "";
	//upload URL
	public static String upload_url="";
	
	
	//other fields in the string.xml
	public static String type_pipeline = "edu.mit.media.funf.pipeline.BasicPipeline";
	public static String name = "pipeline_configuration";
	public static String version = "1";
	
	

}
