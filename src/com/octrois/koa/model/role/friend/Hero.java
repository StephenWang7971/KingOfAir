package com.octrois.koa.model.role.friend;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;
import com.octrois.koa.model.role.Role;
import com.octrois.koa.model.weapon.friend.HeroWeapon;
import com.octrois.koa.util.BitmapFlyweight;

public class Hero extends Role {

	public boolean forceMove = false;

	public Hero() {
		Game game = Game.getInstance();
		weapon = new HeroWeapon();
		weapon.setOwner(this);
		x = game.mCanvasWidth - 100;
		y = game.mCanvasHeight - 150;
		weaponSpeed = 1500;
		health = 100;
		power = 30;
		width = 64;
		height = 64; // TODO According to screen size.
		maxHealth = 100;
	}

	@Override
	public void move(Direction dir) {
		Game game = Game.getInstance();
		boolean xMove = true;
		boolean yMove = true;
		if (x >= game.mCanvasWidth - width + 20 && dir.getDiffX() > 0) {
			xMove = false;
		}
		if (x <= -20 && dir.getDiffX() < 0) {
			xMove = false;
		}
		if (y <= 0 && dir.getDiffY() < 0 && !forceMove) {
			yMove = false;
		}

		if (y <= -80 && forceMove) {
			yMove = false;
			game.sendEvent(new GameEvent(GameEvent.SHOW_SCORE_PANEL));
		}
		if (y >= game.mCanvasHeight - height && dir.getDiffY() > 0) {
			yMove = false;
		}
		if (xMove) {
			x += dir.getDiffX() * 10;
		}
		if (yMove) {
			y += dir.getDiffY() * 10;
		}
	}

	@Override
	public void render(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		canvas.drawBitmap(bf.getBitmap("hero"), x, getTop(), null);

		if (forceMove) {
			// TODO show a longer fire.
		}

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
		if (forceMove) {
			return;
		}
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
