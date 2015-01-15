package kr.ac.jnu.netsys;
import java.util.ArrayList;



import kr.ac.jnu.netsys.adapter.*;
import kr.ac.jnu.netsys.cfg.CBaseObj;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import edu.mit.media.funf.wifiscanner.*;



public class SettingActivity extends ListFragment {
	ArrayList<CBaseObj> probeList = MainActivity.lst_probe_item;
	SettingAdapter settingAdapter = null;
	public static int selected_item;
	public static Context mContext;
	public Handler _handler;
	/** Called when the activity is first created. */
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	  {
		  View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
		  
	        // Inflate the layout for this fragment       
		  _handler = new Handler(); 
		  	mContext = getActivity().getApplicationContext();
		    //display probe list
	        settingAdapter = new SettingAdapter(getActivity(),R.layout.setting_probe_row,probeList);
			setListAdapter(settingAdapter);
			
			return rootView;
	    }
	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id)
	  {
		  selected_item = position;
		  //Toast.makeText(MainActivity.appContext, position+" clicked", Toast.LENGTH_LONG);
		  
		  Intent setting_detail_activity = new Intent(v.getContext(),SettingDetailActivity.class);
		  startActivityForResult(setting_detail_activity,0);
		  //store changed configuration to the prefs
		  
		  
		  super.onListItemClick(l, v, position, id);
	  }
	  @Override
	  public void onResume()
	  {
		  settingAdapter.notifyDataSetChanged();
		  
		  super.onResume();
		  
	  }
}
