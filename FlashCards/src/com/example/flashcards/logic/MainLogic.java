package com.example.flashcards.logic;

import java.io.Serializable;
import java.util.Random;

import com.example.flashcards.database.Flashcard_struct;



public class MainLogic implements Logic, Serializable {

	private Flashcard_struct[] cardsToUse;
	private int laenge=0;
	private int aktuell;
	public MainLogic(){
		
	}
	
	@Override
	public void loadCards(Flashcard_struct[] card) {
		cardsToUse=card;
		laenge=cardsToUse.length;
	}

	@Override
	public Flashcard_struct nextCard() {
		if(laenge==0){return null;}
		int up;
		int count=0;
		do{
			Random temp = new Random();
			aktuell=temp.nextInt(laenge);
			up=temp.nextInt(laenge)-laenge/2;
		}while(cardsToUse[aktuell].mistakes>up && count<20);
		return cardsToUse[aktuell];
	}

	//changed, it was the opposite
	@Override
	public void correctAnswer(boolean answer) {
		if(answer){
			cardsToUse[aktuell].mistakes--;
		}else{
			cardsToUse[aktuell].mistakes++;
		}

	}

	@Override
	public Flashcard_struct[] getUpdated() {
		
		return cardsToUse;
	}

}
