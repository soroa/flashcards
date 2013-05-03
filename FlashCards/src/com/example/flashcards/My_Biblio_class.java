package com.example.flashcards;


import android.app.Activity;
import android.content.Context;


public class My_Biblio_class implements Bibliothek_IO {

	private Context main_context;
	public My_Biblio_class(Context c){
		main_context=c;		
	}
	
	@Override
	public boolean delete_biblio( String bibliothek) {
		Word temp = new Word(main_context, bibliothek);
		temp.openToWrite();
		boolean rueck = (0 < temp.deleteAll());
		temp.close();
		return rueck;
	}

}
