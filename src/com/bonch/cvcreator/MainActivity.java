package com.bonch.cvcreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity {
	private static final int SELECT_IMAGE = 0;
	private SharedPreferences mPrefs;
	private int index=0;
	static ArrayAdapter<String> dataAdapter;
	static Bitmap foto=null;
	static String name="";
	static String address="";
	static String email="";
	static String fax="";
	static String telefono="";
	static String nazionalita="";
	static String dataNascita="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPrefs = getSharedPreferences("dati", MODE_PRIVATE);
		setContentView(R.layout.activity_main);
		setTitle(getString(R.string.title_activity_main));
		setCountries();
		((EditText)findViewById(R.id.name)).setText(mPrefs.getString("nome", ""));
		((EditText)findViewById(R.id.address)).setText(mPrefs.getString("indirizzo", ""));
		((EditText)findViewById(R.id.telefono)).setText(mPrefs.getString("telefono", ""));
		((EditText)findViewById(R.id.fax)).setText(mPrefs.getString("fax", ""));
		((EditText)findViewById(R.id.email)).setText(mPrefs.getString("email", ""));
		((Spinner)findViewById(R.id.nazionalita)).setSelection(mPrefs.getInt("nazionalita", index));
		((Button)findViewById(R.id.data)).setText(mPrefs.getString("nascita", ""));
		if(savedInstanceState!=null)
			onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	
	public void showDatePickerDialog(View view){
		DialogFragment newFragment = new DatePickerFragment(view.getId());
		newFragment.show(getFragmentManager(), "datePicker");
	}
	public void setCountries(){
		List<String> countries = new ArrayList<String>();
	    Locale[] locales = Locale.getAvailableLocales();
	    for (Locale locale : locales) {
	      String name = locale.getDisplayCountry();
	      if (!"".equals(name) &&(!countries.contains(name))){
	        countries.add(name);
	      }
	    }
	    Collections.sort(countries);
	    Spinner s=(Spinner) findViewById(R.id.nazionalita);
	    dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    s.setAdapter(dataAdapter);
	    index=countries.indexOf(Locale.getDefault().getDisplayCountry());
	    s.setSelection(index);
	}
	public void select_photo(View view){
		startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), SELECT_IMAGE);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri selectedImage;
		  super.onActivityResult(requestCode, resultCode, data);
		  if (requestCode == SELECT_IMAGE)
		    if (resultCode == Activity.RESULT_OK) {
		    	try{
		    		selectedImage = data.getData();
				    Bitmap bitmap=decodeFileSafe(getRealPathFromURI(selectedImage),100,100);
				     /*int width = bitmap.getWidth();
				     int height = bitmap.getHeight();
				     int newWidth = 200;
				     int newHeight = 200;

				     // calculate the scale - in this case = 0.4f
				     float scaleWidth = ((float) newWidth) / width;
				     float scaleHeight = ((float) newHeight) / height;

				     // createa matrix for the manipulation
				     Matrix matrix = new Matrix();
				     // resize the bit map
				     matrix.postScale(scaleWidth, scaleHeight);
				     // rotate the Bitmap


				     // recreate the new Bitmap
				     Bitmap resized = Bitmap.createBitmap(bitmap, 0, 0,
				                       width, height, matrix, true);
					*/
				     // make a Drawable from Bitmap to allow to set the BitMap
				     // to the ImageView, ImageButton or what ever
					    ImageView i=(ImageView) findViewById(R.id.foto);
					    foto=bitmap;
					    i.setImageBitmap(foto);

		    	}
		    	catch(Exception e){
		    		Context context = getApplicationContext();
		    		CharSequence text = "Impossibile recuperare la foto";
		    		int duration = Toast.LENGTH_SHORT;
		    		Toast toast = Toast.makeText(context, text, duration);
		    		toast.show();
		    }
		   } 
	}
	public void nextPage (View view){
		name=((EditText) findViewById(R.id.name)).getText().toString();
		address=((EditText)findViewById(R.id.address)).getText().toString();
		email=((EditText)findViewById(R.id.email)).getText().toString();
		fax=((EditText)findViewById(R.id.fax)).getText().toString();
		telefono=((EditText)findViewById(R.id.telefono)).getText().toString();
		nazionalita=dataAdapter.getItem(((Spinner)findViewById(R.id.nazionalita)).getSelectedItemPosition()).toString();
		dataNascita=((Button)findViewById(R.id.data)).getText().toString();
		Intent intent = new Intent(this, JobExperiences.class);
		startActivity(intent);
	}
	
	public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaColumns.DATA };
        Cursor cursor =  getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putString("data",((Button)findViewById(R.id.data)).getText().toString());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle outState){
		super.onRestoreInstanceState(outState);
		ImageView i=(ImageView) findViewById(R.id.foto);
		((Button)findViewById(R.id.data)).setText(outState.getString("data"));
		if(foto!=null)
			i.setImageBitmap(foto);
		else
			i.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.android_default));
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("nome", ((EditText)findViewById(R.id.name)).getText().toString());
        ed.putString("indirizzo", ((EditText)findViewById(R.id.address)).getText().toString());
        ed.putString("telefono", ((EditText)findViewById(R.id.telefono)).getText().toString());
        ed.putString("fax", ((EditText)findViewById(R.id.fax)).getText().toString());
        ed.putString("email", ((EditText)findViewById(R.id.email)).getText().toString());
        ed.putInt("nazionalita", ((Spinner)findViewById(R.id.nazionalita)).getSelectedItemPosition());
        ed.putString("nascita", ((Button)findViewById(R.id.data)).getText().toString());
        ed.commit();
	}
	@Override
	public void onPause(){
		super.onPause();
		SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("nome", ((EditText)findViewById(R.id.name)).getText().toString());
        ed.putString("indirizzo", ((EditText)findViewById(R.id.address)).getText().toString());
        ed.putString("telefono", ((EditText)findViewById(R.id.telefono)).getText().toString());
        ed.putString("fax", ((EditText)findViewById(R.id.fax)).getText().toString());
        ed.putString("email", ((EditText)findViewById(R.id.email)).getText().toString());
        ed.putInt("nazionalita", ((Spinner)findViewById(R.id.nazionalita)).getSelectedItemPosition());
        ed.putString("nascita", ((Button)findViewById(R.id.data)).getText().toString());
        ed.commit();
		
	}
	@Override
	public void onResume(){
		super.onResume();
		((EditText)findViewById(R.id.name)).setText(mPrefs.getString("nome", ""));
		((EditText)findViewById(R.id.address)).setText(mPrefs.getString("indirizzo", ""));
		((EditText)findViewById(R.id.telefono)).setText(mPrefs.getString("telefono", ""));
		((EditText)findViewById(R.id.fax)).setText(mPrefs.getString("fax", ""));
		((EditText)findViewById(R.id.email)).setText(mPrefs.getString("email", ""));
		((Spinner)findViewById(R.id.nazionalita)).setSelection(mPrefs.getInt("nazionalita", index));
		((Button)findViewById(R.id.data)).setText(mPrefs.getString("nascita", ""));
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and width
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);

        // Choose the smallest ratio as inSampleSize value, this will guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
}
	
	public  Bitmap decodeFileSafe(String path,  int reqWidth, int reqHeight) throws NullPointerException{
	    // First decode with inJustDecodeBounds=true to check dimensions
	    if(path.equals(null))
	    	throw new NullPointerException();
	    else{
		final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(path,options);
	    
	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(path, options);
	    }
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId()) {
	        case R.id.menu_info:
	            info();      
	    }
	    return true;
	}
	
	public void info(){
		Intent i=new Intent(this,Info.class);
		startActivity(i);
	}
}




