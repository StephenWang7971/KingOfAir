package com.octrois.koa.model.role.friend;

import android.graphics.Canvas;

import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.role.Role;
import com.octrois.koa.model.weapon.friend.FriendWeapon;

public abstract class Friend extends Role {

	public Friend() {
		weapon = new FriendWeapon();
		weapon.setOwner(this);
	}

	@Override
	public void move(Direction dir) {

	}

	@Override
	public void render(Canvas canvas) {

	}

	public void fire() {
		long tick = System.currentTimeMillis();
		if (tick - lastFire > (2000 - weaponSpeed)) {
			weapon.fire();
			lastFire = tick;
		}
	}

}
