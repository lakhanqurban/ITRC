package kr.ac.jnu.netsys.cfg;

public class CDefinedProbe {
	private static final String prefix_class = "edu.mit.media.funf.probe.builtin.";
	public static String[] name_class = 
	{
		
		prefix_class + "SmsProbe",
		prefix_class + "ContactProbe",
		prefix_class + "CallLogProbe",
		
			
	};
	public static String[] name_display = 
	{
		
		"SMS Logs",
		"Contacts",
		"Call Logs",
		
	};
	public static int[] category = 
	{
		6,6,6,
	};
	public static boolean[] hasDuration = 
		{
		 false, false, false, 
		};
	
	public static boolean[] strict = 
		{
		 true, true, true,
		 
		};
	public static boolean[] opportunistic = 
		{
		 false, false, true,
		 
		};
	public static int[] minInterval = 
		{
		3600,86400,120,
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
