package com.octrois.koa.model.role.boss;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.role.Role;
import com.octrois.koa.util.BitmapFlyweight;

public class Carrier extends Role {
	public String picKey;
	public boolean attackable;

	@Override
	public void move(Direction dir) {
		x += dir.getDiffX();
		y += dir.getDiffY();

		if (!isLiving() && !isExploded()) {
			life++;
			return;
		}

		if (isExploded()) {
			exploded();
		}
	}

	@Override
	public void render(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();

		if (!isLiving() && !isExploded()) {
			String key = "";
			switch (life) {
			case EXPLODING_1:
				key = "explosion_1";
				break;
			case EXPLODING_2:
				key = "explosion_2";
				break;
			case EXPLODING_3:
				key = "explosion_3";
				break;
			case EXPLODING_4:
				key = "explosion_4";
				break;
			}
			canvas.drawBitmap(bf.getBitmap(key), x, getTop(), null);
		} else if (isLiving()) {
			Bitmap image = bf.getBitmap(picKey);
			canvas.drawBitmap(image, x, y, null);
		} else if (isExploded()) {
			// TODO show a damaged picture.
		}
	}

	@Override
	public void fire() {
		if (isLiving()) {
			weapon.fire();
		}
	}

	public RectF getAttackableRect() {
		// TODO implement later.
		return getRect();
	}

	public void explode() {
		this.life = EXPLODING_1;
	}

	public void exploded() {
		Game game = Game.getInstance();
		game.sendEventDelayed(new GameEvent(GameEvent.REMOVE_BOSS, this));
	}
}
