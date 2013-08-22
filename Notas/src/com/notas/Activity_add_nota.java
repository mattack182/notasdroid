package com.notas;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_add_nota extends Activity {
	
	String FOLDER = new String();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_nota);
		FOLDER = getIntent().getStringExtra("folder");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_nota, menu);	
		return true;
	}
	
	public void bt_salva_nota(View v){
		EditText txt = (EditText)findViewById(R.id.editText_nota);
		DatabaseHandler db = new DatabaseHandler(this);
		
		if(!txt.getText().toString().isEmpty()){
			// Salva no DB
			Nota nota = new Nota();
			nota.set_folder(FOLDER);
			nota.set_date(System.currentTimeMillis());
			nota.set_note(txt.getText().toString());
			Log.v("ADD", nota.get_note());
			db.addNota(nota);
			
			Intent in = new Intent();
			setResult(RESULT_OK, in);
			finish();	
		}
		else{
			Toast.makeText(getApplicationContext(), "A nota está vazia, digite algum texto para salvar.", Toast.LENGTH_SHORT).show();
		}
		
	}

}
