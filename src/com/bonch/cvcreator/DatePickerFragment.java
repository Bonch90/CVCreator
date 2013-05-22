package com.bonch.cvcreator;

import java.util.Calendar;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;


@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener {
	private final Calendar c = Calendar.getInstance();
	private int year = c.get(Calendar.YEAR);
	private int month = c.get(Calendar.MONTH);
	private int day = c.get(Calendar.DAY_OF_MONTH);
	private Button button;
	private int id;
	public DatePickerFragment(){
		super();
	}
	public DatePickerFragment(int id){
		this.id=id;
	}

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		DatePickerDialog dpd;
		if(savedInstanceState!=null)
			id=savedInstanceState.getInt("id");
		button=(Button) getActivity().findViewById(id);
		String data=button.getText().toString();
		if(!data.equals("")){
        	day=Integer.parseInt(data.substring(0, 2).toString());
        	month=Integer.parseInt(data.substring(3, 5).toString())-1;
        	year=Integer.parseInt(data.substring(6, 10).toString());
		}
        dpd= new DatePickerDialog(getActivity(), this, year, month, day);
        dpd.getDatePicker().setCalendarViewShown(false);
        return dpd;
	}
    @Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
    	String d=String.valueOf(day);
    	String m=String.valueOf(month+1);;
    	if(day<10)
    		d="0"+d;
    	if(month+1<10)
    		m="0"+m;
    	 button.setText(("" + d + "/" + m + "/" + year));
    }
 @Override   
    public void onSaveInstanceState(Bundle outState){
    	super.onSaveInstanceState(outState);
    	outState.putInt("id", id);
    }
 
}