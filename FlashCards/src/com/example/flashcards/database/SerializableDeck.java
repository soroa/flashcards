package com.example.flashcards.database;

import java.io.File;
import java.io.Serializable;

public class SerializableDeck implements Serializable {
	private Flashcard_struct[] myDeck; 

	public SerializableDeck(Flashcard_struct[] deck){
		
		myDeck = deck;
	}
	public Flashcard_struct[] extractDeck(){
		
		return myDeck;
		
	}
	
}
