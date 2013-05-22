package com.bonch.cvcreator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LanguageExperience extends Activity {
	private ArrayAdapter<CharSequence> capacita;
	private Language language;
	private boolean editable=false;
	private int position;
    private Spinner name;
	private Spinner orale;
	private Spinner lettura;
	private Spinner scrittura;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_language_experience);
	    name=(Spinner) findViewById(R.id.madrelingua);
		orale=(Spinner) findViewById(R.id.capacita_orale);
		lettura=(Spinner) findViewById(R.id.capacita_lettura);
		scrittura=(Spinner) findViewById(R.id.capacita_scrittura);
		setSpinners();
		Intent i=getIntent();
		editable=i.getBooleanExtra("editable", false);
		if(editable){
			position=i.getIntExtra("position", -1);
			language=Abilities.adapter.getItem(position);
			name.setSelection(language.name);
			orale.setSelection(language.orale);
			lettura.setSelection(language.lettura);
			scrittura.setSelection(language.scrittura);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.language_experience, menu);
		return true;
	}
	
	public void setSpinners(){
	    capacita=ArrayAdapter.createFromResource(this, R.array.capacita, android.R.layout.simple_spinner_item);
	    capacita.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    name.setAdapter(Abilities.dataAdapter);
	    orale.setAdapter(capacita);
	    lettura.setAdapter(capacita);
	    scrittura.setAdapter(capacita);
	}
	
	public void confirm(){
		Intent resultIntent=new Intent();
		if(!editable){
			language=new Language();
			language.name=name.getSelectedItemPosition();
			language.lettura=lettura.getSelectedItemPosition();
			language.orale=orale.getSelectedItemPosition();
			language.scrittura=scrittura.getSelectedItemPosition();
			Abilities.adapter.add(language);
		}
		else
		{
			Abilities.adapter.getItem(position).name=name.getSelectedItemPosition();
			Abilities.adapter.getItem(position).lettura=lettura.getSelectedItemPosition();
			Abilities.adapter.getItem(position).scrittura=scrittura.getSelectedItemPosition();
			Abilities.adapter.getItem(position).orale=orale.getSelectedItemPosition();
			
		}
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

}
