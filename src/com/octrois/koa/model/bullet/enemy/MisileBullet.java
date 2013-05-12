package com.octrois.koa.model.bullet.enemy;

import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.role.Role;

public class MisileBullet extends Bullet {

	public MisileBullet(Role role) {
		width = 10;
		height = 40;
		x = role.x + (role.width - width) / 2;
		y = role.y;
		dir = new Direction(0, 15);
		state = NORMAL;
		friend = false;
		power = role.power;
		picKey = "misile_bullet";
	}

}
