package com.bonch.cvcreator;
import java.util.Comparator;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SchoolExperience extends Activity {
	private School school;
	private boolean editable=false;
	private int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_school_experience);
		if(savedInstanceState!=null)
			onRestoreInstanceState(savedInstanceState);
		Intent i=getIntent();
		editable=i.getBooleanExtra("editable", false);
		if(editable){
			position=i.getIntExtra("position", -1);
			school=SchoolExperiences.adapter.getItem(position);
			((Button) findViewById(R.id.startDate)).setText(school.startDate);
			((Button) findViewById(R.id.endDate)).setText(school.endDate);
			((EditText) findViewById(R.id.nSchool)).setText(school.nome);
			((EditText) findViewById(R.id.materie)).setText(school.materie);
			((EditText) findViewById(R.id.qualifica)).setText(school.qualifica);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.school_experience, menu);
		return true;
	}
	
	public void showStartDatePickerDialog(View view){
		DialogFragment newFragment = new DatePickerFragment(view.getId());
		newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void showEndDatePickerDialog(View view){
		DialogFragment newFragment = new DatePickerFragment(view.getId());
		newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void confirm(){
		if(!editable)
			school=new School();
		else
			SchoolExperiences.adapter.remove(school);
		Intent resultIntent=new Intent();
		school.startDate=((Button) findViewById(R.id.startDate)).getText().toString();
		school.endDate=((Button) findViewById(R.id.endDate)).getText().toString();
		school.nome=((EditText) findViewById(R.id.nSchool)).getText().toString();
		school.materie=((EditText) findViewById(R.id.materie)).getText().toString();
		school.qualifica=((EditText) findViewById(R.id.qualifica)).getText().toString();
		SchoolExperiences.adapter.add(school);
		SchoolExperiences.adapter.sort(new Comparator<School>() {
			public int compare(School lhs, School rhs) {
				return lhs.compareTo(rhs);
			}
    	});
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId()) {
	        case R.id.salva:
	            confirm();
	            return true;
	    }

	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putString("startDate",((Button)findViewById(R.id.startDate)).getText().toString());
		outState.putString("endDate",((Button)findViewById(R.id.endDate)).getText().toString());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle outState){
		super.onRestoreInstanceState(outState);
		((Button)findViewById(R.id.startDate)).setText(outState.getString("startDate"));
		((Button)findViewById(R.id.endDate)).setText(outState.getString("endDate"));
	}
}