package flashcards;

public class Flashcard_struct  {
	public String question;
	public String solution;
	public short mistakes;
	
	public Flashcard_struct(String q, String s, short m){
		question=q;
		solution=s;
		mistakes=m;
	}
	
	public Flashcard_struct(String q, String s){
		question=q;
		solution=s;
		mistakes=0;
	}
}
