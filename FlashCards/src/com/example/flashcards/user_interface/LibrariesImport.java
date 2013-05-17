package com.example.flashcards.user_interface;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.flashcards.R;

public class LibrariesImport extends Activity {
	private String TAG = "DECK_IMPORT";
	private String[] libs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_libraries_import);
		Intent i = getIntent();
		Bundle b = i.getExtras();
		libs = b.getStringArray("libs");
		setDecksListView(libs);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.libraries_import, menu);
		

		return true;
	}

	public void setDecksListView(String[] libs) {
		if (libs.length == 0) {
			Toast.makeText(getApplicationContext(), "No Libraries Found",
					Toast.LENGTH_LONG).show();
			return;
		}
		final ListView listview = (ListView) findViewById(R.id.libslist);
		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < libs.length; i++) {
			list.add(libs[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				// item is a String of the name of the selected deck
				
				final String item = (String) parent.getItemAtPosition(position);
				Log.v(TAG,"SELECTED LIBRARY = "+item);
				Intent returnIntent = getIntent();
				returnIntent.putExtra("library", item);
				setResult(RESULT_OK, returnIntent);
				finish();
			}

		});
	}

}
