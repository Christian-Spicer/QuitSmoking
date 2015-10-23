package com.ChristianSpicer.quitsmoking;

import java.util.concurrent.TimeUnit;
import com.ChristianSpicer.quitsmoking.CounterTimer.CountDownClock;
import com.ChristianSpicer.quitsmoking.CounterTimer.SmokeTimeClock;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FirstMainActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener{

	private TextView clockFace;
	Button timerStartStopButton;
	Receiver1 Receiver1;
	Receiver2 Receiver2;
	String datapassed1;
	ImageView Animation;
	LinearLayout ml;
	long datapassed2;
	TimeService mBoundService;
    boolean mServiceBound = false;
    AsyncService interFace;
    SharedPreference sharedPreference = new SharedPreference();
	@SuppressWarnings("deprecation")
	static SharedPreferences sp = AppContext.getContext().getSharedPreferences("PREFS", Context.MODE_MULTI_PROCESS);
    CounterTimer counterTimer = new CounterTimer();
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_main);
		timerStartStopButton = (Button) findViewById(R.id.btnStartStop);      
		clockFace = (TextView) findViewById(R.id.textTimer);
		clockFace.setText("00:00:00");   
        Receiver1 = new Receiver1();
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(CountDownClock.QUITSOMKING_MY_ACTION);
        registerReceiver(Receiver1, intentFilter1);
        Receiver2 = new Receiver2();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(SmokeTimeClock.QUITSOMKING_ACTION2);
        registerReceiver(Receiver2, intentFilter2);
        ml = (LinearLayout) findViewById(R.id.smoking_caterpillar_animation_layout);
        Animation = (ImageView)ml.findViewById(R.id.smoking_caterpillar_animation);

        if (sharedPreference.getValueAnimation(getApplicationContext()) && sharedPreference.getValueSmokeTime(getApplicationContext()))
        {
        	showImage();
        }
        else
        {
        	hideImage();
        }
        if (isServiceRunning(TimeService.class))
        {
        	timerStartStopButton.setText("Stop");
        }
	    timerStartStopButton.setOnClickListener(new View.OnClickListener()
	    {
	        public void onClick(View view)
	        {
	        	if(timerStartStopButton.getText().equals("Start"))
	        	{
	        		timerStartStopButton.setText("Stop");
	        		onClickStartService(view);
	        	} else if(timerStartStopButton.getText().equals("Stop"))
	        	{
	        		timerStartStopButton.setText("Start");
	        		onClickStopService(view);
	        	}
	        }
	    });
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
	//start the service
	public void onClickStartService(View V)
	{
		sharedPreference.saveChange(getApplicationContext(), true);
		startService(new Intent(this, TimeService.class));
		getApplicationContext().bindService(new Intent(this, TimeService.class), mServiceConnection, 0);
	}
	//Stop the service
	public void onClickStopService(View V)
	{	
		sharedPreference.saveChange(getApplicationContext(), true);
		sharedPreference.saveAnimation(getApplicationContext(), false);
		stopService(new Intent(this, TimeService.class));
		clockFace.setText("00:00:00");
		if (mServiceBound) 
		{
			getApplicationContext().unbindService(mServiceConnection);
			mServiceBound = false;
		}
		if (interFace != null)
		{
			try {
				interFace.unregisterCallBack(mCallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	public void hideImage()
	{
		Animation.setVisibility(ImageView.GONE);
	}

	public boolean showImage()
	{
		Animation.setVisibility(ImageView.VISIBLE);
        final AnimationDrawable AnimationDrawable = (AnimationDrawable)Animation.getDrawable();
		AnimationDrawable.setVisible(true, true);
        return Animation.post(
        new Runnable()
        {
			@Override
			public void run()
			{
				AnimationDrawable.start();
			}
        });
	}
	private class Receiver1 extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context arg0, Intent arg1) 
		{
			 datapassed1 = arg1.getStringExtra("DATAPASSED");
			 clockFace.setText(datapassed1);
		}
	}
	private class Receiver2 extends BroadcastReceiver
	{
        @Override
		public void onReceive(Context arg0, Intent arg1) 
        {
			 datapassed2 = arg1.getLongExtra("TIMEPASSED2", 0);
		}
	}
    @Override
    protected void onStop() 
    {
		super.onStop();
		sp.unregisterOnSharedPreferenceChangeListener(this);
    	try
	    {
	        if (Receiver1 != null)
	            unregisterReceiver(Receiver1);
	        if (Receiver2 != null)
	            unregisterReceiver(Receiver2);
	        if (interFace != null)
	        {
				try {
					interFace.unregisterCallBack(mCallback);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
	        }
	    }
	    catch (IllegalArgumentException e) { }
    	 if (mServiceBound) {
    		 getApplicationContext().unbindService(mServiceConnection);
    		 mServiceBound = false;
    	 }
    }
    @Override
    protected void onPause() 
    {
    	//sp.unregisterOnSharedPreferenceChangeListener(listener);
        super.onPause();
        try
	    {
	        if (Receiver1 != null)
	            unregisterReceiver(Receiver1);
	        if (Receiver2 != null)
	            unregisterReceiver(Receiver2);
	        if (interFace != null)
	        {
				try {
					interFace.unregisterCallBack(mCallback);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
	        }
	    }
	    catch (IllegalArgumentException e) { }
		if (mServiceBound) {
			AppContext.getContext().unbindService(mServiceConnection);
			mServiceBound = false;
		}
    }
    @Override
    protected void onResume() 
    {
    	super.onResume();
    	sp.registerOnSharedPreferenceChangeListener(this);
    	IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(CountDownClock.QUITSOMKING_MY_ACTION);
    	registerReceiver(Receiver1, intentFilter1);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(SmokeTimeClock.QUITSOMKING_ACTION2);
        registerReceiver(Receiver2, intentFilter2);
    	if (isServiceRunning(TimeService.class))
        {
    		AppContext.getContext().bindService(new Intent(this, TimeService.class), mServiceConnection, 0);
    	}
    }
    @Override
    public void onRestart() 
    {
        super.onRestart();
        sp.registerOnSharedPreferenceChangeListener(this);
    	IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(CountDownClock.QUITSOMKING_MY_ACTION);
        registerReceiver(Receiver1, intentFilter1);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(SmokeTimeClock.QUITSOMKING_ACTION2);
        registerReceiver(Receiver2, intentFilter2);
        if (isServiceRunning(TimeService.class))
        {
    		AppContext.getContext().bindService(new Intent(this, TimeService.class), mServiceConnection, 0);
        }
    }
    @Override
    protected void onDestroy() 
    {
        super.onDestroy();
    	sp.unregisterOnSharedPreferenceChangeListener(this);
		try
		{
	        if (Receiver1 != null)
				unregisterReceiver(Receiver1);
			if (Receiver2 != null)
	            unregisterReceiver(Receiver2);
			if (interFace != null)
	        {
				try {
					interFace.unregisterCallBack(mCallback);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
	        }
		}
		catch (IllegalArgumentException e) { }
  		if (mServiceBound) {
  			AppContext.getContext().unbindService(mServiceConnection);
			mServiceBound = false;
		}
    }
    private ServiceConnection mServiceConnection = new ServiceConnection() 
    {
		@Override
		public void onServiceDisconnected(ComponentName name) 
		{
			mServiceBound = false;
			Toast.makeText(getApplicationContext(), "OOPS Somethig went wrong Error 7", Toast.LENGTH_SHORT).show();
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
	    	runOnUiThread(new Runnable() 
	    	{
	    	    public void run()
	    	    {   
	    	    	showImage();
	    	    	//Toast.makeText(getApplicationContext(), "show image", Toast.LENGTH_SHORT).show();
	    	    }
	    	});
	    }
		@Override
		public void fromServiceFalse() throws RemoteException 
		{
			runOnUiThread(new Runnable() 
			{
	    	    public void run()
	    	    {   
	    	    	hideImage();
	    	    	//Toast.makeText(getApplicationContext(), "hide image", Toast.LENGTH_SHORT).show();
	    	    }
	    	});
		}
		@Override
		public void fromServiceCountDownTimer() throws RemoteException {
	
		}
	};
	@SuppressLint("DefaultLocale")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) 
	{
        if(key == "SMOKETIME_PREFS_String") 
        {
        	if (sharedPreference.getValueSmokeTime(getApplicationContext()) && sharedPreference.getValueAnimation(getApplicationContext()))
        	{
        		showImage();
        		long time = datapassed2 - 1000;
        		String hms = String.format("%02d:%02d:%02d", 
				TimeUnit.MILLISECONDS.toHours(time), 
				TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)), 
				TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
        		counterTimer.startSmokingAlert(time);
        		Toast.makeText(AppContext.getContext(), "You Have " + hms + " Of Time To Have Smoko.", Toast.LENGTH_LONG).show();
        	}
        	else
        	{
        		hideImage();
        		counterTimer.cancelSmokeTimeAlert();
        	}
        }
        if(key == "OFFON_PREFS_String") {
         	if (!sharedPreference.getValueSwitch(getApplicationContext()))
         	{
         		counterTimer.cancelAlert();
         		counterTimer.cancelSmokeTimeAlert();
         	}
         }
        if(key == "ANIMATION_PREFS_String") {
        	if (sharedPreference.getValueAnimation(AppContext.getContext()) && sharedPreference.getValueSmokeTime(AppContext.getContext()))
        		showImage();
        	else
        		hideImage();;
        }
	}
}