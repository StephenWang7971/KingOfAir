package com.octrois.koa.model.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public class SkillsPanel implements Game.State {
	private RectF backgroundRect = new RectF(0, 0, 480, 800);
	private RectF playRect = new RectF(60, 650, 160, 700);
	private RectF closeRect = new RectF(260, 650, 460, 700);

	private RectF powerRect = new RectF(60, 420, 120, 450);
	private RectF speedRect = new RectF(60, 470, 120, 500);
	private RectF healthRect = new RectF(60, 520, 120, 550);

	private RectF goldRect = new RectF(60, 50, 160, 100);
	private RectF silverRect = new RectF(220, 50, 320, 100);

	private RectF powerBarRect = new RectF(140, 400, 320, 430);
	private RectF speedBarRect = new RectF(140, 450, 320, 480);
	private RectF healthBarRect = new RectF(140, 500, 320, 530);

	private RectF powerPriceRect = new RectF(360, 420, 400, 450);
	private RectF speedPriceRect = new RectF(360, 470, 400, 500);
	private RectF healthPriceRect = new RectF(360, 520, 400, 550);

	private RectF buyPowerRect = new RectF(440, 400, 480, 430);
	private RectF buySpeedRect = new RectF(440, 450, 480, 480);
	private RectF buyHealthRect = new RectF(440, 500, 480, 530);

	@Override
	public void update() {
		Game game = Game.getInstance();
		backgroundRect.right = game.mCanvasWidth;
		backgroundRect.bottom = game.mCanvasHeight;
	}

	@Override
	public void render(Canvas canvas) {
		Paint paint = new Paint();
		paint.setARGB(255, 255, 200, 0);
		canvas.drawRect(backgroundRect, paint);

		Paint valuePaint = new Paint();
		valuePaint.setARGB(255, 255, 0, 0);

		Paint vacancePaint = new Paint();
		vacancePaint.setStyle(Style.STROKE);
		vacancePaint.setARGB(255, 255, 0, 0);

		Paint textPaint = new Paint();
		textPaint.setTextSize(22);
		textPaint.setARGB(255, 0, 0, 128);

		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		Bitmap playButton = bf.getBitmap("start_button");
		Bitmap closeButton = bf.getBitmap("close_button");
		Bitmap goldCoin = bf.getBitmap("small_gold_coin");
		Bitmap silverCoin = bf.getBitmap("small_silver_coin");
		Bitmap buyButton = bf.getBitmap("small_gold_coin");

		canvas.drawBitmap(goldCoin, goldRect.left, goldRect.top, null);
		canvas.drawBitmap(silverCoin, silverRect.left, silverRect.top, null);
		canvas.drawBitmap(playButton, playRect.left, playRect.top, null);
		canvas.drawBitmap(closeButton, closeRect.left, closeRect.top, null);

		canvas.drawText("Attack", powerRect.left, powerRect.top, textPaint);
		canvas.drawText("Speed", speedRect.left, speedRect.top, textPaint);
		canvas.drawText("Life", healthRect.left, healthRect.top, textPaint);

		canvas.drawRect(powerBarRect, vacancePaint);
		canvas.drawRect(speedBarRect, vacancePaint);
		canvas.drawRect(healthBarRect, vacancePaint);

		// XXX count the value.
		canvas.drawRect(powerBarRect, valuePaint);
		canvas.drawRect(speedBarRect, valuePaint);
		canvas.drawRect(healthBarRect, valuePaint);

		// XXX need a price system.
		canvas.drawText("5", powerPriceRect.left, powerPriceRect.top, textPaint);
		canvas.drawText("30", speedPriceRect.left, speedPriceRect.top,
				textPaint);
		canvas.drawText("200", healthPriceRect.left, healthPriceRect.top,
				textPaint);

		canvas.drawBitmap(buyButton, buyPowerRect.left, buyPowerRect.top, null);
		canvas.drawBitmap(buyButton, buySpeedRect.left, buySpeedRect.top, null);
		canvas.drawBitmap(buyButton, buyHealthRect.left, buyHealthRect.top,
				null);
	}

	@Override
	public void onTouch(MotionEvent event) {

		Game game = Game.getInstance();
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (MathUtil.inside(event.getX(), event.getY(), closeRect)) {
				game.restart();
			} else if (MathUtil.inside(event.getX(), event.getY(), playRect)) {
				game.play();
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
