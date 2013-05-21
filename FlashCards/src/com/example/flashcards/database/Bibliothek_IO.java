package com.example.flashcards.database;

import android.content.res.Resources.NotFoundException;

public interface Bibliothek_IO {

	/**
	 * Returns all Names of existing librarys
	 * 
	 * @return a String-Array. Every Element contains a libraryname
	 */
	public String[] get_existing_librarys();

	/**
	 * Creates a new library with the given name from the file
	 * 
	 * @param originallibraryName
	 *           name got from library lookUpForNewLibrarys()
	 * @param newLibraryName
	 *           the name that the library should be saved as.
	 * @return if the library was created, false means that probably the name is
	 *         already used (could also mean that original file could not be
	 *         deleted)
	 */
	public boolean create_library(String originallibraryName, String newLibraryName) throws NotFoundException;

	/**
	 * looks up if there are unrecognized librarys
	 * 
	 * @return Filenames in a String[] of the new Library
	 */
	public String[] lookUpForNewLibrarys();

	/**
	 * Delete a existing library
	 * 
	 * @param library name of the library to delete
	 * @return if library has been deleted
	 */
	public boolean delete_library(String library);

	/**
	 * Get all Words in a Flashcard_struct-Array
	 * 
	 * @param library name of the library to delete
	 * @return all Flashcards
	 */
	public Flashcard_struct[] getAllWords(String library);

	/**
	 * change a card of a library (Attention can cause problems if some cards
	 * have the same front)
	 * 
	 * @param library name of the library to delete
	 * @param question The Frontside of the flashcard that should be changed
	 * @param newcard The new corrected Flashcard
	 * @return if Flashcard is updated
	 */
	public boolean change_Word_byFront(String library, String question,
			Flashcard_struct newcard);

	/**
	 * change a card of a library (Attention can cause problems if some cards
	 * have the same back)
	 * 
	 * @param library name of the library to delete
	 * @param solution The Backside of the flashcard that should be changed
	 * @param newcard The new corrected Flashcard
	 * @return if Flashcard is updated
	 */
	public boolean change_Word_byBack(String library, String solution,
			Flashcard_struct newcard);

	/**
	 * delete a word of a library (Attention can cause problems if some cards
	 * have the same front)
	 * 
	 * @param library name of the library to delete
	 * @param question The Frontside of the flashcard that should be deleted
	 * @return if Flashcard is deleted
	 */
	public boolean delete_Word_byFront(String library, String question);

	/**
	 * delete a word of a library (Attention can cause problems if some cards
	 * have the same back)
	 * 
	 * @param library name of the library to delete
	 * @param solution The Backside of the flashcard that should be deleted
	 * @return if Flashcard is deleted
	 */
	public boolean delete_Word_byBack(String library, String solution);

	/**
	 * insert a Word in a existing library
	 * 
	 * @param name
	 *            of the existing library
	 * @param Card
	 *            to insert in the library as Flashcard_struct
	 * @return if library was found
	 */
	public boolean insert_new_Card(String library, Flashcard_struct cardToInsert);

	/**
	 * Use this only with the given output of Logic.update()
	 * 
	 * @param name
	 *            of the used library
	 * @param updatedCards
	 *            the output from Logic.update()
	 * @return if all cards where updated
	 */
	public boolean update(String library, Flashcard_struct[] updatedCards);

	/**
	 * Will be deleted later
	 */
	public void loadExampleLibrary();

	/**
	 * 
	 * @param libraryName the name of the new Library
	 * @param Card the card that the library should contain (has NOT to be equal null!)
	 * @return if the library was created, false means that probably the name is
	 *         already used
	 */
	public boolean create_library_from_one_word(String libraryName,
			Flashcard_struct Card);
}
