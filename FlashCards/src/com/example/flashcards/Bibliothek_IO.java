package com.example.flashcards;

 

public interface Bibliothek_IO {
	
	
	
	
	/**
	 * Delete a existing library 
	 * 
	 * @param name of the library to delete
	 * @return if library has been deleted
	 */
	public boolean delete_biblio(String library);
	
	/**
	 * Get all Words in a Flashcard_struct-Array
	 * @param name of the library to delete
	 * @return all Flashcards
	 */
	public Flashcard_struct[] getAllWords(String library);
	
	
	/**
	 * change a word of a library
	 * @param name of the library to delete
	 * @param The Frontside of the flashcard that should be changed
	 * @param The new corrected Flashcard
	 * @return if Flashcard is updated 
	 */
	public boolean change_Word_byFront(String library, String question, Flashcard_struct newcard);
	
	/**
	 * change a word of a library
	 * @param name of the library to delete
	 * @param The Backside of the flashcard that should be changed
	 * @param The new corrected Flashcard
	 * @return if Flashcard is updated 
	 */
	public boolean change_Word_byBack(String library, String solution, Flashcard_struct newcard);
	 
	/**
	 * delete a word of a library
	 * @param name of the library to delete
	 * @param The Frontside of the flashcard that should be deleted
	 * @return if Flashcard is deleted
	 */
	public boolean delete_Word_byFront(String library, String question);
	
	
	/**
	 * delete a word of a library
	 * @param name of the library to delete
	 * @param The Backside of the flashcard that should be deleted
	 * @return if Flashcard is deleted
	 */
	public boolean delete_Word_byBack(String library, String solution);
	
}
