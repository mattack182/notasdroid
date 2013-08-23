package com.notas;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_add_bloco extends Activity {	
	
	DatabaseHandler db = new DatabaseHandler(this);
	ArrayList<Nota> notas = new ArrayList<Nota>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_bloco);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_bloco, menu);
		return true;
	}
	
	public void bt_OK(View v){
		EditText nome_pasta = (EditText)findViewById(R.id.editText_add_bloco);
		nome_pasta.setSingleLine(true);
		
		notas = db.getNotaFolder(nome_pasta.getText().toString());
		if (notas.isEmpty()){
			if (!nome_pasta.getText().toString().isEmpty()){
				
				// Salva no DB
				Nota nota = new Nota();
				nota.set_folder(nome_pasta.getText().toString());
				nota.set_date(System.currentTimeMillis());
				nota.set_note("");
				nota.set_title("");
				db.addNota(nota);
				
				Intent in = new Intent();
				setResult(RESULT_OK, in);
				finish();	
			}
			else{
				Toast.makeText(getApplicationContext(), "Você não digitou um nome para o bloco de notas", Toast.LENGTH_SHORT).show();
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "Bloco de notas já existe!", Toast.LENGTH_SHORT).show();
		}
		
		
	}

}
