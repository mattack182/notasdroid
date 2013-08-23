package com.notas;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private MenuItem settings;
	private MenuItem adicionar;
	public DatabaseHandler db;
	public int REQUEST_ADD_BLOCO = 10;
	public int REQUEST_EDIT_BLOCO = 11;
	ArrayList<Nota> array_notas = new ArrayList<Nota>();	
	ArrayList<String> array_blocos = new ArrayList<String>();
	Adaptador adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// consulta banco, instancia arrays
		dataInit();
		ListView lista = (ListView)findViewById(R.id.listView_blocos);
		adapter = new Adaptador(this);
		lista.setAdapter(adapter);	
		
		/*
		 * ListView Listeners
		 */
	
		// SIMPLE CLICK
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//Toast.makeText(getApplicationContext(), "SHORT CLICK", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), Activity_Notas.class);
				intent.putExtra("folder", array_blocos.get(arg2));
				startActivityForResult(intent, REQUEST_ADD_BLOCO);				
				
			}
		});	
		
		// LONG CLICK
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//Toast.makeText(getApplicationContext(), "LONG CLICK", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), Activity_edit_bloco.class);
				intent.putExtra("folder", array_blocos.get(arg2));
				startActivityForResult(intent, REQUEST_EDIT_BLOCO);
				return false;
			}
		});
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Retorno a MainActivity !
		
		if ( requestCode == REQUEST_ADD_BLOCO && resultCode == RESULT_OK ){
			dataInit();
			adapter.notifyDataSetChanged();
		}
		
		if ( requestCode == REQUEST_EDIT_BLOCO && resultCode == RESULT_OK){
			dataInit();
			adapter.notifyDataSetChanged();
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		settings = menu.add(Menu.NONE, 10, 0, "Apagar Tudo");
		settings.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		settings.setIcon(android.R.drawable.ic_menu_delete);
		
		adicionar = menu.add(Menu.NONE, 11, 1, "Adicionar");
		adicionar.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		adicionar.setIcon(android.R.drawable.ic_menu_add);
		
		return true;
	}
		
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {		
		
		if(item==settings){
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);		
			builder.setTitle("Atenção!");
			builder.setMessage("Você está prestes a apagar TODOS os blocos de nota.\nDeseja apagar todos os blocos de notas?");
			
			builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int which) {		    	
			    	
			    	for (int i = 0; i < array_notas.size(); i++){
						db.deleteNota(array_notas.get(i));
					}
					dataInit();
					Toast.makeText(getApplicationContext(), "Todos os blocos de notas foram apagados!!", Toast.LENGTH_SHORT).show();
					adapter.notifyDataSetChanged();

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
		if ( item == adicionar){
			bt_add_bloco(getCurrentFocus());
		}
		return super.onMenuItemSelected(featureId, item);
	}	

	
	/*
	 * Evento de botão
	 */	
	public void bt_add_bloco(View v){
		Intent intent = new Intent(getApplicationContext(), Activity_add_bloco.class);
		startActivityForResult(intent, REQUEST_ADD_BLOCO);	
	}
	
	public void dataInit(){
		db = new DatabaseHandler(this);
		array_notas.clear();
		array_notas = db.getAllNotas();
		
		// linkedhashset para remover as duplicacoes e obter um arraylist de string em sequencia fixa
		LinkedHashSet<String> pastas = new LinkedHashSet<String>();		
		for (Nota bloco : array_notas){
			pastas.add(bloco.get_folder());
		}
		array_blocos.clear();
		array_blocos.addAll(pastas);		

	}
	
	/*
	 * Classe que trata a ListView
	 */	
	class Adaptador extends BaseAdapter {
		
		Context context;
		LayoutInflater inflater;

		public Adaptador(Context context) {
			this.context = context;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return array_blocos.size();			
		}

		@Override
		public Object getItem(int arg0) {	
			return array_blocos.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
					
			if (arg1 == null) {
				arg1 = inflater.inflate(R.layout.row_blocos, arg2, false);
			}
			
			TextView folder_name = (TextView) arg1.findViewById(R.id.folder_name);
			ImageView thumb_folder = (ImageView) arg1.findViewById(R.id.thumb_folder);			
			folder_name.setText(array_blocos.get(arg0));			
			thumb_folder.setImageResource(R.drawable.folder);

			return arg1;
		}
		
	}
	
}
