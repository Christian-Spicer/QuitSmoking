package com.ChristianSpicer.quitsmoking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThirdMainActivity extends Activity{

	Button about;
	TextView instructions;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_main);
        about = (Button) findViewById(R.id.btnabout);
        instructions = (TextView) findViewById(R.id.instructions);
        about.setOnClickListener(new View.OnClickListener() 
        {
	        public void onClick(View view)
	        {
	        	About();
	        }
	    });
        instructions();
	}
	private void About() 
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
	public void instructions()
	{
		instructions.setText("This application is for you to take control of your smoking habit. "
				+ "The idea behind this application is to wean you off smoking slowly or quickly "
				+ "depending upon your choice of settings. This is done by setting the countdown "
				+ "clock to what ever time you think is approperate to have a smoke, so when the "
				+ "alarm goes off you can have a smoke. It will then add extra time to the countdown "
				+ "clock increasing the amount of time between smokes. You can in addition to that, "
				+ "set another alarm so that when the alarm goes off for a second time, the chance "
				+ "to have a smoke is gone, you will have to wait until the next smoko alarm "
				+ "goes off."
				+ "\n\nHow to use this application."
				+ "\n\n1) To Start and Stop the countdown timer, press Start button under the clock tab. "
				+ "The Start button will turn into the Stop button. The countdown timer can run when "
				+ "the application is closed and/or when the screen is turned off. The only way to stop the "
				+ "countdown timer is by pressing the Stop button which will also reset the countdown clock."
				+ "\n\n2) Set the countdown clock, this is done by pressing the set countdown timer "
				+ "button under the settings tab and then scrolling through the time picker to the desired "
				+ "time and then pressing Ok. The default is set to one hour. Note you cannot set the "
				+ "countdown timer to zero hours and zero minutes."
				+ "\n\n3) Set the extra time, this is done by pressing the set extra time button "
				+ "under the settings tab. Use the time picker to choose the approperate amount of time to "
				+ "add extra time to your countdown clock when it reaches zero. The default is set to one "
				+ "minute. Note you can set the duration time to zero."
				+ "\n\n4) If you change the countdown clock timer value or the extra time value the "
				+ "application will reset the countdown timer clock when the smoko alarm goes off or you can "
				+ "achieve the same result by pressing stop and starting the countdown timer again."
				+ "\n\n5) Set the ringtone, this allows you to choose what ringtone you would like as "
				+ "an alarm. this is done by pressing the select ringtone button under the settings tab "
				+ "which will display a list of ringtones to chose from, select a ringtone and press Ok "
				+ "The default is set to your device default ringtone. Note there is a silent ringtone."
				+ "\n\n6) Vibrate mode, toggle on or off vibrate. The default is on."
				+ "\n\n7) Smoko mode, this switch lets you turn on and off the smoko time feature. This "
				+ "feature allows you to choose the amount of time you have got for smoko. Upon selecting "
				+ "on, the set smoko time button will appear and disappear when turned off. If you press the "
				+ "set smoko time button, a time picker will appear which will allow you to adjust the time for "
				+ "smoko. The default for the switch is off and the default for the smoko time is set to 15 "
				+ "minutes. Note you cannot choose zero hours and zero minutes. "
				+ "The smoko time value must be 4 times less than the countdown timer, if you have set the "
				+ "smoko time for more than that, the application will divide the countdown timer by 4 and "
				+ "give you that number for smoko, until the countdown timer is 4 times greater than the "
				+ "amount of smoko time, then it will give you the amount of time that you have selected for "
				+ "smoko. Smoko time must be set before the countdown timer alarm has gone off. You may notice "
				+ "that when the smoko switch is on and it is smoko time that a blue caterpillar sitting on a "
				+ "mushroom smoking a bong will appear in the clock tab, it will disappear when smoke time "
				+ "is finshed."
				+ "\n\n8) There is a exit situated in the action bar menu, and pressing the rotating "
				+ "no smoking image or the \"QUIT SMOKING\" title will exit the application as well, you "
				+ "can also exit the application through the normal ways of your device."
				+ "\n\n9) If the you want to turn the alarm off while it is ringing, just shake the device "
				+ "vigorously, give the deivce a really good shake until the alarm stops. "
				+ "Note with some devices you might need to turn the screen on.\n");
	}
}