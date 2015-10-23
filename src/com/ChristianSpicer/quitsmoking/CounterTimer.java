package com.ChristianSpicer.quitsmoking;

import java.util.concurrent.TimeUnit;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.CountDownTimer;
import android.widget.Toast;

public class CounterTimer { 
		
	SharedPreference sharedPreference = new SharedPreference();
	private MediaPlayer mMediaPlayer;
	CountDownClock counterTimer;
	SmokeTimeClock smokingTimer;

	private void alarm()
	{
	   	try {
			String stringUri = sharedPreference.getValueString(AppContext.getContext());
			Uri uri = Uri.parse(stringUri);
			playSound(AppContext.getContext(), uri);
		} 
	   	catch (NullPointerException e) {
        	Toast.makeText(AppContext.getContext(), "OOPS Please Select Ringtone.", Toast.LENGTH_LONG).show();
        }
	}
	public void stopMediaPlayer()
	{
		if (mMediaPlayer != null)
		{
			try { 
				mMediaPlayer.stop();
				mMediaPlayer.release();
				mMediaPlayer = null;
			}
			catch(Exception ex) 
			{ 
				Toast.makeText(AppContext.getContext(), "OOPS Something Went Wrong. Error 8", Toast.LENGTH_LONG).show(); 
			}
		}
	}
	public void finsh()
	{
		counterTimer.cancel();
		if (smokingTimer != null)
		{
			smokingTimer.cancel();
		}
		cancelAlert();
		cancelSmokeTimeAlert();
	}
	@SuppressWarnings("deprecation")
	private void playSound(Context context, Uri alert) 
	{
		if(mMediaPlayer != null)
	    {  
				mMediaPlayer.stop();
		}
		mMediaPlayer = new MediaPlayer();		
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if(audioManager.isWiredHeadsetOn())
            {
            	audioManager.setWiredHeadsetOn(false);
            	audioManager.setSpeakerphoneOn(true); 
            	audioManager.setRouting(AudioManager.MODE_CURRENT, AudioManager.ROUTE_SPEAKER, AudioManager.ROUTE_ALL);  
            	audioManager.setMode(AudioManager.MODE_CURRENT); 
            }
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) 
            {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
	            mMediaPlayer.setOnPreparedListener(new OnPreparedListener() 
	            { 
	                @Override
	                public void onPrepared(MediaPlayer mp) 
	                {
	                    mMediaPlayer.start();
	                }
	            });
            }
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() 
            {
                public void onCompletion(MediaPlayer mediaPlayer) 
                {    
                    mediaPlayer.release();
                    mMediaPlayer = null;
                }
            });
        }
        catch (Exception e) {
        	Toast.makeText(AppContext.getContext(), "OOPs Please Select A Ringtone.", Toast.LENGTH_SHORT).show();
        }
	}
	@SuppressWarnings("static-access")
	public void startAlert(long time) 
	{
		if (sharedPreference.getValueSwitch(AppContext.getContext()) && time >= 1000)
		{
			Intent intent = new Intent(AppContext.getContext(), CountDownBroadcastReceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(AppContext.getContext(), 234324243, intent, 0);
			AlarmManager alarmManager = (AlarmManager) AppContext.getContext().getSystemService(AppContext.getContext().ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pendingIntent);
		}
	}
	@SuppressWarnings("static-access")
	public void startSmokingAlert(long time) 
	{
		if (sharedPreference.getValueSwitch(AppContext.getContext()) && time >= 1000)
		{
			Intent intent = new Intent(AppContext.getContext(), StopSmokingBroadcastReceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(AppContext.getContext(), 234324243, intent, 0);
			AlarmManager alarmManager = (AlarmManager) AppContext.getContext().getSystemService(AppContext.getContext().ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pendingIntent);
		}
	}
	@SuppressWarnings("static-access")
	public void cancelAlert() 
	{
		Intent intent1 = new Intent(AppContext.getContext(), CountDownBroadcastReceiver.class);
		PendingIntent pendingIntent1 = PendingIntent.getBroadcast(AppContext.getContext(), 234324243, intent1, 0);
		AlarmManager alarmManager1 = (AlarmManager) AppContext.getContext().getSystemService(AppContext.getContext().ALARM_SERVICE);
		alarmManager1.cancel(pendingIntent1);

	}
	@SuppressWarnings("static-access")
	public void cancelSmokeTimeAlert()
	{
		Intent intent2 = new Intent(AppContext.getContext(), StopSmokingBroadcastReceiver.class);
		PendingIntent pendingIntent2 = PendingIntent.getBroadcast(AppContext.getContext(), 234324243, intent2, 0);
		AlarmManager alarmManager2 = (AlarmManager) AppContext.getContext().getSystemService(AppContext.getContext().ALARM_SERVICE);
		alarmManager2.cancel(pendingIntent2);

	}
	public void Start() 
	{
		try {
				int hour1 = sharedPreference.getValueCountDownClockHour(AppContext.getContext());
				int minute1 = sharedPreference.getValueCountDownClockMinute(AppContext.getContext());
				int hour2 = sharedPreference.getValueDurationHour(AppContext.getContext());
				int minute2 = sharedPreference.getValueDurationMinute(AppContext.getContext());
				long time1 = (long)(hour1 * 1000 * 60 * 60) + (minute1 * 1000 * 60);
				long time2 = (long)(hour2 * 1000 * 60 * 60) + (minute2 * 1000 * 60);
				long time = calculateDuration(time1, time2);
				counterTimer = new CountDownClock(time, 1000);
				counterTimer.start();
				sharedPreference.saveCountDownClockHour(AppContext.getContext(), (int) TimeUnit.MILLISECONDS.toHours(time));
				sharedPreference.saveCountDownClockMinute(AppContext.getContext(), (int) (TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time))));
				startAlert(time);
		}catch (NullPointerException e) {
				Toast.makeText(AppContext.getContext(), "OOPS Please adjust your settings.", Toast.LENGTH_SHORT).show();
		}
 	}
	public long calculateDuration(long time1, long time2)
	{
		if (sharedPreference.getValueChange(AppContext.getContext()))
		{
			sharedPreference.saveChange(AppContext.getContext(), false);
			return time1;
		}
		else
		{
			return time1 += time2;
		}
	}	
	@SuppressLint("DefaultLocale")
	public long calculateSmokeTime(long time)
	{
		long time2 = time * 4;
		int hour1 = sharedPreference.getValueCountDownClockHour(AppContext.getContext());
		int minute1 = sharedPreference.getValueCountDownClockMinute(AppContext.getContext());
		long time1 = (long)(hour1 * 1000 * 60 * 60) + (minute1 * 1000 * 60);
		if (time1 < time2)
		{
			time = time1 / 4;
			
		}
		else
		{
			return time;
		}
		return time;
	}
	@SuppressLint("DefaultLocale")
	public void startSmokeTime()
	{
		int hour = sharedPreference.getValueSmokeTimeHour(AppContext.getContext());
		int minute = sharedPreference.getValueSmokeTImeMinute(AppContext.getContext());
		long time1 = (long)(hour * 1000 * 60 * 60) + (minute * 1000 * 60);
		long time2 = calculateSmokeTime(time1);
		smokingTimer = new SmokeTimeClock(time2, 1000);
		smokingTimer.start();
		long millis = time2;
		String hms = String.format("%02d:%02d:%02d", 
		TimeUnit.MILLISECONDS.toHours(millis), 
		TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), 
		TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		if (sharedPreference.getValueSmokeTime(AppContext.getContext()))
		{
			startSmokingAlert(time2);
			Toast.makeText(AppContext.getContext(), "You Have " + hms + " Of Time To Have Smoko.", Toast.LENGTH_LONG).show();
		}
	}
	public class CountDownClock extends CountDownTimer { 

		final static String QUITSOMKING_MY_ACTION = "QUITSOMKING_MY_ACTION";
		final static String QUITSOMKING_ACTION1 = "QUITSOMKING_ACTION1";
		
		public CountDownClock(long millisInFuture, long countDownInterval) 
		{ 
			super(millisInFuture, countDownInterval); 
		}
		@Override 
		public void onFinish() 
		{
			Toast.makeText(AppContext.getContext(), "Smoko Time.", Toast.LENGTH_LONG).show();
			sharedPreference.saveAnimation(AppContext.getContext(), true);
			Start();
			startSmokeTime();
			alarm();
		}  
		@SuppressLint({ "InlinedApi", "DefaultLocale" })
		@Override 
		public void onTick(long millisUntilFinished) 
		{ 
			long millis = millisUntilFinished; 
			String hms = String.format("%02d:%02d:%02d", 
			TimeUnit.MILLISECONDS.toHours(millis), 
			TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), 
			TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	        Intent intent = new Intent();
			intent.setAction(QUITSOMKING_MY_ACTION);
			intent.putExtra("DATAPASSED", hms);
			AppContext.getContext().sendBroadcast(intent);
			Intent vibrator = new Intent();
			vibrator.setAction(QUITSOMKING_ACTION1);
			vibrator.putExtra("TIMEPASSED1", millis);
			AppContext.getContext().sendBroadcast(vibrator);
		}
	}
	public class SmokeTimeClock extends CountDownTimer {
		
		final static String QUITSOMKING_ACTION2 = "QUITSOMKING_ACTION2";

		public SmokeTimeClock(long millisInFuture, long countDownInterval) 
		{ 
			super(millisInFuture, countDownInterval); 
		} 
		@Override 
		public void onFinish() 
		{
			sharedPreference.saveAnimation(AppContext.getContext(), false);
			if (sharedPreference.getValueSmokeTime(AppContext.getContext()))
			{
				alarm();
				Toast.makeText(AppContext.getContext(), "Smoko Time Has Finshed.", Toast.LENGTH_LONG).show();
			}
		}
		@Override 
		public void onTick(long millisUntilFinished) 
		{ 
			Intent vibrate = new Intent();
			vibrate.setAction(QUITSOMKING_ACTION2);
			vibrate.putExtra("TIMEPASSED2", millisUntilFinished);
			AppContext.getContext().sendBroadcast(vibrate);
		}
	}
}
