package Test;

import java.io.Serializable;
import java.util.Random;

import com.example.flashcards.database.Flashcard_struct;



public class MainLogic implements Logic, Serializable {

private Flashcard_struct[] cardsToUse;
private int laenge=0;
private int aktuell;
private int maxwert=-1;
public MainLogic(){

}

@Override
public void loadCards(Flashcard_struct[] card) {
cardsToUse=card;
laenge=cardsToUse.length;
maxwert=1;
for (int i =0; i<card.length();i++){
	if(maxwert< card[i].mistakes){
		maxwert=card[i].mistakes;
	}
}
}

@Override
public Flashcard_struct nextCard() {
if(laenge==0 || maxwert==-1){return null;}
int up;
int count=0;
do{
Random temp = new Random();
aktuell=temp.nextInt(laenge);
up=temp.nextInt(maxwert+maxwert/2)-maxwert/2;
count++;
}while(cardsToUse[aktuell].mistakes>up && count<20);
return cardsToUse[aktuell];
}

//changed, it was the opposite
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
