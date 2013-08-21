package com.notas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_add_bloco extends Activity {	
	
	DatabaseHandler db = new DatabaseHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_bloco);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adicionar__pasta, menu);
		return true;
	}
	
	public void bt_OK(View v){
		EditText nome_pasta = (EditText)findViewById(R.id.editText_edit_bloco);
		nome_pasta.setSingleLine(true);
		if (!nome_pasta.getText().toString().isEmpty()){
			
			// Salva no DB
			Nota nota = new Nota();
			nota.set_folder(nome_pasta.getText().toString());
			nota.set_date(System.currentTimeMillis());
			db.addNota(nota);
			
			Intent in = new Intent();
			in.putExtra("nome_pasta", nome_pasta.getText().toString());
			setResult(RESULT_OK, in);
			finish();	
		}
		else{
			Toast.makeText(getApplicationContext(), "Você não digitou o nome do bloco", Toast.LENGTH_SHORT).show();
		}
		
	}

}
