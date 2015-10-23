package com.ChristianSpicer.quitsmoking;

import android.app.Application;
import android.content.Context;

public class AppContext extends Application{

	
	private static Context sContext;

	@Override
	public void onCreate() 
	{
		AppContext.sContext = getApplicationContext();
	    super.onCreate();
	}
	public static Context getContext() 
	{
	    return sContext;
	}
}