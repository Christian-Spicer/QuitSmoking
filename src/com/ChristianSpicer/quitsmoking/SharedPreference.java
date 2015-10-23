package com.ChristianSpicer.quitsmoking;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharedPreference {

	public static final String PREFS_NAME = "PREFS";
	public static final String TITLE_KEY = "TITLE_PREFS_String";
	public static final String URI_KEY = "STRING_PREFS_String";
	public static final String HOUR_KEY = "HOUR_PREFS_String";
	public static final String MINUTE_KEY = "MINUTE_PREFS_String";
	public static final String DURATIONHOUR_KEY = "DURATIONHOUR_PREFS_String";
	public static final String DURATIONMINUTE_KEY = "DURATIONMINUTE_PREFS_String";
	public static final String OFFON_KEY = "OFFON_PREFS_String";
	public static final String ANIMATION_KEY = "ANIMATION_PREFS_String";
	public static final String SMOKETIME_KEY = "SMOKETIME_PREFS_String";
	public static final String SMOKETIMEHOUR_KEY = "SMOKETIMEHOUR_PREFS_String";
	public static final String SMOKETIMEMINUTE_KEY = "SMOKETIMEMINUTE_PREFS_String";
	public static final String CHANGE_KEY = "CHANGE_PREFS_String";
	
	public SharedPreference() 
	{
		super();
	}
	@SuppressWarnings("deprecation")
	public void save(Context context, String text) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putString(TITLE_KEY, text); //3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public String getValue(Context context)
	{
		SharedPreferences settings;
		String text;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		text = settings.getString(TITLE_KEY, "Default Ringtone");
		return text;
	}
	@SuppressWarnings("deprecation")
	public void saveString(Context context, String URI) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putString(URI_KEY, URI); //3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public String getValueString(Context context) 
	{
		SharedPreferences settings;
		String URI;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		URI = settings.getString(URI_KEY, "content://settings/system/ringtone");
		return URI;
	}
	@SuppressWarnings("deprecation")
	public void saveCountDownClockHour(Context context, int mHour)
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putInt(HOUR_KEY, mHour);//3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public int getValueCountDownClockHour(Context context)
	{
		SharedPreferences settings;
		int HOUR;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		HOUR = settings.getInt(HOUR_KEY, 1);
		return HOUR;
	}
	@SuppressWarnings("deprecation")
	public void saveCountDownClockMinute(Context context, int mMinute) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putInt(MINUTE_KEY, mMinute);//3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public int getValueCountDownClockMinute(Context context) 
	{
		SharedPreferences settings;
		int MINUTE;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		MINUTE = settings.getInt(MINUTE_KEY, 0);
		return MINUTE;
	}
	@SuppressWarnings("deprecation")
	public void saveDurationHour(Context context, int mHour) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putInt(DURATIONHOUR_KEY, mHour);//3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public int getValueDurationHour(Context context) 
	{
		SharedPreferences settings;
		int DURATIONHOUR;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		DURATIONHOUR = settings.getInt(DURATIONHOUR_KEY, 0);
		return DURATIONHOUR;
	}
	@SuppressWarnings("deprecation")
	public void saveDurationMinute(Context context, int mMinute) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putInt(DURATIONMINUTE_KEY, mMinute);//3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public int getValueDurationMinute(Context context) 
	{
		SharedPreferences settings;
		int DURATIONMINUTE;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		DURATIONMINUTE = settings.getInt(DURATIONMINUTE_KEY, 1);
		return DURATIONMINUTE;
	}
	@SuppressWarnings("deprecation")
	public void saveSwitch(Context context, boolean OFFON) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putBoolean(OFFON_KEY, OFFON);//3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public boolean getValueSwitch(Context context) 
	{
		SharedPreferences settings;
		boolean OFFON;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		OFFON = settings.getBoolean(OFFON_KEY, true);
		return OFFON;
	}
	@SuppressWarnings("deprecation")
	public void saveAnimation(Context context, boolean ANIMATION) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putBoolean(ANIMATION_KEY, ANIMATION);//3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public boolean getValueAnimation(Context context) 
	{
		SharedPreferences settings;
		boolean ANIMATION;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		ANIMATION = settings.getBoolean(ANIMATION_KEY, false);
		return ANIMATION;
	}
	@SuppressWarnings("deprecation")
	public void saveSmokeTime(Context context, boolean SMOKETIME) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putBoolean(SMOKETIME_KEY, SMOKETIME);//3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public boolean getValueSmokeTime(Context context) 
	{
		SharedPreferences settings;
		boolean SMOKETIME;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		SMOKETIME = settings.getBoolean(SMOKETIME_KEY, false);
		return SMOKETIME;
	}
	@SuppressWarnings("deprecation")
	public void saveSmokeTimeHour(Context context, int mHour) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putInt(SMOKETIMEHOUR_KEY, mHour);//3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public int getValueSmokeTimeHour(Context context) 
	{
		SharedPreferences settings;
		int SMOKETIMEHOUR;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		SMOKETIMEHOUR = settings.getInt(SMOKETIMEHOUR_KEY, 0);
		return SMOKETIMEHOUR;
	}
	@SuppressWarnings("deprecation")
	public void saveSmokeTimeMinute(Context context, int mMinute) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putInt(SMOKETIMEMINUTE_KEY, mMinute);//3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public int getValueSmokeTImeMinute(Context context) 
	{
		SharedPreferences settings;
		int SMOKETIMEMINUTE;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		SMOKETIMEMINUTE = settings.getInt(SMOKETIMEMINUTE_KEY, 15);
		return SMOKETIMEMINUTE;
	}
	@SuppressWarnings("deprecation")
	public void saveChange(Context context, boolean CHANGE) 
	{
		SharedPreferences settings;
		Editor editor;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS); //1
		editor = settings.edit(); //2
		editor.putBoolean(CHANGE_KEY, CHANGE);//3
		editor.commit(); //4
	}
	@SuppressWarnings("deprecation")
	public boolean getValueChange(Context context) 
	{
		SharedPreferences settings;
		boolean CHANGE;
		//settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
		CHANGE = settings.getBoolean(CHANGE_KEY, false);
		return CHANGE;
	}
}