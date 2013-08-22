package com.notas;

public class Nota {
	
	// variaveis privadas
	int _id;
	private String _folder;
	private String _title;
	private String _note;
	private long _date;	
	
	// construtor vazio
	public Nota(){
		
	}
	
	// construtor	
	public Nota(int id, String folder, String title, String note, long date){
		this._id = id;
		this._folder = folder;
		this._title = title;
		this._note = note;
		this._date = date;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_folder() {
		return _folder;
	}

	public void set_folder(String _folder) {
		this._folder = _folder;
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public String get_note() {
		return _note;
	}

	public void set_note(String _note) {
		this._note = _note;
	}

	public long get_date() {
		return _date;
	}

	public void set_date(long _date) {
		this._date = _date;
	}
	
	

}
