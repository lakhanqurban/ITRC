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



public class StorageActivity extends ListFragment {
	
	public static int selected_item;
	StorageAdapter storageAdapter;
	public Handler _handler;
	/** Called when the activity is first created. */
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	  {
		  View rootView = inflater.inflate(R.layout.storage_setting, container, false);
		  // Inflate the layout for this fragment       
		  _handler = new Handler(); 
		  
		    //display probe list
		  storageAdapter = new StorageAdapter(getActivity(),R.layout.storage_row);
		  setListAdapter(storageAdapter);
			
			return rootView;
	    }
	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id)
	  {
		  //store changed configuration to the prefs
		  
		  
		  super.onListItemClick(l, v, position, id);
	  }
	  @Override
	  public void onResume()
	  {
		  
		  
		  super.onResume();
		  
	  }
}
