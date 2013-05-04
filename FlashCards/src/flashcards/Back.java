package flashcards;

import com.example.flashcards.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Back extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_back);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.back, menu);
		return true;
	}

}
