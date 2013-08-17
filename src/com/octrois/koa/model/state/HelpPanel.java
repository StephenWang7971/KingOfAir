package com.octrois.koa.model.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public class HelpPanel implements Game.State {
	private RectF backRect;

	@Override
	public void update() {
		backRect = new RectF(150, 440, 350, 490);
	}

	@Override
	public void render(Canvas canvas) {
		StateFlyweight.MAIN_SCREEN.render(canvas);

		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		Bitmap helpBackground = bf.getBitmap("help_background");
		canvas.drawBitmap(helpBackground, 100, 100, null);
		Bitmap close = bf.getBitmap("close_button");

		canvas.drawBitmap(close, backRect.left, backRect.top, null);

		Paint text = new Paint();
		text.setARGB(255, 255, 255, 255);
		text.setTextSize(20);
		canvas.drawText("Control aircraft with", 120, 135, text);
		canvas.drawText("arrow steer.", 120, 150, text);
		canvas.drawText("Destroy enemies.", 120, 165, text);
		canvas.drawText("Destroy Boss.", 120, 180, text);
		canvas.drawText("Escape enemy's bullets!", 120, 195, text);
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
