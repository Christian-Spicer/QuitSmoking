package com.ChristianSpicer.quitsmoking;

import com.ChristianSpicer.quitsmoking.ShakeDetector.OnShakeListener;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.widget.Toast;

public class TimeService extends Service implements SharedPreferences.OnSharedPreferenceChangeListener, SensorEventListener{

	CounterTimer countdownclock = new CounterTimer();
	static SharedPreference sharedPreference = new SharedPreference();
	@SuppressWarnings("deprecation")
	static SharedPreferences sp = AppContext.getContext().getSharedPreferences("PREFS", Context.MODE_MULTI_PROCESS);
	final static RemoteCallbackList<RemoteService> mCallbacks = new RemoteCallbackList<RemoteService>();
	private static boolean isConnected;
    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    
    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
        public void onReceive(Context context, Intent intent) {
            // Check action just to be on the safe side.
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                // Unregisters the listener and registers it again.
            	mSensorManager.unregisterListener(TimeService.this);
            	mSensorManager.registerListener(TimeService.this, mAccelerometer,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    };
	private final static AsyncService.Stub mBinder = new AsyncService.Stub() 
	{
	    @Override
	    public void FirstMainActivity() throws RemoteException 
	    {
        	if (sharedPreference.getValueAnimation(AppContext.getContext()) && sharedPreference.getValueSmokeTime(AppContext.getContext()))
        	{
        		toActivityProcessTrue();
        	}
        	else
        	{
        		toActivityProcessFalse();
        	}
	    }
		@Override
		public void SecondMainActivity() throws RemoteException {
			toSecondMainActivity();
		}
	    public void registerCallBack(RemoteService cb) throws RemoteException 
	    {
            mCallbacks.register(cb);
		}
		@Override
		public void unregisterCallBack(RemoteService cb) throws RemoteException 
		{
			mCallbacks.unregister(cb);
		}
	};
	public static void toActivityProcessTrue()
	{
    	final int N = mCallbacks.beginBroadcast();
    	for (int i = 0; i < N; i++) 
    	{
	    	try {
		    	if (isConnected){					 
		    		mCallbacks.getBroadcastItem(i).fromServiceTrue();  
	    		}
	    		//NullPointerException here
	    	} catch (DeadObjectException e) {
	    		mCallbacks.finishBroadcast();
	    		Toast.makeText(AppContext.getContext(), "OOPS something went wrong. Error 4", Toast.LENGTH_SHORT).show();
	    	} catch (NullPointerException npe) {
	    		// PROBLEM:  GETS Thrown every now and then
	    		mCallbacks.finishBroadcast();
			    Toast.makeText(AppContext.getContext(), "OOPS something went wrong. Error 5", Toast.LENGTH_SHORT).show();
	    	} catch (RemoteException e) {
	    		mCallbacks.finishBroadcast();
			    Toast.makeText(AppContext.getContext(), "OOPS something went wrong. Error 6", Toast.LENGTH_SHORT).show();
	    	}
    	}
		mCallbacks.finishBroadcast();
    }
	public static void toActivityProcessFalse()
	{
    	final int N = mCallbacks.beginBroadcast();
    	for (int i = 0; i < N; i++) 
    	{
	    	try {
		    	if (isConnected){					 
		    		mCallbacks.getBroadcastItem(i).fromServiceFalse();
		    	}
	    	} catch (DeadObjectException e) {
	    		mCallbacks.finishBroadcast();
			    Toast.makeText(AppContext.getContext(), "OOPS something went wrong. Error 1", Toast.LENGTH_SHORT).show();
	    	} catch (NullPointerException npe) {
	    		// PROBLEM:  GETS Thrown every now and then
	    		mCallbacks.finishBroadcast();
			    Toast.makeText(AppContext.getContext(), "OOPS something went wrong. Error 2", Toast.LENGTH_SHORT).show();
	    	} catch (RemoteException e) {
	    		mCallbacks.finishBroadcast();
			    Toast.makeText(AppContext.getContext(), "OOPS something went wrong. Error 3", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
    	}
    	mCallbacks.finishBroadcast();
	}
	public static void toSecondMainActivity()
	{
    	final int N = mCallbacks.beginBroadcast();
    	for (int i = 0; i < N; i++) 
    	{
	    	try {
		    	if (isConnected){					 
		    		mCallbacks.getBroadcastItem(i).fromServiceCountDownTimer();
		    	}
	    	} catch (DeadObjectException e) {
	    		mCallbacks.finishBroadcast();
			    Toast.makeText(AppContext.getContext(), "OOPS something went wrong. Error 1", Toast.LENGTH_SHORT).show();
	    	} catch (NullPointerException npe) {
	    		// PROBLEM:  GETS Thrown every now and then
	    		mCallbacks.finishBroadcast();
			    Toast.makeText(AppContext.getContext(), "OOPS something went wrong. Error 2", Toast.LENGTH_SHORT).show();
	    	} catch (RemoteException e) {
	    		mCallbacks.finishBroadcast();
			    Toast.makeText(AppContext.getContext(), "OOPS something went wrong. Error 3", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
    	}
    	mCallbacks.finishBroadcast();
	}
	@Override
	public IBinder onBind(Intent intent) 
	{
		//Toast.makeText(getApplicationContext(), "binded", Toast.LENGTH_SHORT).show();
		isConnected = true;
		return (IBinder) mBinder;
	}
	@Override
	public void onRebind(Intent intent) 
	{
		super.onRebind(intent);
		isConnected = true;
		//Toast.makeText(getApplicationContext(), "Service is ReBinded", Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onUnbind(Intent intent) 
	{
		//Toast.makeText(getApplicationContext(), "Service is unBinded", Toast.LENGTH_SHORT).show();
		isConnected = false;
		return true;
	}
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		//android.os.Debug.waitForDebugger();
		countdownclock.Start();
		isConnected = true;
        sp.registerOnSharedPreferenceChangeListener(this);
		//Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_LONG).show();
		Notification("Quit Smoking", "Quit Now While You Still Can");
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
		Shaking();
		return Service.START_STICKY;
	}
	private void Shaking()
	{
		//final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector();
		mShakeDetector.setOnShakeListener(new OnShakeListener() {
		    @Override
		    public void onShake(int count) {
		    		//vibe.vibrate(1000);
		            countdownclock.stopMediaPlayer();
		        	//Toast.makeText(getApplicationContext(), "Shaking Works", Toast.LENGTH_LONG).show();
		        }
		    });
		mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);	
	}
	private void Notification(String notificationTitle, String notificationMessage) 
	{ 
		Bitmap bitMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
		Intent notificationIntent = new Intent(this, MainActivity.class);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		android.app.Notification.Builder builder = new android.app.Notification.Builder(this);
		builder.setSmallIcon(R.drawable.ic_stat_notification);
		builder.setContentTitle(notificationTitle);
		builder.setContentText(notificationMessage);
		builder.setContentIntent(pendingIntent);
		builder.setWhen(System.currentTimeMillis());
		builder.setLargeIcon(bitMap);
		builder.setTicker("Time To Quit Smoking.");
		startForeground(1234, builder.build());
	}
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		countdownclock.finsh();
		sp.unregisterOnSharedPreferenceChangeListener(this);
		isConnected = false;
		mSensorManager.unregisterListener(mShakeDetector);
		// Unregister our receiver.
        unregisterReceiver(mReceiver);
        // Unregister from SensorManager.
        mSensorManager.unregisterListener(this);
		//Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		if(key == "ANIMATION_PREFS_String") 
		{
			try {
				mBinder.FirstMainActivity();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		if(key == "HOUR_PREFS_String" || key == "MINUTE_PREFS_String") 
		{
			try {
				mBinder.SecondMainActivity();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
}