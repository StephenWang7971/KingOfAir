package com.octrois.koa.model.bullet.enemy;

import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.role.Role;

public class FireBallBullet extends Bullet {

	public FireBallBullet(Role role) {
		width = 16;
		height = 16;
		x = role.x + (role.width - width) / 2;
		y = role.y;
		state = NORMAL;
		friend = false;
		power = role.power;
		picKey = "fire_ball";
		route.add(200, 0, 5);
	}

}
