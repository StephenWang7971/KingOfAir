package com.octrois.koa;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		Game game = Game.getInstance();
		game.start(this.getResources());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		Game game = Game.getInstance();
		if (game.isPlaying()) {
			if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
					|| keyCode == KeyEvent.KEYCODE_A) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.WEST));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
					|| keyCode == KeyEvent.KEYCODE_D) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.EAST));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP
					|| keyCode == KeyEvent.KEYCODE_W) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.NORTH));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
					|| keyCode == KeyEvent.KEYCODE_S) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.SOUTH));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_Q) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.NORTH_WEST));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_E) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.NORTH_EAST));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_C) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.SOUTH_EAST));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_Z) {
				game.sendEvent(new GameEvent(GameEvent.KEEP_MOVING,
						Direction.SOUTH_WEST));
				return true;
			}
		}

		return super.onKeyDown(keyCode, keyEvent);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
		Game game = Game.getInstance();
		if (game.isPlaying()) {
			if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
					|| keyCode == KeyEvent.KEYCODE_A) {
				game.sendEvent(new GameEvent(GameEvent.STOP_MOVING));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
					|| keyCode == KeyEvent.KEYCODE_D) {
				game.sendEvent(new GameEvent(GameEvent.STOP_MOVING));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP
					|| keyCode == KeyEvent.KEYCODE_W) {
				game.sendEvent(new GameEvent(GameEvent.STOP_MOVING));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
					|| keyCode == KeyEvent.KEYCODE_S) {
				game.sendEvent(new GameEvent(GameEvent.STOP_MOVING));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_Q) {
				game.sendEvent(new GameEvent(GameEvent.STOP_MOVING));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_E) {
				game.sendEvent(new GameEvent(GameEvent.STOP_MOVING));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_Z) {
				game.sendEvent(new GameEvent(GameEvent.STOP_MOVING));
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_C) {
				game.sendEvent(new GameEvent(GameEvent.STOP_MOVING));
				return true;
			}
		}
		return super.onKeyUp(keyCode, keyEvent);
	}

	@Override
	public void onPause() {
		Game game = Game.getInstance();
		game.pause();
		game.hide();
		game.pauseMusic();
		super.onPause();
	}

	@Override
	public void onResume() {
		Game game = Game.getInstance();
		game.show();
		game.resume();
		if (game.musicOn) {
			game.playMusic();
		}
		super.onResume();
	}

}
