package flashcards;

import java.io.Serializable;

import android.content.res.Resources.NotFoundException;

 

public interface Bibliothek_IO extends Serializable{
	
	/**
	 * Returns all Names of existing librarys
	 * @return a String-Array. Every Element contains a libraryname 
	 */
	public String[] get_existing_librarys();
		
	
	
	/**
	 * creats a new library with the given name from the file
	 * @param name got from library lookUpForNewLibrarys() 
	 * @param the name that the library should be saved as.
	 * @return if the library was created, false means that probably the name is already used
	 */
	public boolean create_library(String originallibraryName, String newLibraryName) throws NotFoundException;
	
	
	/**
	 * looks up if there are unrecongized librarys
	 * @return Filenames in a String[] of the new Library
	 */
	public String[] lookUpForNewLibrarys();
	
	/**
	 * Delete a existing library 
	 * 
	 * @param name of the library to delete
	 * @return if library has been deleted
	 */
	public boolean delete_library(String library);
	
	/**
	 * Get all Words in a Flashcard_struct-Array
	 * @param name of the library to delete
	 * @return all Flashcards
	 */
	public Flashcard_struct[] getAllWords(String library);
	
	
	/**
	 * change a card of a library (Attention can cause problems if some cards have the same front)
	 * @param name of the library to delete
	 * @param The Frontside of the flashcard that should be changed
	 * @param The new corrected Flashcard
	 * @return if Flashcard is updated 
	 */
	public boolean change_Word_byFront(String library, String question, Flashcard_struct newcard);
	
	/**
	 * change a card of a library (Attention can cause problems if some cards have the same back)
	 * @param name of the library to delete
	 * @param The Backside of the flashcard that should be changed
	 * @param The new corrected Flashcard
	 * @return if Flashcard is updated 
	 */
	public boolean change_Word_byBack(String library, String solution, Flashcard_struct newcard);
	 
	/**
	 * delete a word of a library (Attention can cause problems if some cards have the same front)
	 * @param name of the library to delete
	 * @param The Frontside of the flashcard that should be deleted
	 * @return if Flashcard is deleted
	 */
	public boolean delete_Word_byFront(String library, String question);
	
	
	/**
	 * delete a word of a library (Attention can cause problems if some cards have the same back)
	 * @param name of the library to delete
	 * @param The Backside of the flashcard that should be deleted
	 * @return if Flashcard is deleted
	 */
	public boolean delete_Word_byBack(String library, String solution);
	
	
	
	/**
	 * insert a Word in a existing library
	 * @param name of the existing library
	 * @param Card to insert in the library as Flashcard_struct
	 * @return if library was found
	 */
	public boolean insert_new_Card(String library, Flashcard_struct cardToInsert);
	
	
}
