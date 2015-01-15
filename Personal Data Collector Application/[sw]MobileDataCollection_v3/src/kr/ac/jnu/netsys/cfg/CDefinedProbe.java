package kr.ac.jnu.netsys.cfg;

public class CDefinedProbe {
	private static final String prefix_class = "edu.mit.media.funf.probe.builtin.";
	public static String[] name_class = 
	{
		
		prefix_class + "AudioFeaturesProbe",
		prefix_class + "TemperatureSensorProbe",
		prefix_class + "LightSensorProbe",
		prefix_class + "ProximitySensorProbe",
		prefix_class + "MagneticFieldSensorProbe",
		prefix_class + "PressureSensorProbe",
		
		
			
	};
	public static String[] name_display = 
	{
		
		"Audio Features",
		"Temperature Sensor",
		"Light Sensor",
		"Proximity Sensor",
		"MagneticField Sensor",
		"Pressure Sensor",
		
		
	};
	public static int[] category = 
	{
		3,3,3,3,3,3,
		
	};
	public static boolean[] hasDuration = 
		{
		 true, true, true, true, true, true,
		 true, true, true, true ,true, true, true ,
		 
		};
	
	public static boolean[] strict = 
		{
		 true, true, true, true, true, true,
		 
		};
	public static boolean[] opportunistic = 
		{
		 false, false, false, false, false, false,
		 
		};
	public static int[] minInterval = 
		{
		3600,1,1,1,1,1,
		
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
