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
		
		prefix_class + "BrowserSearchesProbe",
		prefix_class + "BrowserBookmarksProbe",
		prefix_class + "ScreenProbe",
		prefix_class + "ApplicationsProbe",
		prefix_class + "AudioMediaProbe",
		prefix_class + "VideoMediaProbe",
		prefix_class + "ImageMediaProbe",
		prefix_class + "RunningApplicationsProbe",
		prefix_class + "AccountsProbe",
		
		prefix_class + "AudioFeaturesProbe",
		prefix_class + "TemperatureSensorProbe",
		prefix_class + "LightSensorProbe",
		prefix_class + "ProximitySensorProbe",
		prefix_class + "MagneticFieldSensorProbe",
		prefix_class + "PressureSensorProbe",
		
		prefix_class + "GravitySensorProbe",
		prefix_class + "RotationVectorSensorProbe",
		prefix_class + "AccelerometerSensorProbe",
		prefix_class + "LinearAccelerationSensorProbe",
		prefix_class + "ActivityProbe",
		prefix_class + "OrientationSensorProbe",
		prefix_class + "GyroscopeSensorProbe",
		
		prefix_class + "LocationProbe",
		prefix_class + "CellTowerProbe",
		prefix_class + "SimpleLocationProbe",
		prefix_class + "WifiProbe",
		prefix_class + "BluetoothProbe",
		
		prefix_class + "SmsProbe",
		prefix_class + "ContactProbe",
		prefix_class + "CallLogProbe",
		
			
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
		
		"Browser Searches",
		"Browser Bookmarks",
		"Screen On/Off",
		"Applications",
		"Audio Media File Stats",
		"Video File Stats",
		"Image File Stats",
		"Running Applications",
		"Accounts",

		
		"Audio Features",
		"Temperature Sensor",
		"Light Sensor",
		"Proximity Sensor",
		"MagneticField Sensor",
		"Pressure Sensor",
		
		"Gravity Sensor",
		"Rotation Vector Sensor",
		"Accelerometer Sensor",
		"Linear Accelerometer Sensor",
		"Activity",
		"Orientation Sensor",
		"Gyroscope Sensor",
		
		"Continuous Location",
		"Nearby Cellular Towers",
		"Simple Location",
		"Nearby Wifi Devices",
		"Nearby Bluetooth Devices",
		
		"SMS Logs",
		"Contacts",
		"Call Logs",
		
	};
	public static int[] category = 
	{
		1,1,1,1,1,1,1, // devices
		2,2,2,2,2,2,2,2,2,			//Device Interaction
		3,3,3,3,3,3,
		4,4,4,4,4,4,4,
		5,5,5,5,5,
		6,6,6,
	};
	public static boolean[] hasDuration = 
		{
		 false, false, false, false, false, false, false,
		 false, false, true, false, false, false, false ,true , false,
		 true, true, true, true, true, true,
		 true, true, true, true ,true, true, true ,
		 true, false , false, false, false,
		 false, false, false, 
		};
	
	public static boolean[] strict = 
		{
		true, true, true, true, true, true, true,
		true, true, true, true, true, true, true ,true , true,
		 true, true, true, true, true, true,
		 true, true, true, true ,true, true, true ,
		 true, true , true, true, true,
		 true, true, true,
		 
		};
	public static boolean[] opportunistic = 
		{
		 false, false, false, false, false, false, false,
		 false, false, true, false, false, false, false ,true , false,
		 false, false, false, false, false, false,
		 false, false, false, false ,false, false, false ,
		 false, false , false, false, false,
		 false, false, true,
		 
		};
	public static int[] minInterval = 
		{
		1,1,1,1,86400,86400,86400,
		86400,86400,1,86400,86400,86400,86400,1,86400,
		3600,1,1,1,1,1,
		1,1,1,1,1,1,1,
		1,1,1,1,1,
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
