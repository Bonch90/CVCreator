package com.bonch.cvcreator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class JobExperiences extends Activity {
	private static final String FILENAME="Lavori.dat";
	static List<Job> lista_job;
	static ArrayAdapter<Job> adapter;
	private ListView listView = null;
	private Context context;
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
	    @Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        Intent i=new Intent(v.getContext(),JobExperience.class);
	        i.putExtra("editable", true);
	        i.putExtra("position", position);
	        startActivity(i);
	    }
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=getApplicationContext();
		carica();
		setContentView(R.layout.activity_job_experiences);
		adapter=new ArrayAdapter<Job>(this,android.R.layout.simple_list_item_multiple_choice,lista_job);
		if(listView==null){
			listView = (ListView) findViewById(R.id.listView);
		}
		listView.setOnItemClickListener(mMessageClickedHandler); 
		listView.setAdapter(adapter);
		listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
		    @Override
		    public void onItemCheckedStateChanged(ActionMode mode, int position,
		                                          long id, boolean checked) {
		        // Here you can do something when items are selected/de-selected,
		        // such as update the title in the CAB
		    	if(listView.getCheckedItemCount()==1)
		    		mode.setTitle("1 selezionato");
		    	else if(listView.getCheckedItemCount()>1)
		    		mode.setTitle(listView.getCheckedItemCount()+" selezionati");
		    }

		    @Override
		    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		        // Respond to clicks on the actions in the CAB
		        switch (item.getItemId()) {
		            case R.id.menu_delete:
		            	int n=listView.getCount();
		            	SparseBooleanArray sparseBooleanArray=listView.getCheckedItemPositions();
		            	for(int i=n-1;i>=0;i--){
		            		if(sparseBooleanArray.get(i))
		            			adapter.remove((Job) listView.getItemAtPosition(i));
		            	}
		                adapter.notifyDataSetChanged();
		                mode.finish(); // Action picked, so close the CAB
		                return true;
		            default:
		                return false;
		        }
		    }

		    @Override
		    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		        // Inflate the menu for the CAB
		        MenuInflater inflater = mode.getMenuInflater();
		        inflater.inflate(R.menu.job_experiences, menu);
		        MenuItem menu1 = menu.findItem(R.id.menu_delete);
		        MenuItem menu2 = menu.findItem(R.id.menu_add);
		        menu1.setVisible(true);
		        menu2.setVisible(false);
		        return true;
		    }

		    @Override
		    public void onDestroyActionMode(ActionMode mode) {
		        // Here you can make any necessary updates to the activity when
		        // the CAB is removed. By default, selected items are deselected/unchecked.
		    }

		    @Override
		    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		        // Here you can perform updates to the CAB due to
		        // an invalidate() request
		        return false;
		    }
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.job_experiences, menu);
	    return true;
	}
	
	public void addExperience(){
		Intent intent = new Intent(this,JobExperience.class);
		intent.putExtra("editable", false);
		startActivity(intent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId()) {
	        case R.id.menu_add:
	            addExperience();
	            return true;
	    }

	    return super.onOptionsItemSelected(item);
	}   
	
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data); 
		if (resultCode == Activity.RESULT_OK) {
			runOnUiThread(new Runnable() {
			    @Override
				public void run() {
			        adapter.notifyDataSetChanged();
			    }});
		}
	}
	
	public void nextPage(View view){
		Intent i=new Intent(this,SchoolExperiences.class);
		startActivity(i);
	}
	
	 @Override
	 public void onPause(){
		super.onPause();
		salva();
	 }
	 @Override
	 public void onDestroy(){
			super.onDestroy();
			salva();
	 }
	 @Override
	 public void onRestart(){
		 super.onRestart();
		 salva();
	 }
	/* @Override
	 public void onResume(){
		 Log.i("Job", "resume");
		 super.onResume();
		 carica();
	 }*/
	 public void salva(){
		 try {
		 FileOutputStream fos = context.openFileOutput(FILENAME, MODE_PRIVATE);
		 ObjectOutputStream objectOutStream = new ObjectOutputStream(fos);
		 objectOutStream.writeInt(lista_job.size()); // Save size first
		 for(Job j:lista_job)
				objectOutStream.writeObject(j);
		 objectOutStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 public void carica(){
		 try{
			 lista_job = new ArrayList<Job>();
			 FileInputStream inStream = context.openFileInput (FILENAME);
			 if(inStream!=null){
			ObjectInputStream objectInStream = new ObjectInputStream(inStream);
		 int count = objectInStream.readInt(); // Get the number of regions
		 for (int c=0; c < count; c++)
		     lista_job.add((Job)objectInStream.readObject());
		 objectInStream.close();
		 }
		 }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }



}
