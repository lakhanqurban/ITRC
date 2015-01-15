package kr.ac.jnu.netsys.cfg;

public class CDefinedProbe {
	private static final String prefix_class = "edu.mit.media.funf.probe.builtin.";
	public static String[] name_class = 
	{
		
		
		prefix_class + "GravitySensorProbe",
		prefix_class + "RotationVectorSensorProbe",
		prefix_class + "AccelerometerSensorProbe",
		prefix_class + "LinearAccelerationSensorProbe",
		prefix_class + "ActivityProbe",
		prefix_class + "OrientationSensorProbe",
		prefix_class + "GyroscopeSensorProbe",
		
		
		
			
	};
	public static String[] name_display = 
	{
				
		"Gravity Sensor",
		"Rotation Vector Sensor",
		"Accelerometer Sensor",
		"Linear Accelerometer Sensor",
		"Activity",
		"Orientation Sensor",
		"Gyroscope Sensor",
		
		
		
	};
	public static int[] category = 
	{
		4,4,4,4,4,4,4,
		
	};
	public static boolean[] hasDuration = 
		{
		 true, true, true, true ,true, true, true ,
		};
	
	public static boolean[] strict = 
		{
		 true, true, true, true ,true, true, true ,
		 
		};
	public static boolean[] opportunistic = 
		{
		 false, false, false, false ,false, false, false ,
		 
		};
	public static int[] minInterval = 
		{
		1,1,1,1,1,1,1,
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
