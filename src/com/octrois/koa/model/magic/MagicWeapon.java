package com.octrois.koa.model.magic;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.util.BitmapFlyweight;
import com.octrois.koa.util.MathUtil;

public class MagicWeapon extends Bullet {

	public void render(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		Bitmap bmp = bf.getBitmap(picKey);
		canvas.drawBitmap(bmp, x, getTop(), null);
	}

	public void move() {
		Game game = Game.getInstance();
		x += dir.getDiffX();
		y += dir.getDiffY();

		detectAttack();

		if (!MathUtil.isConflicted(getRect(), game.getBackgroundRect())) {
			game.sendEvent(new GameEvent(GameEvent.REMOVE_SUPER_MISILE, this));
		}
	}

	protected void detectAttack() {
		Game game = Game.getInstance();
		super.detectAttack();
		for (Bullet bullet : game.bullets) {
			if (MathUtil.isConflicted(getRect(), bullet.getRect())) {
				if (!bullet.friend) {
					game.sendEvent(new GameEvent(GameEvent.REMOVE_BULLET,
							bullet));
				}
			}
		}

	}
}
