package kr.ac.jnu.netsys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import java.util.ArrayList;

import edu.mit.media.funf.wifiscanner.*;

import kr.ac.jnu.netsys.*;
import kr.ac.jnu.netsys.cfg.*;

class probeItem
{
	public String name;
	public boolean isActive; //1: active, 0: deactive
};
public class ProbeAdapter extends ArrayAdapter<CBaseObj>
{
	private ArrayList<CBaseObj> probeList;
	
	public ProbeAdapter(Context context, int textViewResourceId, ArrayList<CBaseObj> probeList)  
	{
		super(context, textViewResourceId, probeList);
		this.mContext = context;
		// TODO Auto-generated constructor stub
		this.probeList = new ArrayList<CBaseObj>();
		this.probeList.addAll(probeList);	

		
		
	}

	private class ViewHolder
	{
		public CheckedTextView ctv;		
	}
	private Context mContext;
	public ViewHolder holder;
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub\
		return probeList.size();
		
	}

	@Override
	public CBaseObj getItem(int position) {
		// TODO Auto-generated method stub
		return probeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null)
		{	
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.probe_row, null);
			holder = new ViewHolder();
			holder.ctv = (CheckedTextView)convertView.findViewById(R.id.ctv_probe_row);
			convertView.setTag(holder); 
			holder.ctv.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					CheckedTextView ctv_tmp = (CheckedTextView)v;
					CBaseObj obj = (CBaseObj)ctv_tmp.getTag();
					
					obj.setActive(!ctv_tmp.isChecked());
					ctv_tmp.setChecked(!ctv_tmp.isChecked());
					//update the configuration and reload
					MainActivity.storeConfiguration_to_storage();
					MainActivity.reload_Scheduling();
				}
			});
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		// set name
		CBaseObj obj = probeList.get(position);
		holder.ctv.setText(obj.getDisplay_name());
		holder.ctv.setChecked(obj.getActive());
		holder.ctv.setFocusable(false);
		holder.ctv.setFocusableInTouchMode(false);
		holder.ctv.setTag(obj);
		
			
		return convertView;
		

	}
	

}



