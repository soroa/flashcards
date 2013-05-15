package com.example.flashcards.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;



import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Environment;

public class My_Biblio_class implements Bibliothek_IO{

	private Context main_context;

	public My_Biblio_class(Context c) {
		main_context = c;

	}

	
	private void addToLibraryList(String name){
		String[] oldLibs = load_library_names();
		List<String> temp = Arrays.asList(oldLibs);
		temp.add(name);
		String dir = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/flashcards/FlashcardsLibraryName.fclProtect");
		ExternFileIO.writeStdPath(dir, temp.toArray(new String[temp.size()]), "{|}");
	}
	
	private String[] load_library_names() {
		// filefilter to find the file with the saved librarynames
		FilenameFilter libraryNameFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.equals("FlashcardsLibraryName.fclProtect")) {
					return true;
				} else {
					return false;
				}
			}
		};

		File file[] = Environment.getExternalStorageDirectory().listFiles(
				libraryNameFilter);

		if (file.length == 0) {
			try {
				File sdCard = Environment.getExternalStorageDirectory();
				File dir = new File(sdCard.getAbsolutePath() + "/flashcards");
				if (!dir.exists())
					dir.mkdirs();

				File createfile = new File(dir + "/",
						"FlashcardsLibraryName.fclProtect");

				FileOutputStream fOut = new FileOutputStream(createfile);
				OutputStreamWriter osw = new OutputStreamWriter(fOut);
				osw.write("FileStyle: {|}");
				osw.flush();
				osw.close();
				return new String[0];
			} catch (Exception e) {
				return null;
			}
		} else {
			File sdCard = Environment.getExternalStorageDirectory();
			return ExternFileIO.readStdFile(sdCard.getAbsolutePath()
					+ "/FlashcardsLibraryName.fclProtect");
		}
	}

	private File[] find_new_Librarys() {
		// filefilter to find only needed files
		FilenameFilter libraryFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(".FlashcardLib") || name.endsWith(".fcl")) {
					return true;
				} else {
					return false;
				}
			}
		};
		
		File file[] = Environment.getExternalStorageDirectory().listFiles(libraryFilter);
		return file;
	}

	@Override
	public boolean delete_library(String bibliothek) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, bibliothek);
		temp.openToWrite();
		boolean rueck = (temp.deleteAll());
		temp.close();
		return rueck;
	}

	@Override
	public Flashcard_struct[] getAllWords(String library) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToRead();
		Flashcard_struct[] rueck = temp.getAll();
		temp.close();
		return rueck;
	}

	@Override
	public boolean change_Word_byFront(String library, String question,
			Flashcard_struct newcard) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToRead();
		Flashcard_struct cardToChange = temp.getCard_byFront(question);
		temp.close();
		if (cardToChange == null) {
			return false;
		}// if card doesn't exist

		temp.openToWrite();
		boolean rueck = temp.deleteFlashcard_struct(cardToChange);
		if (rueck) {
			temp.insert(newcard);
		}
		temp.close();
		return rueck;
	}

	@Override
	public boolean change_Word_byBack(String library, String solution,
			Flashcard_struct newcard) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToRead();
		Flashcard_struct cardToChange = temp.getCard_byBack(solution);
		temp.close();
		if (cardToChange == null) {
			return false;
		}// if card doesn't exist

		temp.openToWrite();
		boolean rueck = temp.deleteFlashcard_struct(cardToChange);
		if (rueck) {
			temp.insert(newcard);
		}
		temp.close();
		return rueck;
	}

	@Override
	public boolean delete_Word_byFront(String library, String question) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToRead();
		Flashcard_struct cardToDelete = temp.getCard_byFront(question);
		temp.close();
		if (cardToDelete == null) {
			return false;
		}
		temp.openToWrite();
		boolean rueck = temp.deleteFlashcard_struct(cardToDelete);
		temp.close();
		return rueck;
	}

	@Override
	public boolean delete_Word_byBack(String library, String solution) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToRead();
		Flashcard_struct cardToDelete = temp.getCard_byBack(solution);
		temp.close();
		if (cardToDelete == null) {
			return false;
		}
		temp.openToWrite();
		boolean rueck = temp.deleteFlashcard_struct(cardToDelete);
		temp.close();
		return rueck;
	}

	@Override
	public boolean insert_new_Card(String library, Flashcard_struct cardToInsert) {
		SQL_IO_Class temp = new SQL_IO_Class(main_context, library);
		temp.openToWrite();
		boolean rueck = (-1 != temp.insert(cardToInsert));
		temp.close();
		return rueck;
	}

	@Override
	public boolean create_library(String originallibraryName, String newLibraryName) throws NotFoundException {
		File libs[] =find_new_Librarys();
		if(libs==null){
			throw new NotFoundException("Library can not be load. It doesn't exist.");
		}
		String existingLibrarys[]=load_library_names();
		for (int i=0; i<existingLibrarys.length;i++){
			if(newLibraryName.equals(existingLibrarys[i])){
				return false;
			}
		}
		for (int i =0 ; i<libs.length; i++){
			if(libs[i].getAbsolutePath().endsWith(originallibraryName)){
				String[] woerter = ExternFileIO.readStdFile(libs[i].getAbsolutePath());
				
				SQL_IO_Class library = new SQL_IO_Class(main_context, newLibraryName);
				addToLibraryList(newLibraryName);
				library.openToWrite();
				for (int j = 0; j<woerter.length/2; j++){
					library.insert(woerter[2*j], woerter[2*j+1], (short) 0);
				}
				library.close();				
				return libs[i].delete();
			}
		}
		throw new NotFoundException("Library can not be load. It doesn't exist.");
	}

	/*private Flashcard_struct[] create_Flashcard_struct_array(String[] woerter){
		ArrayList<Flashcard_struct> rueck = new ArrayList<Flashcard_struct>();
		for (int j = 0; j<woerter.length/2; j++){
			rueck.add(new Flashcard_struct(woerter[2*j], woerter[2*j+1]));
		}
		return rueck.toArray(new Flashcard_struct[rueck.size()]);
	}*/
	
	@Override
	public String[] lookUpForNewLibrarys() {
		File libs[] =find_new_Librarys();
		String rueck[] = new String[libs.length];
		for( int i =0; i < libs.length;i++){
			String path = libs[i].getAbsolutePath();
			rueck[i]=path.substring(path.lastIndexOf('/'));
		}
		return rueck;
	}

	@Override
	public String[] get_existing_librarys() {
		return load_library_names();
	}


	@Override
	public boolean update(String library, Flashcard_struct[] updatedCards) {
		boolean rueck=true;
		for (int i=0; i<updatedCards.length; i++){
			rueck=rueck && change_Word_byFront(library,updatedCards[i].question, updatedCards[i]);
		}
		return rueck;
	}


}
