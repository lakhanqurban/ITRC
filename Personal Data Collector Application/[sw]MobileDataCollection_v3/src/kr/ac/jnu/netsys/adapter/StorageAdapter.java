package kr.ac.jnu.netsys.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import edu.mit.media.funf.wifiscanner.*;

import kr.ac.jnu.netsys.InputFilterMinMax;
import kr.ac.jnu.netsys.MainActivity;
import kr.ac.jnu.netsys.cfg.*;


public class StorageAdapter extends ArrayAdapter<CBaseObj>
{
	public class CStorageObj
	{
		String name;
		String value;
		int id;
		public CStorageObj(int id, String name, String value)
		{
			this.id = id;
			this.name = name;
			this.value = value;
		}
		public void setName(String name)
		{
			this.name = name;
		}
		public String getName()
		{
			return this.name;
		}
		public void setValue(String value)
		{
			this.value = value;
		}
		public String getValue()
		{
			return this.value;
		}
		public int getID()
		{
			return this.id;
		}
	};
	private ArrayList<CStorageObj> storageList;
	
	public StorageAdapter(Context context, int textViewResourceId)  
	{
		super(context, textViewResourceId);
		this.mContext = context;
		// TODO Auto-generated constructor stub
		
		//create the list manually
		
		this.storageList = new ArrayList<CStorageObj>();
		CStorageObj archiveInterval = new CStorageObj(0,"Archive Interval",CDefinedProbe.archive_interval);
		this.storageList.add(archiveInterval);
		
		CStorageObj archiveURL = new CStorageObj(1,"Archive Location",CDefinedProbe.archive_repository);
		this.storageList.add(archiveURL);
		
		CStorageObj uploadInterval = new CStorageObj(2,"Upload Interval",CDefinedProbe.upload_interval);
		this.storageList.add(uploadInterval);
		
		CStorageObj uploadURL = new CStorageObj(3,"Upload URL",CDefinedProbe.upload_interval);
		this.storageList.add(uploadURL);
		
	}

	private class ViewHolder
	{
		public TextView tvStorageName;		
		public EditText etStorageValue;
	}
	private Context mContext;
	public ViewHolder holder;

	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub\
		return storageList.size();
		
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	private void updateValue(int position,String newVal)
	{
		if(!newVal.equalsIgnoreCase(this.storageList.get(position).getValue()))
		{
			this.storageList.get(position).setValue(newVal);
			switch (position)
			{
			case 0: //archive interval
				CDefinedProbe.archive_interval = newVal;
				break;
			case 1: // archive URL
				CDefinedProbe.archive_repository = newVal;
				break;
			case 2: // upload interval
				CDefinedProbe.upload_interval = newVal;
				break;
			case 3: //upload URL
				CDefinedProbe.upload_url = newVal;
				break;
				
			}
			MainActivity.storeConfiguration_to_storage();
			MainActivity.reload_Scheduling();
			///update the prefs and reload
		}
	}
	@Override
	public View getView( int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if(convertView == null)
		{	
			final int curPos = position;
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.storage_row, null);
			holder = new ViewHolder();
			holder.tvStorageName = (TextView)convertView.findViewById(R.id.tvStorageName);
			holder.etStorageValue = (EditText)convertView.findViewById(R.id.etStorageValue);
			
			convertView.setTag(holder); 
			
			holder.etStorageValue.setOnEditorActionListener(new TextView.OnEditorActionListener() 
			{
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
				{
					if(actionId == EditorInfo.IME_ACTION_DONE || actionId==EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_PREVIOUS)
					{
						String curString = v.getText()+"";
						updateValue(curPos,curString);
						return false;
					}
					// TODO Auto-generated method stub
					return true;
				}
			});
			/*
			holder.etStorageValue.setOnFocusChangeListener(new View.OnFocusChangeListener()
			{

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					String curVal ;
					EditText t;
					if(!hasFocus)
					{
						t = (EditText)v;
						curVal = t.getText()+"";
						updateValue(curPos,curVal);
					}
				}
				
			});
			
			/*
			holder.etStorageValue.addTextChangedListener(new TextWatcher()
			{
				@Override
				public void afterTextChanged(Editable s) 
				{
					//update the value
					String curString = s.toString();
					boolean needReload = false;
					
					switch(curPos)
					{
					case 0: //archive interval
						if(curString.equalsIgnoreCase(CDefinedProbe.archive_interval)|| curString.isEmpty())
							break;
						CDefinedProbe.archive_interval = curString;
						needReload = true;
						break;
					case 1: // archive URL
						if(curString.equalsIgnoreCase(CDefinedProbe.archive_repository) || curString.isEmpty())
							break;
						CDefinedProbe.archive_repository = curString;
						needReload = true;
						break;
					case 2: // upload interval
						if(curString.equalsIgnoreCase(CDefinedProbe.upload_interval) || curString.isEmpty())
							break;
						CDefinedProbe.upload_interval = curString;
						needReload = true;
						break;
					case 3: //upload URL
						if(curString.equalsIgnoreCase(CDefinedProbe.upload_url) || curString.isEmpty())
							break;
						CDefinedProbe.upload_url = curString;
						needReload = true;
						break;
					}
					if(needReload == true)
					{ // store new setting to storage and reload
						MainActivity.storeConfiguration_to_storage();
						MainActivity.reload_Scheduling();
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					
				}
			});
			*/
			
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		// set name
		CStorageObj obj = storageList.get(position);
		switch(obj.id)
		{
		case 0: //archive interval
			holder.etStorageValue.setInputType(InputType.TYPE_CLASS_NUMBER);
			holder.etStorageValue.setFilters(new InputFilter[] { new InputFilterMinMax("0","2678400")});
			break;
		case 1: // archive URL
			holder.etStorageValue.setInputType(InputType.TYPE_CLASS_TEXT);
			break;
		case 2: // upload interval
			holder.etStorageValue.setInputType(InputType.TYPE_CLASS_NUMBER);
			holder.etStorageValue.setFilters(new InputFilter[] { new InputFilterMinMax("0","2678400")});
			break;
		case 3: //upload URL
			holder.etStorageValue.setInputType(InputType.TYPE_CLASS_TEXT);
			break;
		}
		holder.tvStorageName.setText(obj.getName());
		
		holder.etStorageValue.setText(obj.getValue());

		
		holder.tvStorageName.setTag(obj);
		holder.etStorageValue.setTag(obj);
			
		return convertView;
		

	}
	
	

}



