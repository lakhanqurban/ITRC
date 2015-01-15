 package kr.ac.jnu.netsys;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kr.ac.jnu.netsys.cfg.*;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.JsonReader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.mit.media.funf.FunfManager;
import edu.mit.media.funf.json.IJsonObject;
import edu.mit.media.funf.pipeline.BasicPipeline;
import edu.mit.media.funf.pipeline.PipelineFactory;
import edu.mit.media.funf.probe.Probe.Base;
import edu.mit.media.funf.probe.Probe.DataListener;
import edu.mit.media.funf.probe.builtin.*;
import edu.mit.media.funf.storage.NameValueDatabaseHelper;
import edu.mit.media.funf.wifiscanner.R;


public class MainActivity extends Activity implements DataListener {

  //public static final String PIPELINE_NAME = "default";
  
  public static final String pipeline_config = "pipeline_config"; // this is used for fetching scheduling
  public static final String storage_config = "storage_config"; 	// this is used for storing all configurations including active status
  public static final String pref_storage_name = "kr.ac.jnu.netsys.storage";
  public static final String pref_scheduling_name = "kr.ac.jnu.netsys.scheduling";
  public static FunfManager funfManager;
  private BasicPipeline pipeline;
  public static WifiProbe wifiProbe;
  public static SimpleLocationProbe locationProbe;
  public static AccelerometerSensorProbe accelerometerProbe;
  public static DataListener listener;
  public static SharedPreferences prefs_scheduling;
  public static SharedPreferences prefs_storage;
  private Switch enabledCheckbox;
  
  

  public static Context appContext;
	
  public static ArrayList<CBaseObj> lst_probe_item = new ArrayList<CBaseObj>();
  
  private ServiceConnection funfManagerConn = new ServiceConnection() {    
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      funfManager = ((FunfManager.LocalBinder)service).getManager();
      
      //Gson gson = funfManager.getGson();
      pipeline = (BasicPipeline) funfManager.getRegisteredPipeline(pipeline_config);
      
      enabledCheckbox.setChecked(pipeline.isEnabled());
      enabledCheckbox.setText("ACTIVE");
      enabledCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener()
      {
  		@Override
  		public void onCheckedChanged(CompoundButton buttonView,
  				boolean isChecked) 
  		{
  			// TODO Auto-generated method stub
  			 if (funfManager != null) 
  			 {
  				 if (isChecked) 
  				 {
  					 funfManager.enablePipeline(pipeline_config);
  		             pipeline = (BasicPipeline) funfManager.getRegisteredPipeline(pipeline_config);
  				 }
  				 else
  				 {
  					 funfManager.disablePipeline(pipeline_config);
  				 }
  			 }
  		}
      	
      });
      
      // Set UI ready to use, by enabling buttons
      //enabledCheckbox.setEnabled(true);
    }
     
    @Override
    public void onServiceDisconnected(ComponentName name) {
      funfManager = null;
    }
  };
  @SuppressLint("NewApi")
@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
   
 // init the probe list from predefined probe items
    initProbe_list();
    
    prefs_storage = this.getSharedPreferences(  pref_storage_name, Context.MODE_PRIVATE);
    prefs_scheduling = this.getSharedPreferences(  pref_scheduling_name, Context.MODE_PRIVATE);
    
    MainActivity.fetchConfiguration_from_storage();
    
    //fetch scheduling
    String s = getScheduling_from_configuration();
	MainActivity.prefs_scheduling.edit().putString(MainActivity.pipeline_config, s).commit(); 
    
    //Init 3 tabs
    appContext = getApplicationContext();
    
   //ActionBar
    ActionBar actionbar = getActionBar();
    actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    
    ActionBar.Tab tabProbe = actionbar.newTab().setText("tab Probe");
    ActionBar.Tab tabSetting = actionbar.newTab().setText("tab Setting");
    ActionBar.Tab tabStorage = actionbar.newTab().setText("tab Storage");
    
    Fragment fragmentProbe = new ProbeActivity();
    Fragment fragmentSetting = new SettingActivity();
    Fragment fragmentStorage = new StorageActivity();

    tabProbe.setTabListener(new MyTabsListener(fragmentProbe));
    tabSetting.setTabListener(new MyTabsListener(fragmentSetting));
    tabStorage.setTabListener(new MyTabsListener(fragmentStorage));

    actionbar.addTab(tabProbe);
    actionbar.addTab(tabSetting);
    actionbar.addTab(tabStorage);
    
    //checkbox
    enabledCheckbox = (Switch)findViewById(R.id.swActive);
  
    // Bind to the service, to create the connection with FunfManager
    bindService(new Intent(this, FunfManager.class), funfManagerConn, BIND_AUTO_CREATE);
    
}


  @Override
  protected void onDestroy() {
	  
    super.onDestroy();
    
    unbindService(funfManagerConn);
  }

  private void initProbe_list()
  {
	  if(MainActivity.lst_probe_item.size()==0)
	  {
		  for (int i = 0 ; i <CDefinedProbe.name_class.length;i++)
		  {
			  CBaseObj obj = new CBaseObj(CDefinedProbe.category[i],CDefinedProbe.name_display[i],CDefinedProbe.name_class[i], CDefinedProbe.hasDuration[i],CDefinedProbe.minInterval[i],CDefinedProbe.opportunistic[i],CDefinedProbe.strict[i]);
			  MainActivity.lst_probe_item.add(obj);
		  }
		 
	  
	  }
  }
  private static int find(String name)
  {
	  int i = 0;
	  for (i = 0 ; i < MainActivity.lst_probe_item.size();i++)
	  {
		  if(MainActivity.lst_probe_item.get(i).getClass_name().equalsIgnoreCase(name))
			  return i;
	  }
	  return -1;
  }
  
  @Override
  public void onDataReceived(IJsonObject probeConfig, IJsonObject data) {
    // Not doing anything with the data
    // As an exercise, you could display this to the screen 
    // (Remember to make UI changes on the main thread)
  }

  @Override
  public void onDataCompleted(IJsonObject probeConfig, JsonElement checkpoint) 
  {

    // Re-register to keep listening after probe completes.
    //wifiProbe.registerPassiveListener(this);
   // locationProbe.registerPassiveListener(this);

  }
  

  	public static void fetchConfiguration_from_storage()
  	{
  	//get the pipeline string from the resource
  	    String str_stored_config =  prefs_storage.getString(storage_config, "");
  	    // construct the JsonObject from string;
  	    if(str_stored_config.isEmpty())
  	    	return;
  	    JsonObject json_pipeline = new JsonParser().parse(str_stored_config).getAsJsonObject();
  	    for (Map.Entry<String,JsonElement> entry : json_pipeline.entrySet()) 
  	    {
  	    	String l1_entry_key = entry.getKey();
  	    	JsonElement l1_entry_val = entry.getValue();
  	    	
  	    	if (l1_entry_val.isJsonObject()) //for archive, upload having schedules  
  			{
  	    		if(l1_entry_key.equalsIgnoreCase("archive"))
  	    		{
  	    			JsonObject l2_json_obj = l1_entry_val.getAsJsonObject();
  	    			if(l2_json_obj.has(PipelineFactory.SCHEDULE))
  	    			{
  	    				for (Map.Entry<String,JsonElement> l2_entry : l2_json_obj.entrySet())
  	    				{
  	    					JsonElement l2_entry_val  = l2_entry.getValue();
  	    					 if(l2_entry_val.isJsonObject())
  	    					 {
  	    						 JsonObject l3_json_obj = l2_entry_val.getAsJsonObject();
  		    					 for(Map.Entry<String,JsonElement> l3_entry : l3_json_obj.entrySet())
  		    					 {
  		    						 String l3_entry_key = l3_entry.getKey();
  		    						 JsonElement l3_entry_val  = l3_entry.getValue();
  		    	    				 if(l3_entry_key.equalsIgnoreCase("interval") && !l3_entry_val.isJsonObject()) 
  		    						 {
  		    	    						 String interval_archive_val = l3_entry_val.getAsString();
  		    	    						 CDefinedProbe.archive_interval = interval_archive_val;
  		    						 }
  		    					 }
  		    					
  	    					 }
  	    		         }
  	    			}
  	    		}
  	    		else if (l1_entry_key.equalsIgnoreCase("upload"))
  	    		{
  	    			JsonObject l2_json_obj = l1_entry_val.getAsJsonObject();
  	    			if(l2_json_obj.has(PipelineFactory.SCHEDULE))
  	    			{
  	    				for (Map.Entry<String,JsonElement> l2_entry : l2_json_obj.entrySet())
  	    				{
  	    					JsonElement l2_entry_val  = l2_entry.getValue();
  	    					if(l2_entry_val.isJsonObject())
 	    					 {
 	    						 JsonObject l3_json_obj = l2_entry_val.getAsJsonObject();
 		    					 for(Map.Entry<String,JsonElement> l3_entry : l3_json_obj.entrySet())
 		    					 {
 		    						 String l3_entry_key = l3_entry.getKey();
 		    						 JsonElement l3_entry_val  = l3_entry.getValue();
 		    	    				 if(l3_entry_key.equalsIgnoreCase("interval") && !l3_entry_val.isJsonObject()) 
 		    						 {
 		    	    						 String interval_archive_val = l3_entry_val.getAsString();
 		    	    						 CDefinedProbe.upload_interval = interval_archive_val;
 		    						 }
 		    					 }
 		    					
 	    					 }
  	    				 }
  	        		 }
  	    	    }
  			}
  	    	else if(l1_entry_val.isJsonArray() && l1_entry_key.equalsIgnoreCase("data")) // for data
  	    	{
  	    		JsonArray l2_json_arr = l1_entry_val.getAsJsonArray();
  	    		for (int i = 0 ; i < l2_json_arr.size();i++)
  	    		{
  	    			JsonObject l2_json_obj = l2_json_arr.get(i).getAsJsonObject();
	    			
  	    			//fetch the CBaseObj
    				String curType = "";
    				String interval_data_val = "";
    				String duration_data_val = "";
    				int idx = -1;
  	    			for (Map.Entry<String,JsonElement> l2_entry : l2_json_obj.entrySet())
 	        		{	
  	    				String l2_entry_key = l2_entry.getKey();
 	    				JsonElement l2_entry_val  = l2_entry.getValue();
 	    				

	    				//find the object in the object list having the same name with this
	    				if(l2_entry_key.equalsIgnoreCase("@type"))		//@type tag
	    				{
	    					curType = l2_entry_val.getAsString();
	    					idx = find(curType);
	    					if(idx == -1)
	    						return;
	    				}
	    				else if (l2_entry_key.equalsIgnoreCase("@isActive"))
	    				{
	    					boolean isActive = l2_entry_val.getAsBoolean();
	    					MainActivity.lst_probe_item.get(idx).setActive(isActive);
	    				}
	    				else if (l2_entry_val.isJsonObject() && l2_entry_key.equalsIgnoreCase(PipelineFactory.SCHEDULE)) //@schedule tag
	    				{
	    					JsonObject l3_json_obj = l2_entry_val.getAsJsonObject();
		    				for(Map.Entry<String,JsonElement> l3_entry : l3_json_obj.entrySet())
		    				{
		    					String l3_entry_key = l3_entry.getKey();
		    					JsonElement l3_entry_val  = l3_entry.getValue();
		    	    			if(l3_entry_key.equalsIgnoreCase("interval") && !l3_entry_val.isJsonObject()) 
		    					{
		    	    				interval_data_val = l3_entry_val.getAsString();	    	    						 
		    					}
		    	    			else if(l3_entry_key.equalsIgnoreCase("duration") && !l3_entry_val.isJsonObject()) 
		    					{
		    	    				duration_data_val = l3_entry_val.getAsString();
		    					}
		    				}
		    				MainActivity.lst_probe_item.get(idx).setSchedule_from_string(interval_data_val, duration_data_val);
	    				}
	    				else if(!l2_entry_val.isJsonObject())	//other tag: other configuration
	    				{
	    					MainActivity.lst_probe_item.get(idx).addConfig(l2_entry_key, l2_entry_val.getAsString());
	    				}
 	        		}
  	    		}
  	    	}
  	    }
  	}

  	public static  String getScheduling_from_configuration()
  	{
  		JsonObject root = new JsonObject();
  		//some header information
  		
  		root.addProperty("@type", CDefinedProbe.type_pipeline);
  	    root.addProperty("name", CDefinedProbe.name);
  	    root.addProperty("version", CDefinedProbe.version);
  	    
  	    
  	    //archive obj
	  	    JsonObject archive_obj = new JsonObject();
  	    if(!CDefinedProbe.archive_interval.isEmpty())
  	    {
  	    	
  	  	    JsonObject archive_obj_schedule = new JsonObject();
  	    	archive_obj_schedule.addProperty("interval", CDefinedProbe.archive_interval);
  	    	archive_obj.add("@schedule", archive_obj_schedule);
  	    	
  	    }
  	  root.add("archive", archive_obj);	
  	    	

  	    //upload (later)

  	    //data 
  		JsonArray data_array = new JsonArray();
  		for (int i = 0 ; i <MainActivity.lst_probe_item.size();i++)
  		{
  			CBaseObj obj = MainActivity.lst_probe_item.get(i);
  			if(obj.getActive()==true)
  			{
  				JsonObject json_obj = new JsonObject();
  				json_obj.addProperty("@type", obj.getClass_name());
  				if(obj.getInterval()>0)
  				{
  					JsonObject json_obj_schedule = new JsonObject();
  					json_obj_schedule.addProperty("interval", obj.getInterval()+"");
  					if(obj.hasDuration() && obj.getDuration()>0)
  						json_obj_schedule.addProperty("duration",obj.getDuration()+"");
  					
  					json_obj.add("@schedule", json_obj_schedule);
  				}
  				for (int j = 0; j< obj.getConfig_size();j++)
  					json_obj.addProperty(obj.getConfig_name(j), obj.getConfig_value(j));
  			
  				data_array.add(json_obj);
  			}
  		}
  	  			
  		return root.toString();
  	}
  	
  	// store changed configuration to the prefs, this is commonly used for all activities in the application
  	public static void storeConfiguration_to_storage()
  	{
  		//store two parts: 1. configuration setting for all probes including active status
  		JsonObject root = new JsonObject();
  		//some header information
  		
  		root.addProperty("@type", CDefinedProbe.type_pipeline);
  	    root.addProperty("name", CDefinedProbe.name);
  	    root.addProperty("version", CDefinedProbe.version);
  	    
  	    //archive obj
  	    JsonObject archive_obj = new JsonObject();
  	    JsonObject archive_obj_schedule = new JsonObject();
  	    
  	    if(!CDefinedProbe.archive_interval.isEmpty())
  	    	archive_obj_schedule.addProperty("interval", CDefinedProbe.archive_interval);
  	    
  	    archive_obj.add("@schedule", archive_obj_schedule);
  	    
  	    root.add("archive", archive_obj);
  	    //upload (later)
  	       
  	    //data 
  		JsonArray data_array = new JsonArray();
  		for (int i = 0 ; i <MainActivity.lst_probe_item.size();i++)
  		{
  			CBaseObj obj = MainActivity.lst_probe_item.get(i);
  			JsonObject json_obj = new JsonObject();
  			json_obj.addProperty("@type", obj.getClass_name());
  			
  			json_obj.addProperty("@isActive", obj.getActive());
  			
  			JsonObject json_obj_schedule = new JsonObject();
  			json_obj_schedule.addProperty("interval", obj.getInterval()+"");
  			if(obj.getDuration()>=0)
  				json_obj_schedule.addProperty("duration",obj.getDuration()+"");
  			json_obj.add("@schedule", json_obj_schedule);
  			
  			for (int j = 0; j< obj.getConfig_size();j++)
  				json_obj.addProperty(obj.getConfig_name(j), obj.getConfig_value(j));
  			data_array.add(json_obj);
  			
  		}
  		root.add("data", data_array);
  		
  	
  		String s = root.toString(); 
  		
    	prefs_storage.edit().putString(storage_config,s).commit();
  	}
  	
  	public static void reload_Scheduling()
  	{
  		String s = getScheduling_from_configuration();
  		MainActivity.prefs_scheduling.edit().putString(MainActivity.pipeline_config, s).commit(); 
  		funfManager.reload();
  	}
  	
}


class MyTabsListener implements ActionBar.TabListener
{
	public Fragment fragment;

	public MyTabsListener(Fragment fragment) 
	{
		this.fragment = fragment;
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) 
	{
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) 
	{
		ft.replace(R.id.fragment_container, fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) 
	{
		ft.remove(fragment);
	}

		
}
