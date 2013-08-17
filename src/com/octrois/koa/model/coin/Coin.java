package com.octrois.koa.model.coin;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public abstract class Coin {

	public int x, y;
	public int width, height;
	protected String picKey;

	public int volume;

	public int speed = -19;
	public int type;
	public static final int GOLD = 1;
	public static final int SILVER = 2;
	public static final int HEALTH = 3;
	public static final int B52 = 4;
	public static final int MISSILE = 5;

	public void move(Direction dir) {
		Game game = Game.getInstance();
		x += dir.getDiffX();
		y += dir.getDiffY();
		if (!MathUtil.isConflicted(getRect(), game.getBackgroundRect())) {
			game.sendEvent(new GameEvent(GameEvent.REMOVE_COIN, this));
		}
	}

	public void render(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		canvas.drawBitmap(bf.getBitmap(picKey), x, getTop(), null);
	}

	public RectF getRect() {
		return new RectF(x, getTop(), x + width, getTop() + height);
	}

	private float getTop() {
		return y;
	}

}
