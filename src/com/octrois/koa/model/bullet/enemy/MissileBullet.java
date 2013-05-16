package com.octrois.koa.model.bullet.enemy;

import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.role.Role;

public class MissileBullet extends Bullet {

	public MissileBullet(Role role) {
		width = 10;
		height = 40;
		x = role.x + (role.width - width) / 2;
		y = role.y;
		state = NORMAL;
		friend = false;
		power = role.power;
		picKey = "misile_bullet";
		route.add(200, 0, 15);
	}

}
