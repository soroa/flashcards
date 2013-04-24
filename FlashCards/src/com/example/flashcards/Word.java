package com.example.flashcards;

import java.sql.Date;
import java.util.Calendar;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;


public class Word {
	
	public static final String MYDATABASE_NAME = "Flashcard_DATABASE";
	public static final String MYDATABASE_LIBRARY = "Flashcard_LIBRARY";
	public static final int MYDATABASE_VERSION = 1;
	public static final String KEY_ID = "_id";
	public static final String KEY_QUESTION = "question";
	public static final String KEY_SOLUTION = "solution";
	public static final String KEY_MISTAKES = "Numb_of_m";
	//public static final Calendar KEY_DATE = Calendar.getInstance();
	
	private static final String SCRIPT_CREATE_DATABASE = "create table "
			+ MYDATABASE_LIBRARY + " (" 
			+ KEY_ID + " integer primary key autoincrement, " 
			+ KEY_QUESTION + " text not null"
			+ KEY_SOLUTION + " text not null"
			+ KEY_MISTAKES + " number of mistakes"	
			+");";
	
	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;
	
	private Context libraryname;
	
	public Word(Context c){
		libraryname=c;
	}
	
	
	public Word openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(libraryname, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public Word openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(libraryname, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		sqLiteHelper.close();
	}

	public long insert(String quest, String sol, short mist ) {

		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_QUESTION, quest);
		contentValues.put(KEY_SOLUTION, sol);
		contentValues.put(KEY_MISTAKES, mist);
		return sqLiteDatabase.insert(MYDATABASE_LIBRARY, null, contentValues);
	}
	
	public long insert(Flashcard_struct card) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_QUESTION, card.question);
		contentValues.put(KEY_SOLUTION, card.solution);
		contentValues.put(KEY_MISTAKES, card.mistakes);
		return sqLiteDatabase.insert(MYDATABASE_LIBRARY, null, contentValues);
	}
	
	
	public int deleteAll() {
		return sqLiteDatabase.delete(MYDATABASE_LIBRARY, null, null);
	}
	
	public Cursor queueAll() {
		String[] columns = new String[] { KEY_ID, KEY_QUESTION,KEY_SOLUTION,KEY_MISTAKES };
		Cursor cursor = sqLiteDatabase.query(MYDATABASE_LIBRARY, null, null,
				null, null, null, null);

		return cursor;
	}
	
	/**
	 * @param index of the flashcard start with 0
	 * @return the flashcard ! null if not found
	 */
	public Flashcard_struct getCard(int number){
		Cursor cursor = queueAll();
		if (cursor != null)
			cursor.moveToFirst();
		else 
			return null;
		for (int i=0; i<number; i++){
			cursor.moveToNext();
		}
		return new Flashcard_struct(cursor.getString(0),cursor.getString(1), cursor.getShort(2));
	}
	
	/*public static final String KEY_STRING = "question";
	public static final String KEY_STRING = "answer";
	public static final */
	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(SCRIPT_CREATE_DATABASE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}
}
