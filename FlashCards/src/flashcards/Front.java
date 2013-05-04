package flashcards;

import com.example.flashcards.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.widget.TextView;

public class Front extends Activity  {

	
	//0: right, 1:up, 2:left, 3: down
	private short swipe_h;
	private short swipe_v;

		
	//hallo test
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front);
		
	
	}
	
	@Override
	public boolean onTouchEvent (MotionEvent event){
		
		
		return gd.onTouchEvent(event);
	}
	
	SimpleOnGestureListener simpleonGestureListener = new SimpleOnGestureListener(){
		@Override
		  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		    float velocityY) {
		   
		   float sensitvity = 50;
		   
		   // TODO Auto-generated method stub
		   //right to left
		   if((e1.getX() - e2.getX()) > sensitvity){
		   
		    showBack();
		    swipe_h = 0;//left
		   }else if((e2.getX() - e1.getX()) > sensitvity){
		    
		    swipe_h = 1;//right
		    
		   }else{
		    swipe_h = -1;
		   }
		   
		   if((e1.getY() - e2.getY()) > sensitvity){
		    
		    swipe_v= 0;//down
		   }else if((e2.getY() - e1.getY()) > sensitvity){
		    swipe_v = 1;//up
		   }else{
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
	//external function cuz I can't use the intent constructor with this from within the listener
	public void showBack(){
		Intent i = new Intent(this, Back.class);
		startActivity(i);
		
	}

}
