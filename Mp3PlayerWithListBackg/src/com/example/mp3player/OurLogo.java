package com.example.mp3player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OurLogo extends Activity {
	
	private Button tap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_our_logo);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(1500);
					
				} catch (InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openStartingPoint = new Intent("com.example.mp3player.PLAYACTIVITY");
					startActivity(openStartingPoint);
					finish();
				}
			}	
		};
		timer.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.our_logo, menu);
		return true;
	}
}
