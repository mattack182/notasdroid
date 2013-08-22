package com.notas;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_edit_bloco extends Activity {
	
	public String FOLDER;
	DatabaseHandler db = new DatabaseHandler(this);
	ArrayList<Nota> array_notas = new ArrayList<Nota>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_bloco);		
		// Recupera id do item, consulta o nome do bloco, seta no EditText
		FOLDER = getIntent().getStringExtra("folder");
		array_notas = db.getNotaFolder(FOLDER);	
		EditText nome_bloco = (EditText) findViewById(R.id.editText_add_nota);
		nome_bloco.setText(FOLDER);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_bloco, menu);
		return true;
	}
	
	public void bt_OK(View v){
		EditText txt = (EditText)findViewById(R.id.editText_add_nota);
		txt.setSingleLine(true);
		if (!txt.getText().toString().isEmpty()){			
			for (Nota a : array_notas){
				a.set_folder(txt.getText().toString());
				db.updateNota(a);
			}			
			Intent in = new Intent();			
			setResult(RESULT_OK, in);
			finish();
		
		}
		else{
			Toast.makeText(getApplicationContext(), "Você não digitou o nome do bloco", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public void bt_delete(View v){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);		
		builder.setTitle("Remover Bloco de Notas");
		builder.setMessage("Você tem certeza que deseja remove-lo?");
		
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {		    	
		    	
		    	db.deleteFolder(array_notas.get(0));
		    	EditText nome_bloco = (EditText) findViewById(R.id.editText_add_nota);
				nome_bloco.setText("");
		        dialog.dismiss();		        
		        Intent in = new Intent();			
				setResult(RESULT_OK, in);
				Toast.makeText(getApplicationContext(), "O Bloco de notas \""+FOLDER+"\" foi removido com sucesso!", Toast.LENGTH_SHORT).show();
				finish();
		    }
		});
		
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		        // Não faz nada, fecha a AlertDialog
		        dialog.dismiss();
		        
		    }
		});

		AlertDialog alert = builder.create();
		alert.show();
		
		
		
	}


}
