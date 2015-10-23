package com.ChristianSpicer.quitsmoking;

import com.ChristianSpicer.quitsmoking.CounterTimer.CountDownClock;
import com.ChristianSpicer.quitsmoking.CounterTimer.SmokeTimeClock;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class SecondMainActivity  extends FragmentActivity {
	
	Button smokeTime;
	TextView text;
	RingtoneManager mRingtoneManager;
	String title;
	Ringtone ringtone;
	Receiver1 Receiver1;
	Receiver2 Receiver2;
	long datapassed1;
	long datapassed2;
	TextView countdowntimer;
	TextView smokeTimetimer;
	TextView smokeTimeLabel;
	Switch onOffSwitch;
	Switch smokeTimeSwitch;
    TextView durationtimer;
    LinearLayout smoketime;
    CounterTimer counterTimer = new CounterTimer();
    SharedPreference sharedPreference = new SharedPreference();
    boolean mServiceBound = false;
    AsyncService interFace;
    
    @SuppressLint("HandlerLeak")
	Handler mHandler1 = new Handler()
    {
        @SuppressLint({ "DefaultLocale", "HandlerLeak" })
		@Override
        public void handleMessage(Message m)
        {
            Bundle b = m.getData();
            int Hour1 = b.getInt("set_hour");
            int Minute1 = b.getInt("set_minute");
            if (Hour1 == 0 && Minute1 == 0)
            {
            	Hour1 = sharedPreference.getValueCountDownClockHour(getApplicationContext());
            	Minute1 = sharedPreference.getValueCountDownClockMinute(getApplicationContext());
            	Toast.makeText(getApplicationContext(), "Zero Hour And Zero Minute Value Is Not Permitted", Toast.LENGTH_LONG).show();
            }
            String time = String.format("%02d:%02d", Hour1, Minute1);
            countdowntimer.setText(time);
            //Toast.makeText(getBaseContext(), "The Countdown Clock " + b.getString("set_time"), Toast.LENGTH_SHORT).show();
            if (sharedPreference.getValueCountDownClockHour(getApplicationContext()) < Hour1 || 
            		sharedPreference.getValueCountDownClockHour(getApplicationContext()) > Hour1 || 
            		sharedPreference.getValueCountDownClockMinute(getApplicationContext()) < Minute1 || 
            		sharedPreference.getValueCountDownClockMinute(getApplicationContext()) > Minute1)
			{
				sharedPreference.saveChange(getApplicationContext(), true);
			}
            sharedPreference.saveCountDownClockHour(AppContext.getContext(), Hour1);
            sharedPreference.saveCountDownClockMinute(AppContext.getContext(), Minute1);
        }
    };
    @SuppressLint("HandlerLeak")
	Handler mhandler2 = new Handler()
    {
        @SuppressLint("DefaultLocale")
		@Override
        public void handleMessage(Message m)
        {
            Bundle b = m.getData();
            int Hour2 = b.getInt("set_hour");
            int Minute2 = b.getInt("set_minute");
            String time = String.format("%02d:%02d", Hour2, Minute2);
            durationtimer.setText(time);
            //Toast.makeText(getBaseContext(), "The Add Duration " + b.getString("set_time"), Toast.LENGTH_SHORT).show();
            if (sharedPreference.getValueDurationHour(getApplicationContext()) < Hour2 || 
            		sharedPreference.getValueDurationHour(getApplicationContext()) > Hour2 ||
            		sharedPreference.getValueDurationMinute(getApplicationContext()) < Minute2 || 
            		sharedPreference.getValueDurationMinute(getApplicationContext()) > Minute2)
            {
            	sharedPreference.saveChange(getApplicationContext(), true);
            }
            sharedPreference.saveDurationHour(getApplicationContext(), Hour2);
            sharedPreference.saveDurationMinute(getApplicationContext(), Minute2);
        }
    };
    @SuppressLint("HandlerLeak")
	Handler mhandler3 = new Handler()
    {
        @SuppressLint("DefaultLocale")
		@Override
        public void handleMessage(Message m)
        {
            Bundle b = m.getData();
            int Hour3 = b.getInt("set_hour");
            int Minute3 = b.getInt("set_minute");
            if (Hour3 == 0 && Minute3 == 0)
            {
            	Hour3 = sharedPreference.getValueSmokeTimeHour(getApplicationContext());
            	Minute3 = sharedPreference.getValueSmokeTImeMinute(getApplicationContext());
            	Toast.makeText(getApplicationContext(), "Zero Hour And Zero Minute Value Is Not Permitted", Toast.LENGTH_LONG).show();
            }
            String time = String.format("%02d:%02d", Hour3, Minute3);
            smokeTimetimer.setText(time);
            //Toast.makeText(getBaseContext(), "The Finsh Of Smoko " + b.getString("set_time"), Toast.LENGTH_SHORT).show();
            sharedPreference.saveSmokeTimeHour(getApplicationContext(), Hour3);
            sharedPreference.saveSmokeTimeMinute(getApplicationContext(), Minute3);
        }
    };
	@SuppressLint("DefaultLocale")
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_main);
		countdowntimer = (TextView) findViewById(R.id.CountdownClock);
		durationtimer = (TextView) findViewById(R.id.DurationClock);
		text = (TextView)findViewById(R.id.ringtone);
		String ringtone = sharedPreference.getValue(getApplicationContext());
        text.setText(ringtone);
        int Hour1 = sharedPreference.getValueCountDownClockHour(AppContext.getContext());
        int Minute1 = sharedPreference.getValueCountDownClockMinute(AppContext.getContext());
        String time1 = String.format("%02d:%02d", Hour1, Minute1);
        countdowntimer.setText(time1);
        int Hour2 = sharedPreference.getValueDurationHour(getApplicationContext());
        int Minute2 = sharedPreference.getValueDurationMinute(getApplicationContext());
        String time2 = String.format("%02d:%02d", Hour2, Minute2);
        durationtimer.setText(time2);
        onOffSwitch = (Switch)  findViewById(R.id.on_off_switch); 
        onOffSwitch.setChecked(sharedPreference.getValueSwitch(getApplicationContext()));
        Receiver1 = new Receiver1();
        Receiver2 = new Receiver2();
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(CountDownClock.QUITSOMKING_ACTION1);
        registerReceiver(Receiver1, intentFilter1);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(SmokeTimeClock.QUITSOMKING_ACTION2);
        registerReceiver(Receiver2, intentFilter2);
        smokeTimeSwitch = (Switch) findViewById(R.id.smoketime_on_off_switch);
        smokeTimeSwitch.setChecked(sharedPreference.getValueSmokeTime(getApplicationContext()));
        smokeTime = (Button) findViewById(R.id.smoketimebutton);
        smokeTimeLabel = (TextView) findViewById(R.id.smoketime_label);
        smokeTimetimer = (TextView) findViewById(R.id.smoketimeClock);
        smoketime = (LinearLayout) findViewById(R.id.smoketime);
        int Hour3 = sharedPreference.getValueSmokeTimeHour(getApplicationContext());
        int Minute3 = sharedPreference.getValueSmokeTImeMinute(getApplicationContext());
        String time3 = String.format("%02d:%02d", Hour3, Minute3);
        smokeTimetimer.setText(time3);
        if (sharedPreference.getValueSmokeTime(getApplicationContext()))
		{
        	smoketime.setVisibility(LinearLayout.VISIBLE);
        	smokeTime.setVisibility(Button.VISIBLE);
		}
		else
		{
			smoketime.setVisibility(LinearLayout.GONE);
			smokeTime.setVisibility(Button.GONE);
		}
        smokeTimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
        {
			@Override
	    	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
	    		if (isChecked)
	    		{
	    			sharedPreference.saveSmokeTime(getApplicationContext(), isChecked);
	    	        //Toast.makeText(getApplicationContext(), "Smoke time Is On", Toast.LENGTH_LONG).show();
	    	        smoketime.setVisibility(LinearLayout.VISIBLE);
	    	        smokeTime.setVisibility(Button.VISIBLE);
	    		}
	    		else
	    		{
	    			sharedPreference.saveSmokeTime(getApplicationContext(), isChecked);
	    	        //Toast.makeText(getApplicationContext(), "Smoke time Is Off,", Toast.LENGTH_LONG).show();
	    	        smoketime.setVisibility(LinearLayout.GONE);
	    	        smokeTime.setVisibility(Button.GONE);
	    		}
	    	}       
    	});
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
        {
			@Override
	    	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
	    		if (isChecked)
	    		{
	    			sharedPreference.saveSwitch(getApplicationContext(), isChecked);
	    	        //Toast.makeText(getApplicationContext(), "Vibrate Is On", Toast.LENGTH_LONG).show();
	    	        counterTimer.startAlert(datapassed1 - 1000);
	    	        counterTimer.startSmokingAlert(datapassed2 - 1000);
	    		}
	    		else
	    		{
	    			sharedPreference.saveSwitch(getApplicationContext(), isChecked);
	    	        //Toast.makeText(getApplicationContext(), "Vibrate Is Off,", Toast.LENGTH_LONG).show();
	    	        counterTimer.cancelAlert();
	    		}
	    	}       
    	});
		OnClickListener listener1 = new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Bundle b = new Bundle();
				b.putInt("set_hour", sharedPreference.getValueCountDownClockHour(getApplicationContext()));
				b.putInt("set_minute", sharedPreference.getValueCountDownClockMinute(getApplicationContext()));
				TimePickerDialogFragment timePicker = new TimePickerDialogFragment(mHandler1);	                
				timePicker.setArguments(b);
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.add(timePicker, "time_picker");
				ft.commit();
			}
		};
		OnClickListener listener2 = new OnClickListener()
		{
		    @Override
		    public void onClick(View v) 
		    {
		        Bundle b = new Bundle();
		        b.putInt("set_hour", sharedPreference.getValueDurationHour(getApplicationContext()));
			    b.putInt("set_minute", sharedPreference.getValueDurationMinute(getApplicationContext()));
			    TimePickerDialogFragment timePicker = new TimePickerDialogFragment(mhandler2);	                
			    timePicker.setArguments(b);
			    FragmentManager fm = getSupportFragmentManager();
			    FragmentTransaction ft = fm.beginTransaction();
			    ft.add(timePicker, "time_picker");
		        ft.commit();
		    }
		};
		OnClickListener listener3 = new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				ringtonePicker();
			}
		};
		OnClickListener listener4 = new OnClickListener()
		{
		    @Override
		    public void onClick(View v) 
		    {
		        Bundle b = new Bundle();
		        b.putInt("set_hour", sharedPreference.getValueSmokeTimeHour(getApplicationContext()));
			    b.putInt("set_minute", sharedPreference.getValueSmokeTImeMinute(getApplicationContext()));
			    TimePickerDialogFragment timePicker = new TimePickerDialogFragment(mhandler3);	                
			    timePicker.setArguments(b);
			    FragmentManager fm = getSupportFragmentManager();
			    FragmentTransaction ft = fm.beginTransaction();
			    ft.add(timePicker, "time_picker");
			    ft.commit();
		    }
		};
		  
        Button btnSet = (Button)findViewById(R.id.btnSet);
        btnSet.setOnClickListener(listener1);
        Button btnDuration = (Button)findViewById(R.id.btnDuration);
        btnDuration.setOnClickListener(listener2);
        Button btnselectringtone = (Button)findViewById(R.id.selectringtone);
        btnselectringtone.setOnClickListener(listener3);
        smokeTime.setOnClickListener(listener4);
	}
	public void ringtonePicker() 
	{
	    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
	    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone");
	    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, true);
	    if (sharedPreference.getValueString(getApplicationContext()) == "content://media/internal/audio/media/0")
		{
	        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
	    	
		}
	    else if (sharedPreference.getValueString(getApplicationContext()) == "content://settings/system/ringtone")
	    {
	    	intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
	    }
	    else
	    {
		    String stringUri = sharedPreference.getValueString(getApplicationContext());
			Uri uri = Uri.parse(stringUri);
			intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);
	    }
		this.startActivityForResult(intent, 999);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
	    if (resultCode != RESULT_OK) 
	    {
	    	return;
	    }
	    if (requestCode == 999) 
	    {
	        Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
	        if (uri != null) 
	        {
	        	ringtone = RingtoneManager.getRingtone(this, uri);
	        	String title = ringtone.getTitle(this);
	            text.setText(title);
	            sharedPreference.save(getApplicationContext(), title);
	            sharedPreference.saveString(getApplicationContext(), uri.toString());
	            //Toast.makeText(getApplicationContext(), "You Have Selected " + title + " As A Ringtone.", Toast.LENGTH_LONG).show();
	        }
	        else 
	        {
	            //Toast.makeText(getApplicationContext(), "You Have A Silent Ringtone.", Toast.LENGTH_LONG).show();
	            sharedPreference.save(getApplicationContext(), "Silent");
	            sharedPreference.saveString(getApplicationContext(), "content://media/internal/audio/media/0");
	            text.setText("Silent");
	        }
	    }
	    else 
	    {
	        Toast.makeText(getApplicationContext(), "Request Code is Bad", Toast.LENGTH_LONG).show();
	    }
	    super.onActivityResult(requestCode, resultCode, intent);
	}
	private class Receiver1 extends BroadcastReceiver
	{
    	@Override
		public void onReceive(Context arg0, Intent arg1) {
			 datapassed1 = arg1.getLongExtra("TIMEPASSED1", 0);
		}
	
	}
	private class Receiver2 extends BroadcastReceiver
	{
    	@Override
		public void onReceive(Context arg0, Intent arg1) {
			 datapassed2 = arg1.getLongExtra("TIMEPASSED2", 0);
		}
	
	}
	private boolean isServiceRunning(Class<?> serviceClass) 
	{
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) 
	    {
	        if (serviceClass.getName().equals(service.service.getClassName())) 
	        {
	            return true;
	        }
	    }
	    return false;
	}
	@Override
	protected void onStop()
	{
		super.onStop();
		try
		{
			if (Receiver1 != null)
				unregisterReceiver(Receiver1);
			if (Receiver2 != null)
				unregisterReceiver(Receiver2);
		}
		catch (IllegalArgumentException e) { }
		if (interFace != null)
        {
			try {
				interFace.unregisterCallBack(mCallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
        }
		if (mServiceBound) 
		{
			getApplicationContext().unbindService(mServiceConnection);
			mServiceBound = false;
		}
	}
    @Override
    protected void onPause() 
    {
        super.onPause();
    	try
	    {
	        if (Receiver1 != null)
	            unregisterReceiver(Receiver1);
	        if (Receiver2 != null)
	            unregisterReceiver(Receiver2);
	    }
	    catch (IllegalArgumentException e) { }
		if (interFace != null)
        {
			try {
				interFace.unregisterCallBack(mCallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
        }
		if (mServiceBound) 
		{
			getApplicationContext().unbindService(mServiceConnection);
			mServiceBound = false;
		}
    }
    @Override
    protected void onResume() 
    {
    	super.onResume();
    	IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(CountDownClock.QUITSOMKING_ACTION1);
        registerReceiver(Receiver1, intentFilter1);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(SmokeTimeClock.QUITSOMKING_ACTION2);
        registerReceiver(Receiver2, intentFilter2);
        int Hour1 = sharedPreference.getValueCountDownClockHour(AppContext.getContext());
        int Minute1 = sharedPreference.getValueCountDownClockMinute(AppContext.getContext());
        String time1 = String.format("%02d:%02d", Hour1, Minute1);
        countdowntimer.setText(time1);
    	if (isServiceRunning(TimeService.class))
        {
    		AppContext.getContext().bindService(new Intent(this, TimeService.class), mServiceConnection, 0);
    	}
    }
    @Override
    public void onRestart() 
    {
    	super.onRestart();
    	IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(CountDownClock.QUITSOMKING_ACTION1);
    	registerReceiver(Receiver1, intentFilter1);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(SmokeTimeClock.QUITSOMKING_ACTION2);
        registerReceiver(Receiver2, intentFilter2);
        int Hour1 = sharedPreference.getValueCountDownClockHour(AppContext.getContext());
        int Minute1 = sharedPreference.getValueCountDownClockMinute(AppContext.getContext());
        String time1 = String.format("%02d:%02d", Hour1, Minute1);
        countdowntimer.setText(time1);
    	if (isServiceRunning(TimeService.class))
        {
    		AppContext.getContext().bindService(new Intent(this, TimeService.class), mServiceConnection, 0);
    	}
    }
    @Override
	protected void onDestroy() 
    {
		super.onDestroy();
		try
		{
		    if (Receiver1 != null)
		        unregisterReceiver(Receiver1);
		    if (Receiver2 != null)
		        unregisterReceiver(Receiver2);
		}
		catch (IllegalArgumentException e) { }
		if (interFace != null)
        {
			try {
				interFace.unregisterCallBack(mCallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
        }
		if (mServiceBound) 
		{
			getApplicationContext().unbindService(mServiceConnection);
			mServiceBound = false;
		}
    }
    private ServiceConnection mServiceConnection = new ServiceConnection() 
    {
		@Override
		public void onServiceDisconnected(ComponentName name) 
		{
			mServiceBound = false;
			Toast.makeText(getApplicationContext(), "OOPS Somethig went wrong Error 10", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) 
		{
			interFace = AsyncService.Stub.asInterface((IBinder) service);
			//Toast.makeText(getApplicationContext(), "Bound", Toast.LENGTH_SHORT).show();
			mServiceBound = true;
			try {
				interFace.registerCallBack(mCallback);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			try {
				interFace.FirstMainActivity();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};
	private RemoteService mCallback = new RemoteService.Stub() {
	    @Override
	    public void fromServiceTrue() throws RemoteException 
	    {
	    	
	    }
		@Override
		public void fromServiceFalse() throws RemoteException 
		{
			
		}
		@Override
		public void fromServiceCountDownTimer() throws RemoteException 
		{
			runOnUiThread(new Runnable() 
			{
	    	    public void run()
	    	    {   
	    	    	int Hour1 = sharedPreference.getValueCountDownClockHour(AppContext.getContext());
	    	        int Minute1 = sharedPreference.getValueCountDownClockMinute(AppContext.getContext());
	    	        String time1 = String.format("%02d:%02d", Hour1, Minute1);
	    	        countdowntimer.setText(time1);
	    	    	//Toast.makeText(getApplicationContext(), "Update CountdownTimer", Toast.LENGTH_SHORT).show();
	    	    }
	    	});		
		}
	};
}