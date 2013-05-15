package com.example.flashcards.database;


import android.content.Context;

public class Bibliothek_IO_Factory {
	/**
	 * The factory method for Bibliothek_IO implementations.
	 * @param just put "this" in
	 * @return an Bibliothek_IO instance
	 */
	public static Bibliothek_IO create(Context c )
	{
		return new com.example.flashcards.database.My_Biblio_class(c); 
	}
}
