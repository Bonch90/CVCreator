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

public class JobExperience extends Activity {
	private Job job;
	private boolean editable=false;
	private int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_experience);
		if(savedInstanceState!=null)
			onRestoreInstanceState(savedInstanceState);
		Intent i=getIntent();
		editable=i.getBooleanExtra("editable", false);
		if(editable){
			position=i.getIntExtra("position", -1);
			job=JobExperiences.adapter.getItem(position);
			((Button) findViewById(R.id.startDate)).setText(job.startDate);
			((Button) findViewById(R.id.endDate)).setText(job.endDate);
			((EditText) findViewById(R.id.nJob)).setText(job.nome);
			((EditText) findViewById(R.id.tJob)).setText(job.tipo);
			((EditText) findViewById(R.id.impiego)).setText(job.impiego);
			((EditText) findViewById(R.id.mansione)).setText(job.mansione);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.job_experience, menu);
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId()) {
	        case R.id.salva:
	            confirm();
	            return true;
	    }

	    return super.onOptionsItemSelected(item);
	}
	
	public void confirm(){
		if(!editable)
			job=new Job();
		else
			JobExperiences.adapter.remove(job);
		Intent resultIntent=new Intent();
		job.startDate=((Button) findViewById(R.id.startDate)).getText().toString();
		job.endDate=((Button) findViewById(R.id.endDate)).getText().toString();
		job.nome=((EditText) findViewById(R.id.nJob)).getText().toString();
		job.tipo=((EditText) findViewById(R.id.tJob)).getText().toString();
		job.impiego=((EditText) findViewById(R.id.impiego)).getText().toString();
		job.mansione=((EditText) findViewById(R.id.mansione)).getText().toString();
		JobExperiences.adapter.add(job);
		JobExperiences.adapter.sort(new Comparator<Job>() {
			public int compare(Job lhs, Job rhs) {
				return lhs.compareTo(rhs);
			}
    	});
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
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
