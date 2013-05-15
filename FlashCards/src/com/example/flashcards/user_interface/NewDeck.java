package com.example.flashcards.user_interface;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flashcards.R;

public class NewDeck extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_deck);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_deck, menu);
		return true;
	}
	
	public void addWord(View v){
		EditText etf = (EditText) findViewById(R.id.NewDeckFront);
		EditText etb = (EditText) findViewById(R.id.NewDeckBack);
		String front = etf.getText().toString();
		String back = etb.getText().toString();
		if ((back == "") || (front == "")) {
			Toast.makeText(this, "Field missing", Toast.LENGTH_SHORT).show();
			return;
		}
		else{
			
		}
		
	}
	

}
