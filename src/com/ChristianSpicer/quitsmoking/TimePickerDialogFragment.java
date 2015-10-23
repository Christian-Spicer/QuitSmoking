package com.ChristianSpicer.quitsmoking;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

 
@SuppressLint("DefaultLocale")
public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    Handler mHandler ;
    int mHour;
    int mMinute;
    private boolean mIgnoreTimeSet = false;
    
    public TimePickerDialogFragment(Handler h){
        /** Getting the reference to the message handler instantiated in MainActivity class */
        mHandler = h;
    }

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        /** Creating a bundle object to pass currently set time to the fragment */
        Bundle b = getArguments();
        /** Getting the hour of day from bundle */
        mHour = b.getInt("set_hour");
        /** Getting the minute of hour from bundle */
        mMinute = b.getInt("set_minute");
        
		final TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_Holo_Dialog_Alert, this, mHour, mMinute, true);
		// Make the Set button
		timePickerDialog.setCanceledOnTouchOutside(false);
		timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		        mIgnoreTimeSet = true;
		        // only manually invoke OnTimeSetListener (through the dialog) on pre-ICS devices
		        //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) timePickerDialog.onClick(dialog, which);
		        //Toast.makeText(AppContext.getContext(), "Set", Toast.LENGTH_SHORT).show();
		    }
		});
		// Set the Cancel button
		timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		        //Toast.makeText(AppContext.getContext(), "Cancel", Toast.LENGTH_SHORT).show();
		        mIgnoreTimeSet = false;
		        dialog.cancel();
		    }
		});
		return timePickerDialog;
    }
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		if (!mIgnoreTimeSet)
			return;
		mHour = hourOfDay;
        mMinute = minute;
        /** Creating a bundle object to pass currently set time to the fragment */
         Bundle b = new Bundle();
         /** Adding currently set hour to bundle object */
         b.putInt("set_hour", mHour);
         /** Adding currently set minute to bundle object */
         b.putInt("set_minute", mMinute);
         /** Adding Current time in a string to bundle object */
        //if (mHour == 0 && mMinute == 0)
        //{
        //	b.putString("set_time", "A Zero Value Is Not Permitted");
        //}
        //else
        //{
        //    String time = String.format("%02d:%02d", mHour, mMinute);
        //    b.putString("set_time", "Time Is Set To " + time + " Your Time Has Been Saved");
        //}
         /** Creating an instance of Message */
         Message m = new Message();
         /** Setting bundle object on the message object m */
         m.setData(b);
         /** Message m is sending using the message handler instantiated in MainActivity class */
         mHandler.sendMessage(m);

	}
}