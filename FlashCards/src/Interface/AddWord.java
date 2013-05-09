package Interface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

	public void addCard() {
		EditText etf = (EditText) findViewById(R.id.NewCardFront);
		EditText etb = (EditText) findViewById(R.id.NewCardBack);
		String back = etf.getText().toString();
		String front = etb.getText().toString();
		if ((back == "") || (front == "")) {
			Toast.makeText(this, "Field missing", Toast.LENGTH_SHORT);
			return;
		}
		else{
			Intent returnIntent = new Intent();
			returnIntent.putExtra("Front", front);
			returnIntent.putExtra("Back", back);
			returnIntent.putExtra("result",1);
			setResult(RESULT_OK, returnIntent);
			
			
			
			
		}
	}

}
