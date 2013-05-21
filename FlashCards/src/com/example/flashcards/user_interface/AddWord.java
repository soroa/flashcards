package com.example.flashcards.user_interface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flashcards.R;

public class AddWord extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_word);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_word, menu);
		return true;
	}

	public void addCard(View v) {
		EditText etf = (EditText) findViewById(R.id.NewCardFront);
		EditText etb = (EditText) findViewById(R.id.NewCardBack);
		String front = etf.getText().toString();
		String back = etb.getText().toString();
		if ((back == "") || (front == "")) {
			Toast.makeText(this, "Field missing", Toast.LENGTH_SHORT).show();
			return;
		}
		else{
			Intent returnIntent = getIntent();
			
			returnIntent.putExtra("Front", front);
			returnIntent.putExtra("Back", back);
			setResult(RESULT_OK, returnIntent);
			Log.v("AddCard", "about to finish activity");
			finish();
			
		}
	}

}
