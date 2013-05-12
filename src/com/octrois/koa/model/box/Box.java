package com.octrois.koa.model.box;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.coin.Coin;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public abstract class Box {
	public int x;
	public int y;
	public int height;
	public int width;
	protected String picKey;

	public Coin[] coins;

	public int state;
	public int speed = -19;

	public static final int NORMAL = 0;
	public static final int EXPLOSION_1 = 1;
	public static final int EXPLOSION_2 = 2;
	public static final int EXPLOSION_3 = 3;
	public static final int EXPLOSION_4 = 4;
	public static final int VANISHED = 5;

	public void explode() {
		state = VANISHED;
		Game game = Game.getInstance();
		game.clearBox(this);
	}

	public void move(Direction dir) {
		Game game = Game.getInstance();
		x += dir.getDiffX();
		y += dir.getDiffY();
		if (!MathUtil.isConflicted(getRect(), game.getBackgroundRect())) {
			game.sendEvent(new GameEvent(GameEvent.REMOVE_BOX, this));
		}
	}

	public void beforeExplode() {
		state = EXPLOSION_1;
	}

	public RectF getRect() {
		return new RectF(x, getTop(), x + width, getTop() + height);
	}

	public int getTop() {
		return y;
	}

	public void render(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		canvas.drawBitmap(bf.getBitmap(picKey), x, getTop(), null);
	}

}
