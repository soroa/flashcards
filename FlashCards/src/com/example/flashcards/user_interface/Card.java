package com.example.flashcards.user_interface;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.database.Flashcard_struct;
import com.example.flashcards.logic.MainLogic;


public class Card extends Activity {

	// slide up : Current card marked as guessed right and next card is shown.
	// slide down : Current Card is marked as wrong and next card is shown

	private short swipe_h;
	private short swipe_v;
	private MainLogic myLogic;
	// What side we are currently seeing: 1= front, -1 = back
	private int side;
	private TextView view;
	private Flashcard_struct currentCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		Intent i = getIntent();
		Bundle b = i.getExtras();
		myLogic = (MainLogic) b.getSerializable("myLogic");
		currentCard = myLogic.nextCard();
		view = new TextView(this);
		view = (TextView) findViewById((R.id.Card_text));
		
		view.setText(currentCard.question);
		view.setTextSize(30);
		side = 1;

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return gd.onTouchEvent(event);
	}

	SimpleOnGestureListener simpleonGestureListener = new SimpleOnGestureListener() {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			float sensitvity = 50;

			// TODO Auto-generated method stub
			// right to left
			if ((e1.getX() - e2.getX()) > sensitvity) {
				
				showBack();
				swipe_h = 0;// left
			} else if ((e2.getX() - e1.getX()) > sensitvity) {

				swipe_h = 1;// right
				showFront();
			} else {
				swipe_h = -1;
			}

			if ((e1.getY() - e2.getY()) > sensitvity) {

				swipe_v = 0;// down: guess wrong
				myLogic.correctAnswer(false);
				currentCard = myLogic.nextCard();
				nextCard();
				

			} else if ((e2.getY() - e1.getY()) > sensitvity) {
				swipe_v = 1;// up:guessed right
				myLogic.correctAnswer(true);
				currentCard = myLogic.nextCard();
				nextCard();
			} else {
				swipe_v += -1;
			}

			return super.onFling(e1, e2, velocityX, velocityY);
		}
	};

	GestureDetector gd = new GestureDetector(simpleonGestureListener);

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.front, menu);
		return true;
	}

	// external function cuz I can't use the intent constructor with this from
	// within the listener
	public void showBack() {
		/*
		 * Intent i = new Intent(this, Back.class); //finishActivity(); //so
		 * that when the user presses back we go straight back to the deck
		 * startActivity(i);
		 */
		if(side==-1) return;
		view.setText(currentCard.solution);
		view.setTextSize(30);
		side= -1;
	}
	
	public void showFront(){
		if(side==1) return;
		view.setText(currentCard.question);
		view.setTextSize(30);
		side=1;
	}

	/*
	 * Calls the next card
	 */
	public void nextCard() {
	/*if(next==null) {
			Toast.makeText(this,"You've reached ", Toast.LENGTH_LONG).show();
			
			finish();
		}*/
		side = 1; 
		view.setText(currentCard.question);
		view.setTextSize(30);


	}

}
