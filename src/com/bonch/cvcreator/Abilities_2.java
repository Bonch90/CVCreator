package com.bonch.cvcreator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class Abilities_2 extends Activity {
	EditText name;
	private SharedPreferences mPrefs;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPrefs = getSharedPreferences("dati", MODE_PRIVATE);
		setContentView(R.layout.activity_abilities_2);
		((EditText)findViewById(R.id.relazioni)).setText(mPrefs.getString("relazioni", ""));
		((EditText)findViewById(R.id.organizza)).setText(mPrefs.getString("organizza", ""));
		((EditText)findViewById(R.id.tecnica)).setText(mPrefs.getString("tecnica", ""));
		((EditText)findViewById(R.id.arte)).setText(mPrefs.getString("arte", ""));
		((EditText)findViewById(R.id.altri)).setText(mPrefs.getString("altri", ""));
		((EditText)findViewById(R.id.patente)).setText(mPrefs.getString("patente", ""));
		name=(EditText) findViewById(R.id.name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("relazioni", ((EditText)findViewById(R.id.relazioni)).getText().toString());
        ed.putString("organizza", ((EditText)findViewById(R.id.organizza)).getText().toString());
        ed.putString("tecnica", ((EditText)findViewById(R.id.tecnica)).getText().toString());
        ed.putString("arte", ((EditText)findViewById(R.id.arte)).getText().toString());
        ed.putString("altri", ((EditText)findViewById(R.id.altri)).getText().toString());
        ed.putString("patente", ((EditText)findViewById(R.id.patente)).getText().toString());
        ed.commit();
	}
	@Override
	public void onPause(){
		super.onPause();
		if (progressDialog != null)
	        progressDialog.dismiss();
		SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("relazioni", ((EditText)findViewById(R.id.relazioni)).getText().toString());
        ed.putString("organizza", ((EditText)findViewById(R.id.organizza)).getText().toString());
        ed.putString("tecnica", ((EditText)findViewById(R.id.tecnica)).getText().toString());
        ed.putString("arte", ((EditText)findViewById(R.id.arte)).getText().toString());
        ed.putString("altri", ((EditText)findViewById(R.id.altri)).getText().toString());
        ed.putString("patente", ((EditText)findViewById(R.id.patente)).getText().toString());
        ed.commit();
		
	}
	@Override
	public void onResume(){
		super.onResume();
		((EditText)findViewById(R.id.relazioni)).setText(mPrefs.getString("relazioni", ""));
		((EditText)findViewById(R.id.organizza)).setText(mPrefs.getString("organizza", ""));
		((EditText)findViewById(R.id.tecnica)).setText(mPrefs.getString("tecnica", ""));
		((EditText)findViewById(R.id.arte)).setText(mPrefs.getString("arte", ""));
		((EditText)findViewById(R.id.altri)).setText(mPrefs.getString("altri", ""));
		((EditText)findViewById(R.id.patente)).setText(mPrefs.getString("patente", ""));
	}
	
	
	public void creaPDF(View v){
			 	progressDialog = ProgressDialog.show(this, getString(R.string.elaborazione), getString(R.string.attendere));
				new PDFTask().execute();
			}
			
			public String getRealPathFromURI(Uri contentUri) {
		        String[] proj = { MediaColumns.DATA };
		        Cursor cursor =  getContentResolver().query(contentUri, proj, null, null, null);
		        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		        cursor.moveToFirst();
		        return cursor.getString(column_index);
		    }
			private String capitalize(String line)
			{
			  return Character.toUpperCase(line.charAt(0)) + line.substring(1);
			}
			private class PDFTask extends AsyncTask<Void, Void, Integer> {
				
				@Override
				protected Integer doInBackground(Void... params) {
			    	 String[] capacita= getResources().getStringArray(R.array.capacita);
			 		// step 1: creation of a document-object
			 				Font campo = FontFactory.getFont(FontFactory.HELVETICA, 13);
			 				Font titolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13);
			 		        Document document = new Document(PageSize.A4);
			 		        try {
			 		                // step 2:
			 		                // we create a writer that listens to the document
			 		                // and directs a PDF-stream to a file
			 		                // step 3: we open the document
			 		                PdfWriter.getInstance(document,new FileOutputStream(android.os.Environment.getExternalStorageDirectory() + java.io.File.separator +"CV.pdf"));
			 		                float width = document.getPageSize().getWidth();
			 		                // step 3
			 		                document.open();
			 		                document.addTitle("CV di "+MainActivity.name);
			 		                document.addAuthor("CV Creator");
			 		                // step 4
			 		                float[] columnDefinitionSize = { 30F, 70F};
			 		                PdfPTable table = null;
			 		                PdfPCell c1 = null;
			 		                PdfPCell c2 =null;
			 				        table = new PdfPTable(columnDefinitionSize);
			 				        table.getDefaultCell().setBorder(0);
			 				        table.setHorizontalAlignment(0);
			 				        table.setTotalWidth(width - 72);
			 				        table.setLockedWidth(true);
			 				        c1=new PdfPCell(new Phrase("Curriculum Vitae",titolo));
			 				        c1.setPadding(10);
			 				        c1.disableBorderSide(Rectangle.LEFT);
			 				        c1.disableBorderSide(Rectangle.TOP);
			 				        c1.disableBorderSide(Rectangle.BOTTOM);
			 				        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 				        table.addCell(c1);
			 				        c2=new PdfPCell(new Phrase());
			 				        c2.setPadding(10);
			 				        c2.setBorder(Rectangle.NO_BORDER);
			 				        c2.setHorizontalAlignment(Element.ALIGN_LEFT);
			 				        table.addCell(c2);
			 				        c1.setPhrase(new Phrase(getString(R.string.title_activity_main),titolo));
			 				        table.addCell(c1);
			 				        try{
			 					        ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 				        	Bitmap bitmap = MainActivity.foto;
			 				        	bitmap.compress(Bitmap.CompressFormat.JPEG ,100, stream);
			 				        	Image jpg = Image.getInstance(stream.toByteArray());
			 				        	jpg.scalePercent(30);
			 				        	jpg.setAbsolutePosition(400, 730);
			 				        	document.add(jpg);
			 				        }
			 				        catch(NullPointerException e){}
			 				        table.addCell(c2);
			 				        //NOME
			 				        c1.setPhrase(new Phrase(getString(R.string.nome), campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(MainActivity.name, campo));
			 				        table.addCell(c2);
			 				        //INDIRIZZO
			 				        c1.setPhrase(new Phrase(capitalize(getString(R.string.indirizzo)), campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(MainActivity.address, campo));
			 				        table.addCell(c2);
			 				        //TELEFONO
			 				        c1.setPhrase(new Phrase(capitalize(getString(R.string.telefono)), campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(MainActivity.telefono, campo));
			 				        table.addCell(c2);
			 				        if(!MainActivity.fax.equals("")){
			 				        //FAX
			 				        c1.setPhrase(new Phrase(capitalize(getString(R.string.fax)), campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(MainActivity.fax, campo));
			 				        table.addCell(c2);
			 				        }
			 				        //EMAIL
			 				        c1.setPhrase(new Phrase("E-mail", campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(MainActivity.email, campo));
			 				        table.addCell(c2);
			 				        //NAZIONALITA'
			 				        c1.setPhrase(new Phrase(capitalize(getString(R.string.nazionalita)), campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(MainActivity.nazionalita, campo));
			 				        table.addCell(c2);
			 				        //DATA DI NASCITA
			 				        c1.setPhrase(new Phrase(capitalize(getString(R.string.data)), campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(MainActivity.dataNascita, campo));
			 				        table.addCell(c2);
			 				        //ESPERIENZE LAVORATIVE
			 				        c1.setPhrase(new Phrase(getString(R.string.title_activity_job_experiences),titolo));
			 				        c1.setPadding(10);
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase());
			 				        table.addCell(c2);
			 				        for(Job j:JobExperiences.lista_job){
			 				        	c1.setPhrase(new Phrase(capitalize(getString(R.string.date)), campo));
			 				            table.addCell(c1);
			 				            c2.setPhrase(new Phrase(j.startDate+" - "+j.endDate, campo));
			 				            table.addCell(c2);
			 				            c1.setPhrase(new Phrase(capitalize(getString(R.string.njob)), campo));
			 				            table.addCell(c1);
			 				            c2.setPhrase(new Phrase(j.nome, campo));
			 				            table.addCell(c2);
			 				            c1.setPhrase(new Phrase(capitalize(getString(R.string.tjob)), campo));
			 				            table.addCell(c1);
			 				            c2.setPhrase(new Phrase(j.tipo, campo));
			 				            table.addCell(c2);
			 				            c1.setPhrase(new Phrase(capitalize(getString(R.string.impiego)), campo));
			 				            table.addCell(c1);
			 				            c2.setPhrase(new Phrase(j.impiego, campo));
			 				            table.addCell(c2);
			 				            c1.setPhrase(new Phrase(capitalize(getString(R.string.mansione)), campo));
			 				            table.addCell(c1);
			 				            c2.setPhrase(new Phrase(j.mansione, campo));
			 				            table.addCell(c2);
			 				        }
			 				        //ISTRUZIONE E FORMAZIONE
			 				        c1.setPhrase(new Phrase(getString(R.string.title_activity_school_experiences),titolo));
			 				        c1.setPadding(10);
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase());
			 				        table.addCell(c2);
			 				        for(School s:SchoolExperiences.lista_school){
			 				        	c1.setPhrase(new Phrase(capitalize(getString(R.string.date)), campo));
			 				            table.addCell(c1);
			 				            c2.setPhrase(new Phrase(s.startDate+" - "+s.endDate, campo));
			 				            table.addCell(c2);
			 				            c1.setPhrase(new Phrase(capitalize(getString(R.string.nschool)), campo));
			 				            table.addCell(c1);
			 				            c2.setPhrase(new Phrase(s.nome, campo));
			 				            table.addCell(c2);
			 				            c1.setPhrase(new Phrase(capitalize(getString(R.string.materie)), campo));
			 				            table.addCell(c1);
			 				            c2.setPhrase(new Phrase(s.materie, campo));
			 				            table.addCell(c2);
			 				            c1.setPhrase(new Phrase(capitalize(getString(R.string.qualifica)), campo));
			 				            table.addCell(c1);
			 				            c2.setPhrase(new Phrase(s.qualifica, campo));
			 				            table.addCell(c2);
			 				        }
			 				        //CAPACITA' E COMPETENZE PERSONALI
			 				        c1.setPhrase(new Phrase(getString(R.string.title_activity_abilities_2),titolo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase());
			 				        table.addCell(c2);
			 				        c1.setPhrase(new Phrase(capitalize(getString(R.string.madrelingua)),campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(capitalize(Abilities.madrelingua),campo));
			 				        table.addCell(c2);
			 				        for(Language l:Abilities.lista_lingue){
			 					        c1.setPhrase(new Phrase(capitalize(getString(R.string.altraLingua)),campo));
			 					        table.addCell(c1);
			 					        c2.setPhrase(new Phrase(capitalize(l.toString()),campo));
			 					        table.addCell(c2);
			 					        c1.setPhrase(new Phrase(capitalize(getString(R.string.lettura)),campo));
			 					        table.addCell(c1);
			 					        c2.setPhrase(new Phrase(capacita[l.lettura],campo));
			 					        table.addCell(c2);
			 					        c1.setPhrase(new Phrase(capitalize(getString(R.string.scrittura)),campo));
			 					        table.addCell(c1);
			 					        c2.setPhrase(new Phrase(capacita[l.scrittura],campo));
			 					        table.addCell(c2);
			 					        c1.setPhrase(new Phrase(capitalize(getString(R.string.orale)),campo));
			 					        table.addCell(c1);
			 					        c2.setPhrase(new Phrase(capacita[l.orale],campo));
			 					        table.addCell(c2);
			 				        }
			 				        c1.setPhrase(new Phrase(getString(R.string.relazioni),campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(((EditText)findViewById(R.id.relazioni)).getText().toString(),campo));
			 				        table.addCell(c2);
			 				        c1.setPhrase(new Phrase(getString(R.string.organizza),campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(((EditText)findViewById(R.id.organizza)).getText().toString(),campo));
			 				        table.addCell(c2);
			 				        c1.setPhrase(new Phrase(getString(R.string.tecnica),campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(((EditText)findViewById(R.id.tecnica)).getText().toString(),campo));
			 				        table.addCell(c2);
			 				        c1.setPhrase(new Phrase(getString(R.string.arte),campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(((EditText)findViewById(R.id.arte)).getText().toString(),campo));
			 				        table.addCell(c2);
			 				        c1.setPhrase(new Phrase(getString(R.string.altri),campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(((EditText)findViewById(R.id.altri)).getText().toString(),campo));
			 				        table.addCell(c2);
			 				        c1.setPhrase(new Phrase(getString(R.string.patente),campo));
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(((EditText)findViewById(R.id.patente)).getText().toString(),campo));
			 				        table.addCell(c2);
			 				        c1.setPhrase(new Phrase());
			 				        table.addCell(c1);
			 				        c2.setPhrase(new Phrase(getString(R.string.liberatoria),campo));
			 				        table.addCell(c2);
			 				        document.add(table);
			 				        
			 		        }
			 		        catch (DocumentException de) {
			 				                return 0;
			 				} catch (IOException ioe) {
			 				          return 0;
			 				}
			 				// step 5: we close the document
			 				document.close();
							return 1;
			     }

				
				@Override
				protected void onPostExecute(Integer result){
					progressDialog.dismiss();
					if(result==1){
						File pdfFile = new File(android.os.Environment.getExternalStorageDirectory()+java.io.File.separator+"CV.pdf"); 
				        if(pdfFile.exists()) 
				        {
				        	Toast.makeText(Abilities_2.this, "Saved in "+android.os.Environment.getExternalStorageDirectory()+java.io.File.separator+"CV.pdf", Toast.LENGTH_LONG).show();
				            Uri path = Uri.fromFile(pdfFile); 
				            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
				            pdfIntent.setDataAndType(path, "application/pdf");
				            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				            try
				            {
				                startActivity(pdfIntent);
				            }
				            catch(ActivityNotFoundException e)
				            {
				                Toast.makeText(Abilities_2.this, "No Application Available to View PDF", Toast.LENGTH_LONG).show(); 
				            }
				        }
					}
					else{
						 Toast.makeText(Abilities_2.this, "Error while creating PDF", Toast.LENGTH_LONG).show();
					}
			     }


			 }
			
	}


