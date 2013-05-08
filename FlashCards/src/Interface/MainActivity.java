package Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.flashcards.R;

import flashcards.Bibliothek_IO;
import flashcards.Bibliothek_IO_Factory;
import flashcards.Flashcard_struct;

public class MainActivity extends Activity {
	// Button deckButton ;
	private Bibliothek_IO lib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lib = Bibliothek_IO_Factory.create(this);
		String[] decks = lib.get_existing_librarys();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void openDeck(View view) {
		Intent intent = new Intent(this, Front.class);
		startActivity(intent);

	}

	public void setDecksListView(String[] decks) {
		if (decks.length==0){
			Toast.makeText(getApplicationContext(), "No Decks Available", Toast.LENGTH_LONG);
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
				Intent i = new Intent(getApplicationContext(), Interface.Deck.class);
				Flashcard_struct[] deck = lib.getAllWords(item);
				// deck of type Flashcard struct is automatically converted to serializable 
				i.putExtra("deck", deck);
				startActivity(i);
				
			}

		});
	}

}
