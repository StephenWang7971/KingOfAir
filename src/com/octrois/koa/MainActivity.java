package com.octrois.koa;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.octrois.koa.model.Game;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		System.out.println("onCreate");
		setContentView(R.layout.activity_main);

		Game game = Game.getInstance();
		game.start(this.getResources());
	}

	@Override
	public void onPause() {
		Game game = Game.getInstance();
		System.out.println("onPause");
		game.pause();
		game.hide();
		game.pauseMusic();
		super.onPause();
	}

	@Override
	public void onResume() {
		Game game = Game.getInstance();
		System.out.println("onResume");
		game.show();
		game.resume();
		if (game.musicOn) {
			game.playMusic();
		}
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
		System.out.println("onStop");
	}

	@Override
	public void onRestart() {
		super.onRestart();
		System.out.println("onRestart");
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("onStart");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("onDestroyed");
	}

}
