package com.notas;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.drm.DrmManagerClient.OnEventListener;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	MenuItem adicionar;
	
	DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Instancia DatabaseHandler
		db = new DatabaseHandler(this);
		
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
			// TODO Ir para Activity Adicionar Novo Bloco de Notas
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	public void AAA_TESTE(){
		File f = new File(Environment.getExternalStorageDirectory(), "img.jpg");		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		startActivityForResult(intent, 80);
	}
	

	public void bt_add_bloco(View v){
		AAA_TESTE();
	}
	
	
	
	

	

}
