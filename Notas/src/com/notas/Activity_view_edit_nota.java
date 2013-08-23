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

public class Activity_view_edit_nota extends Activity {
	
	DatabaseHandler db = new DatabaseHandler(this);
	Nota nota = new Nota();
	public int _ID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_edit_nota);
		_ID = getIntent().getIntExtra("id", 0);
		nota = db.getNota(_ID);	
		EditText txt = (EditText)findViewById(R.id.editText1);		
		txt.setText(nota.get_note());
		
	}
	
	public void bt_OK(View v){
		EditText txt = (EditText)findViewById(R.id.editText1);
		if (!txt.getText().toString().isEmpty()){
			nota.set_note(txt.getText().toString());
			nota.set_date(System.currentTimeMillis());
			db.updateNota(nota);
			Intent in = new Intent();			
			setResult(RESULT_OK, in);
			finish();
		}
		else {
			Toast.makeText(getApplicationContext(), "A nota está vazia.\n Digite uma nota para salvar.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void bt_delete(View v){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);		
		builder.setTitle("Remover nota");
		builder.setMessage("Você tem certeza que deseja remove-la?");
		
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {		    	
		    	
		    	db.deleteNota(nota);
		    	EditText txt = (EditText)findViewById(R.id.editText1);
				txt.setText("");
		        dialog.dismiss();		        
		        Intent in = new Intent();			
				setResult(RESULT_OK, in);
				Toast.makeText(getApplicationContext(), "A nota foi removida com sucesso!", Toast.LENGTH_SHORT).show();
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

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_edit_nota, menu);
		return true;
	}
	

}
