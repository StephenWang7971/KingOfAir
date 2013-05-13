package com.octrois.koa.model.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public class MenuPanel implements Game.State {
	private RectF homeRect = new RectF(150, 380, 350, 430);
	private RectF closeRect = new RectF(150, 460, 350, 510);
	private RectF musicRect = new RectF(150, 300, 190, 340);
	private RectF soundRect = new RectF(240, 300, 280, 340);

	@Override
	public void update() {
		// musicRect
		// soundRect
		// homeRect
		// closeRect
	}

	@Override
	public void render(Canvas canvas) {
		StateFlyweight.PLAYING.render(canvas);

		Game game = Game.getInstance();

		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		Bitmap menuBackground = bf.getBitmap("menu_background");
		canvas.drawBitmap(menuBackground, 100, 100, null);

		Bitmap close = bf.getBitmap("close_button");
		Bitmap home = bf.getBitmap("home_button");

		canvas.drawBitmap(home, homeRect.left, homeRect.top, null);
		canvas.drawBitmap(close, closeRect.left, closeRect.top, null);

		if (game.musicOn) {
			Bitmap musicOn = bf.getBitmap("music_on");
			canvas.drawBitmap(musicOn, musicRect.left, musicRect.top, null);
		} else {
			Bitmap musicOff = bf.getBitmap("music_off");
			canvas.drawBitmap(musicOff, musicRect.left, musicRect.top, null);
		}

		if (game.soundOn) {
			Bitmap soundOn = bf.getBitmap("sound_on");
			canvas.drawBitmap(soundOn, soundRect.left, soundRect.top, null);
		} else {
			Bitmap soundOff = bf.getBitmap("sound_off");
			canvas.drawBitmap(soundOff, soundRect.left, soundRect.top, null);
		}

	}

	@Override
	public void onTouch(MotionEvent event) {
		Game game = Game.getInstance();
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (MathUtil.inside(event.getX(), event.getY(), closeRect)) {
				game.resume();
			}
			if (MathUtil.inside(event.getX(), event.getY(), homeRect)) {
				game.sendEvent(new GameEvent(GameEvent.GO_HOME));
			}
			if (MathUtil.inside(event.getX(), event.getY(), musicRect)) {
				game.muteMusic();
			}
			if (MathUtil.inside(event.getX(), event.getY(), soundRect)) {
				game.muteSound();
			}
		}
	}

	@Override
	public void worldEvent() {

	}

	@Override
	public void onGameEvent(GameEvent event) {
		Game game = Game.getInstance();
		switch (event.eventCode) {
		case GameEvent.GO_HOME:
			game.clear();
			game.restart();
			break;
		}
	}

	@Override
	public void fadeOut() {

	}

	@Override
	public void fadeIn() {

	}
}
