package com.octrois.koa.model.state;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.util.MathUtil;

public class SettingPanel implements Game.State {
	private RectF backRect;

	@Override
	public void update() {

		Game game = Game.getInstance();
		backRect = new RectF(220, game.mCanvasHeight - 280,
				game.mCanvasWidth - 220, game.mCanvasHeight - 240);

	}

	@Override
	public void render(Canvas canvas) {
		StateFlyweight.MAIN_SCREEN.render(canvas);

		Game game = Game.getInstance();

		Paint paint = new Paint();
		paint.setARGB(255, 250, 200, 100);
		canvas.drawRect(new RectF(100, 100, game.mCanvasWidth - 100,
				game.mCanvasHeight - 200), paint);

		Paint button = new Paint();
		button.setStyle(Style.FILL);
		button.setARGB(255, 10, 220, 10);

		canvas.drawRect(backRect, button);
	}

	@Override
	public void onTouch(MotionEvent event) {
		Game game = Game.getInstance();
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (MathUtil.inside(event.getX(), event.getY(), backRect)) {
				game.restart();
			}
		}
	}

	@Override
	public void worldEvent() {

	}

	@Override
	public void onGameEvent(GameEvent event) {

	}

	@Override
	public void fadeOut() {

	}

	@Override
	public void fadeIn() {

	}
}
