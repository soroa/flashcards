
package com.example.flashcards.user_interface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.database.Flashcard_struct;


public class Search extends Activity {
private Flashcard_struct[] deck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent i = getIntent();
		Bundle b = i.getExtras();
		 
		deck =(Flashcard_struct[])b.getSerializable("deck");
    }

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
    
    public void search(){
    	EditText etFront= (EditText)findViewById( R.id.SearchCardFront);
		EditText etBack = (EditText)findViewById(R.id.SearchCardBack);
		String Front = etFront.getText().toString();
		String Back = etBack.getText().toString();
		if ((Back == "") && (Front == "")) {
			Toast.makeText(this, "You need to fill in at least one field", Toast.LENGTH_SHORT).show();
			return;
		}
		else{
			for (int i=0;i<deck.length; i++)
			{
				if (deck[i].question==Front||deck[i].solution==Back){
					//TODO 
					
				}
			}
		}
    }
    
}
