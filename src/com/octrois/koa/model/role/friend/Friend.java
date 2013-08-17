package com.octrois.koa.model.role.friend;

import android.graphics.Canvas;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.role.Role;
import com.octrois.koa.model.weapon.friend.FriendWeapon;
import com.octrois.koa.util.BitmapFlyweight;

public abstract class Friend extends Role {

	public boolean forceMove = false;
	protected String picKey;
	private int dx;
	private int dy;

	public Friend() {
		weapon = new FriendWeapon();
		weapon.setOwner(this);
	}

	@Override
	public void move(Direction dir) {
		// x += dir.getDiffX() * 10;
		// y += dir.getDiffY() * 10;
	}

	@Override
	public void render(Canvas canvas) {
		BitmapFlyweight bf = BitmapFlyweight.getInstance();
		canvas.drawBitmap(bf.getBitmap(picKey), x, getTop(), null);
	}

	public void fire() {
		long tick = System.currentTimeMillis();
		if (tick - lastFire > (2000 - weaponSpeed)) {
			weapon.fire();
			lastFire = tick;
		}
	}

	public void keepAround(Hero hero, int dx, int dy) {
		x = hero.x + dx;
		y = hero.y + dy;
		this.dx = dx;
		this.dy = dy;
	}

	public void keepAround() {
		Game game = Game.getInstance();
		x = game.hero.x + dx;
		y = game.hero.y + dy;
	}

}
