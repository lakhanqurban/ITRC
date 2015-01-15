package kr.ac.jnu.netsys;


import edu.mit.media.funf.wifiscanner.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingDetailActivity extends Activity{
	private EditText etDuration;
	private EditText etInterval;
	private long oldInterval;
	private long oldDuration;
	
	SharedPreferences prefs;
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{          
		
		super.onCreate(savedInstanceState);    
		//no title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_detail); //main view is xml layout
        
      //set layout width height
        getWindow().setLayout ((int)(getWindowManager().getDefaultDisplay().getWidth()*0.9), (int)(getWindowManager().getDefaultDisplay().getHeight()*0.55));
        prefs = this.getSharedPreferences(MainActivity.pipeline_config, Context.MODE_PRIVATE);
        
        etDuration = (EditText)findViewById(R.id.etDuration);
        etInterval = (EditText)findViewById(R.id.etInterval);
        
        
        etDuration.setFilters(new InputFilter[] { new InputFilterMinMax("0","2678400")});
        etInterval.setFilters(new InputFilter[] { new InputFilterMinMax("0","2678400")});
        int idx = SettingActivity.selected_item;
        if(MainActivity.lst_probe_item.get(idx).hasDuration()==false)
        {
        	etDuration.setEnabled(false);
        }
        getSelectedValues();
        Button btnOK = (Button) findViewById(R.id.btn_OK_setting_detail);
        btnOK.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//finish this activity
				if(etDuration.getText().toString().isEmpty() || etInterval.getText().toString().isEmpty())
				{
					Toast.makeText(SettingDetailActivity.this, "Please fill the empty slot(s)", Toast.LENGTH_LONG).show();
					return;
				}
				long newInterval = Long.parseLong(etInterval.getText()+"");
				int idx = SettingActivity.selected_item;
				if(newInterval!= 0 && newInterval<MainActivity.lst_probe_item.get(idx).getMinInterval())
				{
					Toast.makeText(SettingDetailActivity.this, "The new value should be equal or larger than "+MainActivity.lst_probe_item.get(idx).getMinInterval()+" seconds", Toast.LENGTH_LONG).show();
					return;
				}
				Intent intent = new Intent();
				setResult(RESULT_OK,intent);
				SaveConfig();
				finish();
			}
        });
        
        
    }
	public void getSelectedValues()
	{
		int idx = SettingActivity.selected_item;
		oldInterval = MainActivity.lst_probe_item.get(idx).getInterval();
		oldDuration = MainActivity.lst_probe_item.get(idx).getDuration();
		etInterval.setText(MainActivity.lst_probe_item.get(idx).getInterval()+"");
		etDuration.setText(MainActivity.lst_probe_item.get(idx).getDuration()+"");
		
	}
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent();
		setResult(RESULT_OK,intent);
		SaveConfig();
		finish();
		
        return;
	 }
	public void SaveConfig()
	{
		int idx = SettingActivity.selected_item;
		
		long newInterval = Long.parseLong(etInterval.getText()+"");
		long newDuration = Long.parseLong(etDuration.getText()+"");
		if(newInterval != oldInterval || newDuration != oldDuration)
		{
			MainActivity.lst_probe_item.get(idx).setDuration(etDuration.getText()+"");
			MainActivity.lst_probe_item.get(idx).setInterval(etInterval.getText()+"");    	
    		//store changed configuration to the prefs and reload
			MainActivity.storeConfiguration_to_storage();
			MainActivity.reload_Scheduling();
		}
	
	}

}
