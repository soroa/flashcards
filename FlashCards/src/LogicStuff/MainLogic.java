package LogicStuff;

import java.util.Random;

import flashcards.Flashcard_struct;
import flashcards.Logic;

public class MainLogic implements Logic {

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
		if(laenge==0){return new Flashcard_struct("Error, you did not use \"loadCards()\"!", "Error, you did not use \"loadCards()\"!");}
		int up;
		int count=0;
		do{
			Random temp = new Random();
			aktuell=temp.nextInt(laenge);
			up=temp.nextInt(laenge)-laenge/2;
		}while(cardsToUse[aktuell].mistakes>up && count<20);
		return cardsToUse[aktuell];
	}

	@Override
	public void correctAnswer(boolean answer) {
		if(answer){
			cardsToUse[aktuell].mistakes++;
		}else{
			cardsToUse[aktuell].mistakes--;
		}

	}

	@Override
	public Flashcard_struct[] getUpdated() {
		
		return cardsToUse;
	}

}
