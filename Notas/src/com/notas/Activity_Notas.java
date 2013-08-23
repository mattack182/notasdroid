package com.notas;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class Activity_Notas extends Activity {

	private MenuItem adicionar;
	public DatabaseHandler db = new DatabaseHandler(this);
	public ArrayList<Nota> array_notas = new ArrayList<Nota>();
	public Adaptador adapter;
	public String FOLDER;
	public int REQUEST_ADD_NOTE = 20;
	public int REQUEST_VIEW_EDIT_NOTE = 21;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notas);		
		FOLDER = getIntent().getStringExtra("folder");
		setTitle(FOLDER.toUpperCase(Locale.US));
		array_notas = db.getNotaFolder(FOLDER);
		// remove linha nota =""
		for (int i = 0 ; i < array_notas.size(); i++){
			if(array_notas.get(i).get_note().isEmpty()){
				array_notas.remove(i);
			}
		}	
		
		ListView lista = (ListView)findViewById(R.id.listView_notas);
		adapter = new Adaptador(this);
		lista.setAdapter(adapter);
		
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(), Activity_view_edit_nota.class);
				intent.putExtra("id", array_notas.get(arg2).get_id());
				startActivityForResult(intent, REQUEST_VIEW_EDIT_NOTE);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__notas, menu);
		adicionar = menu.add(Menu.NONE, 10, 0, "Adicionar");
		adicionar.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		adicionar.setIcon(android.R.drawable.ic_menu_add);	
		return true;
	}
	
	public void bt_add_nota(View v){
		Intent intent = new Intent(getApplicationContext(), Activity_add_nota.class);
		intent.putExtra("folder", FOLDER);
		startActivityForResult(intent, REQUEST_ADD_NOTE);	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		array_notas = db.getNotaFolder(FOLDER);
		// remove linha nota =""
		for (int i = 0 ; i < array_notas.size(); i++){
			if(array_notas.get(i).get_note().isEmpty()){
				array_notas.remove(i);
			}
		}
		adapter.notifyDataSetChanged();
		
		
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		if (item == adicionar){
			bt_add_nota(getCurrentFocus());
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	
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
				arg1 = inflater.inflate(R.layout.row_notas, arg2, false);
			}		
			
			String nota_subject = array_notas.get(arg0).get_note();
			nota_subject = nota_subject.replace("\n"," ");
			
			if(!nota_subject.isEmpty()){
				if(nota_subject.length() > 20){
					nota_subject = (arg0 + 1) + ". " + nota_subject.substring(0, 20) + "...";	
				}
				else{
					nota_subject = (arg0 + 1) + ". "+nota_subject;
				}
				TextView nota_name = (TextView)arg1.findViewById(R.id.TextView_nota_name);
				nota_name.setText(nota_subject);
				ImageView thumb_folder = (ImageView)arg1.findViewById(R.id.imageView_nota);
				thumb_folder.setImageResource(R.drawable.nota);
			}

			return arg1;
		}
		
	}

}
