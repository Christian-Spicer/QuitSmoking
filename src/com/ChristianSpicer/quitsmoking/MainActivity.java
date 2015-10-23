package com.ChristianSpicer.quitsmoking;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity  implements OnTabChangeListener, OnClickListener{

	@SuppressLint("InflateParams")
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ActionBar = getActionBar();
		ActionBar.setDisplayShowHomeEnabled(false);
		ActionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater Inflater = LayoutInflater.from(this);
		View CustomView = Inflater.inflate(R.layout.custombar, null);
		TextView TitleTextView = (TextView)CustomView.findViewById(R.id.title_text);
		View ImageView = (View)CustomView.findViewById(R.id.nosmoking);
		TitleTextView.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
		    	finish();
			}
		});
		ImageView.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
		    	finish();
			}
		});
		ActionBar.setCustomView(CustomView);
		ActionBar.setDisplayShowCustomEnabled(true);
        
        final TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
    
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid2");
        TabSpec thirdTabSpec = tabHost.newTabSpec("tid3");

        Intent intent1 = new Intent(getApplicationContext(), FirstMainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        firstTabSpec.setIndicator("Clock");
        firstTabSpec.setContent(intent1);

        Intent intent2 = new Intent(getApplicationContext(), SecondMainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        secondTabSpec.setIndicator("Settings");
        secondTabSpec.setContent(intent2);
        
        Intent intent3 = new Intent(getApplicationContext(), ThirdMainActivity.class);
        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        thirdTabSpec.setIndicator("Info");
        
        thirdTabSpec.setContent(intent3);
        
        /* Add tabSpec to the TabHost to display. */
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
        tabHost.addTab(thirdTabSpec);
        int index = 0;
		tabHost.setCurrentTab(index);
		
		tabHost.setOnTabChangedListener(new OnTabChangeListener() 
		{
			@Override
			public void onTabChanged(String arg0) 
			{
				setTabColor(tabHost);
			}
		});
		setTabColor(tabHost);

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    { 
        switch (item.getItemId())
        {
        case R.id.Exit:
        	finish();
        	return true;
		case R.id.About:
			AboutMenuItem();
			break;
        }
		return false;
    }
    public void setTabColor(TabHost tabhost) 
    {
		for(int i=0;i<tabhost.getTabWidget().getChildCount();i++) 
		{
			TextView tv = (TextView)  tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //unselected
			tabhost.getTabWidget().getChildAt(i).getBackground().setColorFilter(Color.parseColor("#0277BD"), PorterDuff.Mode.OVERLAY);
			tv.setTextColor(Color.parseColor("#FFFFFF"));
		}
		TextView tv = (TextView)  tabhost.getCurrentTabView().findViewById(android.R.id.title); //selected
		tv.setTextColor(Color.parseColor("#00FFFF"));
	}
    @Override
	public void onTabChanged(String tabId) 
    {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		
	}
	private void AboutMenuItem() 
	{
		ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.Theme_Holo_Dialog_Alert);
		Builder builder = new AlertDialog.Builder(ctw);
		builder.setTitle("About");
		builder.setMessage("Quit Smoking Application.\nVersion 1.0.\nDeveloped By Christian Spicer."
				+ "\nEmail me any faults that you may encouter at\nchristian.spicer@Rocketmail.com");
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				
			}
			
		});
		builder.show();
	}

}
