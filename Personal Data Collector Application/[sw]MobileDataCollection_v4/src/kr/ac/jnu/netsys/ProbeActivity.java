package kr.ac.jnu.netsys;

import java.util.ArrayList;

import kr.ac.jnu.netsys.adapter.*;
import kr.ac.jnu.netsys.cfg.*;

import edu.mit.media.funf.wifiscanner.*;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ProbeActivity extends ListFragment 
{
	ProbeAdapter probeAdapter = null;
	ArrayList<CBaseObj> probeList = MainActivity.lst_probe_item;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
	    View rootView = inflater.inflate(R.layout.fragment_probe, container, false);
	    //String[] values = new String[] { "Message1", "Message2", "Message3" };
	  
	    //display probe list
		probeAdapter = new ProbeAdapter(getActivity(),R.layout.probe_row,probeList);
		setListAdapter(probeAdapter);
	    
	    
	    return rootView;

	}

	@Override
    public void onListItemClick(ListView l, View v, int position, long id) 
	{
		
        // TODO Auto-generated method stub
		//not do anything since it's controlled by the OnClickItem in the ProbeAdapter class
        super.onListItemClick(l, v, position, id);
       
    }
}
