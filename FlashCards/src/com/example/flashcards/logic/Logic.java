package com.example.flashcards.logic;

import com.example.flashcards.database.Flashcard_struct;



public interface Logic {
	/**
	 * Loads all the 
	 * @param card
	 */
	public void loadCards(Flashcard_struct[] card);
	/**
	 * gets the next to show based on number of mistakes i.e. the card with the 
	 * biggest amount of mistakes is picekd first
	 * @return Flashcard_stuct
	 */
	public Flashcard_struct nextCard();
	
	/**
	 * gets Feedback about whether the user got the answer right or wrong in oder
	 * eventually update the deck
	 * @param answer
	 * Doesn't need the card!
	 */
	public void correctAnswer( boolean answer);
	
	
	/**
	 * Get a updated Version of the cards (mistakes)
	 * @return Flashcard_struct[] with the updated cards
	 */
	public Flashcard_struct[] getUpdated();
	
	
	
	
}
