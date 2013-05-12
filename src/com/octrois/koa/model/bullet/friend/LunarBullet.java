package com.octrois.koa.model.bullet.friend;

import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.role.Role;

public class LunarBullet extends Bullet {

	public LunarBullet(Role role) {
		width = 20;
		height = 10;
		state = NORMAL;
		x = role.x + (role.width - width) / 2;
		y = role.y - height;
		dir = new Direction(0, -10);
		friend = true;
		power = role.power;
		picKey = "lunar";
	}
}
