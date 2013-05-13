package com.octrois.koa.model.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public class ScorePanel implements Game.State {

	private RectF homeRect;
	private RectF nextStageRect;
	private RectF retryRect;
	private RectF skillsRect;

	@Override
	public void update() {
		homeRect = new RectF(160, 400, 320, 430);
		nextStageRect = new RectF(160, 460, 320, 590);
		retryRect = new RectF(160, 520, 320, 550);
		skillsRect = new RectF(160, 580, 320, 610);
	}

	@Override
	public void render(Canvas canvas) {
		StateFlyweight.PLAYING.render(canvas);

		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		Bitmap menuBackground = bf.getBitmap("score_background");
		canvas.drawBitmap(menuBackground, 100, 100, null);

		Bitmap back = bf.getBitmap("close_button");
		// Bitmap nextStage = bf.getBitmap("next_stage_button");
		Bitmap retry = bf.getBitmap("retry_button");
		// Bitmap skills = bf.getBitmap("skills_button");

		canvas.drawBitmap(back, homeRect.left, homeRect.top, null);
		// canvas.drawBitmap(nextStage, nextStageRect.left, nextStageRect.top,
		// null);
		canvas.drawBitmap(retry, retryRect.left, retryRect.top, null);
		// canvas.drawBitmap(skills, skillsRect.left, skillsRect.top, null);

	}

	@Override
	public void onTouch(MotionEvent event) {
		Game game = Game.getInstance();
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (MathUtil.inside(event.getX(), event.getY(), homeRect)) {
				game.sendEvent(new GameEvent(GameEvent.GO_HOME));
			}

			if (MathUtil.inside(event.getX(), event.getY(), retryRect)) {
				game.sendEvent(new GameEvent(GameEvent.START_GAME));
			}

			if (MathUtil.inside(event.getX(), event.getY(), skillsRect)) {
				// game.showSkillPanel();
			}
			if (MathUtil.inside(event.getX(), event.getY(), nextStageRect)) {

				// XXX if failed, thi would be continue for one life continue.
			}
		}
	}

	@Override
	public void worldEvent() {
		System.out.println("score panel.world event");
	}

	@Override
	public void onGameEvent(GameEvent event) {
		Game game = Game.getInstance();
		switch (event.eventCode) {
		case GameEvent.GO_HOME:
			game.clear();
			game.restart();
			break;
		case GameEvent.START_GAME:
			game.clear();
			game.play();
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
