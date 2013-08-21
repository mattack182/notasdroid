package com.notas;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Activity_add_nota extends Activity {
	
	String FOLDER;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_nota);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_nota, menu);
	
		return true;
	}
	
	public void bt_salva_nota(View v){
		
	}

}
