package com.notas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private MenuItem adicionar;
	public DatabaseHandler db;
	public int REQUEST_ADD_BLOCO = 10;
	public int REQUEST_EDIT_BLOCO = 11;
	ArrayList<Nota> array_notas = new ArrayList<Nota>();
	Adaptador adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		// Instancia DatabaseHandler
		db = new DatabaseHandler(this);
		// obtem todas as entradas do banco
		array_notas = db.getAllNotas();		
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
				intent.putExtra("folder", array_notas.get(arg2).get_folder());
				startActivityForResult(intent, 999);				
				
			}
		});	
		
		// LONG CLICK
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//Toast.makeText(getApplicationContext(), "LONG CLICK", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), Activity_edit_bloco.class);
				intent.putExtra("id", array_notas.get(arg2).get_id());
				startActivityForResult(intent, REQUEST_EDIT_BLOCO);
				return false;
			}
		});
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Retorno a MainActivity !
		
		if ( requestCode == REQUEST_ADD_BLOCO && resultCode == RESULT_OK ){
			array_notas.clear();
			array_notas = db.getAllNotas();
			adapter.notifyDataSetChanged();
		// usuario criou novo bloco de notas
		// make toast "bloco criado com sucesso"
		// atualiza listview
		}
		
		if ( requestCode == REQUEST_EDIT_BLOCO && resultCode == RESULT_OK){
			array_notas.clear();
			array_notas = db.getAllNotas();
			adapter.notifyDataSetChanged();		
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		adicionar = menu.add(Menu.NONE, 10, 0, "Adicionar");
		adicionar.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		adicionar.setIcon(android.R.drawable.ic_menu_manage);		
		return true;
	}
		
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {		
		
		if(item==adicionar){		
			for (int i = 0; i < array_notas.size(); i++){
				db.deleteNota(array_notas.get(i));
			}
			array_notas.clear();
			Toast.makeText(getApplicationContext(), "Todo conteúdo foi apagado!!", Toast.LENGTH_SHORT).show();
			adapter.notifyDataSetChanged();
			
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
			return array_notas.size();			
		}

		@Override
		public Object getItem(int arg0) {	
			return array_notas.get(arg0);
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

			// seta o nome_da_foto da linha
			TextView folder_name = (TextView) arg1.findViewById(R.id.folder_name);
			HashSet<Nota> hash = new HashSet<Nota>();
			ArrayList<Nota> nh = array_notas;
			hash.addAll(nh);
			nh.clear();
			nh.addAll(hash);
			
			
			folder_name.setText(array_notas.get(arg0).get_folder());

			// seta o thumbnail da linha
			ImageView thumb_folder = (ImageView) arg1.findViewById(R.id.thumb_folder);
			thumb_folder.setImageResource(R.drawable.folder);

			return arg1;
		}
		
	}
	
}
