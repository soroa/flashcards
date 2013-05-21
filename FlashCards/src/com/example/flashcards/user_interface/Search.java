package com.example.flashcards.user_interface;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.database.Flashcard_struct;
import com.example.flashcards.database.SerializableDeck;
import com.example.flashcards.logic.MainLogic;

public class Search extends Activity {
	private Flashcard_struct[] deck;
	private static final int DIALOG_ALERT = 10;
	private boolean found;
	private String Front, Back, Front_match, Back_match;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Intent i = getIntent();
		Bundle b = i.getExtras();
	SerializableDeck d = (SerializableDeck) b.getSerializable("deck");
	deck = d.extractDeck();
	for (int j =0; j<deck.length;j++){
		Log.v("Search", deck[j].question);
	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	public void search(View v){
    	EditText etFront= (EditText)findViewById( R.id.SearchCardFront);
		EditText etBack = (EditText)findViewById(R.id.SearchCardBack);
	Front = etFront.getText().toString();
		Back = etBack.getText().toString();
		if ((Back.equals("")) && (Front.equals( ""))) {
			Toast.makeText(this, "You need to fill in at least one field", Toast.LENGTH_SHORT).show();
			return;
		}
		else{
			for (int i=0;i<deck.length; i++)
			{
				Log.v("Search", "input: "+Front + " match: " + deck[i].question);
				if ((deck[i].question.equals(Front))||(deck[i].solution.equals(Back))){
					found = true;
					Front_match = deck[i].question;
					Back_match = deck[i].solution;
					break;	
				}
				else{
					
					found = false;
				}
			}
			showDialog(DIALOG_ALERT);
		}
    }

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ALERT:
			// Create out AlterDialog
			Builder builder = new AlertDialog.Builder(this);
			if(!found){
				builder.setMessage("Card not in deck");
			}
			else {
				builder.setMessage("Card Found! -  Front=".concat(Front_match)
						.concat("  Back = " + Back_match));
			}
		
			builder.setCancelable(true);
			builder.setPositiveButton("Done", new OkOnClickListener());
			// builder.setNegativeButton("No, no", new CancelOnClickListener());
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		return super.onCreateDialog(id);
	}


	private final class OkOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			//Search.this.finish();
			dialog.dismiss();
			dialog.dismiss();
		}
	}

}
