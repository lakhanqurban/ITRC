package kr.ac.jnu.netsys.cfg;

public class CDefinedProbe {
	private static final String prefix_class = "edu.mit.media.funf.probe.builtin.";
	public static String[] name_class = 
	{
		
		prefix_class + "BrowserSearchesProbe",
		prefix_class + "BrowserBookmarksProbe",
		prefix_class + "ScreenProbe",
		prefix_class + "ApplicationsProbe",
		prefix_class + "AudioMediaProbe",
		prefix_class + "VideoMediaProbe",
		prefix_class + "ImageMediaProbe",
		prefix_class + "RunningApplicationsProbe",
		prefix_class + "AccountsProbe",
		
	
			
	};
	public static String[] name_display = 
	{
		
		"Browser Searches",
		"Browser Bookmarks",
		"Screen On/Off",
		"Applications",
		"Audio Media File Stats",
		"Video File Stats",
		"Image File Stats",
		"Running Applications",
		"Accounts",

	
		
	};
	public static int[] category = 
	{
		2,2,2,2,2,2,2,2,2,			//Device Interaction
	
	};
	public static boolean[] hasDuration = 
		{
		 false, false, true, false, false, false, false ,true , false,
		};
	
	public static boolean[] strict = 
		{
		true, true, true, true, true, true, true ,true , true,
		 
		};
	public static boolean[] opportunistic = 
		{
		 false, false, true, false, false, false, false ,true , false,
		 
		};
	public static int[] minInterval = 
		{
		86400,86400,1,86400,86400,86400,86400,1,86400,
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
