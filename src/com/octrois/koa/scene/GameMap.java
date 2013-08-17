package com.octrois.koa.scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public class GameMap {
	public int x, y;
	public int speed;
	public int width;
	public int height;
	public String picKey;

	public void render(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		Bitmap bmp = bf.getBitmap(picKey);
		canvas.drawBitmap(bmp, x, getTop(), null);
	}

	protected float getTop() {
		Game game = Game.getInstance();
		return y - game.scene.top;
	}

	public void move(Direction dir) {
		Game game = Game.getInstance();
		x += dir.getDiffX() * speed;
		y += dir.getDiffY() * speed;

		if (!MathUtil.isConflicted(getRect(), game.getBackgroundRect())) {
			// TODO remove it.
		}
	}

	public RectF getRect() {
		return new RectF(x, y, x + width, y + height);
	}
}
