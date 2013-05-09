package Interface;

import java.io.Serializable;

import com.example.flashcards.R;

import flashcards.Bibliothek_IO;
import flashcards.Bibliothek_IO_Factory;
import flashcards.Flashcard_struct;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class Deck extends Activity {
	
	 Context context = this;
	 String  deck_name;
	 Flashcard_struct[] deck;
	 Bibliothek_IO lib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);
		Intent i = getIntent();
		deck_name = i.getStringExtra("deck");
		lib = Bibliothek_IO_Factory.create(getApplicationContext());
		deck =  lib.getAllWords(deck_name);
				
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deck, menu);
		return true;
	}
	
	public void addWord(){
		
		Intent intent = new Intent(this, AddWord.class);
		//startActivityforResult(1,intent);
		
		
	}
	
	private void startActivityforResult(int requestCode, int resultCode, Intent Data) {
		 
		 
	}


	public void study(){
		Intent i = new Intent(this, Front.class);
		//.... finish
		
	}

	public void search(){
		
	}
	
	public void deleteWord(){
		
	}
}
