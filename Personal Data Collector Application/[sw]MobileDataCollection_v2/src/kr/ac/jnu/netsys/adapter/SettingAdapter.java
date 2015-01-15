package kr.ac.jnu.netsys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.mit.media.funf.wifiscanner.*;

import kr.ac.jnu.netsys.cfg.*;


public class SettingAdapter extends ArrayAdapter<CBaseObj>
{
	private ArrayList<CBaseObj> settingList;
	
	public SettingAdapter(Context context, int textViewResourceId, ArrayList<CBaseObj> lst)  
	{
		super(context, textViewResourceId,lst);
		this.mContext = context;
		// TODO Auto-generated constructor stub
		this.settingList = new ArrayList<CBaseObj>();
		this.settingList.addAll(lst);
		
	}

	private class ViewHolder
	{
		public TextView tvProbeName;		
		public TextView tvSettingValue;
	}
	private Context mContext;
	public ViewHolder holder;

	
	
	public void addItem(CBaseObj item)
	{
		this.settingList.add(item);
		 notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub\
		return settingList.size();
		
	}

	@Override
	public CBaseObj getItem(int position) {
		// TODO Auto-generated method stub
		return settingList.get(position);
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
			convertView = inflater.inflate(R.layout.setting_probe_row, null);
			holder = new ViewHolder();
			holder.tvProbeName = (TextView)convertView.findViewById(R.id.tvProbeName);
			holder.tvSettingValue = (TextView)convertView.findViewById(R.id.tvProbeSettingValue);
			
			convertView.setTag(holder); 
			holder.tvProbeName.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					//CheckedTextView ctv_tmp = (CheckedTextView)v;
					//CBaseObj obj = (CBaseObj)ctv_tmp.getTag();
					//
					//obj.isActive = !ctv_tmp.isChecked();
					//ctv_tmp.setChecked(!ctv_tmp.isChecked());
					
				}
			});
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		// set name
		CBaseObj obj = settingList.get(position);
		holder.tvProbeName.setText(obj.getDisplay_name());

		
		holder.tvSettingValue.setText(getValue(obj));

		
		holder.tvProbeName.setTag(obj);
		holder.tvSettingValue.setTag(obj);
			
		return convertView;
		

	}
	private String getValue(CBaseObj obj)
	{
		long days, hours =0, minutes = 0, seconds = 0;
    	long totalInterval = (long)obj.getInterval();
    	long totalDuration = (long)obj.getDuration();
    	days = (long)(totalInterval/86400);
    	hours = (long)(totalInterval - (days*86400))/3600;
    	minutes = (long)(totalInterval - (days*86400)-hours*3600)/60;
    	seconds = (long)(totalInterval - (days*86400)-hours*3600-minutes*60);
    		        	
    	String value = "";
    	if (totalInterval==0)
    		value = "Run always";
    	else
    	{
    		value += "Run every ";
        	if(days>0)
        		value += days+ " days ";
        	if(hours>0)
        		value += hours+ " hours ";
        	if(minutes>0)
        		value += minutes+ " minutes ";
        	if(seconds>0)
    		value += seconds + " seconds ";	
        	if(obj.hasDuration())
        	{
        		value += "for ";
        		days = (int)(totalDuration/86400);
	        	hours = (totalDuration - (days*86400))/3600;
	        	minutes = (totalDuration - (days*86400)-hours*3600)/60;
	        	seconds = (totalDuration - (days*86400)-hours*3600-minutes*60);	        	
	        	if(days>0)
	        		value += days+ " days ";
	        	if(hours>0)
	        		value += hours+ " hours ";
	        	if(minutes>0)
	        		value += minutes+ " minutes ";
	        	if(seconds>0)
	        		value += seconds + " seconds ";
        	}
    	}
    	return value;
	}
	

}



