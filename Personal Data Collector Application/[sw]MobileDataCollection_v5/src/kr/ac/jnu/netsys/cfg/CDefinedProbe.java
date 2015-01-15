package kr.ac.jnu.netsys.cfg;

public class CDefinedProbe {
	private static final String prefix_class = "edu.mit.media.funf.probe.builtin.";
	public static String[] name_class = 
	{
		
		prefix_class + "LocationProbe",
		prefix_class + "CellTowerProbe",
		prefix_class + "SimpleLocationProbe",
		prefix_class + "WifiProbe",
		prefix_class + "BluetoothProbe",
		
			
	};
	public static String[] name_display = 
	{
		"Continuous Location",
		"Nearby Cellular Towers",
		"Simple Location",
		"Nearby Wifi Devices",
		"Nearby Bluetooth Devices",
		
	};
	public static int[] category = 
	{
		5,5,5,5,5,
	};
	public static boolean[] hasDuration = 
		{
		 true, false , false, false, false,
		};
	
	public static boolean[] strict = 
		{
		 true, true , true, true, true,
		 
		};
	public static boolean[] opportunistic = 
		{
		 false, false , false, false, false,
		 
		};
	public static int[] minInterval = 
		{
		1,1,1,1,1,
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
