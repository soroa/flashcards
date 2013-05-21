package com.example.flashcards.user_interface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.database.Bibliothek_IO;
import com.example.flashcards.database.Bibliothek_IO_Factory;
import com.example.flashcards.database.Flashcard_struct;
import com.example.flashcards.database.SerializableDeck;
import com.example.flashcards.logic.MainLogic;


public class Deck extends Activity {

	private static final int DELETE_CARD = 2;
	private Context context = this;
	private String deck_name;
	private Flashcard_struct[] deck;
	private Bibliothek_IO lib;
	private MainLogic myLogic;
	private static final int ADD_CARD= 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);
		Intent i = getIntent();
		deck_name = i.getStringExtra("deck");
		lib = Bibliothek_IO_Factory.create(getApplicationContext());
		deck = lib.getAllWords(deck_name);
		myLogic = new MainLogic();
		myLogic.loadCards(deck);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deck, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,Intent Data) {
		if (requestCode == ADD_CARD) {
			Log.v("Deck", "Result code "+Integer.toString(resultCode));

			if (resultCode == RESULT_OK) {
				Bundle data = Data.getExtras();
				String Front = data.getString("Front");
				String Back = data.getString("Back");
				Flashcard_struct newCard = new Flashcard_struct(Front, Back);
				Log.v("Deck", "new Card Created");
				
				if (lib.insert_new_Card(deck_name, newCard)){
					Toast.makeText(this, "New Card successfully added",
							Toast.LENGTH_LONG).show();		
					deck = lib.getAllWords(deck_name);
					myLogic.loadCards(deck);

				}
				else {
					Toast.makeText(this, "Something went wrong, try again",
							Toast.LENGTH_LONG).show();		
				}
				//Deck and Logic update
				deck = lib.getAllWords(deck_name);
				myLogic.loadCards(deck);
				
			}
		}
		if (requestCode==DELETE_CARD){
			if (resultCode==RESULT_OK){
				Bundle data = Data.getExtras();
				String Front = data.getString("Front");
				String Back = data.getString("Back");
				if(Front==""){
					lib.delete_Word_byBack(deck_name, Back);
				}
				else{
					lib.delete_Word_byFront(deck_name, Front);
					
				}
				//Deck and Logic update
				deck = lib.getAllWords(deck_name);
				myLogic.loadCards(deck);
			
			}
		}
	}
	
	public void addWord(View v) {

		Intent intent = new Intent(this, AddWord.class);
		startActivityForResult(intent, ADD_CARD);

	}

	public void study(View v) {
		Intent i = new Intent(this, Card.class);
		i.putExtra("myLogic", myLogic);
		startActivity(i);

	}

	public void search(View v) {
		Intent i = new Intent(this,Search.class);
		SerializableDeck d = new SerializableDeck(deck);
		i.putExtra("deck", d);
		startActivity(i);
		

	}

	public void deleteCard(View v) {
		Intent i = new Intent(this,DeleteWord.class);
		SerializableDeck d = new SerializableDeck(deck);
		i.putExtra("deck", d);
		startActivityForResult(i, DELETE_CARD);

	}
}
