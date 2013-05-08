package Interface;

import java.io.Serializable;

import com.example.flashcards.R;

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
	 Flashcard_struct deck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);
		Intent i = getIntent();
		deck = (Flashcard_struct) i.getSerializableExtra("deck");
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deck, menu);
		return true;
	}
	
	public void addWord(){
		Intent intent = new Intent(this, AddWord.class);
		startActivity(intent);
		
	}

}
