package com.notas;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_Notas extends Activity {

	public DatabaseHandler db = new DatabaseHandler(this);
	public ArrayList<Nota> array_notas = new ArrayList<Nota>();
	public Adaptador adapter;
	public String FOLDER = new String();
	public int REQUEST_ADD_NOTE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notas);
		FOLDER = getIntent().getStringExtra("folder");
		array_notas = db.getNotaFolder(FOLDER);
		ListView lista = (ListView)findViewById(R.id.listView_notas);
		adapter = new Adaptador(this);
		lista.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__notas, menu);
		return true;
	}
	
	public void bt_add_nota(View v){
		Intent intent = new Intent(getApplicationContext(), Activity_add_nota.class);
		intent.putExtra("folder", FOLDER);
		startActivityForResult(intent, REQUEST_ADD_NOTE);	
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
			// gambi para não carregar uma linha vazia quando não há notas no array
			if(!array_notas.get(0).get_note().isEmpty()){
				return array_notas.size();	
			}
			return 0;
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
			
			String nota_substring = array_notas.get(arg0).get_note();
			if(!nota_substring.isEmpty()){
				nota_substring = nota_substring.substring(0, 30);				
				TextView nota_name = (TextView)arg1.findViewById(R.id.TextView_nota_name);
				nota_name.setText(nota_substring);
				ImageView thumb_folder = (ImageView)arg1.findViewById(R.id.imageView_nota);
				thumb_folder.setImageResource(R.drawable.nota);
			}

			return arg1;
		}
		
	}

}
