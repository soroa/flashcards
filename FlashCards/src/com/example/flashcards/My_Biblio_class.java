package com.example.flashcards;


import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;


public class My_Biblio_class implements Bibliothek_IO {

	private Context main_context;
	
	public My_Biblio_class(Context c){
		main_context=c;		
	}
	
	@Override
	public boolean delete_biblio(String bibliothek) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, bibliothek);
		temp.openToWrite();
		boolean rueck = (temp.deleteAll());
		temp.close();
		return rueck;
	}

	@Override
	public Flashcard_struct[] getAllWords(String library) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToRead();
		Flashcard_struct[] rueck = temp.getAll();
		temp.close();
		return rueck;
	}

	@Override
	public boolean change_Word_byFront(String library, String question,	Flashcard_struct newcard) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToRead();
		Flashcard_struct cardToChange = temp.getCard_byFront(question);
		temp.close();
		if(cardToChange==null){return false;}//if card doesn't exist
		
		temp.openToWrite();
		boolean rueck= temp.deleteFlashcard_struct(cardToChange);
		if(rueck){
			temp.insert(newcard);
		}
		temp.close();
		return rueck;
	}

	@Override
	public boolean change_Word_byBack(String library, String solution,Flashcard_struct newcard) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToRead();
		Flashcard_struct cardToChange = temp.getCard_byBack(solution);
		temp.close();
		if(cardToChange==null){return false;}//if card doesn't exist
		
		temp.openToWrite();
		boolean rueck= temp.deleteFlashcard_struct(cardToChange);
		if(rueck){
			temp.insert(newcard);
		}
		temp.close();
		return rueck;
	}

	@Override
	public boolean delete_Word_byFront(String library, String question) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToRead();
		Flashcard_struct cardToDelete = temp.getCard_byFront(question);
		temp.close();
		if(cardToDelete==null){return false;}
		temp.openToWrite();
		boolean rueck= temp.deleteFlashcard_struct(cardToDelete);
		temp.close();
		return rueck;
	}

	@Override
	public boolean delete_Word_byBack(String library, String solution) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToRead();
		Flashcard_struct cardToDelete = temp.getCard_byBack(solution);
		temp.close();
		if(cardToDelete==null){return false;}
		temp.openToWrite();
		boolean rueck= temp.deleteFlashcard_struct(cardToDelete);
		temp.close();
		return rueck;
	}

	@Override
	public boolean insert_new_Card(String library, Flashcard_struct cardToInsert) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToWrite();
		boolean rueck = (-1!=temp.insert(cardToInsert));
		temp.close();
		return rueck;
	}

}
