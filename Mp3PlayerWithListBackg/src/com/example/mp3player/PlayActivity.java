package com.example.mp3player;

import java.io.IOException;
import com.google.android.gms.ads.*;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class PlayActivity extends Activity {
	

	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		startActivity(i);
		if(!mp.isPlaying()&&!mp2.isPlaying())
		{
			finish();
		}
	}


	private Button PlayPause, PlayPause2, Pause1, Pause2 ,PList,Plist2,Switch;
	MediaPlayer mp = new MediaPlayer();
	MediaPlayer mp2= new MediaPlayer();	
	private int flag = 0;
	private String path_l;
	private String path_r, tempPath;
	boolean p1, p2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		
		
		// Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
		
		
		
		
		
		PlayPause =(Button)findViewById(R.id.PlayBtn);
		PlayPause2 =(Button)findViewById(R.id.PlayBtn1);
		PList =(Button)findViewById(R.id.PlayList1);
		Plist2 =(Button)findViewById(R.id.PlayList2);
		Pause1 =(Button)findViewById(R.id.pause1);
		Pause2 =(Button)findViewById(R.id.pause2);
		
		Switch = (Button)findViewById(R.id.Switch);
		
		PhoneStateListener phoneStateListener = new PhoneStateListener() {
		    @Override
		    public void onCallStateChanged(int state, String incomingNumber) {
		        if (state == TelephonyManager.CALL_STATE_RINGING) {
		            //Incoming call: Pause music
		        	mp.pause();
		        	mp2.pause();
		        } else if(state == TelephonyManager.CALL_STATE_IDLE) {
		            //Not in call: Play music
		        	mp.start();
		        	mp2.start();
		        } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
		            //A call is dialing, active or on hold
		        	mp.pause();
		        	mp2.pause();
		        }
		        super.onCallStateChanged(state, incomingNumber);
		    }
		};
		TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		if(mgr != null) {
		    mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		}
		PlayPause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mp.start();
			}
		});
		PlayPause2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mp2.start();
			}
		});
		PList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flag = 1;
				Intent PlayListIntent = new Intent(PlayActivity.this, MainActivity.class);
				startActivityForResult(PlayListIntent, 1);
			 }
		});
		Plist2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flag = 2;
				Intent PlayListIntent = new Intent(PlayActivity.this, MainActivity.class);
				startActivityForResult(PlayListIntent, 1);
			}
		});	
Pause1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mp.isPlaying())
				{
					mp.pause();
				}
			}
		});
Pause2.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		if(mp2.isPlaying())
		{
			mp2.pause();
		}
	}
});
			Switch.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					swtch();
				}
			});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String newString = data.getStringExtra("LeftSong");
		if(flag == 1)
		{
			path_l = data.getStringExtra("LeftSong");
			if(newString != null)
			{
			play_left(path_l);
			}
		}
			else if(flag == 2)
			{
				path_r = data.getStringExtra("LeftSong");
				if(newString != null)
				{
					play_right(path_r);
				}
			}
			else
				finish();
		}
		
	public void swtch()
	{
		if(mp.isPlaying()||mp2.isPlaying())
		{
			tempPath=path_l;
			path_l=path_r;
			path_r=tempPath;
			if(path_l!=null)
				play_left(path_l);
			else
				mp.stop();
			if(path_r!=null)
				play_right(path_r);
			else
				mp2.stop();
		}
	}
	
	public void play_left(String l)
	{
		try 
		{
			mp.reset();
			mp.setDataSource(l);
			mp.setVolume(0.0f, 1.0f);
			mp.prepare();
			mp.start();
		}catch(IOException e)
		{
			
		}
	}
	public void play_right(String r)
	{
		try 
		{
			mp2.reset();
			mp2.setDataSource(r);
			mp2.setVolume(1.0f, 0.0f);
			mp2.prepare();
			mp2.start();
		}catch(IOException e)
		{
			
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}
}