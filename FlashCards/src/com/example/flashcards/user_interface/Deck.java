package com.example.flashcards.user_interface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.database.Bibliothek_IO;
import com.example.flashcards.database.Bibliothek_IO_Factory;
import com.example.flashcards.database.Flashcard_struct;
import com.example.flashcards.logic.MainLogic;


public class Deck extends Activity {

	Context context = this;
	String deck_name;
	Flashcard_struct[] deck;
	Bibliothek_IO lib;
	MainLogic myLogic;

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

	public void addWord(View v) {

		Intent intent = new Intent(this, AddWord.class);
		startActivityForResult(intent, 1);

	}

	private void onActivityforResult(int requestCode, int resultCode,
			Intent Data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Bundle data = Data.getExtras();
				String Front = data.getString("Front");
				String Back = data.getString("Back");
				Flashcard_struct newCard = new Flashcard_struct(Front, Back);
				lib.insert_new_Card(deck_name, newCard);
				Toast.makeText(this, "New Card successfully added",
						Toast.LENGTH_LONG).show();
			//TODO : Logic update				
			}
		}
		if (requestCode==2){
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
			//TODO : UPDATE
			
			}
		}
	}

	public void study(View v) {
		Intent i = new Intent(this, Card.class);
		i.putExtra("myLogic", myLogic);
		startActivity(i);

	}

	public void search(View v) {

	}

	public void deleteCard(View v) {
		Intent i = new Intent(this,DeleteWord.class);
		i.putExtra("deck", deck);
		startActivityForResult(i, 2);
		

	}
}
