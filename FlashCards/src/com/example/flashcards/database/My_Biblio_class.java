package com.example.flashcards.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;



import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Path;
import android.os.Environment;
import android.provider.ContactsContract.Directory;

public class My_Biblio_class implements Bibliothek_IO{

	private Context main_context;

	public My_Biblio_class(Context c) {
		main_context = c;

	}

	
	private boolean addToLibraryList(String name){
		String[] oldLibs = load_library_names();
		
		/*if(oldLibs==null){return false;}
		List<String> temp = (List<String>) Arrays.asList(oldLibs);
		temp.add(name);*/
		String[] temp = new String[oldLibs.length+1];
		for (int i =0;i<oldLibs.length;i++){
			temp[i]=oldLibs[i];
		}
		temp[oldLibs.length]=name;
		String dir = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/flashcards/FlashcardsLibraryName.fclProtect");
		ExternFileIO.writeStdPath(dir, temp, "{|}");
		//ExternFileIO.writeStdPath(dir, temp.toArray(new String[temp.size()]), "{|}");
		return true;
	}
	
	private String[] load_library_names() {
		//Problem
		
		// filefilter to find the file with the saved librarynames
		FilenameFilter libraryNameFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith("FlashcardsLibraryName.fclProtect")) {
					return true;
				} else {
					return false;
				}
			}
		};
		File file[];
		
		try{
			File linkToData= new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/flashcards");
			file = linkToData.listFiles(libraryNameFilter);
		}catch (Exception e) {
			return new String[0];
		}
		//android.view.Display("\n reached \n");
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
				//show("","");
				return new String[0];
			}
		} else {
			//show("", "");
			String dir = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/flashcards/FlashcardsLibraryName.fclProtect");
			
			return ExternFileIO.readStdFile(dir);
			
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
		
		File firstfiles[] = Environment.getExternalStorageDirectory().listFiles(libraryFilter);
		File linkToData= new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/flashcards");
		File additionalfiles[]=linkToData.listFiles(libraryFilter);
		
		File file[]=new File[firstfiles.length+additionalfiles.length];
		System.arraycopy(firstfiles, 0, file, 0, firstfiles.length);
		System.arraycopy(additionalfiles, 0, file, firstfiles.length, additionalfiles.length);
		
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
		Flashcard_struct[] rueck;
		//try{
			rueck= temp.getAll();
		/*}catch (Exception e) {
			return null;
		}*/
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
				//woerter nicht leer bei create new Library 
				//show("ExternFileIO", "ExternFileIO gibt nichts zurück");
				//show(woerter[0], woerter[1]);
				SQL_IO_Class library = new SQL_IO_Class(main_context, newLibraryName);
				
				
				addToLibraryList(newLibraryName);
				
				library.openToWrite();
				
				for (int j = 0; j<woerter.length/2; j++){
					library.insert(woerter[2*j], woerter[2*j+1], (short) 0);
				}
				library.close();	
				//throw new NotFoundException("länge " + woerter[0]+woerter[1]+woerter[2]+woerter[3]+";");
				return libs[i].delete();
			}
		}
		throw new NotFoundException("Library can not be load. It doesn't exist.");
		//return false;
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
			rueck[i]=path.substring(path.lastIndexOf('/')+1);
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

	private void show(String title, String text){
		AlertDialog.Builder builder = new AlertDialog.Builder(null);
		builder.setTitle(title);
		builder.setMessage(text);
		builder.setPositiveButton("OK", null);
		AlertDialog dialog = builder.show();
	}
 

	@Override
	public void loadExampleLibrary() {
		// TODO Auto-generated method stub
		String newLibraryName="TestLibrary";
		SQL_IO_Class library = new SQL_IO_Class(main_context, newLibraryName);
		
		String[] existingLibs=load_library_names();
		for (int i=0; i<existingLibs.length; i++){
			if(existingLibs[i].equals(newLibraryName)){
				return;
			}
		}
		
		addToLibraryList(newLibraryName);
		
		library.openToWrite();
		String woerter[]=new String[]{"Apple", "Apfel","House", " Haus", "Andrea", "asozial", "Michael", "cool"};
		
		for (int j = 0; j<woerter.length/2; j++){
			library.insert(woerter[2*j], woerter[2*j+1], (short) 0);
		}
		library.close();	
		return;
	}

}
