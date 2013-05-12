package com.octrois.koa.model.role.friend;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.role.Role;
import com.octrois.koa.model.weapon.friend.HeroWeapon;
import com.octrois.koa.util.BitmapFlyweight;

public class Hero extends Role {

	public Hero() {
		Game game = Game.getInstance();
		weapon = new HeroWeapon();
		weapon.setOwner(this);
		x = game.mCanvasWidth - 100;
		y = game.mCanvasHeight - 150;
		weaponSpeed = 1500;
		health = 3000;
		power = 30;
		width = 64;
		height = 64; // TODO According to screen size.
		maxHealth = 3000;
	}

	@Override
	public void move(Direction dir) {
		Game game = Game.getInstance();
		if (x >= game.mCanvasWidth - width + 20 && dir.getDiffX() > 0) {
			return;
		}
		if (x <= -20 && dir.getDiffX() < 0) {
			return;
		}
		if (y <= 0 && dir.getDiffY() < 0) {
			return;
		}
		if (y >= game.mCanvasHeight - height && dir.getDiffY() > 0) {
			return;
		}
		x += dir.getDiffX() * 10;
		y += dir.getDiffY() * 10;
	}

	@Override
	public void render(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		canvas.drawBitmap(bf.getBitmap("hero"), x, getTop(), null);

		if (isExploded()) {
		}
	}

	public void drawHealth(Canvas c) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		if (health < 0) {
			health = 0;
		}
		c.drawText(String.valueOf(health) + "/" + String.valueOf(maxHealth),
				100, 20, paint);
	}

	public void fire() {
		long tick = System.currentTimeMillis();
		if (tick - lastFire > (2000 - weaponSpeed)) {
			weapon.fire();
			lastFire = tick;
		}
	}

	public void explode() {
		life = EXPLODED;
	}

}
