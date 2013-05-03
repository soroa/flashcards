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
		Word temp = new Word(main_context, bibliothek);
		temp.openToWrite();
		boolean rueck = (0 < temp.deleteAll());
		temp.close();
		return rueck;
	}

	@Override
	public Flashcard_struct[] getAllWords(String library) {
		Word temp = new Word(main_context, library);
		List<Flashcard_struct> list_Fc = new List<Flashcard_struct>();
		for( int i=0; temp.getCard(i)!=null; i++){
			list_Fc.add(temp.getCard(i));
		}
		return null;
	}

	@Override
	public boolean change_Word_byFront(String library, String question,
			Flashcard_struct newcard) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean change_Word_byBack(String library, String solution,
			Flashcard_struct newcard) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete_Word_byFront(String library, String question) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete_Word_byBack(String library, String solution) {
		// TODO Auto-generated method stub
		return false;
	}

}
