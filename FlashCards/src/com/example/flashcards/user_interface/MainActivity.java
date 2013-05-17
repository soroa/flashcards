package com.example.flashcards.user_interface;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.database.Bibliothek_IO;
import com.example.flashcards.database.Bibliothek_IO_Factory;


public class MainActivity extends Activity {
	// Button deckButton ;
	private Bibliothek_IO lib;
	private String[] decks;
	private final int DECK_IMPORT_REQUEST=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//the library is created in different activity, always make sure that the context is the Application Context
		lib = Bibliothek_IO_Factory.create(getApplicationContext());
		decks = lib.get_existing_librarys();
		setDecksListView(decks);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	public void setDecksListView(String[] decks) {
		if (decks.length==0){
			Toast.makeText(getApplicationContext(), "No Decks Available", Toast.LENGTH_LONG).show();
			return; 
		}
		final ListView listview = (ListView) findViewById(R.id.decklist);
		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < decks.length; i++) {
			list.add(decks[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				//item is a String of the name of the selected deck
				final String item = (String) parent.getItemAtPosition(position);
				//is getApplicationContext() right??
				Intent i = new Intent(MainActivity.this, com.example.flashcards.user_interface.Deck.class);
				
				// deck of type Flashcard struct is automatically converted to serializable 
				i.putExtra("deck", item);
				startActivity(i);
				
			}

		});
	}
	
	public void createNewDeck(View view){
		
		Intent i = new Intent(MainActivity.this, NewDeck.class);
		startActivity(i);
		
		
	}
	
	public void importDeck(View v){
		String[] new_libs = lib.lookUpForNewLibrarys();
		Intent i = new Intent(MainActivity.this, LibrariesImport.class);
		Bundle b = new Bundle();
		b.putStringArray("libs", new_libs);
		i.putExtras(b);
		startActivityForResult(i,this.DECK_IMPORT_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==DECK_IMPORT_REQUEST){
			if(resultCode==RESULT_OK){
				
			Bundle b = data.getExtras();
			
			String libSelected = b.getString("library");
			try{
			lib.create_library(libSelected, libSelected);
			}
			catch(Exception e){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(libSelected);
				builder.setMessage(e.getLocalizedMessage());
				builder.setPositiveButton("OK", null);
				AlertDialog dialog = builder.show();
			}
			this.setDecksListView(decks);
			
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	}
}
