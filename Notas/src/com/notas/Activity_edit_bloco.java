package com.notas;

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
	
	public int _ID = 0;
	DatabaseHandler db = new DatabaseHandler(this);
	Nota nota = new Nota();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_bloco);		
		// Recupera id do item, consulta o nome do bloco, seta no EditText
		_ID = getIntent().getIntExtra("id", 0);
		nota = db.getNota(_ID);
		String nome = nota.get_folder();		
		EditText nome_bloco = (EditText) findViewById(R.id.editText_add_nota);
		nome_bloco.setText(nome);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_bloco, menu);
		return true;
	}
	
	public void bt_OK(View v){
		EditText nome_pasta = (EditText)findViewById(R.id.editText_add_nota);
		nome_pasta.setSingleLine(true);
		if (!nome_pasta.getText().toString().isEmpty()){
			
			// Salva no DB			
			nota.set_folder(nome_pasta.getText().toString());
			if(db.updateNota(nota) > 0){
			
			Intent in = new Intent();			
			setResult(RESULT_OK, in);
			finish();
			}
			else{
				Log.v("ERRO", "Nenhuma entrada foi atualizada -> db.updateNota() retornou "+ db.updateNota(nota) + " linhas.");
			}
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
		    	db.deleteFolder(nota);
		    	EditText nome_bloco = (EditText) findViewById(R.id.editText_add_nota);
				nome_bloco.setText("");
		        dialog.dismiss();		        
		        Intent in = new Intent();			
				setResult(RESULT_OK, in);
				Toast.makeText(getApplicationContext(), "O Bloco de notas \""+nota.get_folder()+"\" foi removido com sucesso!", Toast.LENGTH_SHORT).show();
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
