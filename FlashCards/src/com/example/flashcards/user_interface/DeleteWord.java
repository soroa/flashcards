package com.example.flashcards.user_interface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.database.Flashcard_struct;
import com.example.flashcards.database.SerializableDeck;



public class DeleteWord extends Activity {
	private Flashcard_struct[] deck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_word);
		Intent i = getIntent();
		Bundle b = i.getExtras();
		 SerializableDeck d =(SerializableDeck)b.getSerializable("deck");
		deck = d.extractDeck();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_word, menu);
		return true;
	}
	
	public void delete(View v){
		EditText etFront= (EditText)findViewById(R.id.DeleteCardFront);
		EditText etBack = (EditText)findViewById(R.id.DeleteCardBack);
		String Front = etFront.getText().toString();
		String Back = etBack.getText().toString();
		if ((Back == "") && (Front == "")) {
			Toast.makeText(this, "You need to fill in at least one field", Toast.LENGTH_SHORT).show();
			return;
		}
		else{
			Intent returnIntent = getIntent();
			returnIntent.putExtra("Front", Front);
			returnIntent.putExtra("Back", Back);
			setResult(RESULT_OK, returnIntent);
			finish();
			
		
	}
	

	}
}
