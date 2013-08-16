package com.notas;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "notas_db";
	private static final String TABLE_NOTA = "nota_table";
	
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_NOTA = "nota";
	private static final String KEY_DATE = "date";
	private static final String KEY_FOLDER = "folder";
	
	
	public DatabaseHandler(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_NOTA_TABLE = "CREATE TABLE " 
				+ TABLE_NOTA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_FOLDER + " TEXT,"
				+ KEY_TITLE + " TEXT," 
				+ KEY_DATE + " INTEGER," 
				+ KEY_NOTA + " TEXT" 
				+ ")";
		db.execSQL(CREATE_NOTA_TABLE);
	}	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTA);		
		onCreate(db);		
	}
	
	// GRUD Operations (Create, Read, Update and Delete)
	public void addNota(Nota nota){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_FOLDER, nota.get_folder());
		values.put(KEY_TITLE, nota.get_title());
		values.put(KEY_DATE, nota.get_date());
		values.put(KEY_NOTA, nota.get_note());
		db.insert(TABLE_NOTA, null, values);
	}
	
	// Select by id
	public Nota getNota(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_NOTA, new String[] { 
				KEY_ID,  
				KEY_FOLDER,
				KEY_TITLE,
				KEY_DATE,
				KEY_NOTA}, KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		
		Nota nota = new Nota(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), Long.parseLong(cursor.getString(4)));
		
		return nota;
	}
	
	// Select All by id
	public List<Nota> getAllNotas(){
		
	}
	
	public int getNotasCount(){
		
	}
	
	public int updateNota(Nota nota){
		
	}
	
	public void deleteNota(Nota nota){
		
	}
	
	
	
	
}
