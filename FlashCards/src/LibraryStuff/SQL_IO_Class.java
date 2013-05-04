package LibraryStuff;

import java.io.File;
import java.util.ArrayList;

import flashcards.Flashcard_struct;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL_IO_Class {

	public static final String MYDATABASE_NAME = "Flashcard_DATABASE";
	public static final String MYDATABASE_LIBRARY = "Flashcard_LIBRARY";
	public static final int MYDATABASE_VERSION = 1;
	public static final String KEY_ID = "_id";
	public static final String KEY_QUESTION = "question";
	public static final String KEY_SOLUTION = "solution";
	public static final String KEY_MISTAKES = "Numb_of_m";
	// public static final Calendar KEY_DATE = Calendar.getInstance();

	private static final String SCRIPT_CREATE_DATABASE = "create table "
			+ MYDATABASE_LIBRARY + " (" + KEY_ID
			+ " integer primary key autoincrement, " + KEY_QUESTION
			+ " text not null" + KEY_SOLUTION + " text not null" + KEY_MISTAKES
			+ " number of mistakes" + ");";

	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;
	private String libraryname;

	/**
	 * @param Context
	 *            , just put "this" in
	 * @param string
	 *            with the name of the library
	 */
	public SQL_IO_Class(Context c, String name) {
		libraryname = name;
		context = c;
	}

	public SQL_IO_Class openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, libraryname, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public SQL_IO_Class openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, libraryname, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		sqLiteHelper.close();
	}

	public long insert(String quest, String sol, short mist) {

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

	/**
	 * Attention might cause problems warnings ignored (see source, line commented)
	 * @return if library is successfully deleted
	 */
	public boolean deleteAll() {
		int worked;
		worked=sqLiteDatabase.delete(libraryname, null, null);
		if(worked!=1){
			return false;
		}
		File libraryFile = new File(sqLiteDatabase.getPath());
		return SQLiteDatabase.deleteDatabase(libraryFile); //might cause problems warnings ignored
		
	}

	/**
	 * Don't ask Why, it works! (Jedenfalls als ich es in "My_First_App" ausprobiert habe)
	 * @param Flashcard_struct of the card to delete
	 * @return true card is deleted, false card not found
	 */
	public boolean deleteFlashcard_struct(Flashcard_struct card){
		Cursor cursor = queueAll();
		cursor.moveToFirst();
		do{
			if(getFlashcard_structFromCursor(cursor).equals(card)){
				String toDelete = cursor.getString(0);
				sqLiteDatabase.delete(libraryname, KEY_ID+" = "+toDelete, null);
				return true;
			}
		}while(cursor.moveToNext());
		return false;
	}
	
	
	private Cursor queueAll() {
		/*String[] columns = new String[] { KEY_ID, KEY_QUESTION, KEY_SOLUTION,
				KEY_MISTAKES };*/ 
		Cursor cursor = sqLiteDatabase.query(libraryname, null, null,
				null, null, null, null);

		return cursor;
	}

	/**
	 * Get all Flachcards in a Flashcard_struct-Array
	 * @return the flashcards as Flashcard_struct[]
	 */
	public Flashcard_struct[] getAll() {
		ArrayList<Flashcard_struct> list_Fc = new ArrayList<Flashcard_struct>();
		Cursor allcards = queueAll();
		allcards.moveToFirst();
		while(!allcards.isLast()){ //stops before the last card is reached
			list_Fc.add(getFlashcard_structFromCursor(allcards));
			allcards.moveToNext();
		}
		list_Fc.add(getFlashcard_structFromCursor(allcards));// add last card.
		return (Flashcard_struct[]) list_Fc.toArray(); // hope it Works, produce Error
	}
	
	/**
	 * @param the cursor that points to the card 
	 * @return the flashcard as Flashcard_struct
	 */
	private Flashcard_struct getFlashcard_structFromCursor(Cursor cursor){
		
		return new Flashcard_struct(cursor.getString(1), cursor.getString(2),
				cursor.getShort(3));
	}

	/**
	 * @param index
	 *            of the flashcard start with 0
	 * @return the flashcard ! null if not found
	 */
	public Flashcard_struct getCard(int number) {
		Cursor cursor = queueAll();
		if (cursor != null)
			cursor.moveToFirst();
		else
			return null;
		for (int i = 0; i < number; i++) {
			if (cursor.isLast()) {
				return null;
			}
			cursor.moveToNext();
		}
		return getFlashcard_structFromCursor(cursor);
	}

	/**
	 * @param question
	 *            search a flashcard with the given question
	 * @return the flashcard ! null if not found
	 */
	public Flashcard_struct getCard_byFront(String question) {
		Cursor cursor = queueAll();
		if (cursor != null)
			cursor.moveToFirst();
		else
			return null;
		do {
			if (cursor.getString(1).equals(question)) {
				return getFlashcard_structFromCursor(cursor);
			}
		} while (cursor.moveToNext());
		return null; // if not found null will be reached
	}

	/**
	 * @param solution
	 *            search a flashcard with the given solution
	 * @return the flashcard ! null if not found
	 */
	public Flashcard_struct getCard_byBack(String solution) {
		Cursor cursor = queueAll();
		if (cursor != null)
			cursor.moveToFirst();
		else
			return null;
		do {
			if (cursor.getString(2).equals(solution)) {
				return getFlashcard_structFromCursor(cursor);
			}
		} while (cursor.moveToNext());
		return null; // if not found null will be reached
	}
	
	/**
	 * Is a Class for Class Word
	 */
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
