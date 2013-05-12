package com.octrois.koa.model.bullet.enemy;

import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.role.Role;

public class LaserBullet extends Bullet {

	public LaserBullet(Role role) {
		width = 10;
		height = 20;
		x = role.x + (role.width - width) / 2;
		y = role.y + role.height;
		dir = new Direction(0, 10);
		state = NORMAL;
		friend = false;
		power = role.power;
		picKey = "laser";
	}

}
